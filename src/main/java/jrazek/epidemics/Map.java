package jrazek.epidemics;

import java.util.ArrayList;
import java.util.List;
import jrazek.epidemics.Utilities.*;

import static jrazek.epidemics.Utilities.randomInt;
import static jrazek.epidemics.Utilities.randomDouble;

public class Map {
    private final List<Person> people = new ArrayList<>();
    private final List<Epidemy> epidemies = new ArrayList<>();
    private Utilities.EnvironmentRestrictions enR;
    private int day = 0;
    Map(Utilities.EnvironmentRestrictions enR){
        this.enR = enR;
    }
    public void randomEvents(){
        //zmień na rozmnazanie podczas spotkan dwoch przeciwnych plci
      /*  if(bornR*10000 > randomInt(0, 9999)){
            int xS = size.getX();
            int yS = size.getY();
            Vector2I pos =
                    new Vector2I(randomInt(0, xS-1), randomInt(0, yS-1));
            people.add(new Person(pos, 1, ));
        }*/
        if(enR.getDeathRate()*10000 > randomInt(0, 9999)){
            if(people.size() > 0) {
                killByAge();
            }
        }
        if(enR.getMoveRate()*10000 > randomInt(0, 9999)){
            if(people.size() > 0) {
                int index = randomInt(0, people.size()-1);
                if(!people.get(index).isDead()) {
                    Vector2f randV = new Vector2f((float) randomDouble(-10, 10), (float) randomDouble(-10, 10));
                    people.get(index).setVelocity(randV);
                }
            }
        }
    }
    public List<Person> getPeople (){
        return people;
    }
    public List<Epidemy> getEpidemies(){
        return epidemies;
    }
    public void createPopulation(){
        for(int i = 0; i < enR.getStartPopulationSize(); i ++){
            int age = Utilities.randomInt(1, 99) * enR.getYearLength();
            int randX = Utilities.randomInt(0, enR.getMapSize().getX());
            int randY = Utilities.randomInt(0, enR.getMapSize().getY());
            boolean gender = Utilities.randomState();
            Utilities.Vector2I pos = new Utilities.Vector2I(randX, randY);
            people.add(new Person(pos, age, gender, null, null));
        }
    }
    private void killByAge(){
        //killing people - bigger age - bigger chance
        int ageSum = 0;
        for(Person pr : people){
            ageSum += pr.getAge();
        }
        if(ageSum > 0){
            int randNum = randomInt(0, ageSum);
            ageSum = 0;
            int index = 0;
            for(Person pr : people){
                ageSum += pr.getAge();
                if(ageSum >= randNum) {
                    if(!people.get(index).isDead()){
                        people.get(index).kill();
                        System.out.println("Maamaaa! Just killed the man!");
                        break;
                    }
                }
                index ++;
            }
        }
    }
    public void startRandomEpidemy(){
        Epidemy ep = new Epidemy(people);
        epidemies.add(ep);
        ep.start();
    }
    public void nextDay(){
        day++;
        for(Person p : people){
            p.regenerate();
            p.getOlder();
            if(p.getSickness() != null) {
                p.getSickness().fightVictim();
                p.fightSickness();
            }
        }
    }
    public Utilities.EnvironmentRestrictions getEnvironmentRestrictions() {
        return enR;
    }
}