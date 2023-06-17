/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cgassignment;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import static com.jogamp.opengl.GL.GL_TRIANGLES;
import com.jogamp.opengl.GL2;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
//import com.jogamp.opengl.util.texture.TextureIO;
//import java.io.File;
//import java.io.IOException;

public class CGAssignment implements GLEventListener{
    
    private double pos = -4;  //The position of one of the suns
    private double theta = 0; //The angle of one of the suns.
    
    /*private Texture woodBodyTexture;
    private Texture clothSailsTexture;
    private Texture woodPoleTexture;*/
    
    public static void main(String[] args) {
           // Initialise OpenGL
       GLProfile glp = GLProfile.getDefault();
       GLCapabilities caps = new GLCapabilities(glp);

       // Create a panel to draw on
       GLJPanel panel = new GLJPanel(caps);

       // Put it in a window
       final JFrame jframe = new JFrame("Assignment");
       jframe.setSize(900, 700);
       jframe.add(panel);
       jframe.setVisible(true);
     
       jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
       // add a GL Event listener to handle rendering
       panel.addGLEventListener(new CGAssignment());
       
       // NEW: add an animator to create display events at 60 FPS
       FPSAnimator animator = new FPSAnimator(60);
       animator.add(panel);
       animator.start();
    }
    
    private float textureOffset;
    @Override
    public void display(GLAutoDrawable drawable) {
           GL2 gl = drawable.getGL().getGL2();
   gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Fills the scene with blue.

   gl.glMatrixMode(GL2.GL_MODELVIEW);
   gl.glLoadIdentity();
   
   
   update(); // Update the models
   draw(gl); // Draw the models
   
   drawGrass(gl);
   drawRiver(gl); // Draw the river
   drawHills(gl);
   drawBoat(gl);
   drawHouse(gl);
   drawClouds(gl);
   drawTree(gl);
   
   }
    
       @Override
   public void dispose(GLAutoDrawable drawable) {
        //GL2 gl = drawable.getGL().getGL2();
        //woodBodyTexture.destroy(gl);
        //clothSailsTexture.destroy(gl);
       // woodPoleTexture.destroy(gl);
   }
   
   @Override
   public void init(GLAutoDrawable drawable) {
       
       
      GL2 gl = drawable.getGL().getGL2();
      gl.glClearColor(0.6f, 0.9f, 1f, 1);
      
      /*gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        try {
            woodBodyTexture = TextureIO.newTexture(new File("C:\\Users\\Asus\\Desktop\\um stuffs\\CLASS\\WIG2002 - Computer Graphics\\CG_Assignment\\CGAssignment\\src\\texture\\wood_body.jpg"), true);
            clothSailsTexture = TextureIO.newTexture(new File("C:\\Users\\Asus\\Desktop\\um stuffs\\CLASS\\WIG2002 - Computer Graphics\\CG_Assignment\\CGAssignment\\src\\texture\\cloth_sails.jpg"), true);
            woodPoleTexture = TextureIO.newTexture(new File("C:\\Users\\Asus\\Desktop\\um stuffs\\CLASS\\WIG2002 - Computer Graphics\\CG_Assignment\\CGAssignment\\src\\texture\\wood_pole.jpg"), true);

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
     */
   }
   
   @Override
   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	      GL2 gl = drawable.getGL().getGL2();
	      // The next three lines set up the coordinates system.
	      gl.glMatrixMode(GL2.GL_PROJECTION);
	      gl.glLoadIdentity();
	      GLU glu = new GLU();
	      glu.gluOrtho2D(-4, 4, -4, 4);
   }
   
   public void draw(GL2 gl) {
    gl.glPushMatrix();
    gl.glTranslated(2, 2.5, 0);
    gl.glRotated(theta, 0, 0, 1);
    drawSun(gl);
    gl.glPopMatrix();
    }
   
   private void drawGrass(GL2 gl) {
    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.0f, 0.5f, 0.5f);
    gl.glVertex3f(-5.0f, -1.0f, 0.0f);
    gl.glVertex3f(5.0f, -1.0f, 0.0f);
    gl.glVertex3f(5.0f, -3.0f, 0.0f);
    gl.glVertex3f(-5.0f, -3.0f, 0.0f);
    gl.glEnd();       
   }
   
   private void drawBoat(GL2 gl) {
       
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glPushMatrix();
    gl.glTranslated(2.0, -2.0, 0.0);
    gl.glScaled(2.0, 2.0, 0.0);
    
       //gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //gl.glClear(GL.GL_COLOR_BUFFER_BIT);
       // gl.glLoadIdentity();

        // Bind the wood texture
        //woodBodyTexture.bind(gl);

        // Boat body 
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(0.65f, 0.50f, 0.39f);
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
        //woodPoleTexture.bind(gl);

        // Pole 
        gl.glBegin(GL2.GL_POLYGON);
        gl.glColor3f(0.65f, 0.50f, 0.39f);
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
        //clothSailsTexture.bind(gl);

        // Big sail
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glColor3f(0.96f, 0.80f, 0.69f);
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
            gl.glPopMatrix();
          // gl.glDisable(GL2.GL_TEXTURE_2D);
   }
   
   private void drawTree(GL2 gl) {
       
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslated(3.0, -0.1, 0.0);
        gl.glScaled(3.0, 3.0, 0.0);
        
        // Draw the trunk
        gl.glColor3f(0.54f, 0.27f, 0.07f); // Brown color
        gl.glBegin(GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.05f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.05f, -0.5f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex2f(0.05f, -0.9f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex2f(-0.05f, -0.9f);
        gl.glEnd();

        // Bind the leaves texture
        //leavetexture.bind(gl);

        // Draw the branches
        gl.glColor3f(0.0f, 0.5f, 0.0f); // Green color
        gl.glBegin(GL_TRIANGLES);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.4f, -0.5f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.4f, -0.5f);
        gl.glTexCoord2f(0.5f, 1.0f);
        gl.glVertex2f(0.0f, 0.2f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.35f, -0.3f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.35f, -0.3f);
        gl.glTexCoord2f(0.5f, 1.0f);
        gl.glVertex2f(0.0f, 0.3f);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex2f(-0.3f, -0.1f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex2f(0.3f, -0.1f);
        gl.glTexCoord2f(0.5f, 1.0f);
        gl.glVertex2f(0.0f, 0.4f);
        gl.glEnd();
        
        gl.glPopMatrix();
   }
private void drawHouse(GL2 gl) {
    
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glPushMatrix();
    gl.glTranslated(-2.0, -0.2, 0.0);
    gl.glScaled(3.0, 3.0, 0.0);
    

    // house roof
    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.2f, 0.1f, 0.0f);
    gl.glVertex3f(-0.3f, 0.3f, 0.0f);
    gl.glVertex3f(0.3f, 0.3f, 0.0f);
    gl.glVertex3f(0.6f, 0.0f, 0.0f);
    gl.glVertex3f(-0.6f, 0.0f, 0.0f);
    gl.glEnd();

    // house body
    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.910f, 0.76f, 0.65f);
    gl.glVertex3f(-0.6f, 0.0f, 0.0f);
    gl.glVertex3f(0.6f, 0.0f, 0.0f);
    gl.glVertex3f(0.6f, -0.75f, 0.0f);
    gl.glVertex3f(-0.6f, -0.75f, 0.0f);
    gl.glEnd();

    // windows
    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.25f, 0.5f, 1.0f);
    gl.glVertex3f(-0.5f, -0.25f, 0.0f);
    gl.glVertex3f(-0.25f, -0.25f, 0.0f);
    gl.glVertex3f(-0.25f, -0.5f, 0.0f);
    gl.glVertex3f(-0.5f, -0.5f, 0.0f);
    gl.glEnd();

    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.25f, 0.5f, 1.0f);
    gl.glVertex3f(0.5f, -0.25f, 0.0f);
    gl.glVertex3f(0.25f, -0.25f, 0.0f);
    gl.glVertex3f(0.25f, -0.50f, 0.0f);
    gl.glVertex3f(0.5f, -0.50f, 0.0f);
    gl.glEnd();

    // door
    gl.glBegin(GL_QUADS);
    gl.glColor3f(0.85f, 0.53f, 0.10f);
    gl.glVertex3f(-0.2f, -0.25f, 0.0f);
    gl.glVertex3f(0.2f, -0.25f, 0.0f);
    gl.glVertex3f(0.2f, -0.75f, 0.0f);
    gl.glVertex3f(-0.2f, -0.75f, 0.0f);
    gl.glEnd();

    gl.glPopMatrix();
}

private void drawHills(GL2 gl) {
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    
    // Set the number of hill duplicates and their positions
    int numDuplicates = 3;
    double[] translationX = { 3.0, -0.5, -3.0 };  // X-axis translations
    double[] translationY = { 0.0, 0.0, 0.0 };   // Y-axis translations
    double[] translationZ = { 0.0, 0.0, 0.0 };   // Z-axis translations
    
    gl.glPushMatrix();
    
    // Loop through each duplicate
    for (int i = 0; i < numDuplicates; i++) {
        gl.glPushMatrix();
        gl.glTranslated(translationX[i], translationY[i], translationZ[i]);
        drawHill(gl); // Call the method to draw the hill
        gl.glPopMatrix();
    }
    
    gl.glPopMatrix();
}

private void drawHill(GL2 gl) {
    gl.glPushMatrix();
    gl.glTranslated(-1.0, -0.2, 0.0);
    gl.glScaled(8.0, 8.0, 0.0);
   
    gl.glBegin(GL2.GL_TRIANGLES);        // Drawing Using Triangles
    gl.glColor3f(0.38f, 0.41f, 0.36f);
    gl.glVertex2f(0.450f, -0.1f);       // Bottom Right
    gl.glVertex2f(-0.05f, -0.1f);      // Bottom Left
    gl.glVertex2f(0.2f, 0.225f);      // Top
    gl.glEnd();
   
    gl.glBegin(GL2.GL_POLYGON);
    gl.glColor3f(1.25f, 0.924f, 0.930f);
    gl.glVertex2f(0.2f, 0.225f);
    gl.glVertex2f(0.23f, 0.19f);
    gl.glVertex2f(0.22f, 0.18f);
    gl.glVertex2f(0.2f, 0.19f);
    gl.glVertex2f(0.19f, 0.18f);
    gl.glVertex2f(0.17f, 0.19f);
    gl.glEnd();
    
    gl.glPopMatrix();
}

private void drawClouds(GL2 gl) {
    gl.glMatrixMode(GL2.GL_MODELVIEW);
    gl.glLoadIdentity();
    
    // Set the number of hill duplicates and their positions
    int numDuplicates = 3;
    double[] translationX = { 3.0, 0.0, -3.0 };  // X-axis translations
    double[] translationY = { 1.0, 3.0, 2.0 };   // Y-axis translations
    double[] translationZ = { 0.5, 1.0, 0.0 };   // Z-axis translations

    double[] scaleX = { 1.0, 0.5, 1.5 };         // Scaling factors along the X-axis
    double[] scaleY = { 1.0, 0.5, 1.5 };         // Scaling factors along the Y-axis
    double[] scaleZ = { 1.0, 0.5, 1.5 };         // Scaling factors along the Z-axis
    gl.glPushMatrix();
    
    // Loop through each duplicate
    for (int i = 0; i < numDuplicates; i++) {
        gl.glPushMatrix();
        gl.glTranslated(translationX[i], translationY[i], translationZ[i]);
        gl.glScaled(scaleX[i], scaleY[i], scaleZ[i]);
        drawCloud(gl); // Call the method to draw the hill
        gl.glPopMatrix();
    }
    
    gl.glPopMatrix();
}
   
   private void drawCloud(GL2 gl){

        
        // Set the color for the cloud
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        
        // Draw the cloud using multiple circle shapes
        drawCircle2(gl, 0.0f, 0.2f, 0.2f);
        drawCircle2(gl, -0.15f, 0.1f, 0.15f);
        drawCircle2(gl, 0.15f, 0.1f, 0.15f);
        drawCircle2(gl, -0.3f, 0.0f, 0.15f);
        drawCircle2(gl, 0.15f, 0.0f, 0.15f);
        drawCircle2(gl, -0.15f, 0.0f, 0.15f);
        drawCircle2(gl, 0.3f, 0.0f, -0.15f);
        drawCircle2(gl, 0.0f, 0.0f, 0.15f);
   }
   
    private void drawCircle2(GL2 gl, float centerX, float centerY, float radius) {
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
    private void drawSun(GL2 gl) {
    //  gl.glColor3f(1,1,0);
      gl.glColor3f(1.0f, 0.5f, 0.0f);
      gl.glPushMatrix(); 
      for (int i = 0; i < 13; i++) { // Draw 13 rays, with different rotations.
         gl.glRotatef( 360f / 13, 0, 0, 1 ); // Note that the rotations accumulate!
         drawLine(gl,0.75);
      }
      gl.glPopMatrix();
      drawCircle(gl, 0.5);      
   }
    
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

   //Enable texture mapping
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
}

