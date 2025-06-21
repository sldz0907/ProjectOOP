package LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CloudInBackground {
    public int x,y,width,height;
    public float dirX;
    public BufferedImage img;
    public CloudInBackground(int x, int y, int width, int height, float dirX)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dirX = dirX;
        getCloudImage();
    }
    private void getCloudImage()
    {
        img =  LoadAndSave.GetSpriteAtlas(LoadAndSave.SMALL_CLOUDS);
    }
    public void update()
    {
        x += dirX;
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset)
    {
        g.drawImage(img, x - xLvlOffset, y - yLvlOffset, width, height, null);
    }
}
