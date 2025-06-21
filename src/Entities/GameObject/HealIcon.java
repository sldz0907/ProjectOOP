package Entities.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;

public class HealIcon extends GameObjects{
    private BufferedImage img;
    public boolean active;
    private Playing playing;

    public HealIcon (float x, float y, float width, float height, Playing playing) {
        super (x, y, width, height, playing);
        this.playing = playing;
        active = true;
        img = loadImg();
    }

    private BufferedImage loadImg() {
        return LoadAndSave.loadHeartIcon();
    }

    @Override
    public float distanceToPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'distanceToPlayer'");
    }

    @Override
    public void update() {
        if (active) {
            if (this.playing.getPlayer().getPlayerHitBox().isCollide(hitBox)) {
                this.playing.getPlayer().changeHealth(500);
                active = false;
            }
        }
    }

    @Override
    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        if (active) {
            g.setColor(Color.RED);
            // g.drawRect((int)x - xLvlOffset, (int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width,(int) ((MyRectangle)hitBox).height);
            g.drawImage(img, (int)x - xLvlOffset,(int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width, (int) ((MyRectangle)hitBox).height, null);
        }
    }

    @Override
    public void processRender(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processRender'");
    }

    @Override
    public void processLogic() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processLogic'");
    }


}
