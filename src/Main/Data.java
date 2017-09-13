/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 *
 * @author Никита Русецкий
 * 
 * Класс, содержащий поля с данными
 */
public class Data {

    public final static int SIZE = 80;
    public final static Dimension SIZES = new Dimension(SIZE, SIZE);

    public static boolean stepChanged = true;

    // ЦВЕТ
    public static Color circleColor = Color.yellow;
    public static Color metaColor = new Color(238, 238, 224, 255);
    public static Color gridColor = new Color(255, 255, 255, 100);
    public static Color darkgridColorolor = new Color(255, 255, 255, 100);
    public static Color lightgridColorolor = new Color(255, 255, 255, 100);

    public static int gridStep = 30;
    public static int tempgridStep = 30;
    public static int type = 2;
    public static int speedCoef = 5;
    public static int circleSize = 20;
    public static int circleCount = 5;

    public static ArrayList<Circle> Balls = new ArrayList<Circle>();

    public static void sync() {
        if (stepChanged) {
            gridStep = tempgridStep;
            wMath.getGridDimension();
            stepChanged = false;
        }
        if (Data.Balls.size() < circleCount) {
            Circle.add();
        } else if (Data.Balls.size() > circleCount) {
            Circle.remove();
        }
    }
}
