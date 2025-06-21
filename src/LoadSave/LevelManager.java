package LoadSave;

import Mainpackage.Game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class LevelManager {

    private BufferedImage[] levelTiles;
    @SuppressWarnings("unused")
    private Game game;
    private BufferedImage bigCloud;
    private int bigCloudPosX = 0;
    private ArrayList<CloudInBackground> clouds;
    private ArrayList<CloudInBackground> needRemove;
    private int randNum;
    public static final int maxCloud = 10;
    public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
	public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
	public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
	public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
	public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * 1.5f);
	public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * 1.5f);
	public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * 1.5f);
	public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * 1.5f);
    
    public Level level;
    public int levelId;
    public LevelManager(Game game, int levelId)
    {
        this.game = game;
        this.levelId = levelId;
        level = new Level(LoadAndSave.getLvData(Integer.toString(levelId))); // lấy level data
        getTilesMap();
        PlayingBackground();
    }

    private void getTilesMap()
    {
        levelTiles = LoadAndSave.loadTiles();
    }
    private void PlayingBackground() {
        bigCloud = LoadAndSave.GetSpriteAtlas(LoadAndSave.BIG_CLOUDS);
        clouds = new ArrayList<>();
        needRemove = new ArrayList<>();
    }
    private void updateCloud()
    {
        randNum = (int) (Math.random() * 100 + 1); // từ 1 tới 100
        if (clouds.size() < maxCloud && randNum % 2 == 0) //này là xác suất thoai, số từ 1 tới 100 chia hết cho 3 thì có 33 số, xác suất là 33/100
        {
            int posX = (int)(-0.01f * Game.gameWidth + 1.01f*Game.gameWidth*Math.random());
            int posY = (int)(0.2f * Game.gameHeight + 0.8f * Game.gameHeight * Math.random());
            int t = (int)(Math.random() * 100), k = (int)(Math.random() * 100);
            int cloudWidth,cloudHeight;
            // chỉ là random thôi
            if (t%2 ==0)
            {
                cloudWidth = (int) (SMALL_CLOUD_WIDTH + SMALL_CLOUD_WIDTH * Math.random() * 0.3);
            }
            else
            {
                cloudWidth = (int) (SMALL_CLOUD_WIDTH - SMALL_CLOUD_WIDTH * Math.random() * 0.3);
            }
            if (k % 2 == 0)
            {
                cloudHeight = (int) (SMALL_CLOUD_HEIGHT + SMALL_CLOUD_HEIGHT * Math.random() * 0.3);
            }
            else
            {
                cloudHeight = (int) (SMALL_CLOUD_HEIGHT - SMALL_CLOUD_HEIGHT * Math.random() * 0.3);
            }
            float dirX = (float) Math.random() * 2 + 1f; // ngẫu nhiên tốc độ từ 1f tới 3f 
            if (t % 2 == 0) dirX = -dirX; // cho có bay bên trái bên phải
            //System.out.println("successful add cloud");
            clouds.add(new CloudInBackground(posX, posY, cloudWidth,cloudHeight, dirX)); // nói chung là để cloud vào array cho dễ quản lí
        }
    }
    public void resetCloud()
    {
        clouds.clear();
    }
    private void updateCloudPositions() {
        bigCloudPosX -= 1f;
        if (bigCloudPosX + BIG_CLOUD_WIDTH < 0) {
            bigCloudPosX += BIG_CLOUD_WIDTH;
        }
        for (CloudInBackground i : clouds)
        {
            i.update();
            if (i.x + i.width < 0 || i.x - i.width > level.getLvData()[0].length * Game.TILE_WIDTH_SIZE) // vượt ngoài map
            {
                needRemove.add(i);
            }
        }
        for (int i = 0; i < needRemove.size(); i++)
        {
            clouds.remove(needRemove.get(i));
            //System.out.println("successful delete cloud");
        }
        needRemove.clear();
    }

    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
        updateCloud();
        updateCloudPositions();        
        Color peachColor = Color.decode("#F0CEE7");
        g.setColor(peachColor);
        g.fillRect(0, 0, Game.gameWidth, Game.gameHeight);
        drawClouds(g,xLvlOffset,yLvlOffset);
        for (int i = 0; i < (level.getLvData().length ); i++)
        {
            for (int j =0; j < level.getLvData()[0].length; j++)
            {
                int index = level.getMapIndex(i, j);
                g.drawImage(levelTiles[index], j * Game.TILE_WIDTH_SIZE - xLvlOffset, i * Game.TILE_HEIGHT_SIZE - yLvlOffset, Game.TILE_WIDTH_SIZE, Game.TILE_HEIGHT_SIZE, null);
            }
        }
    }
    private void drawClouds(Graphics g, int xLvlOffset, int yLvlOffset) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, bigCloudPosX + (i * BIG_CLOUD_WIDTH), (int) (level.getLvData().length * Game.TILE_HEIGHT_SIZE - Game.gameHeight*0.4  - yLvlOffset), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for (CloudInBackground i : clouds)
        {
            i.render(g, xLvlOffset, yLvlOffset);
        }
    }
}
