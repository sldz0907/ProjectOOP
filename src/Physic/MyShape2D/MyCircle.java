package Physic.MyShape2D;

import Physic.SupportVector.MyVector2D;

public class MyCircle implements MyShape {
    public float x, y;
    public float radius;
    public MyVector2D vector;
    public MyCircle(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }
    // Kiểm tra va chạm giữa một điểm (a, b) và một hình tròn
    public boolean isAPointCollideCircle(float a, float b, MyCircle cir) {
        MyVector2D point = new MyVector2D(a, b);
        MyVector2D center = new MyVector2D(cir.x, cir.y);
        return point.getDistanceBetweenVector(center) <= cir.radius;
    }

    @Override
    public boolean isCollide(MyShape other) {
        if (other instanceof MyRectangle) {
            // Kiểm tra va chạm với hình chữ nhật
            if (((MyRectangle) other).isRectCollideWidthCir(this)) {
                return true;
            }
            return false;
        } else if (other instanceof MyCircle) {
            // Kiểm tra va chạm với hình tròn khác
            MyVector2D otherCenter = new MyVector2D(((MyCircle) other).x, ((MyCircle) other).y);
            MyVector2D thisCenter = new MyVector2D(x, y);
            float distance = thisCenter.getDistanceBetweenVector(otherCenter);
            if (distance <= ((MyCircle) other).radius + this.radius) {
                return true;  // Va chạm giữa hai hình tròn
            }
            return false;
        }
        return false;
    }
}
