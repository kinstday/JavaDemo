package org.example.springdata;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA 演示
 *
 * 核心概念：
 * 1. Repository — 继承 JpaRepository 即获得完整 CRUD，无需写实现类
 * 2. 方法命名查询 — 按规则命名方法，Spring 自动生成 SQL
 * 3. @Query — 自定义 JPQL 查询
 * 4. 分页排序 — Pageable + Page 内置支持
 *
 * 与 HibernateDemo 的区别：
 * - Hibernate: 手动管理 Session/Transaction，面向 API 编程
 * - Spring Data JPA: 面向 Repository 接口编程，框架管理生命周期
 *
 * 使用 H2 内存数据库，无需外部数据库服务
 */
public class SpringDataJpaDemo {

    public static void main(String[] args) {
        System.out.println("=== Spring Data JPA 演示 ===\n");

        // 手动创建 Spring 上下文（非 Spring Boot 应用）
        var context = new AnnotationConfigApplicationContext(SpringDataConfig.class);
        EmployeeRepository repo = context.getBean(EmployeeRepository.class);

        // 1. 插入数据（CRUD - Create）
        System.out.println("--- 插入数据 ---");
        repo.saveAll(List.of(
            new Employee("张三", "技术部", 25000.0, LocalDate.of(2020, 3, 15)),
            new Employee("李四", "技术部", 30000.0, LocalDate.of(2018, 7, 1)),
            new Employee("王五", "市场部", 20000.0, LocalDate.of(2021, 1, 10)),
            new Employee("赵六", "市场部", 22000.0, LocalDate.of(2019, 5, 20)),
            new Employee("钱七", "技术部", 35000.0, LocalDate.of(2017, 11, 8)),
            new Employee("孙八", "人事部", 18000.0, LocalDate.of(2022, 6, 1)),
            new Employee("周九", "人事部", 19000.0, LocalDate.of(2023, 2, 14))
        ));
        System.out.println("插入 7 条员工数据\n");

        // 2. 查询所有（CRUD - Read）
        System.out.println("--- 查询所有员工 ---");
        List<Employee> all = repo.findAll();
        all.forEach(e -> System.out.println("  " + e));
        System.out.println("共 %d 条记录\n".formatted(all.size()));

        // 3. 按 ID 查询
        System.out.println("--- 按 ID 查询 ---");
        repo.findById(1L).ifPresent(e -> System.out.println("  ID=1: " + e));
        repo.findById(999L).ifPresentOrElse(
            e -> System.out.println("  ID=999: " + e),
            () -> System.out.println("  ID=999: 未找到")
        );

        // 4. 方法命名查询 — 按部门查询
        System.out.println("\n--- 方法命名查询：技术部员工 ---");
        List<Employee> tech = repo.findByDepartment("技术部");
        tech.forEach(e -> System.out.println("  " + e));

        // 5. 方法命名查询 — 薪资大于 25000
        System.out.println("\n--- 方法命名查询：薪资 > 25000 ---");
        List<Employee> highSalary = repo.findBySalaryGreaterThan(25000.0);
        highSalary.forEach(e -> System.out.println("  " + e));

        // 6. 方法命名查询 — 组合条件
        System.out.println("\n--- 方法命名查询：技术部 & 薪资 > 25000 ---");
        List<Employee> techHigh = repo.findByDepartmentAndSalaryGreaterThan("技术部", 25000.0);
        techHigh.forEach(e -> System.out.println("  " + e));

        // 7. 模糊查询
        System.out.println("\n--- 模糊查询：名字含 '三' ---");
        List<Employee> likeResult = repo.findByNameContaining("三");
        likeResult.forEach(e -> System.out.println("  " + e));

        // 8. @Query JPQL — 薪资范围查询
        System.out.println("\n--- @Query JPQL：薪资 20000-30000 ---");
        List<Employee> range = repo.findBySalaryRange(20000.0, 30000.0);
        range.forEach(e -> System.out.println("  " + e));

        // 9. @Query — 聚合查询（各部门平均薪资）
        System.out.println("\n--- @Query 聚合：各部门平均薪资 ---");
        List<Object[]> avgResult = repo.avgSalaryByDepartment();
        for (Object[] row : avgResult) {
            System.out.println("  部门: %s, 平均薪资: %.0f".formatted(row[0], row[1]));
        }

        // 10. 分页排序
        System.out.println("\n--- 分页排序：按薪资降序，每页3条 ---");
        Page<Employee> page1 = repo.findAll(PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "salary")));
        System.out.println("  第1页 (共%d页, %d条):".formatted(page1.getTotalPages(), page1.getTotalElements()));
        page1.getContent().forEach(e -> System.out.println("    " + e));

        Page<Employee> page2 = repo.findAll(PageRequest.of(1, 3, Sort.by(Sort.Direction.DESC, "salary")));
        System.out.println("  第2页:");
        page2.getContent().forEach(e -> System.out.println("    " + e));

        // 11. 更新（CRUD - Update）
        System.out.println("\n--- 更新员工薪资 ---");
        Employee emp = repo.findById(1L).orElseThrow();
        System.out.println("  更新前: " + emp);
        emp.setSalary(28000.0);
        repo.save(emp);  // save 既是新增也是更新（根据 ID 判断）
        System.out.println("  更新后: " + repo.findById(1L).orElseThrow());

        // 12. 删除（CRUD - Delete）
        System.out.println("\n--- 删除员工 ---");
        repo.deleteById(7L);
        System.out.println("  删除 ID=7 后，剩余 %d 条".formatted(repo.count()));
        repo.findAll().forEach(e -> System.out.println("    " + e));

        // 13. 统计
        System.out.println("\n--- 统计 ---");
        System.out.println("  总记录数: " + repo.count());
        System.out.println("  技术部人数: " + repo.countByDepartment("技术部"));

        context.close();
        System.out.println("\n=== Spring Data JPA 演示完成 ===");
    }
}
