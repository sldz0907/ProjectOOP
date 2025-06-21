package Entities.Traps;

import Entities.Player;
import GameState.Playing;
import Entities.Entity;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.EntititesCollision;
import Physic.MyShape2D.MyRectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MysteryBox extends Entity implements Trap{
    private Player player;
    private long effectTime = 8000;
    private long startEffectTime;
    private long lastEffectTime = 0;
    private long effectCoolDown = 500;
    private int mysBit;
    private boolean changeHealth = false;
    private boolean Taken = false;
    private boolean recoveredSpeed = false;
    private long waitingTime = 0;
    private BufferedImage[] ImgMysBox;
    private int aniTick = 0, aniSpeed = 50, aniIndex = 0;

     public MysteryBox(float x, float y, float width, float height, Player player, int mysBit)
    {
        super(x, y, new MyRectangle(x, y, width, height));
        this.player = player;
        this.mysBit = mysBit;
        this.ImgMysBox = LoadAndSave.loadMysteryBox();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex = (aniIndex + 1) % ImgMysBox.length;
        }
    }


    public void update(){
        if (!Taken){
            getCollideObject();
            updateAnimationTick();
        } else{
            activate();
        }

    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        if (!Taken){
            g.drawImage(ImgMysBox[aniIndex], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
        }
     
    }

    @Override
    public void entityInAffect(){

    }

    @Override
    public void activate(){
        if (mysBit == 0){
            jumpEffect();
        }

        if (mysBit == 1){
            speedEffect();
        }

        if (mysBit == 2){
            healEffect();
        }

        if (mysBit == 3){
            poisonEffect();
        }




    }

    public void jumpEffect(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - startEffectTime <= effectTime){
            if (!player.inWater){
            //System.out.println("jump affect");
                player.changeSpeedCollideWater(player.getMaxSpeed(),player.getMaxJumpSpeed()*2f);
            }
            else {
                player.changeSpeedCollideWater(player.getMaxSpeed()/5,player.getMaxJumpSpeed());
            }
        }else{
            if (!recoveredSpeed){
            player.recoverSpeed();
            recoveredSpeed = true;
            }
        }

    }

    public void healEffect(){
        if (!changeHealth){
            player.changeHealth(100);
            //System.out.println("health effect");
        }
        changeHealth = true;

    }

    public void speedEffect(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - startEffectTime <= effectTime){
            if (!player.inWater){
            //System.out.println("speed effect");
                player.changeSpeedCollideWater(player.getMaxSpeed()*3f, player.getMaxJumpSpeed());
            }
            else{
                player.changeSpeedCollideWater(player.getMaxSpeed(), player.getMaxJumpSpeed()/1.5f);
            }
        } else {
            if (!recoveredSpeed ){
            player.recoverSpeed();
            recoveredSpeed = true;
            }
        }

    }

    public void poisonEffect(){
        long currentTime = System.currentTimeMillis();
        if (currentTime- startEffectTime <= effectTime){
            if (currentTime - lastEffectTime >= effectCoolDown){
                //System.out.println("poisoi effect");
                player.changeHealth(-10);
                lastEffectTime = currentTime;
            }

        }
    }

    public void reset(){
        Taken = false;
        changeHealth = false;
        recoveredSpeed = false;
        Random random = new Random();
        this.mysBit = random.nextInt(4);
    }

    public void getCollideObject(){
        long currentTime  = System.currentTimeMillis();
        if (player.getPlayerHitBox().isCollide(hitBox)){
            if (currentTime - waitingTime >= 1000){
            Taken = true;
            startEffectTime = System.currentTimeMillis();
            }
        } else{
            waitingTime = currentTime;
        }
    }

}
