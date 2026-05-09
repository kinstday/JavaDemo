package org.example.hibernate;

import jakarta.persistence.*;

/**
 * Hibernate 实体类：对应 products 表
 *
 * 演示 JPA 注解：
 * @Entity — 标记为 JPA 实体
 * @Table — 指定表名
 * @Id + @GeneratedValue — 主键策略
 * @Column — 列映射
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    public enum Category {
        ELECTRONICS, CLOTHING, FOOD, BOOKS, OTHER
    }

    public Product() {}

    public Product(String name, String description, Double price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public String toString() {
        return "Product{id=%d, name='%s', price=%.2f, stock=%d, category=%s}"
            .formatted(id, name, price, stock, category);
    }
}
