package jrazek.epidemics;

import jrazek.epidemics.graphics.Frame;


public class Main {
    static Utilities.EnvironmentRestrictions enR = new Utilities.EnvironmentRestrictions();
    public static Map map = new Map(enR);
    static Frame gp = new Frame(enR.getMapSize(), "26 needs more tits");
    public static void main(String[] args){
        map.createPopulation();
        map.startRandomEpidemy();
    }
}