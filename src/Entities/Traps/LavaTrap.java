package Entities.Traps;

import java.awt.*;
import java.awt.image.BufferedImage;

import Audio.AudioPlayer;
import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;

public class LavaTrap extends Entity implements Trap{
      private Player player;
      private Playing playing;
      private BufferedImage[] ImgLava;
      private int bitLava;
      private Graphics2D g2d;
      public LavaTrap(float x, float y, float width, float height, Player player, Playing playing, int bit){
        super(x,y,new MyRectangle(x, y, width,height));
        this.playing = playing;
        this.player = player;
        this.ImgLava = LoadAndSave.loadLava();
        this.bitLava = bit;
      }

      @Override
      public void activate(){
        player.changeHealth(-100);
      }

      @Override
      public void entityInAffect(){
        if (player.getPlayerHitBox().isCollide(hitBox)){
            activate();
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HURT_LAVA);
        }
      }

      public void update(){
        entityInAffect();
      }

      public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        g2d = (Graphics2D) g;
          float opacity = 0.95f;
          g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        if (bitLava == 1){
        g2d.drawImage(ImgLava[0], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
        }else{
        g2d.drawImage(ImgLava[1], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
      }
}
