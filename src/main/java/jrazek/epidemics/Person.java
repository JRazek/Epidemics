package jrazek.epidemics;

import java.util.List;

public class Person {
    private Utilities.Vector2I pos;
    private Utilities.Vector2f velocity;
    private Sickness sickness;
    private boolean dead;
    private int age;//age in days
    private float healthPoints;
    private float maxHealthPoints;
    private float immunity;
    private boolean gender;//0 stands for woman 1 for man. Sry but cant add 64 genders
    Person(Utilities.Vector2I pos, int age, boolean gender, Person parent1, Person parent2){
        this.age = age;
        this.gender = gender;
        if(parent1 == null || parent2 == null) {
            if (age < 1) {
                age = 1;
            }
            this.pos = pos;
            immunity = 0;
            healthPoints = (float)((float)100000* Utilities.randomDouble(0.5, 2)*Main.map.getEnvironmentRestrictions().getYearLength()) / (float) age ;
        }else{
            healthPoints = (parent1.getHealthPoints() + parent2.getHealthPoints()) / 2;
            immunity = (parent1.getImmunity() + parent2.getImmunity()) / 2;
            this.pos = parent1.getPos();
            System.out.println("New child born");
            // creating from mom and dad
        }
        maxHealthPoints = healthPoints;
        velocity = new Utilities.Vector2f(0, 0);
    }

    //fight///
    void receiveDamage(float dmg){
        //   System.out.println(healthPoints);
        float mltp = (float)age/Main.map.getEnvironmentRestrictions().getYearLength();
        if(immunity != 0)
            mltp *= (1/immunity);

        healthPoints -= dmg*mltp;

        gainImmunity();

        if(healthPoints <= 0){
            this.kill();
            System.out.println("Choroba zbiera krwawe żniwo");
        }
    }
    void fightSickness(){
        if(sickness != null) {
            sickness.receiveDamage(immunity * (float) Utilities.randomDouble(0.5, 2));
            if (sickness.getHealthPoints() <= 0)
                sickness = null;
        }
    }
    void gainImmunity(){
        immunity += Main.map.getEnvironmentRestrictions().getImmunityGain();
    }
    //////////
    public int getAge(){
        return age;
    }
    public void move(){
        if(dead) {
            velocity = new Utilities.Vector2f(0, 0);
        }else {
            Utilities.Vector2I tmpPos = new Utilities.Vector2I((int) velocity.getX() + pos.getX(),
                    (int) velocity.getY() + pos.getY());
            Utilities.Vector2I mapSize = Main.map.getEnvironmentRestrictions().getMapSize();
            if (tmpPos.getX() > mapSize.getX() || tmpPos.getX() <= 0) {
                velocity = new Utilities.Vector2f(-velocity.getX(), velocity.getY());
            }
            if (tmpPos.getY() > mapSize.getY() || tmpPos.getY() <= 0) {
                velocity = new Utilities.Vector2f(velocity.getX(), -velocity.getY());
            }

            Utilities.Vector2I finalPos = new Utilities.Vector2I((int) velocity.getX() + pos.getX(),
                    (int) velocity.getY() + pos.getY());
            pos = tmpPos;
            spreadDisease();
        }
    }
    void spreadDisease(){
        if(sickness != null){
            //  System.out.println("im here1");
            int x1 = pos.getX();
            int y1 = pos.getY();
            for(Person p : Main.map.getPeople()){
                int x = p.getPos().getX();
                int y = p.getPos().getY();
                if(p.getSickness() == null) {
                    if (Math.pow(Math.abs(x1 - x), 2) + Math.pow(Math.abs(y1 - y), 2)
                            < Math.pow(sickness.getSpreadRadius(), 2)) {
                        p.infect(new Sickness(sickness, p, sickness.getEpidemy()));
                    }
                }
            }
        }
    }
    public void getOlder(){
        //     System.out.println(age);
        age++;
    }
    public void reproduce(){
        int minRepAge = Main.map.getEnvironmentRestrictions().getMinaReproduceAge();
        int maxRepAge = Main.map.getEnvironmentRestrictions().getMaxReproduceAge();
        int maxRepDifference = Main.map.getEnvironmentRestrictions().getReproduceAgeDifference();

        if(age > minRepAge && age < maxRepAge){
            if(Main.map.getEnvironmentRestrictions().getBornRate()*10000 > Utilities.randomInt(0, 9999)){
                List<Person> potentialPartners;
                //our population likes only people with max 10 yrs age difference
                int x = pos.getX();
                int y = pos.getY();
                for(Person partner : Main.map.getPeople()){
                    if(partner != this)
                        if(!partner.isDead())
                            if(age > minRepAge && age < maxRepAge) {
                                if(Math.abs(age - partner.getAge()) < maxRepDifference) {
                                    int x1 = partner.getPos().getX();
                                    int y1 = partner.getPos().getY();
                                    if (Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2) <
                                            Main.map.getEnvironmentRestrictions().getMinMeetDistance()) {
                                        boolean gender = Utilities.randomState();
                                        Person child = new Person(null, 1, gender, this, partner);
                                        Main.map.getPeople().add(child);
                                        //    System.out.println("New child born!");
                                        break;
                                    }
                                }
                            }
                }
            }
        }
    }
    public float getHealthPoints() {
        return healthPoints;
    }
    void regenerate(){
        //regenerating everyday
        float add = (100)*Main.map.getEnvironmentRestrictions().getRegeneration()/(age);
        if(healthPoints + add >= maxHealthPoints){
            healthPoints = maxHealthPoints;
        }else{
            healthPoints += add;
        }
    }
    public void setVelocity(Utilities.Vector2f velocity) {
        this.velocity = velocity;
    }
    public Utilities.Vector2I getPos(){
        return pos;
    }
    public Utilities.Vector2f getVelocity(){
        return velocity;
    }
    public void infect(Sickness sickness){
        this.sickness = sickness;
    }
    public void kill(){
        dead = true;
        sickness = null;
    }
    public Sickness getSickness(){
        return sickness;
    }
    public float getImmunity() {
        return immunity;
    }
    public boolean isDead() {
        return dead;
    }
}