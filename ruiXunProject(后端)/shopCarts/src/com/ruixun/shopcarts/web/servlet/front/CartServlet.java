package com.ruixun.shopcarts.web.servlet.front;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.pojo.CartItem;
import com.ruixun.shopcarts.pojo.Product;
import com.ruixun.shopcarts.service.ProductService;
import com.ruixun.shopcarts.service.impl.ProductServiceImpl;
import com.ruixun.shopcarts.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
@WebServlet("/cartServlet")
public class CartServlet extends BaseServlet {
    private ProductService productService=new ProductServiceImpl();

    /**
     * 添加购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取购买商品的id和购买数量
        String pid = request.getParameter("pid");
        String count = request.getParameter("count");

        response.setContentType("text/json;charset=utf-8");

        PrintWriter out =response.getWriter();

        //判断session有无购物车？
        Map<String, CartItem> cart= (Map<String, CartItem>) request.getSession().getAttribute("cart");
        System.out.println("使用中。。。。。");
        //判断购物车中是否存在数据
        if (null!=cart){
            //判断商品在购物车中中是否存在
            if(cart.containsKey(pid)){
                //修改其数量
                CartItem original_caryItem=cart.get(pid);
                original_caryItem.setCount(original_caryItem.getCount()+Integer.parseInt(count));
            }else {
                try {
                    //创建购物条目
                    CartItem cartItem=new CartItem();
                    cartItem.setCount(Integer.parseInt(count));
                    //获取商品信息
                    Product product = null;
                    product = productService.findById(pid);
                    cartItem.setProduct(product);

                    //将购物条目--添加---购物车
                    cart.put(pid,cartItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else {
            try {
                //首次创建购物车
                cart=new HashMap<>();
                //创建购物条目
                CartItem cartItem=new CartItem();
                cartItem.setCount(Integer.parseInt(count));
                //获取商品信息
                Product product = productService.findById(pid);
                cartItem.setProduct(product);

                //将购物条目--添加---购物车
                cart.put(pid,cartItem);
                //首次需要放入session
                request.getSession().setAttribute("cart",cart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //转换json数据
        String cartListJSON = JSON.toJSONString(cart);
        System.out.println(cartListJSON);
        out.write(cartListJSON);
        out.flush();
        out.close();
    }

    /**
     * 查询购物车所有信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selAllCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        //取出当前购物车
        Map<String, CartItem> cart= (Map<String, CartItem>) request.getSession().getAttribute("cart");
        PrintWriter out =response.getWriter();
        String cartListJSON = JSON.toJSONString(cart);
        System.out.println(cartListJSON);
        out.write(cartListJSON);
        out.flush();
        out.close();
    }

    /**
     * 修改购物车中的购买数量
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void updateCount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pid = request.getParameter("pid");
        String count = request.getParameter("count");

        //取出当前购物车
        Map<String, CartItem> cart= (Map<String, CartItem>) request.getSession().getAttribute("cart");
        //再取出当前购物条目
        CartItem cartItem=cart.get(pid);
        //更新购买数量
        cartItem.setCount(Integer.valueOf(count));

        Double total=new Double(0.0);
        //遍历Map中的数据
        Set<Map.Entry<String,CartItem>>entries=cart.entrySet();
        for (Map.Entry<String,CartItem> entry:entries) {
            total+=entry.getValue().getSubTotal();
        }

        //回写购物车页面最新数据
        String res=cartItem.getSubTotal()+"&&&"+total;
        PrintWriter out=response.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }


    /**
     * 根据商品id删除购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        String pid = request.getParameter("pid");
        //取出当前购物车
        Map<String, CartItem> cart= (Map<String, CartItem>) request.getSession().getAttribute("cart");
        //删除商品
        cart.remove(pid);

        //返回删除后的商品
        //转换json数据
        PrintWriter out =response.getWriter();
        String cartListJSON = JSON.toJSONString(cart);
        out.write(cartListJSON);
        out.flush();
        out.close();
    }


    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void clear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getSession().removeAttribute("cart");
        PrintWriter out =response.getWriter();
        out.print(1);
        out.flush();
        out.close();
    }
}
