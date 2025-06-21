package Physic.MyShape2D;

public class MyRectangle implements MyShape {
    public float x1, y1;
    public float width, height;

    public MyRectangle(float x1, float y1, float width, float height) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }

    // Kiểm tra va chạm giữa hình chữ nhật và hình tròn
    public boolean isRectCollideWidthCir(MyCircle cir) {
        float cirX = cir.x;
        float cirY = cir.y;
        float cirRadius = cir.radius;

        // Tìm tọa độ gần nhất của hình tròn so với hình chữ nhật
        float closestX = Math.max(x1, Math.min(cirX, x1 + width));
        float closestY = Math.max(y1, Math.min(cirY, y1 + height));

        // Tính khoảng cách giữa hình tròn và điểm gần nhất của hình chữ nhật
        float distanceSquared = (cirX - closestX) * (cirX - closestX) + (cirY - closestY) * (cirY - closestY);
        return distanceSquared <= cirRadius * cirRadius;
    }

    // Kiểm tra nếu điểm (ax, ay) nằm trong hình chữ nhật
    public boolean isAPointCollideRect(float ax, float ay, MyRectangle Rect) {
        return ax >= Rect.x1 && ax <= Rect.x1 + Rect.width && ay >= Rect.y1 && ay <= Rect.y1 + Rect.height;
    }

    // Kiểm tra va chạm giữa 2 hình chữ nhật
    public boolean isRectCollideWidthRect(MyRectangle other) {
        // Kiểm tra nếu "this" nằm trong "other"
        boolean thisInOther = (x1 >= other.x1 && x1 + width <= other.x1 + other.width &&
                               y1 >= other.y1 && y1 + height <= other.y1 + other.height);
    
        // Kiểm tra nếu "other" nằm trong "this"
        boolean otherInThis = (other.x1 >= x1 && other.x1 + other.width <= x1 + width &&
                               other.y1 >= y1 && other.y1 + other.height <= y1 + height);
    
        // Kiểm tra nếu bất kỳ điểm nào của "this" nằm trong "other"
        boolean anyPointThisInOther = isAPointCollideRect(x1, y1, other) ||
                                      isAPointCollideRect(x1, y1 + height, other) ||
                                      isAPointCollideRect(x1 + width, y1, other) ||
                                      isAPointCollideRect(x1 + width, y1 + height, other);
    
        // Kiểm tra nếu bất kỳ điểm nào của "other" nằm trong "this"
        boolean anyPointOtherInThis = isAPointCollideRect(other.x1, other.y1, this) ||
                                      isAPointCollideRect(other.x1, other.y1 + other.height, this) ||
                                      isAPointCollideRect(other.x1 + other.width, other.y1, this) ||
                                      isAPointCollideRect(other.x1 + other.width, other.y1 + other.height, this);
    
        // Va chạm xảy ra nếu bất kỳ điều kiện nào đúng
        return thisInOther || otherInThis || anyPointThisInOther || anyPointOtherInThis;
    }

    @Override
    public boolean isCollide(MyShape other) {
        if (other instanceof MyRectangle) {
            return isRectCollideWidthRect((MyRectangle) other);
        } else if (other instanceof MyCircle) {
            return isRectCollideWidthCir((MyCircle) other);
        }
        return false;
    }
}
