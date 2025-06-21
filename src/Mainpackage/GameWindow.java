package Mainpackage;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameWindow {
    private JFrame jFrame;
    
    public GameWindow(GamePanel gamePanel)
    {
        jFrame = new JFrame();

        jFrame.setTitle("My adventure");
        try {
            BufferedImage gameIcon = ImageIO.read(getClass().getResource("/asset/Icon/icon.png"));
            // Thay đổi kích thước icon
            Image scaledIcon = gameIcon.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            BufferedImage bufferedScaledIcon = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D g2d = bufferedScaledIcon.createGraphics();
            g2d.drawImage(scaledIcon, 0, 0, null);
            g2d.dispose();
            
            jFrame.setIconImage(gameIcon);
        } catch (IOException e) {
            System.out.println("Error: Unable to load game icon.");
        }

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocation((int)(gamePanel.getWidth() * 0.125f), (int)(gamePanel.getHeight() * 0.1f));
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
