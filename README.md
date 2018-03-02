# Metaballs 2D

## Contents

1. [Before we start](#before-we-start)
2. [Definition](#definition)
3. [Math](#math)

## Before we start
This project is based on following articles:
1. "[Metaballs and Marching Squares](http://jamie-wong.com/2014/08/19/metaballs-and-marching-squares/)" by [Jamie Wong](https://github.com/jlfwong)
2. "[Marching Squares Implementation](http://www.tomgibara.com/computer-vision/marching-squares)" by Tom Gibara
3. "[Polygonising a scalar field](http://paulbourke.net/geometry/polygonise/)" by [Paul Bourke](http://paulbourke.net/)
4. "[Metaballs](https://en.wikipedia.org/wiki/Metaballs)" from [Wikipedia](https://en.wikipedia.org/)

Special thanks to [@aptem336](https://github.com/aptem336) for helping me to seek for information and realizations of similar projects.

*The application is developed as the part of the project for computer graphics course at [Irkutsk National Research Techincal University](http://www.istu.edu/eng/).*

## Definition
Metaballs are organic-looking objects that can be defined as a function in n-dimensions [4].

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


# UNDER CONSTRUCTION
