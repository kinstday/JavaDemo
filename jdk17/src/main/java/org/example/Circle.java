package org.example;

/**
 * non-sealed：允许被 ColoredCircle 继承
 */
public non-sealed class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double radius() { return radius; }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return "Circle[radius=%.1f]".formatted(radius);
    }
}
