package Entities.Traps;

import java.awt.*;
import java.awt.image.BufferedImage;

import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;
import Physic.EntititesCollision;

public class Bullet extends Entity implements Trap{
    private Player player;
    private int[][] lvData;
    public  float moveX = 2f;
    private BufferedImage[] animations;

    public Bullet(float x, float y, float width, float height, Player player, int[][] lvData){
        super(x,y,new MyRectangle(x, y,  width, height));
        this.player = player;
        this.lvData = lvData;
        this.animations = LoadAndSave.loadBullet();
    }

    @Override
    public void entityInAffect(){
        if (player.getPlayerHitBox().isCollide(hitBox)){
            //System.out.println("Bullet Shot!");
            activate();
        }
   
    }

    @Override
    public void activate(){
        player.changeHealth(-10);
        //System.out.println("affect!");
    }

    public boolean getCollideObject(){
        if (!EntititesCollision.CanMoveHere(x, y, ((MyRectangle)this.getShape()).width, ((MyRectangle)this.getShape()).height, lvData) || player.getPlayerHitBox().isCollide(hitBox)){
            if (player.getPlayerHitBox().isCollide(hitBox)){
                //System.out.println("player collide hitbox!");
            }
             return true;
        }
        return false;
    }

    public void updatePosBullet(int gunDir){
       x += moveX*gunDir;
       updateHitBox();
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        g.drawImage(animations[0], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
      }

    public void updateHitBox(){
        ((MyRectangle)hitBox).x1 = x;  
    }

    public void resetBullets(float x){
        this.x = x;
        ((MyRectangle)hitBox).x1 = x;  
    }
}
