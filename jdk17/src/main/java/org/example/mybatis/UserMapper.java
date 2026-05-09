package org.example.mybatis;

import java.util.List;

/**
 * MyBatis Mapper 接口：定义 SQL 映射方法
 * 对应 XML 配置中的 SQL 语句
 */
public interface UserMapper {

    void createTable();

    int insert(User user);

    User selectById(Long id);

    List<User> selectAll();

    List<User> selectByAgeRange(int minAge, int maxAge);

    int updateEmail(Long id, String email);

    int deleteById(Long id);

    int count();
}
