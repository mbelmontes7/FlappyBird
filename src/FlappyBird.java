import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.random.*;
import javax.swing.*;

//, it means that FlappyBirth inherits from JPanel. This is a key concept of inheritance in Java, allowing FlappyBirth to use the properties and methods of JPanel in addition to its own.
public class FlappyBird extends JPanel {
    int boardWith = 360;
    int boardHigh = 640; 

    //Constructor 
    FlappyBird () {
        setPreferredSize( new Dimension(boardWith, boardHigh));
        setBackground(Color.blue);
    }
}
