package Physic.SupportVector;

public class MyVector2D {
    public float x, y;

    // Constructor mặc định
    public MyVector2D() {
        this.x = 0;
        this.y = 0;
    }

    // Constructor với giá trị x, y
    public MyVector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Tính khoảng cách giữa 2 vector
    public float getDistanceBetweenVector(MyVector2D other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    // Lấy độ dài của vector
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }

    // Tìm vector vuông góc với vector hiện tại
    public MyVector2D getPerpendicularVector() {
        return new MyVector2D(-this.y, this.x);
    }

    // Hiệu 2 vector
    public MyVector2D subtract(MyVector2D v2) {
        return new MyVector2D(this.x - v2.x, this.y - v2.y);
    }

    // Tổng 2 vector
    public MyVector2D vecSum(MyVector2D other) {
        return new MyVector2D(this.x + other.x, this.y + other.y);
    }

    // Nhân vô hướng giữa 2 vector
    public float dotProduct(MyVector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    // Chuẩn hóa vector
    public MyVector2D normalize() {
        float length = this.getLength();
        if (length == 0) {
            return new MyVector2D(0, 0); // Tránh chia cho 0
        }
        return new MyVector2D(this.x / length, this.y / length);
    }
}
