package com.roaringcatgames.ld33;

/**
 * Created with IntelliJ IDEA.
 * User: barry
 * Date: 1/24/15
 * Time: 10:28 AM
 */
public class MathUtil {

    public static double getSineYForTime(double period, double scale, double timePosition){
        return  Math.sin(timePosition*2*Math.PI/period)*(scale/2);
    }
}
