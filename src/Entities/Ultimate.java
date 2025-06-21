package Entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Audio.AudioPlayer;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;
import Physic.EntititesCollision;

public class Ultimate extends Entity {
    private Playing playing;
    private int[][] lvData;
    private  float speed = 1f;
    private float speedX;
    private BufferedImage[] animations;
    public boolean active;
    private boolean checked;
    private int aniIndex = 0;
    public int flipX = 0;
    public int flipW = 1;
    
    public Ultimate (float x, float y, float width, float height, Playing playing, int[][] lvData) {
        super(x,y,new MyRectangle(x, y, width, height));
        this.playing = playing;
        this.lvData = lvData;
        this.active = true;
        this.checked = false;
        loadAnimations();
        
    }

    private void loadAnimations() {
        animations = LoadAndSave.loadUltimate();
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ULTIMATE);
    }

    public void update() {
        if(active) {
            updatePos();
            updateHitBox();
            checked = true;
            updateAnimationTick();
            checkUltimate();
        }
    }

    private void updateAnimationTick() {
        if(aniIndex < 29)
            aniIndex++;
        else aniIndex = 0;
    }

    private void updatePos() {
        if(playing.getPlayer().isUsingUlt && !checked) {
            if(playing.getPlayer().playerDir == EntityActions.Directions.RIGHT) {
                speedX = speed;
                flipX = 0;
                flipW = 1;
            }             
            if(playing.getPlayer().playerDir == EntityActions.Directions.LEFT) {
                speedX = -speed;
                flipX = 3*36 - 8;
                flipW = -1;
            }              
        }
        if(EntititesCollision.CanMoveHere(x, y, speedX, playing.getPlayer().attackBox.height, lvData)) {
            x += speedX;
        }
        else active = false;
    }

    private void updateHitBox() {
        ((MyRectangle)hitBox).x1 = x;  
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
        if(active) {
            g.setColor(Color.RED);
            // g.drawRect((int)x - xLvlOffset , (int)y - yLvlOffset,(int)((MyRectangle)hitBox).width, (int)((MyRectangle)hitBox).height);
            g.drawImage(animations[aniIndex], (int)(x-40) - xLvlOffset + flipX, (int)(y-2*27+4) - yLvlOffset, (int)(((MyRectangle)hitBox).width*flipW*5), (int)(((MyRectangle)hitBox).height*5), null);
        }   
    }

    public boolean isActive() {
        return active;
    }

    private void checkUltimate() {
        playing.getPlayer().playing.checkEnemyHit((MyRectangle)hitBox, 10);
    }   
}
