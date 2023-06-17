package cgassignment;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Boat extends JFrame implements GLEventListener {

    private Texture woodBodyTexture;
    private Texture clothSailsTexture;
    private Texture woodPoleTexture;

    public Boat() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        GLCanvas glCanvas = new GLCanvas(glCapabilities);
        glCanvas.addGLEventListener(this);
        getContentPane().add(glCanvas);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Boat());
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        try {
            woodBodyTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\texture\\wood_body.jpg"), true);
            clothSailsTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\texture\\cloth_sails.jpg"), true);
            woodPoleTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\texture\\wood_pole.jpg"), true);

            // Set texture parameters for cloth texture
            clothSailsTexture.bind(gl);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

        } catch (IOException e) {
            e.printStackTrace();
        }

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        // Bind the wood texture
        woodBodyTexture.bind(gl);

        // Boat body 
        gl.glBegin(GL2.GL_POLYGON);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.4f, -0.2f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-0.2f, -0.4f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.2f, -0.4f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.4f, -0.2f);
        gl.glEnd();

        // Bind the wood texture
        woodPoleTexture.bind(gl);

        // Pole 
        gl.glBegin(GL2.GL_POLYGON);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.02f, -0.2f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-0.02f, 0.3f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.02f, 0.3f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.02f, -0.2f);
        gl.glEnd();

        // Bind the cloth texture
        clothSailsTexture.bind(gl);

        // Big sail
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.02f, -0.12f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.02f, 0.3f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.35f, -0.12f);
        gl.glEnd();

        gl.glPushMatrix();
        gl.glScalef(-1.0f, 0.8f, 1.0f);
        gl.glTranslatef(0.0f, -0.03f, 0.0f);

        // Small sail 
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(0.02f, -0.12f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(0.02f, 0.3f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.35f, -0.12f);
        gl.glEnd();

        gl.glPopMatrix();

        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        woodBodyTexture.destroy(gl);
        clothSailsTexture.destroy(gl);
        woodPoleTexture.destroy(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
}
