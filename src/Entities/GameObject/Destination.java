package Entities.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;

public class Destination extends GameObjects{
    private BufferedImage Image;
    private Playing playing;
    public Destination (float x, float y, float width, float height, Playing playing, int imageIndex)
    {
        super(x, y, width, height, playing);
        this.playing = playing;
        loadDestinationImg(imageIndex);
    }

    private void loadDestinationImg(int imageIndex) {
        Image = LoadAndSave.getDestination(imageIndex);
    }

    @Override
    public float distanceToPlayer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'distanceToPlayer'");
    }

    @Override
    public void update() {
        if (this.playing.getPlayer().getPlayerHitBox().isCollide(hitBox)){
            playing.setLevelCompleted(true);
        }  
    }

    @Override
    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.RED);
        // g.drawRect((int)x - xLvlOffset, (int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width,(int) ((MyRectangle)hitBox).height);
        g.drawImage(Image, (int)x - xLvlOffset,(int)y - yLvlOffset,(int) ((MyRectangle)hitBox).width, (int) ((MyRectangle)hitBox).height, null);
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
