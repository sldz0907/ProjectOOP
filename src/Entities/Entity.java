package Entities;

import Physic.MyShape2D.MyShape;

public abstract class Entity {
    protected float x, y;
    public float moveX, moveY; // vector di chuyển của Entity
    protected MyShape hitBox;
    public Entity(float x, float y, MyShape hitBox) {
        this.x = x;
        this.y = y;
        moveX = 0;
        moveY = 0;
        this.hitBox = hitBox;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public MyShape getShape(){
        return hitBox;
    }
}
