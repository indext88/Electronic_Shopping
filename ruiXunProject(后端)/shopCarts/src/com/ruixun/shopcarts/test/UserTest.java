package com.ruixun.shopcarts.test;

import com.ruixun.shopcarts.pojo.Product;
import com.ruixun.shopcarts.pojo.User;
import com.ruixun.shopcarts.service.ProductService;
import com.ruixun.shopcarts.service.UserService;
import com.ruixun.shopcarts.service.impl.ProductServiceImpl;
import com.ruixun.shopcarts.service.impl.UserServiceImpl;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/29
 */
public class UserTest {
    private UserService userService=new UserServiceImpl();
    private ProductService productService=new ProductServiceImpl();

    /**
     * 注册测试
     */
    @Test
    public void Testregister(){
        String uname="bb";
        try {
            User user = userService.findByUserName(uname);
            System.out.println(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 热门商品测试
     */
    @Test
    public void TestProduct(){
        try {
            List<Product> hotProductList = productService.findHotProductList();
            for (Product pro:hotProductList
                 ) {
                System.out.println(pro.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //时间转换
    @Test
    public void TestDate(){
    }

}
