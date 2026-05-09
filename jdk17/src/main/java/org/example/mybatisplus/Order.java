package org.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.EnumValue;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 实体类 — 订单
 *
 * 与原生 MyBatis/User.java 的区别：
 * - @TableName 指定表名（MyBatis 需要在 XML 中配置）
 * - @TableId 指定主键策略（MyBatis 需要 useGeneratedKeys）
 * - @EnumValue 枚举字段自动映射
 * - 无需 getter/setter 也能工作（但为了方便演示保留）
 */
@TableName("orders")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private String customerName;

    private Double amount;

    /** 订单状态 — 使用 @EnumValue 将枚举名存入数据库 */
    private OrderStatus status;

    private LocalDateTime createTime;

    /** 订单状态枚举 */
    public enum OrderStatus {
        PENDING("待支付"),
        PAID("已支付"),
        SHIPPED("已发货"),
        COMPLETED("已完成"),
        CANCELLED("已取消");

        private final String label;

        OrderStatus(String label) { this.label = label; }
        public String getLabel() { return label; }
    }

    public Order() {}

    public Order(String orderNo, String customerName, Double amount, OrderStatus status) {
        this.orderNo = orderNo;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
        this.createTime = LocalDateTime.now();
    }

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    @Override
    public String toString() {
        return "Order{id=%d, orderNo='%s', customer='%s', amount=%.2f, status=%s, createTime=%s}"
            .formatted(id, orderNo, customerName, amount, status, createTime);
    }
}
