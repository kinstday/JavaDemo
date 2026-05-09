package org.example.springdata;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Spring Data JPA 实体类 — 员工
 *
 * 使用 JPA 标准注解映射到 employees 表
 * 与 Hibernate/Product.java 不同的是，这里配合 Spring Data Repository 使用
 */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    public Employee() {}

    public Employee(String name, String department, Double salary, LocalDate hireDate) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    @Override
    public String toString() {
        return "Employee{id=%d, name='%s', department='%s', salary=%.0f, hireDate=%s}"
            .formatted(id, name, department, salary, hireDate);
    }
}
