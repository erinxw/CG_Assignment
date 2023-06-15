/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cgassignment;

import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.JFrame;

public class CGAssignment implements GLEventListener {

   @Override
   public void display(GLAutoDrawable drawable) {

   final GL2 gl = drawable.getGL().getGL2();
   
   //house roof

   
   gl.glBegin(GL_QUADS); // draw using quads
         gl.glColor3f(0.2f, 0.1f, 0.0f);
         gl.glVertex3f(-0.3f, 0.3f, 0.0f); //upper left
         gl.glVertex3f(0.3f, 0.3f, 0.0f); //upper right
         gl.glVertex3f(0.6f, 0.0f, 0.0f); //bottom right
         gl.glVertex3f(-0.6f, 0.0f, 0.0f); //bottom left
   
   gl.glEnd(); //end of house roof
   
   //house body
   gl.glBegin(GL_QUADS); // draw using quads
         gl.glColor3f(1.0f, 1.0f, 1.0f);
         gl.glVertex3f(-0.6f, 0.0f, 0.0f); //upper left
         gl.glVertex3f(0.6f, 0.0f, 0.0f); //upper right
         gl.glVertex3f(0.6f, -0.75f, 0.0f); //bottom right
         gl.glVertex3f(-0.6f, -0.75f, 0.0f); //bottom left
         
   gl.glEnd();//end of house body
   
   // windows
   gl.glBegin(GL_QUADS); // draw using quads
         gl.glColor3f(0.25f, 0.5f, 1.0f);
         gl.glVertex3f(-0.5f, -0.25f, 0.0f); //upper left
         gl.glVertex3f(-0.25f, -0.25f, 0.0f); //upper right
         gl.glVertex3f(-0.25f, -0.5f, 0.0f); //bottom right
         gl.glVertex3f(-0.5f, -0.5f, 0.0f); //bottom left
         
   gl.glEnd();
   
   gl.glBegin(GL_QUADS); // draw using quads
         gl.glColor3f(0.25f, 0.5f, 1.0f);
         gl.glVertex3f(0.5f, -0.25f, 0.0f); //upper left
         gl.glVertex3f(0.25f, -0.25f, 0.0f); //upper right
         gl.glVertex3f(0.25f, -0.50f, 0.0f); //bottom right
         gl.glVertex3f(0.5f, -0.50f, 0.0f); //bottom left
         
   gl.glEnd(); // end of windows
   
   gl.glBegin(GL_QUADS); // draw using quads
         gl.glColor3f(1.0f, 1.0f, 0.0f);
         gl.glVertex3f(-0.2f, -0.25f, 0.0f); //upper left
         gl.glVertex3f(0.2f, -0.25f, 0.0f); //upper right
         gl.glVertex3f(0.2f, -0.75f, 0.0f); //bottom right
         gl.glVertex3f(-0.2f, -0.75f, 0.0f); //bottom left
         
   gl.glEnd(); // end of door
  
}
   
   @Override
   public void dispose(GLAutoDrawable arg0) {
      //method body
   }
   
   @Override
   public void init(GLAutoDrawable arg0) {
      // method body
   }
   
   @Override
   public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4) {
      // method body
   }
   
   public static void main(String[] args) {
   
      //getting the capabilities object of GL2 profile
      final GLProfile profile = GLProfile.get(GLProfile.GL2);
      GLCapabilities capabilities = new GLCapabilities(profile);
      
      // The canvas
      final GLCanvas glcanvas = new GLCanvas(capabilities);
      CGAssignment r = new CGAssignment();
      glcanvas.addGLEventListener(r);
      glcanvas.setSize(400, 400);
      
      //creating frame
      final JFrame frame = new JFrame ("House");
      
      //adding canvas to frame
      frame.getContentPane().add(glcanvas);
            
      frame.setSize(frame.getContentPane().getPreferredSize());
      frame.setVisible(true);
      
   }//end of main
	
}//end of classimport javax.media.opengl.GL2;
