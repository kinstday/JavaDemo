package org.example;

public final class Rectangle implements Shape {
    private final double width;
    private final double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double width() { return width; }
    public double height() { return height; }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle[width=%.1f, height=%.1f]".formatted(width, height);
    }
}
