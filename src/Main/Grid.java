/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.MetaBalls.gl;
import java.awt.Color;
import javax.media.opengl.GL;

/**
 *
 * @author Никита Русецкий
 *
 * Класс, реализующий методы отрисовки сетки и его объектов
 */
public class Grid {

    final static double PLUS90 = Math.toRadians(90);

    /**
     * Процедура отрисовки сетки
     */
    private static void drawGrid() {
        double countDraw = 11;
        double stepDraw = Data.SIZE * 2 / (countDraw - 1);
        for (int i = 0; i < countDraw; i++) {
            Grid.createLine(new double[][]{{i * stepDraw - Data.SIZES.width, -Data.SIZES.height}, {i * stepDraw - Data.SIZES.width, Data.SIZES.height}}, Data.gridColor);
        }
        for (int i = 0; i < countDraw; i++) {
            Grid.createLine(new double[][]{{-Data.SIZES.width, i * stepDraw - Data.SIZES.height}, {Data.SIZES.width, i * stepDraw - Data.SIZES.height}}, Data.gridColor);
        }
    }

    /**
     * Процедура отрисовки метасфер в зависимости от выбраного пользователем
     * режима 1) Отображение блоков внутри метасфер 2) Использование метода
     * шагающих квадратов 3) Применение интерполяции к методу №2
     */
    public static void draw() {
        if (MetaBalls.grid) {
            grid();
        }
        switch (Data.type) {
            case 0:
                blocks();
                break;
            case 1:
                wMath.linearInterpolation = false;
                marchingSquares();
                break;
            case 2:
                wMath.linearInterpolation = true;
                marchingSquares();
                break;
        }
        if (MetaBalls.run) {
            Data.sync();
        }
    }

    /**
     * Процедура отрисовки блоков для метасфер
     */
    private static void blocks() {
        if (MetaBalls.draw) {
            drawGrid();
        }
        if (MetaBalls.run) {
            wMath.calcFuncValues();
        }
        for (int i = 0; i < Data.gridStep + 1; i++) {
            for (int j = 0; j < Data.gridStep + 1; j++) {
                double[] P = wMath.mP[i][j];
                Color color;
                if (MetaBalls.grid) {
                    color = Color.blue;
                } else {
                    color = Data.metaColor;
                }
                // Проверяем функцию в точке
                if (wMath.mV[i][j] >= 1) {
                    Grid.createLineLoop(realVertexS(correctPolygon(wMath.step, wMath.step, 4), P[0], P[1]), color);
                }
            }
        }
    }

    /**
     *
     */
    private static void grid() {
        if (MetaBalls.draw) {
            drawGrid();
        }
        if (MetaBalls.run) {
            wMath.calcFuncValues();
        }
        for (int i = 0; i < Data.gridStep + 1; i++) {
            for (int j = 0; j < Data.gridStep + 1; j++) {
                double[] P = wMath.mP[i][j];
                Grid.createLineLoop(realVertexS(correctPolygon(wMath.step, wMath.step, 4), P[0], P[1]), Data.metaColor);
            }
        }
    }

    public static void drawCircles(Circle Ball) {
        createLineLoop(Grid.realVertexS(Grid.inscribedInOval(Data.circleSize, Data.circleSize, 100, 0), Ball.x, Ball.y), Data.circleColor);
    }

    /**
     *
     */
    private static void marchingSquares() {
        if (MetaBalls.draw) {
            drawGrid();
        }
        if (MetaBalls.run) {
            wMath.calcFuncValues();
        }
        for (int i = 0; i < Data.gridStep; i++) {
            for (int j = 0; j < Data.gridStep; j++) {
                buildSquare(i, j);
            }
        }
    }

    /**
     * Построение метасфер методом шагающих квадратов
     *
     * @param i координата
     * @param j координата
     */
    private static void buildSquare(int i, int j) {
        // Получение точек сетки квадрата
        double[][] gridP = wMath.gridPoints(i, j);
        // Получение значений функции в точках сетки данного квадрата
        double[] gridV = wMath.gridValues(i, j);
        // Получение индекса квадрата
        int squareIndex = wMath.squareIndex(gridV);
        // Получение номеров вычисляемых граней
        int edges = wMath.edgeTable[squareIndex];
        if (edges != 0) {
            // Массив точек на гранях
            double[][] vertList = new double[4][2];
            // Получаем точку с нужным значением функции на грани
            if ((edges & 1) > 0) {
                vertList[0] = wMath.vertexInter(gridP[0], gridP[1], gridV[0], gridV[1]);
            }
            if ((edges & 2) > 0) {
                vertList[1] = wMath.vertexInter(gridP[1], gridP[2], gridV[1], gridV[2]);
            }
            if ((edges & 4) > 0) {
                vertList[2] = wMath.vertexInter(gridP[2], gridP[3], gridV[2], gridV[3]);
            }
            if ((edges & 8) > 0) {
                vertList[3] = wMath.vertexInter(gridP[3], gridP[0], gridV[3], gridV[0]);
            }
            // Массив отрисовываемых линий
            double[][][] lines = new double[2][2][2];
            int nlines = 0;

            for (i = 0; wMath.lineTable[squareIndex][i] != -1; i += 2) {
                // Соединяемые точки на гранях берутся из таблицы для квадрата с данным индексом
                lines[nlines][0] = vertList[wMath.lineTable[squareIndex][i]];
                lines[nlines][1] = vertList[wMath.lineTable[squareIndex][i + 1]];
                nlines++;
            }
            // Построение линий
            for (j = 0; j < nlines; j++) {
                Grid.createLine(lines[j], Data.metaColor);
            }
        }
    }

    /**
     * Процедура построения объекта
     *
     * @param vertex вершины
     * @param color цвет
     */
    public static void createObject(double[][] vertex, Color color) {
        gl.glPushMatrix();
        gl.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        gl.glBegin(GL.GL_POLYGON);
        for (double[] vertex1 : vertex) {
            gl.glVertex2d(vertex1[0], vertex1[1]);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    /**
     * Процедура построения замкнутой линии
     *
     * @param vertex вершины
     * @param color цвет
     */
    public static void createLineLoop(double[][] vertex, Color color) {
        GL gl = MetaBalls.gl;
        gl.glPushMatrix();
        gl.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (double[] vertex1 : vertex) {
            gl.glVertex2d(vertex1[0], vertex1[1]);
        }
        gl.glEnd();
        gl.glPopMatrix();
    }

    /**
     * Процедура построения линии
     *
     * @param vertex вершины
     * @param color цвет
     */
    public static void createLine(double[][] vertex, Color color) {
        GL gl = MetaBalls.gl;
        gl.glPushMatrix();
        gl.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(vertex[0][0], vertex[0][1]);
        gl.glVertex2d(vertex[1][0], vertex[1][1]);
        gl.glEnd();
        gl.glPopMatrix();
    }

    /**
     * Процедура построения точек
     *
     * @param vertex вершины
     * @param color цвет
     */
    public static void createPoints(double[] vertex, Color color) {
        GL gl = MetaBalls.gl;
        gl.glPushMatrix();
        gl.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(vertex[0], vertex[1]);
        gl.glEnd();
        gl.glPopMatrix();
    }

    /**
     *
     * @param offset
     * @param x
     * @param y
     * @return
     */
    public static double[][] realVertexS(double[][] offset, double x, double y) {
        double[][] vertex = new double[offset.length][2];
        for (int i = 0; i < offset.length; i++) {
            vertex[i] = vertex(offset[i], x, y);
        }
        return vertex;
    }

    /**
     * Возвращает массив вершин
     *
     * @param offset смещение
     * @param x координата
     * @param y координата
     * @return массив вершин
     */
    private static double[] vertex(double[] offset, double x, double y) {
        double[] vertex = new double[2];
        vertex[0] = offset[0] + x;
        vertex[1] = offset[1] + y;
        return vertex;
    }

    /**
     *
     * @param w
     * @param h
     * @param gridStep
     * @param startAngle
     * @return
     */
    public static double[][] inscribedInOval(double w, double h, int gridStep, double startAngle) {
        double[][] offset = new double[gridStep][2];
        w /= Math.cos(PLUS90 * 2 / gridStep);
        h /= Math.cos(PLUS90 * 2 / gridStep);
        for (int i = 0; i < gridStep; i++) {
            double angle = (Math.toRadians((double) i / gridStep * 360d + startAngle));
            offset[i][0] = wMath.round((Math.cos(angle) * w), 5);
            offset[i][1] = wMath.round((Math.sin(angle) * h), 5);
        }
        return offset;
    }

    /**
     *
     * @param w
     * @param h
     * @param countVertex
     * @return
     */
    public static double[][] correctPolygon(double w, double h, int countVertex) {
        if (countVertex % 2 != 0) {
            return inscribedInOval(w, h, countVertex, 0);
        } else {
            return inscribedInOval(w, h, countVertex, 360 / (2 * countVertex));
        }
    }

}
