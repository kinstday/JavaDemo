package org.example.features;

/**
 * Java 17 特性：Switch Expressions（Switch 表达式）
 *
 * Java 14 正式引入。支持箭头语法、多 case 合并、
 * 作为表达式返回值（无需 break）、yield 返回复杂值。
 */
public class SwitchExpressionDemo {

    enum Season { SPRING, SUMMER, AUTUMN, WINTER }
    enum Level { LOW, MEDIUM, HIGH, CRITICAL }

    // 1. 箭头语法 — 无需 break，不会 fall-through
    static String seasonDesc(Season season) {
        return switch (season) {
            case SPRING -> "万物复苏，春暖花开";
            case SUMMER -> "烈日炎炎，骄阳似火";
            case AUTUMN -> "秋高气爽，硕果累累";
            case WINTER -> "银装素裹，寒风凛冽";
        };
    }

    // 2. 多 case 合并
    static String quarter(Season season) {
        return switch (season) {
            case SPRING -> "Q1 (1-3月)";
            case SUMMER -> "Q2 (4-6月)";
            case AUTUMN -> "Q3 (7-9月)";
            case WINTER -> "Q4 (10-12月)";
        };
    }

    // 3. 作为表达式赋值
    static int levelScore(Level level) {
        int score = switch (level) {
            case LOW      -> 1;
            case MEDIUM   -> 2;
            case HIGH     -> 3;
            case CRITICAL -> 4;
        };
        return score;
    }

    // 4. yield 返回复杂值（多行逻辑）
    static String levelDescription(Level level) {
        return switch (level) {
            case LOW -> "低优先级";
            case MEDIUM -> "中优先级";
            case HIGH -> {
                String msg = "高优先级";
                System.out.println("    [日志] 高优先级告警触发");
                yield msg + " - 需要关注";
            }
            case CRITICAL -> {
                String msg = "严重级别";
                System.out.println("    [日志] 严重告警！立即处理");
                yield msg + " - 立即响应";
            }
        };
    }

    // 5. 传统 switch 表达式对比
    static String traditionalSwitch(Season season) {
        switch (season) {
            case SPRING: return "春天";
            case SUMMER: return "夏天";
            case AUTUMN: return "秋天";
            case WINTER: return "冬天";
            default: throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Java 17: Switch Expressions 演示 ===\n");

        // 1. 箭头语法
        System.out.println("--- 箭头语法 ---");
        for (Season s : Season.values()) {
            System.out.println("  %s -> %s".formatted(s, seasonDesc(s)));
        }

        // 2. 多 case 合并
        System.out.println("\n--- 季度映射 ---");
        for (Season s : Season.values()) {
            System.out.println("  %s -> %s".formatted(s, quarter(s)));
        }

        // 3. 表达式赋值
        System.out.println("\n--- 级别评分（表达式赋值）---");
        for (Level l : Level.values()) {
            System.out.println("  %s -> %d分".formatted(l, levelScore(l)));
        }

        // 4. yield 复杂逻辑
        System.out.println("\n--- yield 复杂逻辑 ---");
        for (Level l : Level.values()) {
            System.out.println("  %s -> %s".formatted(l, levelDescription(l)));
        }
    }
}
