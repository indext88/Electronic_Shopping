package com.ruixun.shopcarts.web.filters;

import com.ruixun.shopcarts.pojo.User;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 订单过滤器
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/11
 */
@WebFilter(urlPatterns = {"/cartServlet","/orderServlet"})
public class CheckLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("检查是否登录。。。");
        /* 允许跨域的主机地址 */

        HttpServletRequest request= (HttpServletRequest) servletRequest;

        request.setCharacterEncoding("utf-8");

        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        if(null==user){
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out=response.getWriter();
            out.print(0);
            out.flush();
            out.close();
        }
        //放行
        filterChain.doFilter(servletRequest,response);
    }

    @Override
    public void destroy() {

    }
}
