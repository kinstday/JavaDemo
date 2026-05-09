package org.example.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * MyBatis-Plus Mapper 接口
 *
 * 继承 BaseMapper<Order> 即可获得 17+ 个内置方法：
 *   insert, deleteById, deleteBatchIds, updateById, selectById,
 *   selectBatchIds, selectOne, selectList, selectCount, selectMaps,
 *   selectObjs, selectPage, selectMapsPage, selectCount ...
 *
 * 与原生 MyBatis/UserMapper.java 的区别：
 * - 原生 MyBatis: 每个方法都要在 XML 中写 SQL
 * - MyBatis-Plus: 继承 BaseMapper 获得通用 CRUD，只需写特殊 SQL
 *
 * 也可以用 @Select 注解写自定义 SQL（与原生 MyBatis 一样）
 */
public interface OrderMapper extends BaseMapper<Order> {

    /** 建表（MyBatis-Plus 不自动建表，需手动执行 DDL） */
    @Update("CREATE TABLE IF NOT EXISTS orders (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "order_no VARCHAR(50) NOT NULL, " +
            "customer_name VARCHAR(50) NOT NULL, " +
            "amount DOUBLE NOT NULL, " +
            "status VARCHAR(20) NOT NULL, " +
            "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)")
    void createTable();

    /** 自定义查询：按客户名查询订单 */
    @Select("SELECT * FROM orders WHERE customer_name = #{customerName}")
    List<Order> selectByCustomerName(String customerName);

    /** 自定义查询：统计指定状态的订单数量 */
    @Select("SELECT COUNT(*) FROM orders WHERE status = #{status}")
    int countByStatus(String status);
}
