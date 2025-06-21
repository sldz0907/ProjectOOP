package Entities.Traps;

import java.awt.*;
import java.awt.image.BufferedImage;

import Audio.AudioPlayer;
import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;

public class WaterTrap extends Entity implements Trap{
      private Player player;
      private Playing playing;
      private BufferedImage[] ImgWater;
      private boolean isInWater = false;
      private boolean wasInWater = false;
      private boolean isPlayingWadingSound = false;
      private int bitWater;
      private Graphics2D g2d;
      public WaterTrap(float x, float y, float width, float height, Player player, Playing playing, int bit){
        super(x,y,new MyRectangle(x, y, width, height));
        this.player = player;
        this.playing = playing;
        this.bitWater = bit;
        this.ImgWater = LoadAndSave.loadWater();
      }

      @Override
      public void activate(){
        player.changeSpeedCollideWater(player.getMaxSpeed()/5, player.getMaxJumpSpeed()/1.5f);
      }

      @Override
      public void entityInAffect() {
        // setStateInWater();
        
        if (player.inWater && !wasInWater) {
            activate();
            if (!isPlayingWadingSound) {
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.WADING);
                isPlayingWadingSound = true;
            }
        }
    
        if (!player.inWater && wasInWater) {
            player.recoverSpeed();
            if (isPlayingWadingSound) {
                isPlayingWadingSound = false; 
            }
        }
    
        wasInWater = player.inWater;
    }

      public void update(){
        entityInAffect();
      }


      public void render(Graphics g, int xLvlOffset, int yLvlOffset){
          g2d = (Graphics2D) g;
          float opacity = 0.5f;
          g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        if (bitWater == 1){
        g2d.drawImage(ImgWater[0], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
      }else{
        g2d.drawImage(ImgWater[1], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
      }
      g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
 }
}
