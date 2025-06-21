package Entities;

import Mainpackage.Game;
import Physic.EntititesCollision;
import Physic.MyShape2D.MyRectangle;

public class Enemy extends Entity {
    // Animations
    public int screenWidth, screenHeight;
    public int aniIndex, enemyState;
    private int aniTick, aniSpeed;
    private int[][] lvData;
    private boolean active = true;

    // Hitbox
    private MyRectangle enemyHitBox;
    public MyRectangle attackBox;
    public float excessInX;
    public float excessInY;

    // Collision
    private boolean firstUpdate = true;
    private boolean inAir;
    private float jumpSpeed = 1.0f;
    private float runSpeed = 0.5f;
    private int runDir = EntityActions.Directions.LEFT;
    private int tileY;
    private float attackDistance = Game.TILE_WIDTH_SIZE;

    // HP & DMG
    private int maxHealth = 20;
    private int currentHealth = maxHealth;
    private int enemyDMG = 25;
    private boolean attackChecked;

    public Enemy(float x, float y, int screenWidth, int screenHeight, int[][] lvData) {
        super(x, y, new MyRectangle(x, y, screenWidth * 0.05f * 0.4f, screenHeight * 0.1f * 0.4f));
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.lvData = lvData;

        excessInX = this.screenWidth * 0.05f * 0.3f;
        excessInY = this.screenHeight * 0.1f * 0.5f;
        this.enemyHitBox = (MyRectangle) hitBox;
        this.attackBox = new MyRectangle(x, y, (int) (10 * Game.gameWidth * 0.001f), (int) (20 * Game.gameHeight * 0.0018f));
    }

    private void updateAnimationTick() {
        aniSpeed = 120 / EntityActions.EnemyConstants.getSpriteAmount(enemyState);
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= EntityActions.EnemyConstants.getSpriteAmount(enemyState)) {
                aniIndex = 0;
                switch (enemyState) {
                    case EntityActions.EnemyConstants.ATTACK:
                    case EntityActions.EnemyConstants.HURT:
                        enemyState = EntityActions.EnemyConstants.IDLE;
                        break;
                    case EntityActions.EnemyConstants.DEAD:
                        active = false;
                        break;
                }
            }
        }
    }

    public MyRectangle getEnemyHitBox() {
        return (MyRectangle) hitBox;
    }

    public void update(int[][] lvData, Player player) {
        updateBehavior(lvData, player);
        updateHitbox();
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (runDir == EntityActions.Directions.RIGHT) {
            attackBox.x1 = enemyHitBox.x1 + enemyHitBox.width + (int) (1 * Game.gameWidth * 0.001f);
        } else if (runDir == EntityActions.Directions.LEFT) {
            attackBox.x1 = enemyHitBox.x1 - 10 * Game.gameWidth * 0.001f + 0.5f ;
        }
        attackBox.y1 = enemyHitBox.y1 + (Game.gameHeight * 0.0018f * 5);
    }

    private void updateBehavior(int[][] lvData, Player player) {
        if (firstUpdate) {
            if (!EntititesCollision.IsEntityOnFloor(enemyHitBox, lvData)) {
                inAir = true;
            }
            firstUpdate = false;
        }
        if (inAir) {
            if (EntititesCollision.CanMoveHere(enemyHitBox.x1, enemyHitBox.y1 + jumpSpeed, enemyHitBox.width, enemyHitBox.height, lvData)) {
                this.y += jumpSpeed;
            } else {
                inAir = false;
                enemyHitBox.y1 = EntititesCollision.GetEntityYPosUnderRoofOrAboveFloor(enemyHitBox, jumpSpeed);
                tileY = (int) (enemyHitBox.y1 / Game.TILE_HEIGHT_SIZE);
            }
        } else {
            switch (enemyState) {
                case EntityActions.EnemyConstants.IDLE:
                    newState(EntityActions.EnemyConstants.RUN);
                    break;
                case EntityActions.EnemyConstants.RUN:
                    if (canSeePlayer(lvData, player)) turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player)) newState(EntityActions.EnemyConstants.ATTACK);
                    float xSpeed = (runDir == EntityActions.Directions.LEFT) ? -runSpeed : runSpeed;
                    if (EntititesCollision.CanMoveHere(enemyHitBox.x1 + xSpeed, enemyHitBox.y1, enemyHitBox.width, enemyHitBox.height, lvData)) {
                        if (EntititesCollision.IsFloor(enemyHitBox, xSpeed, lvData)) {
                            this.x += xSpeed;
                            return;
                        }
                    }
                    changeRunDir();
                    break;
                case EntityActions.EnemyConstants.ATTACK:
                    if (aniIndex == 0) attackChecked = false;
                    if (aniIndex == 2 && !attackChecked) {
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case EntityActions.EnemyConstants.HURT:
                    break;
                default:
                    break;
            }
        }
    }

    private void checkPlayerHit(MyRectangle enemyAttackBox, Player player) {
        if (enemyAttackBox.isRectCollideWidthRect(player.getPlayerHitBox())) {
            // Xác định hướng đẩy lùi: nếu player đối mặt bên phải (flipW = 1), đẩy sang phải, ngược lại đẩy sang trái
            float knockbackDirection = player.flipW == 1 ? 1.0f : -1.0f;
    
            // Gọi phương thức hurt() và truyền vào giá trị đẩy lùi
            player.hurt(-enemyDMG, knockbackDirection, x);
        }
        attackChecked = true;
    }
    
    private void changeRunDir() {
        runDir = (runDir == EntityActions.Directions.LEFT) ? EntityActions.Directions.RIGHT : EntityActions.Directions.LEFT;
    }

    private void updateHitbox() {
        enemyHitBox.x1 = x;
        enemyHitBox.y1 = y;
    }

    private void newState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    private boolean canSeePlayer(int[][] lvData, Player player) {
        int playerTileY = (int) (player.getPlayerHitBox().y1 / Game.TILE_HEIGHT_SIZE);
        tileY = (int) (enemyHitBox.y1 / Game.TILE_HEIGHT_SIZE);
        if (playerTileY == tileY && isPlayerInRange(player)) {
            return EntititesCollision.IsSightClear(lvData, enemyHitBox, player.getPlayerHitBox(), tileY);
        }
        return false;
    }

    private boolean isPlayerInRange(Player player) {
        if (player.getPlayerHitBox().y1 + player.getPlayerHitBox().height <= enemyHitBox.y1) return false;
        int absValue = (int) Math.abs(player.getPlayerHitBox().x1 - enemyHitBox.x1);
        return absValue <= attackDistance * 5;
    }

    private boolean isPlayerCloseForAttack(Player player) {
        int playerTileY = (int) (player.getPlayerHitBox().y1 / Game.TILE_HEIGHT_SIZE);
        if (playerTileY != tileY) return false;
        int absValue = (int) Math.abs(player.getPlayerHitBox().x1 - enemyHitBox.x1);
        return absValue <= attackDistance;
    }

    private void turnTowardsPlayer(Player player) {
        runDir = (player.getPlayerHitBox().x1 > enemyHitBox.x1) ? EntityActions.Directions.RIGHT : EntityActions.Directions.LEFT;
    }

    public int flipX() {
        return (runDir == EntityActions.Directions.RIGHT) ? 0 : (int) (screenWidth * 0.05f);
    }

    public int flipW() {
        return (runDir == EntityActions.Directions.RIGHT) ? 1 : -1;
    }

    public void hurt(int playerDMG) {
        currentHealth -= playerDMG;
        if (currentHealth <= 0) {
            newState(EntityActions.EnemyConstants.DEAD);
        } else {
            newState(EntityActions.EnemyConstants.HURT);
        }
        
    }

    public boolean isActive() {
        return active;
    }

    public void resetAll() {
        enemyHitBox.x1 = x;
        enemyHitBox.y1 = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(EntityActions.EnemyConstants.IDLE);
        active = true;
    }
}
