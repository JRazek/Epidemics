package jrazek.epidemics;

import java.awt.*;
import java.util.List;

public class Epidemy {
    List<Person> people;
    Color sicknessColor = Color.RED;

    Epidemy(List<Person> people){
        this.people = people;
    }
    void start(){
        if(people.size() > 0) {
            int index = Utilities.randomInt(0, people.size());
            Person p = people.get(index);
            Sickness s = new Sickness(null, null, this);
            float mutationRate, spreadRadius, damageRate;
            float hp;
            Utilities.EnvironmentRestrictions.Randomness randomness = Main.map.getEnvironmentRestrictions().getRandomness();
            if(randomness.isRandom()){
                mutationRate = (float)Utilities.randomDouble(0.0001, 0.2);
                spreadRadius = (float)Utilities.randomDouble(5, 6);
                damageRate = (float)Utilities.randomDouble(0.000, 0.000);
                hp = (float)Utilities.randomDouble(100, 101);
            }else{
                mutationRate = randomness.getMutationRate();
                spreadRadius = randomness.getSpreadRadius();
                damageRate = randomness.getDamageRate();
                hp = randomness.getHealthPoints();
            }

            s.init(mutationRate, spreadRadius, damageRate, hp, p);
            p.infect(s);
        }
    }
    public List<Person> getPeople(){
        return people;
    }
    public Color getSicknessColor() {
        return sicknessColor;
    }
}