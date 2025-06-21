package GameState;

import Entities.EnemyManager;
import Entities.EntityActions;
import Entities.GameObject.ObjectManager;
import Entities.Player;
import Entities.Traps.TrapsManager;
import LoadSave.LevelManager;
import LoadSave.LoadAndSave;
import Mainpackage.Game;
import Physic.MyShape2D.MyRectangle;
import UI.GameOverOverlay;
import UI.LevelCompletedOverlay;
import UI.PauseOverlay;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {
    private Player player;
    private Game game;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private ObjectManager objectManager;
    private boolean gameOver = false;
    private TrapsManager trapsmanager;
    private int xLvlOffset = 0;
    private int yLvlOffset = 0;
	private int leftBorder = (int) (0.5 * Game.gameWidth);
	private int rightBorder = (int) (0.5 * Game.gameWidth);
    private int topBorder = (int) (0.5 * Game.gameHeight);
    private int botBorder = (int) (0.5 * Game.gameHeight);
	private int lvlTilesWide;
	private int maxTilesOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX;
    private int lvlTilesHeight;
    private int maxTileOffsetY;
    private int maxLvlOffsetY;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private boolean mouseLeftPressed = false;
    private boolean mouseRightPressed = false;
    private boolean lvlCompleted = false;
    public int currentLevel = 1;
    private int oldLevel = 1;
    public Playing(Game game)
    {
        super(game);
        this.game = game;
        initClasses();        
    }

    private void initClasses() {
        levelManager = new LevelManager(game, currentLevel);
        lvlTilesWide = LoadAndSave.getLvData(Integer.toString(currentLevel))[0].length;
	    maxTilesOffsetX = lvlTilesWide - Game.TILES_IN_WIDTH;
	    maxLvlOffsetX = maxTilesOffsetX * Game.TILE_WIDTH_SIZE;
        lvlTilesHeight = LoadAndSave.getLvData(Integer.toString(currentLevel)).length ;
        maxTileOffsetY = lvlTilesHeight - Game.TILES_IN_HEIGHT;
        maxLvlOffsetY = maxTileOffsetY * Game.TILE_HEIGHT_SIZE;
        player = new Player(getStartingX(), getStartingY(), Game.gameWidth, Game.gameHeight,levelManager.level.getLvData(), this);
        LoadAndSave.loadTrapAndEnemies(this, levelManager.level);
        trapsmanager = new TrapsManager(player);
        enemyManager = new EnemyManager(this);
        gameOverOverlay = new GameOverOverlay(this);
        pauseOverlay = new PauseOverlay(this);
        objectManager = new ObjectManager(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }
    
    public Player getPlayer() {
        return player;
    }
    public Game getGame() {
		return game;
	}
    @Override
    public synchronized void  update() {
        if (paused) {
            pauseOverlay.update();
        } else if(lvlCompleted){
            levelCompletedOverlay.update();
        } else if(!gameOver && !paused && !lvlCompleted) {
            if (currentLevel != oldLevel)
            {
                initClasses();// init lại các class với currentLevel
                oldLevel = currentLevel;
            }
            checkClosedToTheBorder();
            if (player.interact)
                player.getIdOfGameObject().processLogic();
            objectManager.update();
            player.update();
            checkClosedToTheBorder();
            enemyManager.update(levelManager.level.getLvData(), player);
            trapsmanager.update();
        }
        else if (gameOver) {
            gameOverOverlay.update();
        }
    }
    private void checkClosedToTheBorder()
    {
        int playerX = (int) player.getPlayerHitBox().x1;
        int playerY = (int) player.getPlayerHitBox().y1;
        int diffX = playerX - xLvlOffset;
        int diffY = playerY - yLvlOffset;

        if (diffX > rightBorder)
			xLvlOffset += diffX - rightBorder;
		else if (diffX < leftBorder)
			xLvlOffset += diffX - leftBorder;

        if (diffY >= topBorder){
            yLvlOffset += diffY - topBorder;
        }
        else if (diffY <= botBorder){
            yLvlOffset += diffY - botBorder;
        }

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

        if (yLvlOffset >= maxLvlOffsetY)
            yLvlOffset = maxLvlOffsetY;
        else if (yLvlOffset < 0)
            yLvlOffset = 0;
    }
    @Override
    public synchronized void render(Graphics g) {
        levelManager.render(g, xLvlOffset, yLvlOffset);
        objectManager.render(g, xLvlOffset , yLvlOffset);
        player.render(g , xLvlOffset, yLvlOffset);
        enemyManager.draw(g,xLvlOffset, yLvlOffset);
        trapsmanager.drawTraps(g, xLvlOffset, yLvlOffset); // vẽ trap
        getPlayer().drawUI(g);  //vẽ playerBar

        // in cuoi cung
        if (player.interact)
                {player.getIdOfGameObject().processRender(g);}
        if(gameOver) {
            gameOverOverlay.draw(g);
        }
        if(paused)
            pauseOverlay.draw(g);
        if(lvlCompleted)
            levelCompletedOverlay.draw(g);
    }

   public void allReset() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        trapsmanager.resetAlltraps();
        objectManager.resetAllObject();
        levelManager.resetCloud();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public void checkEnemyHit(MyRectangle playerAttackBox, int damage) {
        enemyManager.checkEnemyHit(playerAttackBox, damage);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver && !lvlCompleted)
            if(e.getButton()==MouseEvent.BUTTON1)
                player.setAttacking(true);
            else if (e.getButton()==MouseEvent.BUTTON3)
                player.setUltimate(true);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !paused && !lvlCompleted) {
            if (mouseLeftPressed) {
                player.setAttacking(true);  // Giữ tấn công nếu chuột trái nhấn
            }
            if (mouseRightPressed) {
                player.setUltimate(true);  // Giữ Ultimate nếu chuột phải nhấn
            }
        } else if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver && !lvlCompleted) {
			if (paused)
				pauseOverlay.mouseMoved(e);
		} else if(gameOver){
			gameOverOverlay.mouseMoved(e);
        } else if(lvlCompleted){
            levelCompletedOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!gameOver && !lvlCompleted){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    if(!player.isRight() && !player.attacking && !player.isUsingUlt)
                        player.playerDir = EntityActions.Directions.LEFT;
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    if(!player.isLeft() && !player.attacking && !player.isUsingUlt)
                        player.playerDir = EntityActions.Directions.RIGHT;
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
                case KeyEvent.VK_E:
                    player.Interact();
                    break;
                default:
                    break;
            }
        } if(lvlCompleted){
            levelCompletedOverlay.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver && !lvlCompleted) {
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else {
                if (e.getButton() == MouseEvent.BUTTON1) {  // Chuột trái
                    mouseLeftPressed = true;
                    player.setAttacking(true);
                } else if (e.getButton() == MouseEvent.BUTTON3) {  // Chuột phải
                    mouseRightPressed = true;
                    player.setUltimate(true);
                }
            }
        } else if(gameOver) {
            gameOverOverlay.mousePressed(e);
        } else if(lvlCompleted){
            levelCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver && !lvlCompleted) {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            } else {
                if (e.getButton() == MouseEvent.BUTTON1) {  // Chuột trái
                    mouseLeftPressed = false;
                    player.setAttacking(false);
                } else if (e.getButton() == MouseEvent.BUTTON3) {  // Chuột phải
                    mouseRightPressed = false;
                    player.setUltimate(false);
                }
            }
        } else if(gameOver) {
            gameOverOverlay.mouseReleased(e);
        } else if(lvlCompleted){
            levelCompletedOverlay.mouseReleased(e);
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
		game.getAudioPlayer().lvlCompleted();
		this.lvlCompleted = levelCompleted;
	}

    public float getStartingX() {
        if (currentLevel == 1)
            return 35;
        else return 40;
    }

    public float getStartingY() {
        if (currentLevel == 1)
            return 1235;
        else return 1073;
    }
    public void unpauseGame() {
		paused = false;
	}
    public LevelManager getLevelManager() {
		return levelManager;
	}
}
    
