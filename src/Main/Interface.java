/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.MetaBalls.gl;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.GLUT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author Никита Русецкий
 */
public class Interface extends JFrame {

    static Listener myListener;
    static GLCanvas canvas;
    static Animator animator;

    public Interface() {
        init();
    }

    private void init() {
        setSize(800, 800);
        setTitle("Metaballs 2D");
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        myListener = new Listener();
        canvas = new GLCanvas();
        canvas.addKeyListener(myListener);
        canvas.addMouseListener(myListener);
        canvas.addMouseWheelListener(myListener);
        canvas.addGLEventListener(new MetaBalls());
        canvas.setBounds(0, 0, getWidth(), getHeight() - 30);
        animator = new Animator(canvas);

        add(canvas);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        animator.start();
    }
    
    public void drawText(GLAutoDrawable drawable, GLUT glut) {

        gl.glColor4f(1f, 1f, 1f, 0.6f);
        gl.glWindowPos2i(20, 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Press F1 for help");
        gl.glWindowPos2i(drawable.getWidth() - 180, drawable.getHeight() - 20);

        int space = 0;
        String typeCalcName = "";
        switch (Data.type) {
            case 0:
                typeCalcName = "Blocks";
                space = 100;
                break;
            case 1:
                typeCalcName = "Marching Squares";
                space = 180;
                break;
            case 2:
                typeCalcName = "Interpolated";
                space = 140;
                break;
        }
        gl.glColor4f(1f, 1f, 1f, 0.6f);
        gl.glWindowPos2i(drawable.getWidth() - 200, drawable.getHeight() - 25);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Grid Dimension:  " + Data.gridStep);
        gl.glWindowPos2i(drawable.getWidth() - 130, drawable.getHeight() - 45);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Speed:  " + Data.speedCoef);
        gl.glWindowPos2i(drawable.getWidth() - 110, drawable.getHeight() - 65);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Size:  " + Data.circleSize);
        gl.glWindowPos2i(drawable.getWidth() - 139, drawable.getHeight() - 85);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Number:  " + Data.circleCount);
        
        gl.glWindowPos2i(drawable.getWidth() - space, 20);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, typeCalcName);
        
    }

}
