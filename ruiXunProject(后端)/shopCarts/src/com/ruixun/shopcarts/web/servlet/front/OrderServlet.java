package com.ruixun.shopcarts.web.servlet.front;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.pojo.*;
import com.ruixun.shopcarts.service.OrderService;
import com.ruixun.shopcarts.service.impl.OrderServiceImpl;
import com.ruixun.shopcarts.util.UuidUtils;
import com.ruixun.shopcarts.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 订单控制器
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/11
 */

@WebServlet("/orderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService=new OrderServiceImpl();

    /**
     * 下单+在线支付
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        //下单
        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");

        //获取表单订单数据
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");

        System.out.println("数据"+name+", "+address+", "+telephone);

        //获取订单的商品信息
        String[] pid=request.getParameterValues("tableData[pid]");
        String[] count=request.getParameterValues("tableData[count]");
        String[] subTotal=request.getParameterValues("tableData[subTotal]");


        //封装订单数据
        Order order=new Order();
        String oid=UuidUtils.getUUID();
        order.setOid(oid);
        order.setName(name);
        order.setAddress(address);
        order.setTelephone(telephone);
        order.setUid(user.getUid());
        //订单总额
        Double total = Double.valueOf(request.getParameter("txtTotal"));
        order.setTotal(total);
        //订单状态
        order.setState(0L);
        //商品明细
        List<OrderItem>orderItemList=new ArrayList<>();

        //遍历获取
        for (int i=0;i<pid.length;i++) {
            //购物车条目》订单条目
            OrderItem orderItem=new OrderItem();
            orderItem.setCount(Long.valueOf(count[i]));
            orderItem.setItemid(UuidUtils.getUUID());
            orderItem.setSubtotal(Double.parseDouble(subTotal[i]));

            orderItem.setOid(oid);
            orderItem.setPid(pid[i]);

            orderItemList.add(orderItem);
        }
        System.out.println(orderItemList.toString());
        ////通过获取购物车中的购买条目--获取--订单明细
        //Map<String, CartItem> cart = (Map<String, CartItem>) request.getSession().getAttribute("cart");
        ////遍历获取
        //Set<Map.Entry<String, CartItem>> entries = cart.entrySet();
        //for (Map.Entry<String, CartItem> entrie:entries) {
        //    CartItem cartItem=entrie.getValue();
        //    //购物车条目》订单条目
        //    OrderItem orderItem=new OrderItem();
        //    orderItem.setCount(Long.valueOf(cartItem.getCount()));
        //    orderItem.setItemid(UuidUtils.getUUID());
        //    orderItem.setSubtotal(cartItem.getSubTotal());
        //
        //    orderItem.setOid(oid);
        //    orderItem.setPid(cartItem.getProduct().getPid());
        //
        //    orderItemList.add(orderItem);
        //}
        //调用"下单"
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {

            orderService.save(order,orderItemList);
        } catch (Exception e) {
            e.printStackTrace();
            out.write("0");
            out.flush();
            out.close();
        }
        //订单下单后，立即"清空购物车"
        out.write("1");
        out.flush();
        out.close();
    }


    /**
     * 查看用户订单详情
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void myOrderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        try {
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            String currPage = request.getParameter("currPage");
            Page pages = orderService.findPageByUid(user.getUid(), currPage);
            String orderListJSON = JSON.toJSONString(pages);
            out.write(orderListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据订单id和用户id查询商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myOrderProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        String oid = request.getParameter("oid");
        try {
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            List<Order>orderList=orderService.findListByOid(user.getUid(),oid);
            String orderListJSON = JSON.toJSONString(orderList);
            out.write(orderListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}












