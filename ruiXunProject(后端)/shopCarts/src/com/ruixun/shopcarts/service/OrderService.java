package com.ruixun.shopcarts.service;

import com.ruixun.shopcarts.pojo.Order;
import com.ruixun.shopcarts.pojo.OrderItem;
import com.ruixun.shopcarts.pojo.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单业务类--service类
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public interface OrderService {
    /**
     * 生成订单
     * @param order
     * @throws Exception
     */
    void save(Order order, List<OrderItem>orderItemList)throws Exception;

    /**
     * 分页查询用户订单明细
     * @param uid
     * @param currPage
     * @return
     * @throws Exception
     */
    Page findPageByUid(String uid,String currPage) throws Exception;


    /**
     * 根据订单id和用户id查询用户信息
     * @param uid
     * @param oid
     * @return
     * @throws Exception
     */
    List<Order> findListByOid(@Param("uid") String uid, @Param("oid") String oid)throws Exception;

}
