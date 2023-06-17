package woods;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

public class Woods implements GLEventListener, KeyListener {

    private Texture logTexture;
    private boolean faceNormals = true;
    private static int angle = 0;
    private static int slices = 32;

    public static void main(String[] args) {
        // initialisation
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setDepthBits(24);

        // create a panel to draw on
        GLJPanel panel = new GLJPanel(caps);

        // put it in a JFrame
        final JFrame jframe = new JFrame("Rotations");
        jframe.setSize(600, 600);
        jframe.add(panel);
        jframe.setVisible(true);

        // Catch window closing events and quit             
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Woods c = new Woods();
        // add a GL Event listener to handle rendering
        panel.addGLEventListener(c);

        panel.addKeyListener(c);
        panel.setFocusable(true);

        // NEW: add an animator to create display events at 60 FPS
        FPSAnimator animator = new FPSAnimator(60);
        animator.add(panel);
        animator.start();
    }

    public void render(GLAutoDrawable drawable, int height, int slices, boolean cylinder) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        double z1 = 0;
        double z2 = -height;
        gl.glPolygonMode(GL.GL_BACK, GL2.GL_FILL);

        //Bind log texture
        logTexture.bind(gl);

        //Front circle of first cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glVertex3d(0, 0, z1);

            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double u = (x0 + 1) / 2.0; // Calculate texture U coordinate
                double v = (y0 + 1) / 2.0; // Calculate texture V coordinate

                gl.glTexCoord2d(u, v); // Texture coordinates
                gl.glVertex3d(x0, y0, z1);
            }
        }
        gl.glEnd();

        //Back circle of first cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {

            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(0, 0, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {

                double a0 = 2 * Math.PI - i * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0, y0, z2);
            }

        }
        gl.glEnd();

        // Bind the log texture
        logTexture.bind(gl);

        //Sides of the first cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                //Calculate vertices for the quad
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                //Calculate texture coordinates
                float u0 = (float) i / slices;
                float v0 = 0.0f;
                float u1 = (float) (i + 1) / slices;
                float v1 = 1.0f;

                //Set texture coordinates for the vertices
                gl.glTexCoord2f(u0, v0);
                gl.glVertex3d(x0, y0, z1);

                gl.glTexCoord2f(u0, v1);
                gl.glVertex3d(x0, y0, z2);

                gl.glTexCoord2f(u1, v1);
                gl.glVertex3d(x1, y1, z2);

                gl.glTexCoord2f(u1, v0);
                gl.glVertex3d(x1, y1, z1);
            }
        }
        gl.glEnd();

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        
        // Draw the second cylinder on the side
        double xShift = 1.5; // Horizontal shift for the second cylinder
        double yShift = 0.3;   // Vertical shift for the second cylinder

        // Bind the log texture
        logTexture.bind(gl);

        // Front circle of the second cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate
            gl.glVertex3d(xShift, yShift, z1);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);
                double u = 0.5 * (x0 + 1); // Normalize x coordinate to range [0, 1]
                double v = 0.5 * (y0 + 1); // Normalize y coordinate to range [0, 1]

                gl.glTexCoord2d(u, v);
                gl.glVertex3d(x0 + xShift, y0 + yShift, z1);
            }
        }
        gl.glEnd();

        // Back circle of the second cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(xShift, yShift, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = 2 * Math.PI - i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0 + xShift, y0 + yShift, z2);
            }
        }
        gl.glEnd();

        // Bind the log texture
        logTexture.bind(gl);

        // Sides of the second cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                if (cylinder) {
                    gl.glNormal3d(x0, y0, 0);
                } else {
                    gl.glNormal3d(-(z2 - z1) * (y1 - y0), (x1 - x0) * (z2 - z1), 0);
                }

                gl.glTexCoord2f(0, 0);
                gl.glVertex3d(x0 + xShift, y0 + yShift, z1);

                gl.glTexCoord2f(0, 1);
                gl.glVertex3d(x0 + xShift, y0 + yShift, z2);

                if (cylinder) {
                    gl.glNormal3d(x1, y1, 0);
                }

                gl.glTexCoord2f(1, 1);
                gl.glVertex3d(x1 + xShift, y1 + yShift, z2);

                gl.glTexCoord2f(1, 0);
                gl.glVertex3d(x1 + xShift, y1 + yShift, z1);
            }
        }
        gl.glEnd();

        // Third Cylinder
        double xShiftThird = -1.5; // Horizontal shift for the third cylinder
        double yShiftThird = -0.3;  // Vertical shift for the third cylinder

        // Bind the log texture
        logTexture.bind(gl);

        // Front circle of the third cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate
            gl.glVertex3d(xShiftThird, yShiftThird, z1);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);
                double u = 0.5 * (x0 + 1); // Normalize x coordinate to range [0, 1]
                double v = 0.5 * (y0 + 1); // Normalize y coordinate to range [0, 1]

                gl.glTexCoord2d(u, v);
                gl.glVertex3d(x0 + xShiftThird, y0 + yShiftThird, z1);
            }
        }
        gl.glEnd();

        // Back circle of the third cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(xShiftThird, yShiftThird, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = 2 * Math.PI - i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0 + xShiftThird, y0 + yShiftThird, z2);
            }
        }
        gl.glEnd();

        // Bind the log texture
        logTexture.bind(gl);

        // Sides of the third cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                if (cylinder) {
                    gl.glNormal3d(x0, y0, 0);
                } else {
                    gl.glNormal3d(-(z2 - z1) * (y1 - y0), (x1 - x0) * (z2 - z1), 0);
                }

                gl.glTexCoord2f(0, 0);
                gl.glVertex3d(x0 + xShiftThird, y0 + yShiftThird, z1);

                gl.glTexCoord2f(0, 1.0f); // Use the full height of the texture vertically
                gl.glVertex3d(x0 + xShiftThird, y0 + yShiftThird, z2);

                if (cylinder) {
                    gl.glNormal3d(x1, y1, 0);
                }

                gl.glTexCoord2f(0.5f, 1.0f); // Use the full width of the texture horizontally
                gl.glVertex3d(x1 + xShiftThird, y1 + yShiftThird, z2);

                gl.glTexCoord2f(0.5f, 0); // Use the full width of the texture horizontally
                gl.glVertex3d(x1 + xShiftThird, y1 + yShiftThird, z1);
            }
        }
        gl.glEnd();

        // Fourth Cylinder
        double xShiftFourth = 0.6; // Horizontal shift for the fourth cylinder
        double yShiftFourth = 1.3;  // Vertical shift for the fourth cylinder

        // Bind the log texture
        logTexture.bind(gl);

        // Front circle of the fourth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate
            gl.glVertex3d(xShiftFourth, yShiftFourth, z1);
            gl.glColor3f(0.1f, 0.1f, 0.1f); // Set shadow color
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate for the shadow vertex
            gl.glVertex3d(xShiftThird, yShiftThird, z1);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);
                double u = 0.5 * (x0 + 1); // Normalize x coordinate to range [0, 1]
                double v = 0.5 * (y0 + 1); // Normalize y coordinate to range [0, 1]

                gl.glTexCoord2d(u, v);
                gl.glVertex3d(x0 + xShiftFourth, y0 + yShiftFourth, z1);
            }
        }
        gl.glEnd();

        // Back circle of the fourth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(xShiftFourth, yShiftFourth, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = 2 * Math.PI - i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0 + xShiftFourth, y0 + yShiftFourth, z2);
            }
        }
        gl.glEnd();

        // Sides of the fourth cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                if (cylinder) {
                    gl.glNormal3d(x0, y0, 0);
                } else {
                    gl.glNormal3d(-(z2 - z1) * (y1 - y0), (x1 - x0) * (z2 - z1), 0);
                }

                gl.glTexCoord2f(0, 0);
                gl.glVertex3d(x0 + xShiftFourth, y0 + yShiftFourth, z1);

                gl.glTexCoord2f(0, 1.0f);
                gl.glVertex3d(x0 + xShiftFourth, y0 + yShiftFourth, z2);

                if (cylinder) {
                    gl.glNormal3d(x1, y1, 0);
                }

                gl.glTexCoord2f(0.5f, 1.0f);
                gl.glVertex3d(x1 + xShiftFourth, y1 + yShiftFourth, z2);

                gl.glTexCoord2f(0.5f, 0);
                gl.glVertex3d(x1 + xShiftFourth, y1 + yShiftFourth, z1);
            }
        }
        gl.glEnd();

        // Fifth Cylinder
        double xShiftFifth = -1; // Horizontal shift for the fifth cylinder
        double yShiftFifth = 1;  // Vertical shift for the fifth cylinder

        // Bind the log texture
        logTexture.bind(gl);

        // Front circle of the fifth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate
            gl.glVertex3d(xShiftFifth, yShiftFifth, z1);
            gl.glColor3f(0.1f, 0.1f, 0.1f); // Set shadow color
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate for the shadow vertex
            gl.glVertex3d(xShiftThird, yShiftThird, z1);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);
                double u = 0.5 * (x0 + 1); // Normalize x coordinate to range [0, 1]
                double v = 0.5 * (y0 + 1); // Normalize y coordinate to range [0, 1]

                gl.glTexCoord2d(u, v);
                gl.glVertex3d(x0 + xShiftFifth, y0 + yShiftFifth, z1);
            }
        }
        gl.glEnd();

        // Back circle of the fifth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(xShiftFifth, yShiftFifth, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = 2 * Math.PI - i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0 + xShiftFifth, y0 + yShiftFifth, z2);
            }
        }
        gl.glEnd();

        // Sides of the fifth cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                if (cylinder) {
                    gl.glNormal3d(x0, y0, 0);
                } else {
                    gl.glNormal3d(-(z2 - z1) * (y1 - y0), (x1 - x0) * (z2 - z1), 0);
                }

                gl.glTexCoord2f(0, 0);
                gl.glVertex3d(x0 + xShiftFifth, y0 + yShiftFifth, z1);

                gl.glTexCoord2f(0, 1.0f);
                gl.glVertex3d(x0 + xShiftFifth, y0 + yShiftFifth, z2);

                if (cylinder) {
                    gl.glNormal3d(x1, y1, 0);
                }

                gl.glTexCoord2f(0.5f, 1.0f);
                gl.glVertex3d(x1 + xShiftFifth, y1 + yShiftFifth, z2);

                gl.glTexCoord2f(0.5f, 0);
                gl.glVertex3d(x1 + xShiftFifth, y1 + yShiftFifth, z1);
            }
        }
        gl.glEnd();

        // Sixth Cylinder
        double xShiftSixth = -0.3; // Horizontal shift for the sixth cylinder
        double yShiftSixth = 2.3;  // Vertical shift for the sixth cylinder

        // Bind the log texture
        logTexture.bind(gl);

        // Front circle of the sixth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, 1);
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate
            gl.glVertex3d(xShiftSixth, yShiftSixth, z1);
            gl.glColor3f(0.1f, 0.1f, 0.1f); // Set shadow color
            gl.glTexCoord2d(0.5, 0.5); // Center texture coordinate for the shadow vertex
            gl.glVertex3d(xShiftFifth, yShiftFifth, z1);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);
                double u = 0.5 * (x0 + 1); // Normalize x coordinate to range [0, 1]
                double v = 0.5 * (y0 + 1); // Normalize y coordinate to range [0, 1]

                gl.glTexCoord2d(u, v);
                gl.glVertex3d(x0 + xShiftSixth, y0 + yShiftSixth, z1);
            }
        }
        gl.glEnd();

        // Back circle of the sixth cylinder
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            gl.glNormal3d(0, 0, -1);
            gl.glVertex3d(xShiftSixth, yShiftSixth, z2);
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = 2 * Math.PI - i * angleStep;
                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                gl.glVertex3d(x0 + xShiftSixth, y0 + yShiftSixth, z2);
            }
        }
        gl.glEnd();

        // Sides of the sixth cylinder
        gl.glBegin(GL2.GL_QUADS);
        {
            double angleStep = 2 * Math.PI / slices;
            for (int i = 0; i <= slices; i++) {
                double a0 = i * angleStep;
                double a1 = ((i + 1) % slices) * angleStep;

                double x0 = Math.cos(a0);
                double y0 = Math.sin(a0);

                double x1 = Math.cos(a1);
                double y1 = Math.sin(a1);

                if (cylinder) {
                    gl.glNormal3d(x0, y0, 0);
                } else {
                    gl.glNormal3d(0, 0, 1);
                }

                gl.glTexCoord2f(0, 0);
                gl.glVertex3d(x0 + xShiftSixth, y0 + yShiftSixth, z1);

                gl.glTexCoord2f(0, 1.0f);
                gl.glVertex3d(x0 + xShiftSixth, y0 + yShiftSixth, z2);

                if (cylinder) {
                    gl.glNormal3d(x1, y1, 0);
                }

                gl.glTexCoord2f(0.5f, 1.0f);
                gl.glVertex3d(x1 + xShiftSixth, y1 + yShiftSixth, z2);

                gl.glTexCoord2f(0.5f, 0);
                gl.glVertex3d(x1 + xShiftSixth, y1 + yShiftSixth, z1);
            }
        }
        gl.glEnd();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -8);

        // Set the camera position and orientation
        double cameraDistance = 4.0;
        double cameraHeight = 0.0;
        double cameraAngle = 55.0; // Rotation angle around Y-axis

        double cameraX = cameraDistance * Math.sin(Math.toRadians(cameraAngle));
        double cameraY = cameraHeight;
        double cameraZ = -cameraDistance * Math.cos(Math.toRadians(cameraAngle));

        gl.glTranslatef(0, 0, (float) -cameraDistance);
        gl.glRotatef((float) cameraAngle, 0, 1, 0);
        gl.glTranslatef(0, (float) -cameraHeight, 0);

        render(drawable, 4, slices, !faceNormals);

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        logTexture.destroy(gl);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);

        try {
            logTexture = TextureIO.newTexture(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\CGAssignment\\CGAssignment\\src\\texture\\log.jpg"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

        // enable lighting
        gl.glEnable(GL2.GL_LIGHTING);
        // turn on a light. Use default settings.
        gl.glEnable(GL2.GL_LIGHT0);
        float r = 0.24f;
        float g = 0.13f;
        float b = 0.08f;

        float[] materialAmbient = {r, g, b, 1.0f};
        float[] materialDiffuse = {r, g, b, 1.0f};
        float[] materialSpecular = {1.0f, 1.0f, 1.0f, 1.0f};
        float materialShininess = 32.0f;

        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, materialAmbient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materialDiffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, materialSpecular, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, materialShininess);

        // normalise normals (!)
        // this is necessary to make lighting work properly
        gl.glEnable(GL2.GL_NORMALIZE);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glOrtho(-6, 6, -6, 6, 1, 100);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (e.getKeyCode()) {

            case KeyEvent.VK_SPACE:
                faceNormals = !faceNormals;
                break;

            case KeyEvent.VK_DOWN:

                angle = (angle - 10) % 360;
                break;
            case KeyEvent.VK_UP:

                angle = (angle + 10) % 360;
                break;

            case KeyEvent.VK_RIGHT:

                if (slices < 100) {
                    slices++;
                }
                break;
            case KeyEvent.VK_LEFT:

                if (slices > 5) {
                    slices--;
                }
                break;

            default:
                break;
        }
        System.out.println("face normals " + faceNormals);

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
