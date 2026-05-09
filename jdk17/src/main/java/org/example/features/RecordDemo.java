package org.example.features;

import java.util.List;
import java.util.Objects;

/**
 * Java 17 特性：Records（记录类）
 *
 * Record 是一种特殊的类，自动生成构造器、equals()、hashCode()、toString()。
 * 适合用作不可变数据载体（DTO、值对象）。
 */
public class RecordDemo {

    // 基本 record
    record Point(int x, int y) {}

    // 带 compact constructor 的 record（参数校验）
    record Range(int start, int end) {
        Range {
            if (start > end) {
                throw new IllegalArgumentException("start(%d) > end(%d)".formatted(start, end));
            }
        }

        // 自定义方法
        int length() { return end - start; }
    }

    // 带泛型的 record
    record Pair<A, B>(A first, B second) {}

    // record 可以实现接口
    record Employee(String name, double salary) implements Comparable<Employee> {
        @Override
        public int compareTo(Employee other) {
            return Double.compare(this.salary, other.salary);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Java 17: Records 记录类演示 ===\n");

        // 1. 基本使用
        Point p1 = new Point(3, 4);
        Point p2 = new Point(3, 4);
        Point p3 = new Point(5, 6);
        System.out.println("--- 基本 Record ---");
        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p1.equals(p2) = " + p1.equals(p2));  // true
        System.out.println("p1.equals(p3) = " + p1.equals(p3));  // false
        System.out.println("p1.hashCode() = " + p1.hashCode());
        System.out.println("p2.hashCode() = " + p2.hashCode());  // 相同
        System.out.println("p1.x() = " + p1.x() + ", p1.y() = " + p1.y());

        // 2. Compact constructor 参数校验
        System.out.println("\n--- Compact Constructor 参数校验 ---");
        Range r = new Range(1, 10);
        System.out.println("Range(1,10) = " + r + ", length = " + r.length());
        try {
            new Range(10, 1);  // 应该抛出异常
        } catch (IllegalArgumentException e) {
            System.out.println("Range(10,1) 抛出异常: " + e.getMessage());
        }

        // 3. 泛型 Record
        System.out.println("\n--- 泛型 Record ---");
        Pair<String, Integer> pair = new Pair<>("Java", 17);
        System.out.println("Pair = " + pair);
        System.out.println("first = " + pair.first() + ", second = " + pair.second());

        // 4. Record 实现接口
        System.out.println("\n--- Record 实现 Comparable ---");
        List<Employee> employees = List.of(
            new Employee("张三", 8000),
            new Employee("李四", 12000),
            new Employee("王五", 6000)
        );
        employees.stream()
            .sorted()
            .forEach(e -> System.out.println("  %s: %.0f".formatted(e.name(), e.salary())));

        // 5. Record 在局部作用域中使用
        System.out.println("\n--- 局部 Record ---");
        record NameValue(String name, int value) {}
        NameValue nv = new NameValue("测试", 42);
        System.out.println("局部 record: " + nv);
    }
}
