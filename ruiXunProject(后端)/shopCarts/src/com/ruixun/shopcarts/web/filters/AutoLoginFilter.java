package com.ruixun.shopcarts.web.filters;

import com.ruixun.shopcarts.pojo.User;
import com.ruixun.shopcarts.service.UserService;
import com.ruixun.shopcarts.service.impl.UserServiceImpl;
import com.ruixun.shopcarts.util.CookieUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/29
 */
@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    private UserService userService=new UserServiceImpl();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("自动登录检查......");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        //跨域请求
        String orgHeader = request.getHeader(HttpHeaders.ORIGIN);
        if (orgHeader != null ) {
            // 允许的跨域的域名
            response.addHeader("Access-Control-Allow-Origin", orgHeader);
            // 允许携带 cookies 等认证信息
            response.addHeader("Access-Control-Allow-Credentials", "true");
            // 允许跨域的方法
            response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PATCH, PUT, HEAD");
            // 允许跨域请求携带的请求头
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With");
            // 返回结果可以用于缓存的最长时间，单位是秒。-1表示禁用
            response.addHeader("Access-Control-Max-Age", "3600");
        }

        request.setCharacterEncoding("utf-8");

        //判断是否已经登录
        User user= (User) request.getSession().getAttribute("user");
        //没有登录
        if(user==null){
            //判断是否需要自动登录
            Cookie cookie= CookieUtils.getCookie("autoLogin",request.getCookies());
            if(cookie!=null){
                //获取cookie的值
                String value=cookie.getValue();
                String[] values=value.split("&&&");
                String username=values[0];
                String password=values[1];
                //TODO自动登录bug---cookie的数据可能错误
                try {
                    user=userService.findByUserName(username);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.getSession().setAttribute("user",user);
            }
        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
