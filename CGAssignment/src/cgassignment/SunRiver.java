/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cgassignment;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;



/**
 * A 2D OpenGL animation that demonstrates the use of glPushMatrix and glPopMatrix to
 * implement hierarchical modeling.  
 */


public class SunRiver implements GLEventListener {
  
 
   //This program would be better off restructured as an oo scene graph type approach
   //where each sun would have its own pos, theta and scale.
   
   private double pos = -4;  //The position of one of the suns
   private double theta = 0; //The angle of one of the suns.
	
   public static void main(String[] args) {
       // Initialise OpenGL
       GLProfile glp = GLProfile.getDefault();
       GLCapabilities caps = new GLCapabilities(glp);

       // Create a panel to draw on
       GLJPanel panel = new GLJPanel(caps);

       // Put it in a window
       final JFrame jframe = new JFrame("Suns");
       jframe.setSize(1900, 1900);
       jframe.add(panel);
       jframe.setVisible(true);
     
       jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
       // add a GL Event listener to handle rendering
       panel.addGLEventListener(new SunRiver());
       
       // NEW: add an animator to create display events at 60 FPS
       FPSAnimator animator = new FPSAnimator(60);
       animator.add(panel);
       animator.start();
   }
    private float textureOffset;

   /**
    * This method is called when the GLJPanel is created.  It initializes
    * the GL context.  Here, it sets the clear color to be sky blue.
    */
   public void init(GLAutoDrawable drawable) {
      GL2 gl = drawable.getGL().getGL2();
      gl.glClearColor(0.6f, 0.9f, 1f, 1);
      
   }
   
   /**
    * This method is called when the GLJPanel needs to be redrawn.
    * It draws the current frame of the animation.
    */
public void display(GLAutoDrawable drawable) {
   GL2 gl = drawable.getGL().getGL2();
   gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Fills the scene with blue.

   gl.glMatrixMode(GL2.GL_MODELVIEW);
   gl.glLoadIdentity();
   update(); // Update the models

   draw(gl); // Draw the models

   drawRiver(gl); // Draw the river
}

   
public void draw(GL2 gl) {
    gl.glPushMatrix();
    gl.glTranslated(2, 2.5, 0);
    gl.glRotated(theta, 0, 0, 1);
    drawSun(gl);
    gl.glPopMatrix();
}


  
   
   /**
    * Draw a sun with radius 0.5 centered at (0,0).  There are also 13 rays which
    * extend outside from the sun for another 0.25 units.
    */
   private void drawSun(GL2 gl) {
//      gl.glColor3f(1,1,0);
      gl.glColor3f(1.0f, 0.5f, 0.0f);
      gl.glPushMatrix(); 
      for (int i = 0; i < 13; i++) { // Draw 13 rays, with different rotations.
         gl.glRotatef( 360f / 13, 0, 0, 1 ); // Note that the rotations accumulate!
         drawLine(gl,0.75);
      }
      gl.glPopMatrix();
      drawCircle(gl, 0.5);      
   }
   
   /**
    * Draw a 32-sided regular polygon as an approximation for a circular circle.
    * 
    */
   private void drawCircle(GL2 gl, double radius) {
    
	   	 gl.glBegin(GL2.GL_TRIANGLE_FAN);
	  		  gl.glVertex2d(0, 0); //The centre of the circle
              for (int d = 0; d <= 32; d++) {
                  double angle = 2*Math.PI/32 * d;
                  gl.glVertex2d( radius*Math.cos(angle), radius*Math.sin(angle));
              }
          gl.glEnd();    
   }
   
public void drawRiver(GL2 gl) {
   // Set the color for the river
   gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue color

   // Enable texture mapping
   gl.glEnable(GL2.GL_TEXTURE_2D);
   // Bind the texture you want to use for the river
   // Make sure you have the texture file available and loaded
   // Example: gl.glBindTexture(GL2.GL_TEXTURE_2D, textureID);

   // Set the texture coordinates based on the texture offset
   gl.glMatrixMode(GL2.GL_TEXTURE);
   gl.glLoadIdentity();
   gl.glTranslatef(textureOffset, 0.0f, 0.0f);
   

   // Draw the river as a rectangle
   gl.glBegin(GL2.GL_QUADS);
   gl.glTexCoord2f(0.0f, 0.0f);
   gl.glVertex2d(-4.0, -3.0); // Bottom-left corner
   gl.glTexCoord2f(1.0f, 0.0f);
   gl.glVertex2d(4.0, -3.0);  // Bottom-right corner
   gl.glTexCoord2f(1.0f, 1.0f);
   gl.glVertex2d(4.0, -4.0);  // Top-right corner
   gl.glTexCoord2f(0.0f, 1.0f);
   gl.glVertex2d(-4.0, -4.0); // Top-left corner
   gl.glEnd();

   // Disable texture mapping
   gl.glDisable(GL2.GL_TEXTURE_2D);
}

   
   
  


   
   //Draw a line from 0,0 to size
   private void drawLine(GL2 gl, double size) {
       gl.glBegin(GL2.GL_LINES);{
    	   gl.glVertex2d(0,0);
    	   gl.glVertex2d(size,0);
       }
       gl.glEnd();
   }

   //Update the model of the suns positions and rotations
   private void update() {	      
      pos = pos + 0.01;
      if(pos > 5) pos = -4;
      theta = theta + 0.5;
      
      textureOffset += 0.01f;
if (textureOffset > 1.0f) {
   textureOffset -= 1.0f;
}

   }

   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	      GL2 gl = drawable.getGL().getGL2();
	      // The next three lines set up the coordinates system.
	      gl.glMatrixMode(GL2.GL_PROJECTION);
	      gl.glLoadIdentity();
	      GLU glu = new GLU();
	      glu.gluOrtho2D(-4, 4, -4, 4);
   }

  

   @Override
   public void dispose(GLAutoDrawable arg0) {
	   // TODO Auto-generated method stub

   }
}
