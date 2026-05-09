package org.example;

/**
 * 演示 non-sealed：打破密封限制，允许任意类继承
 */
public class ColoredCircle extends Circle {
    private final String color;

    public ColoredCircle(double radius, String color) {
        super(radius);
        this.color = color;
    }

    public String color() { return color; }

    @Override
    public String toString() {
        return "ColoredCircle[radius=%.1f, color=%s]".formatted(radius(), color);
    }
}
