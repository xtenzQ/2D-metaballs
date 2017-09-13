package Main;

import com.sun.opengl.util.GLUT;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Никита Русецкий
 */
public class MetaBalls implements GLEventListener {

    static GL gl;
    static GLU glu;
    static Interface Interface;

    static long millis1;
    static long millis0;
    static int fps;
    static int frames = 0;

    static boolean draw = false;
    static boolean run = true;
    static boolean grid = false;

    public static void main(String[] args) {
        // Инициализация интерфейса
        Interface = new Interface();
        // Расчёт сетки
        wMath.getGridDimension();
    }

    /**
     * Инициализация
     * @param drawable 
     */
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.setSwapInterval(1);
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
    }

    /**
     * 
     * @param drawable
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl = drawable.getGL();
        glu = new GLU();
        final double h = (double) width / (double) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 10000.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /**
     * 
     * @param drawable 
     */
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, 0);
        glu.gluLookAt(0, 0, 200, 0, 0, 0, 0, 1, 0);

        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        
        GLUT glut = new GLUT();

        // Отображение текста в главном окне
        Interface.drawText(drawable, glut);
        // Вывод FPS
        fixFPS(drawable, glut);

        for (Circle circle : Data.Balls) {
            if (run) {
                // Взаимодействие шара со стеной
                Circle.bounce(circle);
            }
            if (draw) {
                // Отрисовка кругов
                Grid.drawCircles(circle);
            }
        }
        // Процедура отрисовки метасфер
        Grid.draw();

        gl.glFlush();
    }
    
    private void fixFPS(GLAutoDrawable drawable, GLUT glut) {
        frames++;
        millis1 = System.currentTimeMillis();
        if (millis1 - millis0 >= 1000) {
            fps = frames;
            millis0 = millis1;
            frames = 0;
        }
        
        gl.glColor4f(1f, 1f, 1f, 0.6f);
        gl.glWindowPos2i(20, drawable.getHeight() - 25);
        glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "FPS:  " + fps);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
