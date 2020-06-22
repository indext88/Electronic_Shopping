package com.ruixun.shopcarts.dao;

import com.ruixun.shopcarts.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 订单Dao
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public interface OrderDao {

    /**
     * 生成订单
     * @param order
     * @throws Exception
     */
    void save(Order order)throws Exception;

    /**
     * 查询用户的订单总数
     * @param uid
     * @return
     * @throws Exception
     */
    Integer getTotalCountByUid(@Param("uid") String uid)throws Exception;

    /**
     * 分页查询用户订单
     * @param uid
     * @param beginRows
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<Order> findListByUid(@Param("uid") String uid,@Param("beginRows")Integer beginRows,@Param("pageSize")Integer pageSize)throws Exception;


    /**
     * 根据订单id和用户id查询用户信息
     * @param uid
     * @param oid
     * @return
     * @throws Exception
     */
    List<Order> findListByOid(@Param("uid") String uid,@Param("oid") String oid)throws Exception;

}
