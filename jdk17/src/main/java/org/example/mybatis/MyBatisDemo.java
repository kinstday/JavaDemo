package org.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * MyBatis 测试类：演示 MyBatis 基本用法
 *
 * 核心概念：
 * 1. SqlSessionFactory — 全局单例，创建 SqlSession
 * 2. SqlSession — 一次数据库会话，执行 SQL
 * 3. Mapper 接口 — 类型安全的 SQL 映射
 * 4. XML 映射文件 — SQL 与 Java 方法的绑定
 *
 * 使用 H2 内存数据库，无需外部数据库服务
 * 配置文件：src/main/resources/mybatis-config.xml
 * 映射文件：src/main/resources/org/example/mybatis/UserMapper.xml
 */
public class MyBatisDemo {

    public static void main(String[] args) throws IOException {
        System.out.println("=== MyBatis 测试演示 ===\n");

        // 1. 从 XML 配置文件创建 SqlSessionFactory
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 3. 建表
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.createTable();
            session.commit();
            System.out.println("[建表] users 表创建成功\n");
        }

        // 4. 插入数据（CRUD - Create）
        System.out.println("--- 插入数据 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);

            mapper.insert(new User("张三", "zhangsan@example.com", 28));
            mapper.insert(new User("李四", "lisi@example.com", 32));
            mapper.insert(new User("王五", "wangwu@example.com", 25));
            mapper.insert(new User("赵六", "zhaoliu@example.com", 35));
            mapper.insert(new User("钱七", "qianqi@example.com", 22));
            session.commit();

            System.out.println("插入 5 条用户数据");
        }

        // 5. 查询所有（CRUD - Read）
        System.out.println("\n--- 查询所有用户 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            users.forEach(u -> System.out.println("  " + u));
            System.out.println("共 %d 条记录".formatted(users.size()));
        }

        // 6. 按 ID 查询
        System.out.println("\n--- 按 ID 查询 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectById(1L);
            System.out.println("  ID=1: " + user);

            User notFound = mapper.selectById(999L);
            System.out.println("  ID=999: " + notFound);
        }

        // 7. 条件查询（动态参数）
        System.out.println("\n--- 按年龄范围查询 (25-30) ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> users = mapper.selectByAgeRange(25, 30);
            users.forEach(u -> System.out.println("  " + u));
        }

        // 8. 更新（CRUD - Update）
        System.out.println("\n--- 更新用户邮箱 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int rows = mapper.updateEmail(1L, "zhangsan_new@example.com");
            session.commit();
            System.out.println("  更新了 %d 条记录".formatted(rows));

            User updated = mapper.selectById(1L);
            System.out.println("  更新后: " + updated);
        }

        // 9. 删除（CRUD - Delete）
        System.out.println("\n--- 删除用户 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            int rows = mapper.deleteById(5L);
            session.commit();
            System.out.println("  删除了 %d 条记录".formatted(rows));

            List<User> remaining = mapper.selectAll();
            System.out.println("  剩余 %d 条记录".formatted(remaining.size()));
            remaining.forEach(u -> System.out.println("    " + u));
        }

        // 10. 统计
        System.out.println("\n--- 统计 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println("  总记录数: " + mapper.count());
        }

        System.out.println("\n=== MyBatis 测试完成 ===");
    }
}
