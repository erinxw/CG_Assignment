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
import java.awt.*;
//import javax.media.opengl.*;

public class cloud implements GLEventListener {
    

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        // Set the color for the cloud
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        
        // Draw the cloud using multiple circle shapes
        drawCircle(gl, 0.0f, 0.0f, 0.2f);
        drawCircle(gl, -0.15f, 0.1f, 0.15f);
        drawCircle(gl, 0.15f, 0.1f, 0.15f);
        drawCircle(gl, -0.1f, -0.1f, 0.15f);
        drawCircle(gl, 0.1f, -0.1f, 0.15f);
        drawCircle(gl, 0.0f, -0.1f, 0.15f);
    }

    private void drawCircle(GL2 gl, float centerX, float centerY, float radius) {
        int numSegments = 100;
        float angle;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glVertex2f(centerX, centerY); // Center point
        for (int i = 0; i <= numSegments; i++) {
            angle = (float) (2.0 * Math.PI * i / numSegments);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
    }

    public void init(GLAutoDrawable drawable) {
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    public static void main(String[] args) {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
        GLCanvas canvas = new GLCanvas(glCapabilities);
        canvas.addGLEventListener(new cloud());

        Frame frame = new Frame("JOGL Cloud Example");
        frame.add(canvas);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}

