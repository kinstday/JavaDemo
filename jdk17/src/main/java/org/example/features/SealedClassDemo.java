package org.example.features;

import org.example.*;

/**
 * Java 17 特性：Sealed Classes（密封类）
 *
 * 密封类/接口通过 permits 关键字限制哪些类可以实现/继承它，
 * 编译器能据此进行穷举检查（exhaustiveness check）。
 */
public class SealedClassDemo {

    // 密封类 + pattern matching：编译器知道所有可能的子类型，无需 default 分支
//    static String describe(Shape shape) {
//        return switch (shape) {
//            case Circle c       -> "圆形，半径=%.1f，面积=%.2f".formatted(c.radius(), c.area());
//            case Rectangle r    -> "矩形，宽=%.1f，高=%.1f，面积=%.2f".formatted(r.width(), r.height(), r.area());
//            case Triangle t     -> "三角形，底=%.1f，高=%.1f，面积=%.2f".formatted(t.base(), t.height(), t.area());
//        };
//    }

    public static void main(String[] args) {
        System.out.println("=== Java 17: Sealed Classes 密封类演示 ===\n");

        Shape[] shapes = {
            new Circle(5),
            new Rectangle(4, 6),
            new Triangle(3, 8),
            new ColoredCircle(2.5, "红色")  // non-sealed 子类
        };

        for (Shape s : shapes) {
//            System.out.println(describe(s));
        }

        // 验证密封类的编译期穷举检查
        System.out.println("\n--- 密封类层次结构 ---");
        System.out.println("Shape 是 sealed interface，permits: Circle, Rectangle, Triangle");
        System.out.println("Circle 是 non-sealed class（允许被继承）");
        System.out.println("Rectangle 是 final class（不可继承）");
        System.out.println("Triangle 是 final class（不可继承）");
        System.out.println("ColoredCircle extends Circle（non-sealed 的子类）");
    }
}
