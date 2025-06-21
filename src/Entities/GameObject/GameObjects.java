package Entities.GameObject;

import java.awt.Graphics;

import Entities.Entity;
import GameState.Playing;
import Physic.MyShape2D.MyCircle;
import Physic.MyShape2D.MyRectangle;

public abstract class GameObjects extends Entity {
    private Playing playing;
    public GameObjects(float x, float y, float width, float height, Playing playing)
    {
        super(x, y, new MyRectangle(x, y, width, height));
        this.playing = playing;
    }
    public GameObjects(float x, float y, float radius, Playing playing)
    {
        super(x, y, new MyCircle(x, y, radius));
        this.playing = playing;
    }
    public abstract float distanceToPlayer();
    public abstract void update();
    public abstract void render(Graphics g, int xLvlOffset, int yLvlOffset);
    public abstract void processRender(Graphics g);
    public abstract void processLogic();
}
