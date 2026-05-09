package org.example.features;

/**
 * Java 17 特性：Pattern Matching for instanceof
 *
 * 传统写法需要先 instanceof 再强制转型，
 * Java 17 允许在 instanceof 中直接绑定模式变量。
 */
public class PatternMatchingDemo {

    // 传统写法
    static String traditional(Object obj) {
        if (obj instanceof String) {
            String s = (String) obj;  // 需要显式转型
            return "字符串，长度=" + s.length();
        } else if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            return "整数，值=" + i;
        }
        return "其他类型";
    }

    // Java 17 模式匹配写法
    static String modern(Object obj) {
        if (obj instanceof String s) {  // 直接绑定到变量 s
            return "字符串，长度=%d，内容='%s'".formatted(s.length(), s);
        } else if (obj instanceof Integer i) {
            return "整数，值=%d，平方=%d".formatted(i, i * i);  // 直接使用 i
        } else if (obj instanceof int[] arr) {
            return "int数组，长度=%d".formatted(arr.length);
        }
        return "其他类型: " + obj.getClass().getSimpleName();
    }

    // 模式匹配在条件表达式中使用
    static boolean isLongString(Object obj) {
        // 模式变量 s 可以在 && 后续条件中使用（|| 不行）
        return obj instanceof String s && s.length() > 10;
    }

    // 结合 switch 表达式（Java 17 preview，编译需要 --enable-preview）
    // 这里用 if-else 演示等价效果
    static String describeObject(Object obj) {
        if (obj instanceof String s) {
            return "String[%d]='%s'".formatted(s.length(), s);
        } else if (obj instanceof Integer i) {
            return "Integer(%d)".formatted(i);
        } else if (obj instanceof Long l) {
            return "Long(%d)".formatted(l);
        } else if (obj instanceof Double d) {
            return "Double(%.2f)".formatted(d);
        } else if (obj instanceof Boolean b) {
            return "Boolean(%s)".formatted(b);
        } else if (obj instanceof int[] arr) {
            return "int[]{length=%d}".formatted(arr.length);
        } else {
            return obj != null ? obj.getClass().getSimpleName() : "null";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Java 17: Pattern Matching for instanceof ===\n");

        Object[] testCases = {
            "Hello, Java 17!",
            42,
            3.14,
            true,
            new int[]{1, 2, 3},
            new Object(),
            null
        };

        // 1. 传统写法 vs 模式匹配
        System.out.println("--- 传统写法 vs 模式匹配 ---");
        for (Object obj : testCases) {
            System.out.println("传统: " + traditional(obj));
            System.out.println("现代: " + modern(obj));
            System.out.println();
        }

        // 2. 模式变量在条件表达式中
        System.out.println("--- 模式变量在条件表达式中 ---");
        Object[] strings = {"短", "这是一个很长的字符串内容", "Hi"};
        for (Object obj : strings) {
            boolean result = isLongString(obj);
            System.out.println("'%s' isLongString? %s".formatted(obj, result));
        }

        // 3. 综合类型描述
        System.out.println("\n--- 综合类型描述 ---");
        for (Object obj : testCases) {
            System.out.println(describeObject(obj));
        }
    }
}
