/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.MetaBalls.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import static java.lang.Math.max;
import static java.lang.Math.min;
import javax.media.opengl.GLAutoDrawable;
import javax.swing.JOptionPane;

/**
 *
 * @author Никита Русецкий
 */
public class Listener implements KeyListener, MouseListener, MouseWheelListener {

    Point loc = new Point(0, 0);

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            Data.type = 0;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            Data.type = 1;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            Data.type = 2;
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            grid = !grid;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            draw = !draw;
        }
        if (e.getKeyCode() == KeyEvent.VK_TAB) {
            
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            run = !run;
        }
        if (e.getKeyCode() == KeyEvent.VK_F1) {
            JOptionPane.showMessageDialog(null,
                        "Колесо мыши - изменение размерности сетки \n"
                        + "Клавиша 1 - Отображение блоков \n"
                        + "Клавиша 2 - Метод Marching Squares \n"
                        + "Клавиша 3 - Применение линейной интерполяции \n"
                        + "Q / E -  Изменение скорости кругов \n"
                        + "W / S - Изменение количества кругов \n"
                        + "A / D - Изменение размера кругов \n"
                        + "SPACE - Отображение кругов \n"
                        + "ENTER - Остановка симуляции \n"
                        + "CTRL - Отображение сетки", "Справка", 1);
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int sign = e.getWheelRotation();
        Data.tempgridStep = max(10, Data.tempgridStep - (10 * sign));
        Data.tempgridStep = min(200, Data.tempgridStep);
        Data.stepChanged = true;
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            Data.speedCoef = max(0, Data.speedCoef - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            Data.speedCoef = min(20, Data.speedCoef + 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            Data.circleSize = max(1, Data.circleSize - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Data.circleSize = min(50, Data.circleSize + 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Data.circleCount = max(1, Data.circleCount - 1);
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Data.circleCount = min(50, Data.circleCount + 1);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void display(GLAutoDrawable drawable) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

}
