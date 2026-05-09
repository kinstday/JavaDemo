package org.example.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA Repository 接口
 *
 * 继承 JpaRepository 即可获得：
 *   save(), findById(), findAll(), deleteById(), count(), existsById() 等内置方法
 *
 * 自定义查询只需声明方法名，Spring Data 自动生成 SQL（方法命名查询）
 * 也可以用 @Query 注解写 JPQL
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ===== 方法命名查询（Method Name Query）=====
    // Spring Data 解析方法名自动生成 WHERE department = ?

    List<Employee> findByDepartment(String department);

    List<Employee> findBySalaryGreaterThan(Double salary);

    List<Employee> findByDepartmentAndSalaryGreaterThan(String department, Double salary);

    List<Employee> findByNameContaining(String keyword);

    List<Employee> findByOrderBySalaryDesc();

    long countByDepartment(String department);

    // ===== @Query 自定义 JPQL 查询 =====

    @Query("SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max")
    List<Employee> findBySalaryRange(@Param("min") Double min, @Param("max") Double max);

    @Query("SELECT e.department, AVG(e.salary) FROM Employee e GROUP BY e.department")
    List<Object[]> avgSalaryByDepartment();

    @Query("SELECT e FROM Employee e WHERE e.department = :dept ORDER BY e.salary DESC")
    List<Employee> findTopEarnersByDepartment(@Param("dept") String department);
}
