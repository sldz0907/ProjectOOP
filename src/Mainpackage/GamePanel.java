package Mainpackage;
import Input.Keyboard;
import Input.Mouse;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private Game game;
    public GamePanel(int gameWidth, int gameHeight, Game game)
    {
        this.game = game;
        
        setPanelSize(gameWidth, gameHeight);
        setFocusable(true); // Cho phép panel nhận focusaaaaaaa
        requestFocusInWindow(); // Yêu cầu focus khi panel được hiển thị
        super.addKeyListener(new Keyboard(this));

        Mouse mouse = new Mouse(this);
        super.addMouseListener(mouse);
        super.addMouseMotionListener(mouse);
        
    }
    private void setPanelSize(int gameWidth,int gameHeight)
    {
        Dimension size = new Dimension(gameWidth,gameHeight);
        super.setMaximumSize(size);
        super.setPreferredSize(size);
        super.setMinimumSize(size);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        game.render(g);
    }

    public Game getGame()
    {
        return game;
    }
    
}
