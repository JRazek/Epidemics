package jrazek.epidemics;

public class Sickness {
    private float mutationRate;
    private float spreadRadius;
    private float damageRate;
    private Person victim;
    private Epidemy epidemy;
    private float healthPoints;
    Sickness(Sickness s, Person p, Epidemy ep){
        if(s != null && p != null) {
            this.mutationRate = s.getMutationRate();
            this.spreadRadius = s.getSpreadRadius();
            this.damageRate = s.getMutationRate();
            this.healthPoints = s.getHealthPoints();
            this.victim = p;
            mutate();
        }
        if(ep != null){
            this.epidemy = ep;
        }
    }

    //to use only on first one infected
    void init(float mtR, float sprR, float dmgR, float hp, Person v){
        this.mutationRate = mtR;
        this.spreadRadius = sprR;
        this.damageRate = dmgR;
        this.healthPoints = hp;
        this.victim = v;
    }
    void mutate(){
        if(mutationRate*100000 > Utilities.randomInt(0, 99999)) {
            System.out.println("Disease has mutated!");
            mutationRate += (float) Utilities.randomDouble(0, 0.06);
            spreadRadius += (float) Utilities.randomDouble(0, 4);
            damageRate += (float) Utilities.randomDouble(0, 2);
        }
    }

    public float getMutationRate() {
        return mutationRate;
    }
    public float getDamageRate() {
        return damageRate;
    }
    public void fightVictim(){
        victim.receiveDamage(damageRate);
    }
    public void receiveDamage(float dmg){
        healthPoints -= dmg;
    }
    public float getHealthPoints() {
        return healthPoints;
    }
    public Epidemy getEpidemy() {
        return epidemy;
    }

    public float getSpreadRadius() {
        return spreadRadius;
    }
}