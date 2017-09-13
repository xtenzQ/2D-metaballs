/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Data.Balls;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Никита Русецкий
 *
 * Класс метасферы
 */
public class Circle {

    // Координаты метасферы (x, y)
    public double x;
    public double y;

    // Скорости метасферы по OX и OY
    public double speedX;
    public double speedY;

    // ArrayList импульсов для каждой из метасфер
    public ArrayList<Impulse> impulse = new ArrayList<Impulse>();

    /**
     * Конструктор
     *
     * @param x координата
     * @param y координата
     */
    public Circle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Процедура добавления круга
     */
    public static void add() {
        Circle c = new Circle(0, 0);
        c.addImpulse(100, Math.random() * 360);
        Balls.add(c);
    }

    /**
     * Процедура удаления круга
     */
    public static void remove() {
        Balls.remove(Balls.size() - 1);
    }

    /**
     * Задает направление для метасферы под заданным углом
     *
     * @param value значение
     * @param angle угол
     */
    public void addImpulse(final double value, final double angle) {
        impulse.add(new Impulse(value, angle));
    }

    /**
     * Реализует отскок метасферы от стен
     *
     * @param circle метасфера
     */
    public static void bounce(Circle circle) {
        // Взаимодействие со стеной
        wallInteraction(circle);
        // Перемещение круга
        move(circle);
    }

    /**
     * Реализует взаимодействие метасферы со стеной
     *
     * @param circle метасфера
     */
    private static void wallInteraction(Circle circle) {
        if (abs(circle.x) + Data.circleSize >= Data.SIZES.width) {
            circle.speedX = -circle.speedX;
        }
        if (abs(circle.y) + Data.circleSize >= Data.SIZES.height) {
            circle.speedY = -circle.speedY;
        }
    }

    /**
     * Реализует перемещение метасферы
     *
     * @param circle метасфера
     */
    private static void move(Circle circle) {
        // Импульс по осям координат
        double IX = 0;
        double IY = 0;
        // Применение импульса к метасферам
        for (Impulse impulse : circle.impulse) {
            IX += impulse.x;
            IY += impulse.y;
        }
        circle.impulse.clear();
        circle.speedX += IX / 1000;
        circle.speedY += IY / 1000;
        // Коэффициент скорости используется для увеличения / 
        // уменьшения скорости во время симуляции
        circle.x += circle.speedX * Data.speedCoef;
        circle.y += circle.speedY * Data.speedCoef;
    }

}
