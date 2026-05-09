package org.example.mybatisplus;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * MyBatis-Plus 演示
 *
 * 核心概念：
 * 1. BaseMapper — 通用 CRUD 接口，继承即获得 17+ 方法，零 SQL
 * 2. 条件构造器（QueryWrapper）— 链式构建 WHERE 条件
 * 3. Lambda 条件构造器 — 类型安全，避免字段名硬编码
 * 4. 分页插件 — MybatisPlusInterceptor + PaginationInnerInterceptor
 *
 * 与原生 MyBatisDemo 的区别：
 * - 原生 MyBatis: 手写 XML SQL，每张表需要独立 Mapper XML
 * - MyBatis-Plus: 通用 CRUD 自动完成，条件构造器动态拼接
 *
 * 使用 H2 内存数据库，无需外部数据库服务
 */
public class MyBatisPlusDemo {

    public static void main(String[] args) {
        System.out.println("=== MyBatis-Plus 演示 ===\n");

        // 1. 程序化配置（不依赖 XML 和 Spring）
        SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();

        // 2. 建表（MyBatis-Plus 不自动建表，需要手动执行）
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            mapper.createTable();
            session.commit();
        }

        // 3. 插入数据（内置 insert）
        System.out.println("--- 插入数据（内置 insert）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            mapper.insert(new Order("ORD-001", "张三", 299.0, Order.OrderStatus.PAID));
            mapper.insert(new Order("ORD-002", "李四", 1599.0, Order.OrderStatus.PENDING));
            mapper.insert(new Order("ORD-003", "张三", 89.0, Order.OrderStatus.SHIPPED));
            mapper.insert(new Order("ORD-004", "王五", 4299.0, Order.OrderStatus.COMPLETED));
            mapper.insert(new Order("ORD-005", "李四", 599.0, Order.OrderStatus.CANCELLED));
            mapper.insert(new Order("ORD-006", "赵六", 3299.0, Order.OrderStatus.PAID));
            mapper.insert(new Order("ORD-007", "王五", 199.0, Order.OrderStatus.PENDING));
            session.commit();

            System.out.println("插入 7 条订单数据\n");
        }

        // 4. 查询所有（内置 selectList）
        System.out.println("--- 查询所有订单（内置 selectList）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("customer_Name","王五");
            List<Order> orders = mapper.selectList(queryWrapper);  // null = 无条件
            orders.forEach(o -> System.out.println("  " + o));
            System.out.println("共 %d 条记录\n".formatted(orders.size()));
        }

        // 5. 按 ID 查询（内置 selectById）
        System.out.println("--- 按 ID 查询（内置 selectById）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            Order order = mapper.selectById(1L);
            System.out.println("  ID=1: " + order);

            Order notFound = mapper.selectById(999L);
            System.out.println("  ID=999: " + notFound);
        }

        // 6. 条件构造器 QueryWrapper — 等值 + 大小 + 排序
        System.out.println("\n--- QueryWrapper：金额 > 500，按金额降序 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.gt("amount", 500)       // amount > 500
                   .orderByDesc("amount");  // ORDER BY amount DESC

            List<Order> expensive = mapper.selectList(wrapper);
            expensive.forEach(o -> System.out.println("  " + o));
        }

        // 7. 条件构造器 — 模糊查询 + in
        System.out.println("\n--- QueryWrapper：客户姓 '王' 或 '赵' ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.likeLeft("customer_name", "三")  // customer_name LIKE '%三'
                   .or()
                   .in("status", "PAID", "SHIPPED");  // status IN ('PAID', 'SHIPPED')

            List<Order> result = mapper.selectList(wrapper);
            result.forEach(o -> System.out.println("  " + o));
        }

        // 8. Lambda 条件构造器 — 类型安全，避免字符串硬编码字段名
        System.out.println("\n--- LambdaQueryWrapper：技术部 25000+ 订单（类型安全）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Order::getStatus, Order.OrderStatus.PAID)
                   .gt(Order::getAmount, 1000)
                   .orderByDesc(Order::getAmount);

            List<Order> result = mapper.selectList(wrapper);
            result.forEach(o -> System.out.println("  " + o));
        }

        // 9. 条件构造器 — 统计
        System.out.println("\n--- QueryWrapper 统计：已支付订单数 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("status", "PAID");
            int count = mapper.selectCount(wrapper).intValue();
            System.out.println("  已支付订单数: " + count);
        }

        // 10. 自定义 SQL 查询（@Select 注解）
        System.out.println("\n--- 自定义 @Select：按客户名查询 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);
            List<Order> zhangOrders = mapper.selectByCustomerName("张三");
            zhangOrders.forEach(o -> System.out.println("  " + o));
        }

        // 11. 分页查询
        System.out.println("\n--- 分页查询：每页3条 ---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            // 第1页
            Page<Order> page1 = new Page<>(1, 3);
            IPage<Order> result1 = mapper.selectPage(page1, null);
            System.out.println("  第1页 (共%d页, %d条):".formatted(result1.getPages(), result1.getTotal()));
            result1.getRecords().forEach(o -> System.out.println("    " + o));

            // 第2页
            Page<Order> page2 = new Page<>(2, 3);
            IPage<Order> result2 = mapper.selectPage(page2, null);
            System.out.println("  第2页:");
            result2.getRecords().forEach(o -> System.out.println("    " + o));
        }

        // 12. 更新（内置 updateById）
        System.out.println("\n--- 更新订单状态（内置 updateById）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            Order before = mapper.selectById(2L);
            System.out.println("  更新前: " + before);

            before.setStatus(Order.OrderStatus.PAID);
            before.setAmount(1499.0);
            mapper.updateById(before);
            session.commit();

            Order after = mapper.selectById(2L);
            System.out.println("  更新后: " + after);
        }

        // 13. 删除（内置 deleteById）
        System.out.println("\n--- 删除订单（内置 deleteById）---");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            OrderMapper mapper = session.getMapper(OrderMapper.class);

            int rows = mapper.deleteById(5L);
            session.commit();
            System.out.println("  删除 ID=5，影响 %d 行".formatted(rows));
            System.out.println("  剩余 %d 条记录".formatted(mapper.selectCount(null)));
            mapper.selectList(null).forEach(o -> System.out.println("    " + o));
        }

        System.out.println("\n=== MyBatis-Plus 演示完成 ===");
    }

    /**
     * 程序化构建 SqlSessionFactory（不依赖 XML 和 Spring）
     */
    private static SqlSessionFactory buildSqlSessionFactory() {
        DataSource dataSource = createH2DataSource();

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setEnvironment(new Environment("development", new JdbcTransactionFactory(), dataSource));
        configuration.addMapper(OrderMapper.class);
        configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);

        // 注册分页插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        configuration.addInterceptor(interceptor);

        return new MybatisSqlSessionFactoryBuilder().build(configuration);
    }

    private static DataSource createH2DataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:mybatis_plus_test;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
        return ds;
    }
}
