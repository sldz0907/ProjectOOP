package UI;

import GameState.GameStates;
import Physic.MyShape2D.MyRectangle;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class MenuButton {
    private int xPos,yPos;
    private String name;

    private int width,height;
    private BufferedImage imgs[];

    private MyRectangle hitBox;
    private boolean mouseOver = false,mousePress = false;
    private int stateOfButton = 0;
    private GameStates state;
    public MenuButton(int xPosCenter, int yPosCenter, String name, GameStates state)
    {
        this.name = name;
        this.state = state;
        loadImage(xPosCenter,yPosCenter);
        initHitBox();
    }

    private void loadImage(int xPosCenter, int yPosCenter)
    {
        InputStream is;
        imgs = new BufferedImage[3];
        try {
            is = getClass().getResourceAsStream("/asset/MenuButton/" + name + ".png");
            imgs[0] = ImageIO.read(is);

            is = getClass().getResourceAsStream("/asset/MenuButton/" + "ON" + name + ".png");
            imgs[1] = ImageIO.read(is);

            is = getClass().getResourceAsStream("/asset/MenuButton/" + "CHOOSE" + name + ".png");
            imgs[2] = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // lấy kích thước của ảnh:
        this.width = imgs[0].getWidth(); this.height = imgs[0].getHeight();
        xPos = xPosCenter-(width/2);
        yPos = yPosCenter-(height/2); 
    }

    public MyRectangle getHitBox()
    {
        return hitBox;
    }
    public void update()
    {
        stateOfButton = 0;
        if (mouseOver)
            stateOfButton = 1;
        if (mousePress)
            stateOfButton = 2;
    }
    public boolean isMouseOver()
    {
        return mouseOver;
    }
    public boolean isMousePress()
    {
        return mousePress;
    }
    public void resetBool()
    {
        mouseOver = false;
        mousePress = false;
    }
    private void initHitBox()
    {
        hitBox = new MyRectangle(xPos, yPos, width, height);
    }
    public void setMousePress(boolean mousePress)
    {
        this.mousePress = mousePress;
    }
    public void setMouseOver(boolean mouseOver)
    {
        this.mouseOver = mouseOver;
    }
    public void applyGameStates()
    {
        GameStates.state = state;
    }
    public void render(Graphics g)
    {
        g.drawImage(imgs[stateOfButton], xPos, yPos, null);
    }

    public GameStates getState() {
        return state;
    }
}
