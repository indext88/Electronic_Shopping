package com.ruixun.shopcarts.dao;

import com.ruixun.shopcarts.pojo.OrderItem;

/**
 * 订单明细Dao
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public interface OrderItemDao {
    /**
     * 添加一条订单明细
     * @param orderItem
     * @throws Exception
     */
    void save(OrderItem orderItem) throws Exception;
}
