package com.ruixun.shopcarts.web.servlet.front;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.pojo.Category;
import com.ruixun.shopcarts.service.CategoryService;
import com.ruixun.shopcarts.service.impl.CategoryServiceImpl;
import com.ruixun.shopcarts.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/7
 */
@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService=new CategoryServiceImpl();

    /**
     * 查询所有商品分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categoryList = categoryService.findAll();
            System.out.println("调用");
            //数据转换为json
            String categoryListJson= JSON.toJSONString(categoryList);
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.write(categoryListJson);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
