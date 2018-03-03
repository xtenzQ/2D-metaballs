# Metaballs 2D

## Contents

1. [Before we start](#before-we-start)
2. [IDEs and plugins used](#ides-and-plugins-used)
3. [Definition](#definition)
4. [Math](#math)
5. [Screenshots](#screenshots)

## Before we start
This project is based on following articles:
1. "[Metaballs and Marching Squares](http://jamie-wong.com/2014/08/19/metaballs-and-marching-squares/)" by [Jamie Wong](https://github.com/jlfwong)
2. "[Marching Squares Implementation](http://www.tomgibara.com/computer-vision/marching-squares)" by Tom Gibara
3. "[Polygonising a scalar field](http://paulbourke.net/geometry/polygonise/)" by [Paul Bourke](http://paulbourke.net/)
4. "[Metaballs](https://en.wikipedia.org/wiki/Metaballs)" from [Wikipedia](https://en.wikipedia.org/)

Special thanks to [@aptem336](https://github.com/aptem336) for helping me to seek for information and implementations of similar projects.

*The application is developed as the part of the project for computer graphics course at [Irkutsk National Research Techincal University](http://www.istu.edu/eng/).*

## IDEs and plugins used
- NetBeans IDE
- NetBeans OpenGL Pack ([Download](http://plugins.netbeans.org/plugin/3260/netbeans-opengl-pack))

## Definition
> **Metaballs** are organic-looking objects that can be defined as a function in n-dimensions. - _Wikipedia_

![Metaballs from Wikipedia](https://upload.wikimedia.org/wikipedia/commons/6/6d/Metaball_contact_sheet.png)

*Metaballs from [Wikipedia](https://en.wikipedia.org/wiki/Metaballs)*

I'm gonna show you the way to code 2D metaballs and explain some math-related stuff.

## Math

_Based on Jamie Wong article listed above_

As we have two dimensional metaballs the problem is to find all the points inside a circle limited by its bounds. Let’s look at equation defining a circle with center at (x0,y0) and radius r:

![Circle equation](https://i.imgur.com/Gwz2ObP.png?1)

If we want to model all the points inside circle including its bound:

![Circle equation](https://i.imgur.com/S93vAWS.png?1)

Having all the interacting circles we can model all the points inside them if a circle of i is defined with center at (xi,yi) and radius ri:

![Circle equation](https://i.imgur.com/1AmbnoU.png?1)

And finally we get the equation defining two dimensional metaballs:

![Circle equation](https://i.imgur.com/xVzLtDf.png?1)

```Java
public static double f(double[] B) {
    double sum = 0;
    for (Circle circle : Data.Balls) {
        // SUM (r_i)^2 / ((x - x_i)^2 + (y - y_i)^2)
        sum += Math.pow(Data.circleSize, 2) / ((Math.pow(B[0] - circle.x, 2) + Math.pow(B[1] - circle.y, 2)));
    }
    return sum;
}
```
## Marching Squares Method

> **Marching squares** is a computer graphics algorithm that generates contours for a two-dimensional scalar field (rectangular array of individual numerical values) - _Wikipedia_

Binary tables:

```Java
/**
 * Binary tables
 */
static int[] edgeTable = {
    0x0, 0x9, 0x3, 0xA,
    0x6, 0xF, 0x5, 0xC,
    0xC, 0x5, 0xF, 0x6,
    0xA, 0x3, 0x9, 0x0
};

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
```

Marching squares method:

```Java
private static void buildSquare(int i, int j) {

    double[][] gridP = wMath.gridPoints(i, j);

    double[] gridV = wMath.gridValues(i, j);

    int squareIndex = wMath.squareIndex(gridV);

    int edges = wMath.edgeTable[squareIndex];
    if (edges != 0) {

        double[][] vertList = new double[4][2];

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

        double[][][] lines = new double[2][2][2];
        int nlines = 0;
        for (i = 0; wMath.lineTable[squareIndex][i] != -1; i += 2) {

            lines[nlines][0] = vertList[wMath.lineTable[squareIndex][i]];
            lines[nlines][1] = vertList[wMath.lineTable[squareIndex][i + 1]];
            nlines++;
        }

        for (j = 0; j < nlines; j++) {
            Grid.createLine(lines[j], Data.metaColor);
        }
    }
}
```

## Screenshots

Main Window:

![Main Window](https://i.imgur.com/YgTBaLW.png?1)


# UNDER CONSTRUCTION
