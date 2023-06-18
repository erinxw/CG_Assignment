/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cgassignment;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
/**
 *
 * @author Anneisha
 */
public class duckOLD extends JPanel implements ActionListener {

    private int x;
    private int y;
    private Timer timer;

    public duckOLD() {
        x = 0;
        y = 150;
        setPreferredSize(new Dimension(500, 300));
        timer = new Timer(10, this);
        timer.start();
    }

    private void drawBird(Graphics2D g2d) {
    g2d.setStroke(new BasicStroke(2.0f));
    g2d.setColor(Color.YELLOW);

    
    // Head
    g2d.fillOval(x + 43, y - 5, 20, 20);
    g2d.setColor(Color.BLACK);
    g2d.drawOval(x + 43, y - 5, 20, 20);
    
    // Eyes
    int eyeX1 = x + 58;
    int eyeY = y + 5;
    int eyeDiameter = 6;  // Increase the diameter for bigger eyes
    g2d.fillOval(eyeX1 - eyeDiameter / 2, eyeY - eyeDiameter / 2, eyeDiameter, eyeDiameter);
    
     // Beak     
    int beakX1 = x + 60;
    int beakY1 = y + 5;    // Adjust the y-coordinate to move the beak up
    int beakX2 = x + 75;
    int beakY2 = y + 12;   // Adjust the y-coordinate to move the beak up
    g2d.drawLine(beakX1, beakY1, beakX2, beakY2);
    g2d.drawLine(beakX2, beakY2, beakX1, beakY2);


    
    // Body
    g2d.setColor(Color.YELLOW);
    g2d.fillOval(x, y, 50, 30);
    g2d.setColor(Color.BLACK);
    g2d.drawOval(x, y, 50, 30);
    
    

    // Wings
//    g2d.drawLine(x + 20, y + 10, x - 10, y + 10);
//    g2d.drawLine(x + 20, y + 10, x + 10, y - 10);
//    g2d.drawLine(x + 20, y + 10, x + 30, y - 10);
//    g2d.drawLine(x + 20, y + 10, x + 10, y + 30);

    int wingX1 = x + 5;
    int wingY1 = y + 15;
    int wingX2 = x + 20;
    int wingY2 = y + 5;
    int wingX3 = x + 35;
    int wingY3 = y + 15;
    
    g2d.drawLine(wingX1, wingY1, wingX2, wingY2);
    g2d.drawLine(wingX2, wingY2, wingX3, wingY3);

 
    // Tail
//    g2d.drawLine(x, y + 15, x - 10, y + 5);
//    g2d.drawLine(x, y + 15, x - 10, y + 25);
    
}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBird(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x++;
        if (x > getWidth() + 50) {
            x = -50;
        }
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flying Bird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new duckOLD());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

