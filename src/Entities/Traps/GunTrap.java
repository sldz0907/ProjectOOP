package Entities.Traps;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
import Entities.Entity;
import Entities.Player;
import GameState.Playing;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;
import LoadSave.LoadAndSave;


public class GunTrap extends Entity implements Trap {
    private Playing playing;
    public ArrayList<Bullet> bullets;
    private long lastShotTime = 0;
    private long shotCoolDownTime = 1000; // Điều chỉnh thời gian cooldown (ms)
    private float rangeInX = 400;
    private int bulletNum = 10;
    private int bulletCount = 0;
    private int gunDir;
    private BufferedImage[] animations;

    public GunTrap(float x, float y, float width, float height, Playing playing, int gunDir) {
        super(x, y, new MyRectangle(x, y, width,height));
        this.playing = playing;
        this.gunDir = gunDir;
        this.animations = LoadAndSave.loadGun();
        this.bullets = new ArrayList<>();
        for (int i = 1; i <= bulletNum; i++){
          bullets.add(new Bullet(x+5, y+5, width/2, height/2, playing.getPlayer(), LoadAndSave.getLvData(Integer.toString(playing.getLevelManager().levelId))));
        }
    }

    @Override
    public void entityInAffect() {
        if (inRange()) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= shotCoolDownTime && bulletCount < bulletNum) {
                lastShotTime = currentTime;
                bulletCount++;
            }
          }

          if (bulletCount <= bulletNum){
            for (int i = bulletCount - 1; i >= 0; i--){
              if (!bullets.isEmpty()) {
                Bullet bullet = bullets.get(i); 

                bullet.updatePosBullet(gunDir);
                bullet.entityInAffect(); // Cập nhật trạng thái của đạn

                // Kiểm tra va chạm của đạn với tường hoặc player
                if (bullet.getCollideObject()) {
                  bullets.add(new Bullet(x+5, y+5, ((MyRectangle)this.hitBox).width/2, ((MyRectangle)this.hitBox).height/2, playing.getPlayer(), LoadAndSave.getLvData(Integer.toString(playing.getLevelManager().levelId))));
                    bullets.remove(bullet); // Xóa đạn nếu va chạm
                    bulletCount--;
                    if (bulletCount < 0)
                    {
                      bulletCount = 0;
                    }

              
                }
              }
            }
          }
    }

    @Override
    public void activate() {
        // Nếu cần, thêm logic để kích hoạt bẫy (ở đây chưa có logic thêm)
    }

    public void update() {
        entityInAffect();  // Cập nhật trạng thái của bẫy súng
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
      if (gunDir == 1){
      g.drawImage(animations[0], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
      } else{
      g.drawImage(animations[1], (int) this.getX() - xLvlOffset, (int) this.getY() - yLvlOffset,(int)((MyRectangle)this.getShape()).width,(int)((MyRectangle)this.getShape()).height, null);
      } 
        for (Bullet B : bullets){
          if (!B.getShape().isCollide(hitBox)){
            B.render(g,xLvlOffset,yLvlOffset);
          }
        }
    }

    public boolean inRange() {
        // Kiểm tra xem player có trong tầm bắn của súng hay không
        int yTileOfGun = (int)(y / Game.TILE_HEIGHT_SIZE);
        int yTileOfGunMore = yTileOfGun + 1;
        if ((int)(playing.getPlayer().getY() / Game.TILE_HEIGHT_SIZE) + 1 == yTileOfGun || (int)(playing.getPlayer().getY() / Game.TILE_HEIGHT_SIZE) == yTileOfGunMore) {
          float distanceLegit = gunDir*(playing.getPlayer().getX() - x);
            if (distanceLegit >= 0 && distanceLegit <= rangeInX) {

                return true;
            }
        }
        return false;
    }

    public void reset(){
      for (Bullet B : bullets){
        B.resetBullets(this.x+5);
      }
      bulletCount = 0;
      lastShotTime = 0;
    }

}
