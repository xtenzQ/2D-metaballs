/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/**
 *
 * @author Никита Русецкий
 */
public class Impulse {

    public double x;
    public double y;
    public double value;
    public double angle;

    Impulse(double value, double angle) {
        this.angle = angle;
        this.value = value;
        x = (double) (value * Math.cos(Math.toRadians(angle)));
        y = (double) (value * Math.sin(Math.toRadians(angle)));
    }
}
