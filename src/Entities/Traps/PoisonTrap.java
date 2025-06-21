package Entities.Traps;

import Entities.Player;
import Entities.Entity;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.EntititesCollision;
import Physic.MyShape2D.MyRectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class PoisonTrap extends Entity implements Trap {
    private Player player;
    private BufferedImage[] ImgPoison;
    private boolean active = true;
    private boolean aplyForce = false;
    private boolean aplyEffect = false;
    private boolean dropped = false;
    private float initX;
    private float initY;
    private int[][] lvData;

    public PoisonTrap(float x, float y, float width, float height, Player player, int[][] lvData){
        super(x, y, new MyRectangle(x, y,  width/2, height/2));
        this.player = player;
        this.ImgPoison = LoadAndSave.loadPoison();
        this.lvData = lvData;
        this.initX = x;
        this.initY = y;
        
    }

    @Override
    public void activate(){
        if (aplyEffect){
            player.gotEffectbyPoison();
        }
    }

    @Override
    public void entityInAffect(){
        if (inRange() && active ){
            aplyForce = true;
            dropped = true;
        }

        if (aplyForce && active){
            //System.out.println("dropping");
           y += 0.6f;
           updateHitBox();
        }

        if (this.getCollideObject() && aplyForce){
            active = false;
        }

    }



    public void update(){
        if (active){
        entityInAffect();
        }

        activate();
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        if (active && aplyForce){
        g.drawImage(ImgPoison[0], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
        }
      }


    public boolean inRange(){
        int tileX = (int)x / Game.TILE_WIDTH_SIZE;
        if ((int)(player.getX() + player.getPlayerHitBox().width) / Game.TILE_WIDTH_SIZE == tileX && player.getY() >= y){
            int tileY = (int)y / Game.TILE_HEIGHT_SIZE;
            int tileYplayer = (int)player.getY() / Game.TILE_HEIGHT_SIZE;
            for (int j = tileY; j <= tileYplayer; j++){
                if (EntititesCollision.IsTileSolid(tileX, j, lvData)){
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    public boolean getCollideObject(){
        if (!EntititesCollision.CanMoveHere(x, y, ((MyRectangle)this.getShape()).width, ((MyRectangle)this.getShape()).height, lvData) || player.getPlayerHitBox().isCollide(hitBox)){
            if (player.getPlayerHitBox().isCollide(hitBox)){
                //System.out.println("player collide hitbox!");
                player.takePoison = true;
                aplyEffect = true;
            }
             return true;
        }
        return false;
    }

    public void updateHitBox(){
        ((MyRectangle)hitBox).y1 = y;  
    }


    public void reset(){
        active = true;
        aplyForce = false;
        aplyEffect = false;
        player.takePoison = false;
        dropped = false;
        x = initX;
        y = initY;
        ((MyRectangle)hitBox).x1 = initX;
        ((MyRectangle)hitBox).y1 = initY;
        

    }

}
