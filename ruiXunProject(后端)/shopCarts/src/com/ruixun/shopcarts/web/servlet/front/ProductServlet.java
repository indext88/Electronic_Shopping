package com.ruixun.shopcarts.web.servlet.front;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.pojo.Page;
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
import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
@WebServlet("/productServlet")
public class ProductServlet extends BaseServlet {
    private ProductService productService=new ProductServiceImpl();


    /**
     * 查询按生产日期降序的
     * 前10条热门商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findHotProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Product> hotProductList = productService.findHotProductList();
            System.out.println(hotProductList.size());
            String hotProductListJSON = JSON.toJSONString(hotProductList);
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询某类商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @return
     */
    public void findPageByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("调用");
        try {
            String cid = request.getParameter("cid");
            //System.out.println(cid);
            String currPage = request.getParameter("currPage");
            Page page=productService.findListByCid(cid,currPage);

            String hotProductListJSON = JSON.toJSONString(page);
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据商品id查询商品详情
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void findById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String pid = request.getParameter("pid");
            Product product = productService.findById(pid);

            String hotProductListJSON = JSON.toJSONString(product);
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 分页查询所有商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findByPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //System.out.println("调用");
        try {
            //System.out.println(cid);
            String currPage = request.getParameter("currPage");
            Page page=productService.findByPage(currPage);

            String hotProductListJSON = JSON.toJSONString(page);
            response.setContentType("text/json;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
