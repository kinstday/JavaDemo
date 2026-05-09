package org.example.features;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Java 17 特性：Stream API 增强
 *
 * Java 16 引入 Stream.toList()，Java 16 引入 Stream.mapMulti()。
 * 这些方法在 Java 17 中完全可用。
 */
public class StreamEnhancementDemo {

    public static void main(String[] args) {
        System.out.println("=== Java 17: Stream API 增强演示 ===\n");

        // 1. Stream.toList() — 返回不可变 List（Java 16+）
        System.out.println("--- Stream.toList() ---");
        List<String> languages = Stream.of("Java", "Python", "Go", "Rust", "Kotlin")
            .toList();  // 等价于 .collect(Collectors.toUnmodifiableList())
        System.out.println("语言列表: " + languages);
        System.out.println("类型: " + languages.getClass().getSimpleName());

        // 2. toList() vs collect(Collectors.toList())
        System.out.println("\n--- toList() vs collect(Collectors.toList()) ---");
        List<Integer> numbers = IntStream.rangeClosed(1, 5)
            .boxed()
            .toList();  // 不可变
        System.out.println("toList() 不可变: " + numbers);
        try {
            numbers.add(6);  // 会抛出 UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("numbers.add(6) 抛出: " + e.getClass().getSimpleName());
        }

        List<Integer> mutable = IntStream.rangeClosed(1, 5)
            .boxed()
            .collect(Collectors.toList());  // 可变
        mutable.add(6);
        System.out.println("collect(toList()) 可变: " + mutable);

        // 3. Stream.mapMulti() — 扁平化映射的替代方案（Java 16+）
        System.out.println("\n--- Stream.mapMulti() ---");
        List<List<Integer>> nested = List.of(
            List.of(1, 2, 3),
            List.of(4, 5),
            List.of(6, 7, 8, 9)
        );

        // 使用 mapMulti 替代 flatMap
        List<Integer> flat = nested.stream()
            .<Integer>mapMulti(List::forEach)
            .toList();
        System.out.println("嵌套列表: " + nested);
        System.out.println("mapMulti 展平: " + flat);

        // 4. mapMulti 实现一对多映射
        System.out.println("\n--- mapMulti 一对多映射 ---");
        List<Integer> originals = List.of(1, 2, 3);
        List<Integer> expanded = originals.stream()
            .<Integer>mapMulti((num, consumer) -> {
                consumer.accept(num);        // 原始值
                consumer.accept(num * 10);   // 乘以10
                consumer.accept(num * 100);  // 乘以100
            })
            .toList();
        System.out.println("原始: " + originals);
        System.out.println("展开(x1, x10, x100): " + expanded);

        // 5. 实用示例：单词统计
        System.out.println("\n--- 实用示例：单词频率统计 ---");
        String text = "java is great java is powerful java runs everywhere";
        Map<String, Long> wordCount = Stream.of(text.split(" "))
            .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
        wordCount.forEach((word, count) ->
            System.out.println("  '%s' 出现 %d 次".formatted(word, count)));
    }
}
