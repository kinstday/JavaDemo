package org.example.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hibernate 测试类：演示 Hibernate ORM 基本用法
 *
 * 核心概念：
 * 1. SessionFactory — 全局单例，线程安全，创建 Session
 * 2. Session — 一次持久化会话（非线程安全）
 * 3. Transaction — 事务管理
 * 4. Entity — @Entity 注解的 POJO，映射到数据库表
 * 5. Criteria API — 类型安全的查询构建
 *
 * 使用 H2 内存数据库，无需外部数据库服务
 */
public class HibernateDemo {

    public static void main(String[] args) {
        System.out.println("=== Hibernate 测试演示 ===\n");

        // 1. 创建 SessionFactory（程序化配置）
        SessionFactory sessionFactory = new Configuration()
            .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
            .setProperty("hibernate.connection.url", "jdbc:h2:mem:hibernate_test;DB_CLOSE_DELAY=-1")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
            .setProperty("hibernate.hbm2ddl.auto", "update")  // 自动建表
            .setProperty("hibernate.show_sql", "false")
            .setProperty("hibernate.format_sql", "true")
            .addAnnotatedClass(Product.class)
            .buildSessionFactory();

        // 2. 插入数据（CRUD - Create）
        System.out.println("--- 插入数据 ---");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(new Product("MacBook Pro", "Apple 笔记本电脑", 14999.0, 50, Product.Category.ELECTRONICS));
            session.persist(new Product("iPhone 15", "Apple 手机", 7999.0, 200, Product.Category.ELECTRONICS));
            session.persist(new Product("Java 编程思想", "经典 Java 教材", 108.0, 300, Product.Category.BOOKS));
            session.persist(new Product("机械键盘", "Cherry MX 红轴", 599.0, 100, Product.Category.ELECTRONICS));
            session.persist(new Product("纯棉T恤", "舒适透气", 99.0, 500, Product.Category.CLOTHING));

            session.getTransaction().commit();
            System.out.println("插入 5 条商品数据\n");
        }

        // 3. 查询所有（CRUD - Read）
        System.out.println("--- 查询所有商品 ---");
        try (Session session = sessionFactory.openSession()) {
            List<Product> products = session.createQuery("FROM Product", Product.class).getResultList();
            products.forEach(p -> System.out.println("  " + p));
            System.out.println("共 %d 条记录\n".formatted(products.size()));
        }

        // 4. 按 ID 查询
        System.out.println("--- 按 ID 查询 ---");
        try (Session session = sessionFactory.openSession()) {
            Product product = session.get(Product.class, 1L);
            System.out.println("  ID=1: " + product);

            Product notFound = session.get(Product.class, 999L);
            System.out.println("  ID=999: " + notFound);
        }

        // 5. HQL 查询（Hibernate Query Language）
        System.out.println("\n--- HQL 查询：价格 > 1000 的商品 ---");
        try (Session session = sessionFactory.openSession()) {
            List<Product> expensive = session.createQuery(
                "FROM Product WHERE price > :minPrice ORDER BY price DESC", Product.class)
                .setParameter("minPrice", 1000.0)
                .getResultList();
            expensive.forEach(p -> System.out.println("  " + p));
        }

        // 6. Criteria API（类型安全查询）
        System.out.println("\n--- Criteria API：电子产品 ---");
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            cq.select(root).where(cb.equal(root.get("category"), Product.Category.ELECTRONICS));

            List<Product> electronics = session.createQuery(cq).getResultList();
            electronics.forEach(p -> System.out.println("  " + p));
        }

        // 7. 更新（CRUD - Update）
        System.out.println("\n--- 更新商品价格 ---");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Product product = session.get(Product.class, 5L);
            System.out.println("  更新前: " + product);
            product.setPrice(12999.0);
            product.setStock(45);
            // Hibernate 自动检测脏数据并生成 UPDATE SQL

            session.getTransaction().commit();

            // 验证更新
            Product updated = session.get(Product.class, 1L);
            System.out.println("  更新后: " + updated);
        }

        // 8. 删除（CRUD - Delete）
        System.out.println("\n--- 删除商品 ---");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Product product = session.get(Product.class, 5L);
            System.out.println("  删除: " + product);
            session.remove(product);

            session.getTransaction().commit();

            List<Product> remaining = session.createQuery("FROM Product", Product.class).getResultList();
            System.out.println("  剩余 %d 条记录".formatted(remaining.size()));
            remaining.forEach(p -> System.out.println("    " + p));
        }

        // 9. 聚合查询
        System.out.println("\n--- 聚合查询 ---");
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult();
            Double avgPrice = session.createQuery("SELECT AVG(p.price) FROM Product p", Double.class).getSingleResult();
            Double maxPrice = session.createQuery("SELECT MAX(p.price) FROM Product p", Double.class).getSingleResult();
            System.out.println("  商品总数: " + count);
            System.out.println("  平均价格: %.2f".formatted(avgPrice));
            System.out.println("  最高价格: %.2f".formatted(maxPrice));
        }

        // 关闭 SessionFactory
        sessionFactory.close();
        System.out.println("\n=== Hibernate 测试完成 ===");
    }
}
