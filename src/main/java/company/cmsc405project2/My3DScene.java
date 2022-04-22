/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * File Name   : My3DScene.java
 * Author      : Wanner HernandezR
 * Course      : CMSC405 week 2 Project 2
 * Created on  : 04/8/2021
 * Professor   : Jarc Duane
 **/
  
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
  
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
  
@SuppressWarnings("serial")
public class My3DScene extends JPanel implements GLEventListener
{
  
public static void main(String[] args)
{
JFrame window = new JFrame("My 3D Graphics Scene");
My3DScene panel = new My3DScene();
window.setContentPane(panel);
window.pack(); // Set window size based on the preferred sizes of its contents.
window.setResizable(false);
Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
window.setLocation( // Center window on screen.
(screen.width - window.getWidth())/2,
(screen.height - window.getHeight())/2 );
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
window.setVisible(true);
}
  
private MyShapes myShapes = new MyShapes(); // An instance of the MyShpaes class containing all the shapes that I have drawn.
private int frameNumber; // Current frame number, increases by 1 in each frame.
private Timer animationTimer;
  
public My3DScene()
{
GLCapabilities caps = new GLCapabilities(null);
  
GLJPanel display = new GLJPanel(caps);
display.setPreferredSize( new Dimension(640,480) ); //sets display size of the viewing screen to 640-by-480
display.addGLEventListener(this);
setLayout(new BorderLayout());
add(display, BorderLayout.CENTER);
  
//start the animation
animationTimer = new Timer(20, new ActionListener() {
public void actionPerformed(ActionEvent evt) {
frameNumber++;
display.repaint();
}
});
animationTimer.start();
}
  
//Methods of the GLEventListener interface
public void init(GLAutoDrawable drawable)
{
// called when the panel is created
GL2 gl2 = drawable.getGL().getGL2();
  
gl2.glEnable(GL2.GL_DEPTH_TEST); // Required for 3D drawing, not usually for 2D.
gl2.glLineWidth(2);
gl2.glShadeModel(GL2.GL_SMOOTH);
gl2.glEnable(GL2.GL_COLOR_MATERIAL);
gl2.glClearColor(0.5f,0.5f,0.5f,1); // Set background color
  
// The next three lines set up the coordinates system.
gl2.glMatrixMode(GL2.GL_PROJECTION);
gl2.glLoadIdentity();
gl2.glOrtho(-10, 10, -10, 10, -5, 5); //sets up the orthographic projection with the near and far perspective to 5.
gl2.glMatrixMode(GL2.GL_MODELVIEW);
  
  
gl2.glClearDepth(1.0f);
gl2.glDepthFunc(GL2.GL_LEQUAL);
gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
  
//turn transparency on
gl2.glEnable(GL2.GL_BLEND);
gl2.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
  
}
  
@SuppressWarnings("static-access")
public void display(GLAutoDrawable drawable)
{
GL2 gl2 = drawable.getGL().getGL2();
gl2.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT ); // need depth buffer for 3D.
  
gl2.glLoadIdentity();
  
/* Draw the 3D Triangle. The drawTriangle method draws the shape with the center at (0,0).
* The image will be rotating in an infinite loop about the z-axis
* It will be uniformly scaled by 1.5 so that it's displayed proportionally on the window.
* It will be rotated 90 degrees about the line through the points (0,0,0) and (5,5,5).
* A translation is applied to move the triangle to the coordinate (-6,5,0).
*/
  
gl2.glPushMatrix();
gl2.glLineWidth(2);
gl2.glTranslated(-6.0,5.0,0);
gl2.glRotated(90,1.5,2,-3);
gl2.glScaled(1.5,1.5,1.5);
gl2.glRotated(frameNumber*0.7,0,0,1);
myShapes.drawTriangle(gl2);
gl2.glPopMatrix();
  
  
// Draw the 3D Cube.
gl2.glPushMatrix();
gl2.glLineWidth(1);
gl2.glTranslated(0,5.0,0);
gl2.glRotated(30,1.5,-2,3);
gl2.glScaled(1.5,1.5,1.5);
gl2.glRotated(-frameNumber*0.7,1,0,0);
myShapes.drawCube(gl2);
gl2.glPopMatrix();
  
  
// Draw the 3D Cylinder.
gl2.glPushMatrix();
gl2.glTranslated(2*Math.cos(.015*frameNumber) + 0, 2*Math.sin(.015*frameNumber) + 0, 0.0);
gl2.glTranslated(6.0,5.0,0);
gl2.glRotated(45,1.5,-2,3);
gl2.glScaled(1.6,1.6,1.6);
gl2.glRotated(-frameNumber*0.7,0,1,0);
myShapes.drawCylinder(gl2,0.5,1,32,10,5);
gl2.glPopMatrix();
  
  
// Draw the 3D Plus symbol.

gl2.glPushMatrix();
gl2.glLineWidth(2);
gl2.glTranslated(-12 + 24*(frameNumber % 500) / 500.0, 0, 0);
gl2.glRotated(45,1.5,-2,3);
gl2.glScaled(.275,.275,.275);
gl2.glRotated(-frameNumber*0.7,0,1,0);
myShapes.drawPlusSymbol(gl2);
gl2.glPopMatrix();
  
  
// Draw the 3D Tetrahedron.
gl2.glPushMatrix();
gl2.glLineWidth(1);
gl2.glTranslated(-6.0,-5.0,0);
gl2.glRotated(20,1.5,-2,3);
gl2.glScaled(.99*(frameNumber % 250) /250,.99*(frameNumber % 250) / 250,.99*(frameNumber % 250) / 250);
gl2.glRotated(-frameNumber*0.7,1,0,0);
myShapes.drawTetrahedron(gl2);
gl2.glPopMatrix();
  
  
// Draw the 3D Icosahedron.
gl2.glPushMatrix();
gl2.glLineWidth(2);
gl2.glTranslated(0.0,-5.0,0);
gl2.glRotated(30,1.5,-2,3);
gl2.glScaled(1.1,1.1,1.1);
gl2.glRotated(-frameNumber*0.7,1,0,0);
myShapes.drawIcosahedron(gl2);
gl2.glPopMatrix();
  
// Draw the 3D Diamond.
gl2.glPushMatrix();
gl2.glLineWidth(2);
gl2.glTranslated(6.0,-5.0,0);
gl2.glRotated(35,1.5,-2,3);
gl2.glScaled(.35,.35,.35);
gl2.glRotated(-frameNumber*0.7,0,1,0);
myShapes.drawDiamond(gl2);
gl2.glPopMatrix();
}
  
public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height){
  
}
  
public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
}
  
public void dispose(GLAutoDrawable arg0) {
}
}
