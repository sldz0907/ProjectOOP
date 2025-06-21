package Entities.Traps;

import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Audio.AudioPlayer;

public class LazerTrap extends Entity implements Trap {
    private Player player;
    private Playing playing;
    private ArrayList<MyRectangle> Key;
    private BufferedImage[] animations;
    private float startX;
    private float startY;
    private int aniTick = 0, aniSpeed = 2, aniIndex = 0;
    private BufferedImage[] imgKey;
    public LazerTrap(float x, float y, float width, float height, float xKey, float yKey,  Player player, Playing playing) {
        super(x, y, new MyRectangle(x, y, Game.TILE_WIDTH_SIZE * width, Game.TILE_HEIGHT_SIZE * height));
        this.player = player;
        this.playing = playing;
        this.animations = LoadAndSave.loadLaser();
        this.imgKey = LoadAndSave.loadKey();
        Key = new ArrayList<>();
        Key.add(new MyRectangle(xKey, yKey,Game.TILES_IN_WIDTH, Game.TILES_IN_HEIGHT));
        startX = xKey;
        startY = yKey;
    }

    public void update() {
        deActivateLazer();
        entityInAffect();
        updateAnimationTick();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex = (aniIndex + 1) % animations.length;
        }
    }

    public void deActivateLazer(){
        if (!Key.isEmpty() && player.getPlayerHitBox().isCollide(Key.get(0))){
            Key.remove(Key.get(0));
        }
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.drawImage(animations[aniIndex], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);

        if (!Key.isEmpty()){
            g.drawImage(imgKey[0], (int) Key.get(0).x1 - xLvlOffset, (int) Key.get(0).y1 - yLvlOffset,(int)Key.get(0).width,(int)Key.get(0).height, null);
        }
    }

    @Override
    public void entityInAffect() {
        if (!Key.isEmpty()){
            if (player.getPlayerHitBox().isCollide(this.hitBox)) {
                //System.out.println("Lazer is working!");
                activate();
                playing.getGame().getAudioPlayer().playHurt(AudioPlayer.HURT_LASER);
            }
            else{
                playing.getGame().getAudioPlayer().stopEffect();
            }
        }
    }

    @Override
    public void activate() {
        player.gotStruckbyTrap(20);
    }


    public void reset(){
        Key.add(new MyRectangle(startX, startY,Game.TILES_IN_WIDTH,Game.TILES_IN_HEIGHT));

    }

}
