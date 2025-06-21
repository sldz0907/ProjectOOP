package Entities.Traps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;

public class StaticSpike extends Entity implements Trap {
    private Player player;
    private Playing playing;
    private int damage = 20;
    private BufferedImage imgs;
    private float width,height;
    public StaticSpike(float x, float y, float width, float height, Player player, Playing playing)
    {
        super(x, y, new MyRectangle(x, y, width, height));
        this.width = width;
        this.height = height;
        this.player = player;
        this.playing = playing;
        getStaticSpikeImg();
    }
    private void getStaticSpikeImg()
    {
        imgs = LoadAndSave.loadStaticSpike();
    }
    public void update()
    {
        entityInAffect();
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
        g.drawImage(imgs, (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int) width, (int) height, null);
    }
    @Override
    public void entityInAffect() {
        if (player.getPlayerHitBox().isCollide(hitBox))
        {
            //System.out.println("StaticSpike is working");
            activate();
        }
    }

    @Override
    public void activate() {
        player.gotStruckbyTrap(damage);
    }
}
