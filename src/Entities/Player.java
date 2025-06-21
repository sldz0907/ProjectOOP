package Entities;

import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.EntititesCollision;
import Physic.Gravity;
import Physic.MyShape2D.MyRectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Entities.GameObject.GameObjects;
import Entities.Traps.WaterTrap;
import Audio.AudioPlayer;
import GameState.Playing;

public class Player extends Entity{
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed;
    private int playerAction = EntityActions.PlayerConstants.IDLE;
    private boolean moving = false;
    private boolean jumping = false;
    public boolean attacking = false;
    public boolean isUsingUlt;
    public ArrayList<Ultimate> ultimates; 
    private boolean left, up, right, down;
    private final float maxSpeed = 1f;
    private float speed = maxSpeed; // max speed vs speed la 1
    private int screenWidth, screenHeight;
    private boolean ableToInteractWithAnObject = false;
    public boolean interact = false;
    private float distanceToObject = 100000;
    private GameObjects idOfGameObject;
    public int playerDir = EntityActions.Directions.RIGHT;
    private boolean isPlayingHurtSound = false;
    public boolean inWater = false;

    private int[][] lvData;
    public Playing playing;

    private Gravity updateGravityForce;
    public final float jumpSpeedMax = -4.0f;
    public float jumpSpeed = jumpSpeedMax; // jumpspeed laf -4.0

    private MyRectangle playerHitBox;
    private float excessInX,excessInY;

    private BufferedImage statusBarImg;
    private int statusBarWidth = (int) (192*Game.gameWidth*0.001f);
    private int statusBarHeight = (int) (58*Game.gameHeight*0.0018f);
    private int statusBarX = (int) (10*Game.gameWidth*0.0005f);
    private int statusBarY = (int) (10*Game.gameHeight*0.0001f);
    private int healthBarWidth = (int) (152*Game.gameWidth*0.001f);
    private int healthBarHeight = (int) (4*Game.gameHeight*0.0018f);
    private int healthBarXStart = (int) (34*Game.gameWidth*0.001f);
    private int healthBarYStart = (int) (14*Game.gameHeight*0.0018f);
    private int maxHealth = 500; // maxhealth laf 100 thoai
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private int manaBarWidth = (int) (105*Game.gameWidth*0.001f);
    private int manaBarHeight = (int) (2*Game.gameHeight*0.0018f);
    private int manaBarXStart = (int) (44.5*Game.gameWidth*0.001f);
    private int manaBarYStart = (int) (34*Game.gameHeight*0.0018f);
    private float maxMana = 100f;
    private float currentMana = maxMana;
    private long manaCoolDown = 1500;
    private long lastManaUp = 0;
    private int manaWidth = manaBarWidth;


    public MyRectangle attackBox;
    private boolean attackChecked;
    private int typeAttack = 0;
    private final int maxAttackType = 3;
    private int playerDamage = 5;
    public Ultimate ultimate;

    public int flipX = 0;
    public int flipW = 1;

    //Interact Trap
    private long lastStruckTime = 0;
    private long struckByTrapCooldown = 100; // 1 giây miễn nhiễm sau khi bị sét đánh

    //Interact PoisonTrap
    public boolean takePoison = false;
    private long lastEffectTime = 0;
    private long takePoisonCooldown = 5000; // 1 giây miễn nhiễm sau khi bị sét đánh
    
    public Player(float x, float y, int screenWidth, int screenHeight,int[][] lvData, Playing playing) {
        // init hitBox here
        super(x, y,new MyRectangle(x , y , screenWidth*0.05f * 0.4f, screenHeight*0.1f*0.5f));
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.lvData = lvData;
        this.playing = playing;

        updateGravityForce = new Gravity();

        excessInX = screenWidth*0.05f * 0.3f;
        excessInY = screenHeight*0.1f*0.5f;
        // init playerHitBox
        playerHitBox = (MyRectangle) hitBox;

        // init attackBox
        attackBox = new MyRectangle(x, y, (int) (15*Game.gameWidth*0.001f), (int) (20*Game.gameHeight*0.0018f));

        //int ultimateList
        this.ultimates = new ArrayList<>();
        this.isUsingUlt = false;

        loadAnimations();
    }
    // get things
    public MyRectangle getPlayerHitBox()
    {
        return (MyRectangle)hitBox;
    }
    private void loadAnimations() {
        animations = LoadAndSave.loadPlayer();
        statusBarImg = LoadAndSave.loadStatusBarImg();
    }

    public void update() {
        updateHealthBar();
        if (currentHealth <= 0) {
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            playing.setGameOver(true);
            playing.getGame().getAudioPlayer().stopSong();
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            return;
        }
        setStateInWater(LoadAndSave.getWaterTraps());
        recoverMana(); // Hồi Mana theo thời gian
        updatePos();
        updateHitbox();
        updateAttackBox();
        if (attacking) checkAttack();
        updateUlt();      
        setAnimation();
        updateAnimationTick();
    }
    
    private void recoverMana() {
        if (currentMana < maxMana) {
            long currentTime = System.currentTimeMillis();  // Tăng Mana dần theo thời gian
            if (currentTime - lastManaUp >= manaCoolDown){
                lastManaUp = currentTime;
                currentMana = (currentMana + 20f > maxMana) ? maxMana : (currentMana + 20f);
                
            }
        }
        else return;
    }    

    public void checkAttack() {
        switch(typeAttack) {
            case 0:
                if(attackChecked || aniIndex != 4) {
                    return;
                }
                break;
            case 1:
                if(attackChecked || aniIndex != 1) {
                    return;
                }
                break;
            case 2:
                if(attackChecked || aniIndex != 1) {
                    return;
                }
                break;
        }
        attackChecked = true;
        playing.getGame().getAudioPlayer().playAttackSound();
        playing.checkEnemyHit(attackBox, playerDamage);
    }

    private void updateAttackBox() {
        if (!attacking && !isUsingUlt) {  // Chỉ cập nhật khi không tấn công và không dùng chiêu
            if (playerDir == EntityActions.Directions.RIGHT && !left) {
                attackBox.x1 = playerHitBox.x1 + playerHitBox.width + (int)(1 * Game.gameWidth * 0.001f);
            } else if (playerDir == EntityActions.Directions.LEFT && !right) {
                attackBox.x1 = playerHitBox.x1 - 15 * Game.gameWidth * 0.001f;
            }
        }
        attackBox.y1 = playerHitBox.y1 + (Game.gameHeight * 0.0018f * 5);
    }    

    private void updateHealthBar(){
        healthWidth = (int)((currentHealth / (float)(maxHealth)) * healthBarWidth);
        manaWidth = (int)((currentMana/ (float)(maxMana)) * manaBarWidth);
    }

    private void updateHitbox()
    {
        playerHitBox.x1 = x;
        playerHitBox.y1 = y;
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        //System.out.println(screenWidth * 0.05f + " " + screenHeight * 0.1f);
        g.setColor(Color.BLACK);
        // g.drawRect((int)playerHitBox.x1 - xLvlOffset,(int)playerHitBox.y1 - yLvlOffset,(int)playerHitBox.width,(int)playerHitBox.height); // draw hit box
        // g.drawRect((int)attackBox.x1 - xLvlOffset, (int)attackBox.y1 - yLvlOffset, (int)attackBox.width, (int)attackBox.height);

        g.drawImage(animations[playerAction][aniIndex], (int)(x - excessInX) - xLvlOffset + flipX, (int)(y - excessInY) - yLvlOffset, (int)(screenWidth*0.05f)*flipW, (int)(screenHeight*0.1f), null);

        for (Ultimate p : ultimates) {
            p.render(g, xLvlOffset, yLvlOffset);
        }
    }

    public void drawUI(Graphics g){
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight,null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
        g.setColor(Color.YELLOW);
        g.fillRect(manaBarXStart + statusBarX, manaBarYStart + statusBarY, manaWidth, manaBarHeight);
    }

    private void setAnimation() {
        int startAni = playerAction;
    
        if (moving) {
            playerAction = EntityActions.PlayerConstants.RUN;
        } else {
            playerAction = EntityActions.PlayerConstants.IDLE;
        }
    
        if (updateGravityForce.inAir) {
            playerAction = EntityActions.PlayerConstants.JUMP;
        }
        if (attacking) {
            playerAction = EntityActions.PlayerConstants.ATTACK[typeAttack];
        }
        if (isUsingUlt) {
            playerAction = EntityActions.PlayerConstants.ATTACK[3];
        }
    
        if (startAni != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
    }    

    private void updatePos() {
        // Không cho phép di chuyển khi đang tấn công hoặc đang sử dụng Ultimate
        if ((attacking || isUsingUlt) && !updateGravityForce.inAir) {
            return; // Dừng cập nhật vị trí khi đang tấn công hoặc dùng ultimate
        }
    
        moving = false;
        if (jumping) {
            jump(); // áp dụng nhảy
        }
        moveHorizontal(); // di chuyển ngang khi không tấn công và không dùng ultimate
        updateGravityForce.applyGravity(this, lvData);
        applyForce(); // kiểm tra xem có di chuyển được không
    }    
    
    public void changeHealth(int value) {
        if (value < 0) {
            if (!isPlayingHurtSound){
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.HURT);
            isPlayingHurtSound = true;
            }
            else{
                isPlayingHurtSound = false;
            }
        }
        currentHealth += value;
        if(currentHealth<=0) {
            currentHealth = 0;
            //gameOver();
        }
        else if(currentHealth>=maxHealth) {
            currentHealth = maxHealth;
        }
    }
    public void hurt(int enemyDMG, float knockbackDirection, float enemyX) {
        changeHealth(enemyDMG);
        if (currentHealth <= 0) {
            playerAction = EntityActions.PlayerConstants.DEAD;
        } else {
            playerAction = EntityActions.PlayerConstants.HURT;
        }
    
        // Tính toán hướng đẩy lùi dựa trên vị trí của enemy so với player
        float knockbackAmount = 4.0f; // Độ dịch chuyển khi bị đẩy lùi, có thể điều chỉnh tùy thích
        
        // Kiểm tra xem player và enemy đối mặt nhau không
        if (enemyX < x) {
            // Enemy ở bên trái player, player sẽ bị đẩy sang phải
            knockbackDirection = 1; 
        } else {
            // Enemy ở bên phải player, player sẽ bị đẩy sang trái
            knockbackDirection = -1;
        }
        // Dịch chuyển player theo hướng đẩy lùi
        moveX += knockbackDirection * knockbackAmount;
        //updateAttackBox(); // Cập nhật lại attackBox sau khi bị đẩy lùi
    }
    
    private void moveHorizontal() {
        if (left && right) {
            moving = false;
            return;
        }
    
        if (left) {
            moveX -= speed;
            moving = true;
            flipX = (int)(screenWidth * 0.05f); // Lật hình sang trái
            flipW = -1; 
            playerDir = EntityActions.Directions.LEFT;
        } else if (right) {
            moveX += speed;
            moving = true;
            flipX = 0; // Bình thường khi đi phải
            flipW = 1;
            playerDir = EntityActions.Directions.RIGHT;
        } else {
            moving = false;
        }   
        if (this.isUp()) {
            jumping = true;
        }
    }

    private void applyForce()
    {
        if (EntititesCollision.CanMoveHere(x + moveX, y + moveY, playerHitBox.width, playerHitBox.height, lvData))
        {
            x += moveX;
            y += moveY;
        }
        else if (EntititesCollision.CanMoveHere(x + moveX, y, playerHitBox.width, playerHitBox.height, lvData))
        {
            x += moveX;
        }
        else if (EntititesCollision.CanMoveHere(x, y + moveY, playerHitBox.width, playerHitBox.height, lvData))
        {
            y += moveY;
        }
        resetMove();
    }
    private void jump()
    {
        if(updateGravityForce.inAir)
            return;
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        updateGravityForce.inAir = true;
        updateGravityForce.airSpeed = jumpSpeed;
        
    }

    private void resetMove()
    {
        moveX = 0;
        moveY = 0;
    }

    private void updateAnimationTick() {
        aniSpeed = 120 / EntityActions.PlayerConstants.getSpriteAmount(playerAction);
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= EntityActions.PlayerConstants.getSpriteAmount(playerAction)) {
                aniIndex = 0;
                if (attacking) { 
                    attacking = false; // Reset trạng thái tấn công sau khi hoàn thành hoạt ảnh
                    attackChecked = false;
                }
                if (isUsingUlt) { 
                    currentMana -= 50; // Trừ Mana
                    isUsingUlt = false; // Kết thúc Ultimate
                }
            }
        }
    }
    public boolean isAbleToInteractWithAnObject()
    {
        return ableToInteractWithAnObject;
    }
    public void Interact()
    {
        if (ableToInteractWithAnObject == true)
        {
            interact = !interact;
        }
    }
    public void setAbleToInteractWithAnObject(boolean ableToInteractWithAnObject, GameObjects idOfGameObject)
    {
        if (!this.ableToInteractWithAnObject && this.idOfGameObject == null)
        {
            this.ableToInteractWithAnObject = ableToInteractWithAnObject;
            this.idOfGameObject = idOfGameObject;
            distanceToObject = idOfGameObject.distanceToPlayer();
        }
        else
        {
            if (distanceToObject >= idOfGameObject.distanceToPlayer())
            {
                this.ableToInteractWithAnObject =  ableToInteractWithAnObject;
                this.idOfGameObject = idOfGameObject;
                distanceToObject = idOfGameObject.distanceToPlayer();
            }
        }
    }
    public void resetObjectInteract()
    {
        ableToInteractWithAnObject = false;
        idOfGameObject = null;
        distanceToObject = 0;
        interact = false;
    }
    public GameObjects getIdOfGameObject()
    {
        return idOfGameObject;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setAttacking(boolean attacking) {
        // Chỉ cho phép tấn công nếu không ở trên không và không đang tấn công
        if (!this.attacking && attacking && !updateGravityForce.inAir) {
            this.attacking = attacking;
            typeAttack = (typeAttack + 1) % maxAttackType; // Chuyển kiểu tấn công
        }
    }    
    
    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
        attacking = false;
        playerDir = EntityActions.Directions.RIGHT;
    }
    public void resetAll() {
        // Đặt lại các trạng thái di chuyển và hành động
        resetDirBooleans();
        moving = false;
        jumping = false;
        attacking = false;
        attackChecked = false;
        isUsingUlt = false;
        ultimates.clear();
    
        // Đặt lại trạng thái ban đầu
        playerAction = EntityActions.PlayerConstants.IDLE;
    
        // Reset sức khỏe, mana
        currentHealth = maxHealth;
        healthWidth = healthBarWidth;
        currentMana = maxMana;
        manaWidth = manaBarWidth;
    
        // Đặt lại vị trí Player
        x = playing.getStartingX(); // Hoặc giá trị x mặc định
        y = playing.getStartingY(); // Hoặc giá trị y mặc định
    
        // Reset hộp va chạm
        playerHitBox.x1 = x;
        playerHitBox.y1 = y;
    
        // Reset hộp tấn công
        attackBox.x1 = x;
        attackBox.y1 = y;
    
        // Reset các biến hoạt ảnh
        aniIndex = 0;
        aniTick = 0;
    
        // Reset trọng lực
        updateGravityForce.resetGravity();
    
        // Đặt lại trạng thái của game liên quan đến Player
        playing.setGameOver(false); // Đảm bảo game không còn ở trạng thái thua
    
    }
    public void gotStruckbyTrap(int damage){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStruckTime >= struckByTrapCooldown){
            lastStruckTime = currentTime;
            changeHealth(-damage);
        }
    }

    public void gotEffectbyPoison(){
        if (takePoison){
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastEffectTime >= takePoisonCooldown){
                lastEffectTime = currentTime;
                changeHealth(-20);
            }
        }
    }

    public void changeSpeedCollideWater(float newSpeed, float newJumpSpeed){
        //System.out.println(speed);
        speed = newSpeed;
        //System.out.println(speed);
        jumpSpeed = newJumpSpeed;

     }
 
    public void recoverSpeed(){
         speed = inWater ? maxSpeed/5 : maxSpeed;
         jumpSpeed = inWater ? jumpSpeedMax/1.5f : jumpSpeedMax;
     }
     
    public void setUltimate(boolean isUsingUlt) {
        if (currentMana >= 50 && !this.isUsingUlt && !attacking && !updateGravityForce.inAir && isUsingUlt) {
            this.isUsingUlt = isUsingUlt;
            ultimates.add(new Ultimate(attackBox.x1, attackBox.y1, attackBox.width, attackBox.height, playing, lvData));
        }
     }
     

    private void updateUlt() {
        // Duyệt qua danh sách Ultimate và cập nhật từng đối tượng
        for (int i = 0; i < ultimates.size(); i++) {
            ultimate = ultimates.get(i);
            ultimate.update();
    
            // Nếu Ultimate không còn hoạt động (isActive là false), loại bỏ khỏi danh sách
            if (!ultimate.isActive()) {
                ultimates.remove(i);
                i--; // Điều chỉnh chỉ số sau khi xóa phần tử
            }
        }
    }

    public void setStateInWater(ArrayList<WaterTrap> waterTraps){
        for (WaterTrap W : waterTraps){
            if (this.getPlayerHitBox().isCollide(W.getShape())){
                inWater = true;
                return;
            }

        }
        inWater = false;
    }
    
    public float getMaxSpeed(){
        return maxSpeed;
    }

    public float getMaxJumpSpeed(){
        return jumpSpeedMax;
    }
}