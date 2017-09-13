/*
 * ������� �������� ������������, ������ �������� ���������, � ����� ������� 
 * ������ � ��������� ��������� �������� �� �������, ��������� � �������:
 * http://paulbourke.net/geometry/polygonise/ "Polygonising a scalar field" by Paul Bourke
 * http://jamie-wong.com/2014/08/19/metaballs-and-marching-squares/ "Metaballs and Marching Squares" by Jamie Wong
 */
package Main;

/**
 *
 * @author ������ ��������
 */
public class wMath {

    public static double[][][] mP;
    public static double[][] mV;
    public static double step;
    public static boolean linearInterpolation;

    /**
     * �������� �������
     */
    static int[] edgeTable = {
        0x0, 0x9, 0x3, 0xA,
        0x6, 0xF, 0x5, 0xC,
        0xC, 0x5, 0xF, 0x6,
        0xA, 0x3, 0x9, 0x0
    };
    
    /**
     * 2-2-1
     * |   |
     * 3   1
     * |   |
     * 3-0-0
     */

    static int[][] lineTable = {
        {-1, -1, -1, -1},
        {3, 0, -1, -1, -1, -1},
        {0, 1, -1, -1, -1, -1},
        {1, 3, -1, -1, -1, -1},
        {1, 2, -1, -1, -1, -1},
        {1, 2, 3, 0, -1, -1},
        {0, 2, -1, -1, -1, -1},
        {2, 3, -1, -1, -1, -1},
        {2, 3, -1, -1, -1, -1},
        {0, 2, -1, -1, -1, -1},
        {0, 1, 2, 3, -1, -1},
        {1, 2, -1, -1, -1, -1},
        {1, 3, -1, -1, -1, -1},
        {0, 1, -1, -1, -1, -1},
        {3, 0, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1}
    };

    /**
     * ������� �������� ������������ 
     * ������� � �������: 
     * A----B 
     * |    | 
     * |    Q
     * |    |
     * C----D
     *
     * @param D �����
     * @param B �����
     * @param fD �������� ������� � ����� D
     * @param fB �������� ������� � ����� B
     * @return �����
     */
    public static double[] vertexInter(double[] D, double[] B, double fD, double fB) {
        // �������� ���������� �������� �� ������ ��������
        double[] mS = new double[]{(D[0] + B[0]) / 2, (D[1] + B[1]) / 2};
        if (!linearInterpolation) {
            return mS;
        } else {
            double[] Q = new double[2];
            if (Math.abs(1 - fD) < 0.0005) {
                return D;
            } else if (Math.abs(1 - fB) < 0.0005) {
                return B;
            } else if (Math.abs(fD - fB) < 0.0005) {
                return mS;
            } else {
                // (1 - f(B_x, B_y)) / (f(D_x, D_y) - f(B_x, B_y) 
                double exp = (1 - fB) / (fD - fB);
                // Q_x = B_x + (D_x - B_x) * ���������
                Q[0] = B[0] + (D[0] - B[0]) * exp;
                // Q_y = B_y + (D_y - B_y) * ���������
                Q[1] = B[1] + (D[1] - B[1]) * exp;
            }
            return Q;
        }
    }

    /**
     * ����� ����� ��� ������� ��������
     *
     * @param i ���������� x
     * @param j ���������� y
     * @return ������ ����� ��������
     */
    public static double[][] gridPoints(int i, int j) {
        double[][] gridP = new double[4][2];
        gridP[0] = mP[i][j];
        gridP[1] = mP[i + 1][j];
        gridP[2] = mP[i + 1][j + 1];
        gridP[3] = mP[i][j + 1];
        return gridP;
    }

    /**
     * �������� ������� � ������ ������� ��������
     *
     * @param i ���������� x
     * @param j ���������� y
     * @return ������ �������� � ������ ��������
     */
    public static double[] gridValues(int i, int j) {
        double[] gridV = new double[4];
        gridV[0] = mV[i][j];
        gridV[1] = mV[i + 1][j];
        gridV[2] = mV[i + 1][j + 1];
        gridV[3] = mV[i][j + 1];
        return gridV;
    }

    /**
     * ������ ��������
     * @param grid
     * @return
     */
    public static int squareIndex(double[] grid) {
        int index = 0;
        if (grid[0] < 1) {
            index |= 1;
        }
        if (grid[1] < 1) {
            index |= 2;
        }
        if (grid[2] < 1) {
            index |= 4;
        }
        if (grid[3] < 1) {
            index |= 8;
        }
        return index;
    }

    /**
     * �������������� ������ �����
     */
    public static void getGridDimension() {
        // ���������� ���� �����
        step = (double) (Data.SIZES.width * 2d / Data.gridStep);
        mP = new double[Data.gridStep + 1][Data.gridStep + 1][2];
        for (int i = 0; i < Data.gridStep + 1; i++) {
            for (int j = 0; j < Data.gridStep + 1; j++) {
                double[] P = {i * step - Data.SIZES.width, j * step - Data.SIZES.height};
                mP[i][j] = P;
            }
        }
    }

    /**
     *
     */
    public static void calcFuncValues() {
        mV = new double[Data.gridStep + 1][Data.gridStep + 1];
        for (int i = 0; i < Data.gridStep + 1; i++) {
            for (int j = 0; j < Data.gridStep + 1; j++) {
                mV[i][j] = f(mP[i][j]);
            }
        }
    }

    /**
     * �������, �������� ���������
     *
     * @param B ����� (x, y)
     * @return �������� �������
     */
    public static double f(double[] B) {
        double sum = 0;
        for (Circle circle : Data.Balls) {
            // SUM (r_i)^2 / ((x - x_i)^2 + (y - y_i)^2)
            sum += Math.pow(Data.circleSize, 2) / ((Math.pow(B[0] - circle.x, 2) + Math.pow(B[1] - circle.y, 2)));
        }
        return sum;
    }

    /**
     * ���������� ����� (������ ������� �����)
     *
     * @param value ��������
     * @param scale ������� ����������
     * @return ����������� �����
     */
    public static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

}
