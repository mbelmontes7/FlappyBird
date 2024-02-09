package lib;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        //Dimention of the windows and this are going to be in pix
        int boardWith = 360; 
        int boardHigh = 640; 

        // A Frame is a top-level window with a title and a border. 
        JFrame frame = new JFrame("Flappy Bird");
        // frame.setVisible(true);;
        frame.setSize(boardWith, boardHigh);
        frame.setLocationRelativeTo(null); //Place the window at the center of the sceen 
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird FlappyBird = new FlappyBird();
        frame.add(FlappyBird);
        //Causes this Window to be sized to fit the preferred size and layouts of its subcomponents
        frame.pack(); 
        FlappyBird.requestFocus();
        frame.setVisible(true);;


    }
}
