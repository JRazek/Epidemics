package jrazek.epidemics;

import java.util.Random;

public class Utilities {
    public static class Vector2I{
        private int xPos;
        private int yPos;
        Vector2I(int xPos, int yPos){
            this.xPos = xPos;
            this.yPos = yPos;
        }
        public int getX(){
            return xPos;
        }
        public int getY(){
            return yPos;
        }
    }
    public static class Vector2f{
        private float xPos;
        private float yPos;
        Vector2f(float xPos, float yPos){
            this.xPos = xPos;
            this.yPos = yPos;
        }
        public float getX(){
            return xPos;
        }
        public float getY(){
            return yPos;
        }
    }
    public static int randomInt(int min, int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static double randomDouble(double min, double max){
        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }
    public static boolean randomState(){
        int state = randomInt(0, 1);
        if(state == 1)
            return true;
        return false;
    }
    public static class EnvironmentRestrictions{
        static private int startPopulationSize = 1000;
        static private float bornRate = 0.0002f;
        static private float deathRate = 0.006f;
        static private float moveRate = 0.1f;
        static private float immunityGain = 0.001f;
        static private float regeneration = 20;
        static private Vector2I size = new Vector2I(1000, 1000);
        static private int dayLength = 20;//ticks that day takes
        static private int yearLength = 365;//days that takes a year. keep it 365.
        static private int fps = 60;
        static private int deadPersonRemovalPeriod = 10; //days

        static private int minReproduceAge = 18 * yearLength;
        static private int maxReproduceAge = 50 * yearLength;
        static private int reproduceAgeDifference = 10 * yearLength;
        static private int minMeetDistance = 5;

        static public class Randomness {
            static private boolean isRandom = false;
            static private float mutationRate = 0.001f;
            static private float spreadRadius = 8;
            static private float damageRate = 0.00001f;
            static private float healthPoints = 10;
            public static boolean isRandom() {
                return isRandom;
            }
            public static float getMutationRate() {
                return mutationRate;
            }
            public static float getSpreadRadius() {
                return spreadRadius;
            }
            public static float getDamageRate() {
                return damageRate;
            }
            public static float getHealthPoints() {
                return healthPoints;
            }
        }
        public Randomness getRandomness(){
            return new Randomness();
        }
        public static int getFps() {
            return fps;
        }
        public static float getImmunityGain() {
            return immunityGain;
        }
        public int getStartPopulationSize() {
            return startPopulationSize;
        }
        public int getMinaReproduceAge() {
            return minReproduceAge;
        }
        public int getMaxReproduceAge() {
            return maxReproduceAge;
        }
        public int getReproduceAgeDifference() {
            return reproduceAgeDifference;
        }
        public float getBornRate() {
            return bornRate;
        }
        public float getDeathRate() {
            return deathRate;
        }
        public float getMoveRate() {
            return moveRate;
        }
        public Vector2I getMapSize() {
            return size;
        }
        public int getDayLength() {
            return dayLength;
        }
        public int getYearLength() {
            return yearLength;
        }
        public static int getMinMeetDistance() {
            return minMeetDistance;
        }
        public static float getRegeneration() {
            return regeneration;
        }
        public static int getDeadPersonRemovalPeriod() {
            return deadPersonRemovalPeriod;
        }
    }
}