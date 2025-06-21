package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class MenuBackground {
    private String name;
    private BufferedImage img;
    private int height, width;

    public MenuBackground(int width, int height, String name) {
        this.name = name;
        this.height = height;
        this.width = width;
        loadImage();
    }

    private void loadImage() {
        img = null;
        InputStream is = getClass().getResourceAsStream("/asset/MenuBackground/" + name + ".png");
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //System.out.println("/asset/MenuBackground/" + name + ".png");
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(img, 0, 0, width, height, null);
    }
}