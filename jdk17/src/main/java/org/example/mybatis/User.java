package org.example.mybatis;

/**
 * MyBatis 实体类：对应 users 表
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private Integer age;

    public User() {}

    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "User{id=%d, name='%s', email='%s', age=%d}".formatted(id, name, email, age);
    }
}
