package org.example;

public final class Triangle implements Shape {
    private final double base;
    private final double height;

    public Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    public double base() { return base; }
    public double height() { return height; }

    @Override
    public double area() {
        return 0.5 * base * height;
    }

    @Override
    public String toString() {
        return "Triangle[base=%.1f, height=%.1f]".formatted(base, height);
    }
}
