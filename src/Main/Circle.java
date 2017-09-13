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
 * @author ������ ��������
 *
 * ����� ���������
 */
public class Circle {

    // ���������� ��������� (x, y)
    public double x;
    public double y;

    // �������� ��������� �� OX � OY
    public double speedX;
    public double speedY;

    // ArrayList ��������� ��� ������ �� ��������
    public ArrayList<Impulse> impulse = new ArrayList<Impulse>();

    /**
     * �����������
     *
     * @param x ����������
     * @param y ����������
     */
    public Circle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * ��������� ���������� �����
     */
    public static void add() {
        Circle c = new Circle(0, 0);
        c.addImpulse(100, Math.random() * 360);
        Balls.add(c);
    }

    /**
     * ��������� �������� �����
     */
    public static void remove() {
        Balls.remove(Balls.size() - 1);
    }

    /**
     * ������ ����������� ��� ��������� ��� �������� �����
     *
     * @param value ��������
     * @param angle ����
     */
    public void addImpulse(final double value, final double angle) {
        impulse.add(new Impulse(value, angle));
    }

    /**
     * ��������� ������ ��������� �� ����
     *
     * @param circle ���������
     */
    public static void bounce(Circle circle) {
        // �������������� �� ������
        wallInteraction(circle);
        // ����������� �����
        move(circle);
    }

    /**
     * ��������� �������������� ��������� �� ������
     *
     * @param circle ���������
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
     * ��������� ����������� ���������
     *
     * @param circle ���������
     */
    private static void move(Circle circle) {
        // ������� �� ���� ���������
        double IX = 0;
        double IY = 0;
        // ���������� �������� � ����������
        for (Impulse impulse : circle.impulse) {
            IX += impulse.x;
            IY += impulse.y;
        }
        circle.impulse.clear();
        circle.speedX += IX / 1000;
        circle.speedY += IY / 1000;
        // ����������� �������� ������������ ��� ���������� / 
        // ���������� �������� �� ����� ���������
        circle.x += circle.speedX * Data.speedCoef;
        circle.y += circle.speedY * Data.speedCoef;
    }

}
