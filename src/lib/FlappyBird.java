package lib;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.random.*;
import javax.swing.*;

//, it means that FlappyBirth inherits from JPanel. This is a key concept of inheritance in Java, allowing FlappyBirth to use the properties and methods of JPanel in addition to its own.
public class FlappyBird extends JPanel {
    int boardWith = 360;
    int boardHigh = 640; 

    //images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;


    //Constructor 
    FlappyBird () {
        setPreferredSize( new Dimension(boardWith, boardHigh));
        // setBackground(Color.blue);

        //Here is where we load images to the background image variable 
        backgroundImg = new ImageIcon(getClass().getResource("../flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("../flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("../toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("../bottompipe.png")).getImage();

    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWith, boardHigh, null);
    }
}
