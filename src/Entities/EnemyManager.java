package Entities;

import GameState.Playing;
import LoadSave.LoadAndSave;
import Physic.MyShape2D.MyRectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] enemyActions;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        enemies = LoadAndSave.getEnemies();
    }

    private void loadEnemyImgs() {
        enemyActions = LoadAndSave.loadEnemy();
    }

    public void update(int[][] lvData, Player player) {
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                enemy.update(lvData, player);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                drawEnemy(g, enemy, xLvlOffset, yLvlOffset);
            }
        }
    }

    private void drawEnemy(Graphics g, Enemy enemy, int xLvlOffset, int yLvlOffset) {
        g.setColor(Color.BLACK);
        // g.drawRect((int) enemy.x - xLvlOffset, (int) enemy.y - yLvlOffset, (int) enemy.getEnemyHitBox().width, (int) enemy.getEnemyHitBox().height);
        // g.drawRect((int) enemy.attackBox.x1 - xLvlOffset, (int) enemy.attackBox.y1 - yLvlOffset, (int) enemy.attackBox.width, (int) enemy.attackBox.height);
        g.drawImage(enemyActions[enemy.enemyState][enemy.aniIndex],
                (int) (enemy.x - enemy.excessInX) - xLvlOffset + enemy.flipX(),
                (int) (enemy.y - enemy.excessInY) - yLvlOffset,
                (int) (enemy.screenWidth * 0.05f) * enemy.flipW(),
                (int) (enemy.screenHeight * 0.1f), null);
    }

    public void checkEnemyHit(MyRectangle playerAttackBox, int damage) {
        for (Enemy enemy : enemies) {
            if (enemy.isActive() && playerAttackBox.isRectCollideWidthRect(enemy.getEnemyHitBox())) {
                enemy.hurt(damage); // Assuming player does damage
                if(playing.getPlayer().ultimate != null)
                    playing.getPlayer().ultimate.active = false;
                return; // Stop after hitting the first enemy
            }
        }
    }

    public void resetAllEnemies() {
        for (Enemy enemy : enemies) {
            enemy.resetAll();
        }
    }
}
