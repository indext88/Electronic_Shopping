package com.ruixun.shopcarts.web.servlet.admin;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.dao.CategoryDao;
import com.ruixun.shopcarts.pojo.Category;
import com.ruixun.shopcarts.pojo.Page;
import com.ruixun.shopcarts.pojo.Product;
import com.ruixun.shopcarts.service.CategoryService;
import com.ruixun.shopcarts.service.ProductService;
import com.ruixun.shopcarts.service.impl.CategoryServiceImpl;
import com.ruixun.shopcarts.service.impl.ProductServiceImpl;
import com.ruixun.shopcarts.util.MyDateConverter;
import com.ruixun.shopcarts.util.UuidUtils;
import com.ruixun.shopcarts.web.servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 后台管理--商品控制器
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/11
 */
@WebServlet("/adminProductServlet")
@MultipartConfig
public class AdminProductServlet extends BaseServlet {
    private ProductService productService=new ProductServiceImpl();
    private CategoryService categoryService=new CategoryServiceImpl();

    /**
     * 查询所有商品信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void findByPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            String currPage = request.getParameter("currPage");
            System.out.println("页数"+currPage);
            //获取商品信息
            Page page = productService.findByPage(currPage);
            System.out.println(page.toString()+"分页调用属性");

            String hotProductListJSON = JSON.toJSONString(page);
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            //系统错误
            out.write("0");
            out.flush();
            out.close();;
        }
    }

    /**
     * 修改商品在售状态
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    //public void pushDown(HttpServletRequest request, HttpServletResponse response)
    //        throws ServletException, IOException {
    //    try {
    //        String pid = request.getParameter("pid");
    //        productService.updatePFlag(pid,1L);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        request.setAttribute("msg","系统错误了");
    //    }
    //}


    /**
     * 修改商品信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void updateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        System.out.println("调用商品修改");
        try {
            Product product=new Product();
            product.setPid(request.getParameter("pid"));
            product.setPname(request.getParameter("pname"));
            product.setShop_price(Double.parseDouble(request.getParameter("shop_price")));
            product.setIs_hot(Integer.parseInt(request.getParameter("is_hot")));

            //写入数据库上传文件的路径
            product.setPimage(request.getParameter("pimage"));
            product.setPdesc(request.getParameter("pdes"));
            product.setPflag(Long.parseLong(request.getParameter("pflag")));
            product.setCid(request.getParameter("cid"));
            //时间转换
            java.sql.Date date = java.sql.Date.valueOf(request.getParameter("pdata"));
            product.setPdate(date);
            System.out.println(product.toString());

            productService.updateProduct(product);

            out.write("1");
            out.flush();
            out.close();

        } catch (Exception e) {
            //系统错误
            out.write("0");
            out.flush();
            out.close();
        }
    }

    /**
     * 添加商品信息的回显
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void saveUI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            List<Category> all = categoryService.findAll();
            String hotProductListJSON = JSON.toJSONString(all);
            out.write(hotProductListJSON);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            //系统错误
            out.write("0");
            out.flush();
            out.close();
        }
    }


    /**
     * 添加商品
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();

        try {
            //封装表单数据实体类
            Product product=new Product();
            ////注册日期转换器
            //ConvertUtils.register(new Converter() {
            //    @Override
            //    public Object convert(Class aClass, Object value) {
            //        try {
            //            return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
            //        } catch (ParseException e) {
            //            return null;
            //        }
            //    }
            //}, Date.class );
            //
            //BeanUtils.populate(product,request.getParameterMap());
            product.setPname(request.getParameter("pname"));
            product.setShop_price(Double.parseDouble(request.getParameter("shop_price")));
            product.setIs_hot(Integer.parseInt(request.getParameter("is_hot")));
            //封装表单外的数据
            product.setPid(UuidUtils.getUUID());
            //写入数据库上传文件的路径
            product.setPimage(request.getParameter("pimage"));
            product.setPdesc(request.getParameter("pdes"));
            product.setPflag(Long.parseLong(request.getParameter("pflag")));
            product.setCid(request.getParameter("cid"));
            //时间转换
            java.sql.Date date = java.sql.Date.valueOf(request.getParameter("pdata"));
            product.setPdate(date);
            System.out.println(product.toString());
            //开始保存新商品
            productService.save(product);
            //商品添加后
            out.write("1");
            out.flush();
            out.close();

        } catch (Exception e) {
            //添加商品错误
            out.write("0");
            out.flush();
            out.close();
        }
    }


    /**
     * 删除商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            String pid = request.getParameter("pid");
            productService.delProducts(pid);
            out.write("1");
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.write("0");
            out.flush();
            out.close();
        }
    }


    /**
     * 插入图片
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void updateproductImg(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            //获取上传文件信息
            Part uploadPart=request.getPart("file");
            System.out.println("图片名字："+uploadPart.getSubmittedFileName());
            //设置上传服务器的文件名
            String fileName="products/1/"+uploadPart.getSubmittedFileName();

            //String fileName=UuidUtils.getUUID()+uploadPart.getSubmittedFileName();

            System.out.println(fileName);
            //写入图片
            OutputStream outputStream=new FileOutputStream("E:/Vue_Object/shopcart/img/"+fileName);
            //理想化文件拷贝，利用多线程操作流（上传文件大的问题解决）
            IOUtils.copy(uploadPart.getInputStream(),outputStream);
            String hotProductListJSON = JSON.toJSONString(fileName);
            out.write(fileName);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.write("0");
            out.flush();
            out.close();
        }
    }
}
