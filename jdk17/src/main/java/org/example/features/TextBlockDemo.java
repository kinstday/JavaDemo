package org.example.features;

/**
 * Java 17 特性：Text Blocks（文本块）
 *
 * 使用三引号 """ 定义多行字符串，自动处理缩进和换行。
 * 支持 \ 续行（不换行）、\s 尾部空格、\n 显式换行等转义。
 */
public class TextBlockDemo {

    public static void main(String[] args) {
        System.out.println("=== Java 17: Text Blocks 文本块演示 ===\n");

        // 1. 基本文本块 vs 传统拼接
        System.out.println("--- 基本对比 ---");
        String traditional = "第一行\n第二行\n第三行";
        String textBlock = """
                第一行
                第二行
                第三行
                """;
        System.out.println("传统写法:\n" + traditional);
        System.out.println("文本块:\n" + textBlock);

        // 2. JSON 模板
        System.out.println("--- JSON 模板 ---");
        String json = """
                {
                    "name": "张三",
                    "age": 28,
                    "skills": ["Java", "Python", "Go"],
                    "address": {
                        "city": "北京",
                        "district": "海淀"
                    },
                    "wife":"1234"
                }
                """;
        System.out.println(json);

        // 3. SQL 语句
        System.out.println("--- SQL 语句 ---");
        String sql = """
                SELECT u.name, u.email, COUNT(o.id) AS order_count
                FROM users u
                LEFT JOIN orders o ON u.id = o.user_id
                WHERE u.created_at > '2024-01-01'
                GROUP BY u.id
                HAVING order_count > 5
                ORDER BY order_count DESC
                """;
        System.out.println(sql);

        // 4. HTML 模板
        System.out.println("--- HTML 模板 ---");
        String name = "Java 17";
        String html = """
                <html>
                <head><title>%s 新特性</title></head>
                <body>
                    <h1>%s 新特性演示</h1>
                    <ul>
                        <li>Sealed Classes</li>
                        <li>Records</li>
                        <li>Pattern Matching</li>
                    </ul>
                </body>
                </html>
                """.formatted(name, name);
        System.out.println(html);

        // 5. 转义字符演示
        System.out.println("--- 转义字符 ---");

        // \ 续行：行尾加 \ 表示不产生换行
        String noNewline = """
                这一行\
                和这一行连在一起\
                没有换行
                """;
        System.out.println("续行(\\): " + noNewline.trim());

        // \s 保留尾部空格
        String trailingSpaces = """
                代码缩进    \s
                保持对齐    \s
                """;
        System.out.println("尾部空格(\\s): '" + trailingSpaces + "'");

        // 6. 代码片段生成
        System.out.println("--- 代码片段生成 ---");
        String className = "UserService";
        String code = """
                public class %s {
                    private final UserRepository repository;

                    public %s(UserRepository repository) {
                        this.repository = repository;
                    }

                    public Optional<User> findById(Long id) {
                        return repository.findById(id);
                    }
                }
                """.formatted(className, className);
        System.out.println(code);
    }
}
