/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cgassignment;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author Anneisha
 */
public class duck implements GLEventListener {
    
    public static void main(String[] args) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCanvas canvas = new GLCanvas(new GLCapabilities(profile));
        canvas.addGLEventListener(new duck());

        JFrame frame = new JFrame("Duck Renderer");
        frame.getContentPane().add(canvas);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        // Draw the body (oval)
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        float radiusX = 0.6f;
        float radiusY = 0.4f;
        int numSegments = 100;
        for (int i = 0; i < numSegments; i++) {
            float theta = (float) (2 * Math.PI * i / numSegments);
            float x = (float) (radiusX * Math.cos(theta));
            float y = (float) (radiusY * Math.sin(theta));
            gl.glVertex2f(x - 0.3f, y);
        }
        gl.glEnd();
        
        // First pass: Draw the beak
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); // Set polygon mode to fill for the beak
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(0.6f, 0.2f);
        gl.glVertex2f(0.6f, 0.0f);
        gl.glVertex2f(0.8f, 0.1f);
        gl.glEnd();

        // Draw the head (circle)
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_POLYGON);
        float radius = 0.3f;
        int numSeg = 100;
        for (int i = 0; i < numSeg; i++) {
            float theta = (float) (2 * Math.PI * i / numSeg);
            float x = (float) (radius * Math.cos(theta));
            float y = (float) (radius * Math.sin(theta));
            gl.glVertex2f(x + 0.4f, y + 0.2f);
        }
        gl.glEnd();
        
        // Draw the eyes (black dots)
        gl.glPointSize(6.0f);
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_POINTS);
        gl.glVertex2f(0.55f, 0.25f);
        gl.glEnd();

        // Draw the beak (v shape)
        //gl.glColor3f(0.0f, 0.0f, 0.0f);
//        gl.glBegin(GL2.GL_TRIANGLES);
//        gl.glVertex2f(0.6f, 0.2f);
//        gl.glVertex2f(0.6f, 0.0f);
//        gl.glVertex2f(0.9f, 0.1f);
//        gl.glEnd();

        // Draw the wings (letter V)
//        gl.glColor3f(0.0f, 0.0f, 0.0f);
//        gl.glLineWidth(2.0f);
//        gl.glBegin(GL2.GL_LINES);
//        gl.glVertex2f(0.3f, 0.2f); // Left arm of the V
//        //gl.glVertex2f(0.3f, 0.0f);  // Right arm of the V
//        gl.glEnd();

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }
}