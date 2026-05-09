package org.example.features;

/**
 * Java 17 特性：Helpful NullPointerExceptions
 *
 * Java 14 引入，Java 17 进一步增强。
 * NPE 异常消息会精确指出哪个变量为 null，
 * 而不是只显示行号。
 */
public class NullPointerDemo {

    static class Company {
        String name;
        Address address;
        Company(String name, Address address) {
            this.name = name;
            this.address = address;
        }
    }

    static class Address {
        String city;
        String street;
        Address(String city, String street) {
            this.city = city;
            this.street = street;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Java 17: Helpful NullPointerExceptions ===\n");

        // 说明：Java 17 的 NPE 增强信息在 JVM 参数中默认开启
        // -XX:+ShowCodeDetailsInExceptionMessages (Java 14-17 默认 true)
        //
        // 传统 NPE 消息:  "null"
        // Java 17 NPE 消息: "Cannot read field \"city\" because \"company.address\" is null"

        System.out.println("演示场景（NPE 增强信息在控制台输出中体现）：\n");

        // 场景 1：链式调用中的 NPE
        Company company = new Company("测试公司", null);
        System.out.println("场景 1: company.address 为 null");
        System.out.println("  代码: company.address.city");
        try {
            String city = company.address.city;
        } catch (NullPointerException e) {
            System.out.println("  Java 17 NPE: " + e.getMessage());
            // 输出: Cannot read field "city" because "company.address" is null
        }

        // 场景 2：数组访问中的 NPE
        System.out.println("\n场景 2: 数组元素为 null");
        String[] names = {"Java", null, "Go"};
        System.out.println("  代码: names[1].length()");
        try {
            int len = names[1].length();
        } catch (NullPointerException e) {
            System.out.println("  Java 17 NPE: " + e.getMessage());
            // 输出: Cannot invoke "String.length()" because "names[1]" is null
        }

        // 场景 3：方法调用中的 NPE
        System.out.println("\n场景 3: null 对象的方法调用");
        String str = null;
        System.out.println("  代码: str.length()");
        try {
            str.length();
        } catch (NullPointerException e) {
            System.out.println("  Java 17 NPE: " + e.getMessage());
            // 输出: Cannot invoke "String.length()" because "str" is null
        }

        // 场景 4：参数为 null
        System.out.println("\n场景 4: 传递 null 参数");
        try {
            processValue(null);
        } catch (NullPointerException e) {
            System.out.println("  Java 17 NPE: " + e.getMessage());
            // 输出: Cannot invoke "Integer.intValue()" because "value" is null
        }

        System.out.println("\n--- 总结 ---");
        System.out.println("Java 17 的 NPE 消息精确指出:");
        System.out.println("  1. 访问了什么字段/方法");
        System.out.println("  2. 哪个对象或表达式为 null");
        System.out.println("  3. 大幅简化调试过程");
    }

    static void processValue(Integer value) {
        int result = value.intValue();  // NPE if value is null
    }
}
