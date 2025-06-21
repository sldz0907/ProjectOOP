package Entities.Traps;

import Entities.Player;
import GameState.Playing;
import Entities.Entity;
import Entities.EntityActions;
import LoadSave.LoadAndSave;
import Physic.EntititesCollision;
import Physic.MyShape2D.MyCircle;
import Physic.MyShape2D.MyRectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class SpikeTrap extends Entity implements Trap {
    private float spikeSpeed = 0f;
    private float currentSpeed = 1.0f;
    private float jumpSpeed = 1.0f;
    private Player player;
    private Playing playing;
    private int runDir = EntityActions.Directions.LEFT;
    private boolean inAir = false;
    private int[][] lvData;
    private boolean firstUpdate = true;
    MyRectangle physicsBox;
    private BufferedImage[] animations;
    private int aniTick = 0, aniSpeed = 2, aniIndex = 0;
    private int damage = 10;
    public SpikeTrap(float x, float y, float radius, Player player, Playing playing, int[][] lvData) {
        super(x, y, new MyCircle(x + radius, y + radius, radius));
        this.player = player;
        this.playing = playing;
        this.lvData = lvData;
        this.physicsBox = new MyRectangle(x, y, radius * 2, radius);
        this.animations = LoadAndSave.loadSpike();
    }

    public void update() {
        updateBehavior();
        updateHitbox();
        entityInAffect();
        updateAnimationTick();
    }

    private void updateBehavior() {
        if (firstUpdate) {
            if (!EntititesCollision.IsEntityOnFloor(physicsBox, lvData)) {
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir) {
            if (EntititesCollision.CanMoveHere(physicsBox.x1, physicsBox.y1 + jumpSpeed, physicsBox.width, physicsBox.height, lvData)) {
                this.y += jumpSpeed;
            } else {
                inAir = false;
                physicsBox.y1 = EntititesCollision.GetEntityYPosUnderRoofOrAboveFloor(physicsBox, jumpSpeed);
            }
        } else {
            spikeSpeed = (runDir == EntityActions.Directions.LEFT) ? -currentSpeed : currentSpeed;

            if (EntititesCollision.CanMoveHere(physicsBox.x1 + spikeSpeed, physicsBox.y1, physicsBox.width, physicsBox.height, lvData)) {
                if (EntititesCollision.IsFloor(physicsBox, spikeSpeed, lvData)) {
                    this.x += spikeSpeed;
                    return;
                }
            }
            changeRunDir();
        }
    }

    private void changeRunDir() {
        runDir = (runDir == EntityActions.Directions.LEFT) ? EntityActions.Directions.RIGHT : EntityActions.Directions.LEFT;
    }

    private void updateHitbox() {
        getHitBox().x = x + getHitBox().radius;
        getHitBox().y = y + getHitBox().radius;
        physicsBox.x1 = x;
        physicsBox.y1 = y;
    }

    @Override
    public void entityInAffect() {
        if (player.getPlayerHitBox().isCollide(getHitBox())) {
            activate();
        }
    }

    @Override
    public void activate() {
        player.gotStruckbyTrap(damage);
    }

    public MyCircle getHitBox() {
        return (MyCircle) hitBox;
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset) {
        g.drawImage(animations[aniIndex], (int) x - xLvlOffset, (int) y - yLvlOffset, (int) physicsBox.width, (int) physicsBox.height * 2, null);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex = (aniIndex + 1) % animations.length;
        }
    }
}
