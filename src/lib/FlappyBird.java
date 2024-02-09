package lib;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

//, it means that FlappyBirth inherits from JPanel. This is a key concept of inheritance in Java, allowing FlappyBirth to use the properties and methods of JPanel in addition to its own.
public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWith = 360;
    int boardHigh = 640;

    // images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird
    int birdX = boardWith / 8;
    int birdY = boardHigh / 2;
    int birdWidth = 34;
    int birthHight = 24;

    // Class to hold the values
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birthHight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipes of the dimentions
    int pipeX = boardWith;
    int pipeY = 0;
    int pipeWidth = 64; // scalled by 1/6
    int pipeHight = 512;

    // function for the Pipes
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHight;
        Image img;
        boolean passed = false; // keep track of the score

        Pipe(Image img) {
            this.img = img;
        }
    }

    // Game logic
    Bird bird;
    int velocityX = -4; // moves pipes to the left speed (stimulates bird moving right)
    int velocityY = 0; // the game is going to start follwing down
    int gravity = 1; // push the bird down

    ArrayList<Pipe> pipes; // because we have so many pipes we have to create a list
    Random random = new Random();

    // adding a loop per frame so the bird can move
    Timer gameLoop;
    Timer placePipesTimer;
    boolean gameOver = false;
    double score = 0;

    // Constructor
    FlappyBird() {
        setPreferredSize(new Dimension(boardWith, boardHigh));
        // setBackground(Color.blue);
        setFocusable(true); // is going to make sure this is going to take our keypress
        addKeyListener(this);

        // Here is where we load images to the background image variable
        backgroundImg = new ImageIcon(getClass().getResource("../flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("../flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("../toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("../bottompipe.png")).getImage();

        // Bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // place pipes timer
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes(); // A timer that is going to called placepipes every 1500

            }
        });
        placePipesTimer.start();

        // game timer so it can draw the frames per draw
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

    }

    public void placePipes() {
        // (0-1) * pipeHeight/2 -> (0-256)
        // How much you are swiching up
        // Here is where the pippes has shifting by a random amout to go up and see the
        // bird as a game
        int randomPipeY = (int) (pipeY - pipeHight/4 - Math.random() * (pipeHight/2));
        int openingSpace = boardHigh / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
        // oPENING SPACE SO THE BIRD CAN FLY ON
        Pipe bottonPipe = new Pipe(bottomPipeImg);
        bottonPipe.y = topPipe.y + pipeHight + openingSpace;
        pipes.add(bottonPipe);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // System.out.println("draw");
        // background
        g.drawImage(backgroundImg, 0, 0, boardWith, boardHigh, null);

        // draw the bird within the draw function
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        // drawing the pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipeHight, null);

        }
        // score
        g.setColor(Color.red); // Establece el color a rosa

// Establece la fuente a "Arial", estilo negrita, tamaño 32
    g.setFont(new Font("Arial", Font.BOLD, 32));
        // going to make a check when gameover and display the score
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }

    // velocity function
    public void move() {
        // bird postions of the objects up or down
        velocityY += gravity;
        bird.y += velocityY; // per frame
        bird.y = Math.max(bird.y, 0); // Is moving up

        // pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            // bird has not passed we are checking of the x
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
           

                // If user toches the pipe than we stop painting and the game stops
                if (collision(bird, pipe)) {
                    gameOver = true;
                }

                else if (bird.y > boardHigh) {
                    gameOver = true;
                } else {
                    score += 0.5; // 0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
                    pipe.passed = true;
                }

            }
        }
    }

    // creating a function to detenting collison
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width && // a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x && // a's top right corner passes b's top left corner
                a.y < b.y + b.height && // a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y; // a's bottom left corner passes b's top left corner
    }

    // Action perform for the bird
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // 60 times per sec we are moving
        repaint();
        // The game is going to stop if the user looses the game the pipes stop
        if (gameOver) {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // press on the key is going to do a check
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9;
            if (gameOver) {
                //restart game by resetting conditions all the variables to the deafaul values 
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipesTimer.start();
            }

        }
    }

    // type on the key that as a character
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
