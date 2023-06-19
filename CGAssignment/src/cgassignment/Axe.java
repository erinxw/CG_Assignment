package cgassignment;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;

import javax.swing.*;

public class Axe implements GLEventListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Texture bladeTexture;
    private Texture handleTexture;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);
            GLCanvas canvas = new GLCanvas(capabilities);
            canvas.setPreferredSize(new java.awt.Dimension(WIDTH, HEIGHT));
            canvas.addGLEventListener(new Axe());
            JFrame frame = new JFrame("Axe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(canvas);
            frame.pack();
            frame.setVisible(true);
            FPSAnimator animator = new FPSAnimator(canvas, 60);
            animator.start();
        });
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL.GL_TEXTURE_2D);

        // Load blade texture
        // Load blade texture
        try {
            bladeTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\src\\texture\\blade.jpg"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load handle texture
        try {
            handleTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\src\\texture\\handle.png"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        // Enable texture mapping
        gl.glEnable(GL.GL_TEXTURE_2D);

        // Draw handle with texture
        handleTexture.bind(gl);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.0f, -0.6f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.1f, -0.6f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.1f, 0.6f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.0f, 0.6f);
        gl.glEnd();

        // Draw blade with texture
        bladeTexture.bind(gl);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.1f, 0.6f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.4f, 0.7f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.4f, 0.1f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.1f, 0.2f);
        gl.glEnd();

        // Disable texture mapping
        gl.glDisable(GL.GL_TEXTURE_2D);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Empty implementation
    }
}
