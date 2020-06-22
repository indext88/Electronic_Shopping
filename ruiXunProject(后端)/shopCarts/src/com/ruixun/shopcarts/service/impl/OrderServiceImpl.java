package com.ruixun.shopcarts.service.impl;

import com.ruixun.shopcarts.dao.OrderDao;
import com.ruixun.shopcarts.dao.OrderItemDao;
import com.ruixun.shopcarts.pojo.Order;
import com.ruixun.shopcarts.pojo.OrderItem;
import com.ruixun.shopcarts.pojo.Page;
import com.ruixun.shopcarts.service.OrderService;
import com.ruixun.shopcarts.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public class OrderServiceImpl implements OrderService {

    /**
     * 生成订单
     * @param order
     * @param orderItemList
     * @throws Exception
     */
    @Override
    public void save(Order order, List<OrderItem> orderItemList)
            throws Exception {
        SqlSession session=null;
        try {
            session = MybatisUtil.getSession();
            //保存订单
            OrderDao orderDao=session.getMapper(OrderDao.class);
            orderDao.save(order);

            //保存订单明细
            OrderItemDao orderItemDao=session.getMapper(OrderItemDao.class);
            //遍历存储
            for (OrderItem orderitem:orderItemList) {
                orderItemDao.save(orderitem);
            }
            //提交事务
            session.commit();
        }catch (Exception e){
            //数据回滚
            if(null!=session){
                session.rollback();
            }
        } finally {
          MybatisUtil.CloseSession(session);
        }

    }

    /**
     * 分页查询用户订单详情
     * @param uid
     * @param currPage
     * @return
     * @throws Exception
     */
    @Override
    public Page findPageByUid(String uid, String currPage) throws Exception {
       SqlSession session=null;
        //产生page对象，封装多表查询结果
        Page page;
       try {
           session=MybatisUtil.getSession();
           OrderDao orderDao = session.getMapper(OrderDao.class);

            page=new Page();
           //判断页码是否为空
           if(null==currPage||currPage.isEmpty()){
               currPage="1";
           }
           page.setCurrPage(Integer.parseInt(currPage));
           page.setTotalCount(orderDao.getTotalCountByUid(uid));
           //获取查询分页后的数据
           List<Order> listByUid = orderDao.findListByUid(uid, page.getBeginRows(), page.getPageSize());
           page.setList(listByUid);

           System.out.println(page.toString());
       }finally {
           MybatisUtil.CloseSession(session);
       }
        return page;
    }

    /**
     * 根据用户id和订单号查询商品信息
     * @param uid
     * @param oid
     * @return
     * @throws Exception
     */
    @Override
    public List<Order> findListByOid(String uid, String oid) throws Exception {
        SqlSession session=null;
        //产生page对象，封装多表查询结果
        List<Order> listByUid;
        try {
            session=MybatisUtil.getSession();
            OrderDao orderDao = session.getMapper(OrderDao.class);
            //获取查询分页后的数据
           listByUid = orderDao.findListByOid(uid,oid);
        }finally {
            MybatisUtil.CloseSession(session);
        }
        return listByUid;
    }
}
