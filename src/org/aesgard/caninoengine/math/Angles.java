package org.aesgard.caninoengine.math;

public final class Angles {
	
    /*****************************************************
     * calculate X movement value based on direction angle
     *****************************************************/
    public static double calcAngleMoveX(double angle) {
        double movex = Math.cos(angle * Math.PI / 180);
        return movex;
    }

    /*****************************************************
     * calculate Y movement value based on direction angle
     *****************************************************/
    public static double calcAngleMoveY(double angle) {
        double movey = Math.sin(angle * Math.PI / 180);
        return movey;
    }

}
