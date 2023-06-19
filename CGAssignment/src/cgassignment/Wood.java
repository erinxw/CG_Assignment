package cgassignment;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class Wood implements GLEventListener {

    private static final int NUM_SLICES = 50; // Number of slices to create the cylinder
    private static final float RADIUS = 0.5f; // Radius of the cylinder
    private static final float HEIGHT = 2.0f; // Height of the cylinder (increased)
    private Texture texture;

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        try {
            texture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\src\\texture\\log.jpg"), true);
            texture.bind(gl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set texture mapping mode
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        double cameraDistance = 5.0;
        double cameraHeight = 0.0;
        double cameraAngle = 60.0; // Rotation angle around Y-axis

        gl.glTranslatef(0, 0, (float) -cameraDistance);
        gl.glRotatef((float) cameraAngle, 0, 1, 0);
        gl.glTranslatef(0, (float) -cameraHeight, 0);

        gl.glEnable(GL2.GL_TEXTURE_2D);
        texture.bind(gl); // Bind the texture

        // Draw the bottom circle of the cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glTexCoord2f(0.5f, 0.5f); // Center texture coordinate
        gl.glVertex3f(0.0f, 0.0f, -HEIGHT / 2); // Center vertex
        for (int i = NUM_SLICES; i >= 0; i--) {
            float angle = (float) (2 * Math.PI * i / NUM_SLICES);
            float x = (float) (RADIUS * Math.cos(angle));
            float y = (float) (RADIUS * Math.sin(angle));

            float textureX = 0.5f + 0.5f * x / RADIUS;
            float textureY = 0.5f + 0.5f * y / RADIUS;

            gl.glTexCoord2f(textureX, textureY); // Vertex texture coordinate
            gl.glVertex3f(x, y, -HEIGHT / 2); // Vertex on the circle
        }
        gl.glEnd();

        // Draw the cylinder sides
        gl.glBegin(GL2.GL_TRIANGLE_STRIP);
        for (int i = 0; i <= NUM_SLICES; i++) {
            float angle = (float) (2 * Math.PI * i / NUM_SLICES);
            float x = (float) (RADIUS * Math.cos(angle));
            float y = (float) (RADIUS * Math.sin(angle));

            float textureX = (float) i / NUM_SLICES;
            float textureYTop = 0.0f;
            float textureYBottom = 1.0f;

            gl.glTexCoord2f(textureX, textureYTop);
            gl.glVertex3f(x, y, HEIGHT / 2); // Top vertex

            gl.glTexCoord2f(textureX, textureYBottom);
            gl.glVertex3f(x, y, -HEIGHT / 2); // Bottom vertex
        }
        gl.glEnd();

        // Draw the top circle of the cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glTexCoord2f(0.5f, 0.5f); // Center texture coordinate
        gl.glVertex3f(0.0f, 0.0f, HEIGHT / 2); // Center vertex
        for (int i = 0; i <= NUM_SLICES; i++) {
            float angle = (float) (2 * Math.PI * i / NUM_SLICES);
            float x = (float) (RADIUS * Math.cos(angle));
            float y = (float) (RADIUS * Math.sin(angle));

            float textureX = 0.5f + 0.5f * x / RADIUS;
            float textureY = 0.5f + 0.5f * y / RADIUS;

            gl.glTexCoord2f(textureX, textureY); // Vertex texture coordinate
            gl.glVertex3f(x, y, HEIGHT / 2); // Vertex on the circle
        }
        gl.glEnd();

        // Draw the top circle of the cylinder
        gl.glEnd();

        gl.glDisable(GL2.GL_TEXTURE_2D); // Disable texture mapping

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        float aspectRatio = (float) width / (float) height;
        float fieldOfView = 45.0f;
        float near = 0.1f;
        float far = 100.0f;

        // Set up a perspective projection
        float top = near * (float) Math.tan(Math.toRadians(fieldOfView * 0.5));
        float bottom = -top;
        float left = bottom * aspectRatio;
        float right = top * aspectRatio;

        gl.glFrustum(left, right, bottom, top, near, far);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Cleanup code
    }

    public static void main(String[] args) {
        GLProfile.initSingleton();
        GLProfile glProfile = GLProfile.get(GLProfile.GL2);
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(new Wood());

        JFrame frame = new JFrame("Wood");
        frame.getContentPane().add(canvas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
