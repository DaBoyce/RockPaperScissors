package main;
import javax.swing.JFrame;

public class GameWindow {
    private JFrame jframe;
    private final int WINDOW_SIZE = 600;
    
    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame();

        jframe.setSize(WINDOW_SIZE, WINDOW_SIZE);
        jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
