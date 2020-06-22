package com.ruixun.shopcarts.web.servlet.front;

import com.alibaba.fastjson.JSON;
import com.ruixun.shopcarts.pojo.User;
import com.ruixun.shopcarts.service.UserService;
import com.ruixun.shopcarts.service.impl.UserServiceImpl;
import com.ruixun.shopcarts.web.servlet.BaseServlet;
import com.ruixun.shopcarts.util.MyDateConverter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 子控制器---用户控制器
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/29
 */
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();


    /**
     * 用户名是否可用
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        try {
            String username = request.getParameter("username");
            System.out.println(username + ":  获取的数据");
            User user = userService.findByUserName(username);
            System.out.println("查询完毕"+user.toString());
            //判断用户是否存在
            if (null != user) {
                //回显数据
                out.print("1");
                out.flush();
                out.close();
            }else {
                out.print("0");
                out.flush();
                out.close();
            }
        }catch (Exception e) {
            out.print("0");
            out.flush();
            out.close();
        }
    }


    /**
     * 用户注册
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out=response.getWriter();
        //判断验证码
        String sessionYzm = (String) request.getSession().getAttribute("code");
        String yzm = request.getParameter("yzm");
        if (null != yzm && !yzm.isEmpty() && yzm.equals(sessionYzm)) {
            //用户所写验证码和会话中的验证码相同
            //获取注册数据
            User user = new User();
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            user.setTelephone(request.getParameter("telephone"));
            user.setSex(request.getParameter("sex"));
            //user.setBirthday(request.getParameter("birthday"));
            try {
                //时间转换
                //注册时间
                ConvertUtils.register(new MyDateConverter(), Date.class);
                BeanUtils.populate(user, request.getParameterMap());
                //注册判断
                boolean res = userService.register(user);
                out.print(1);
                out.flush();
                out.close();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            out.print(0);
            out.flush();
            out.close();
        }
    }

    /**
     * 用户注销
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("注销用户......");
        request.getSession().removeAttribute("user");
        PrintWriter out =response.getWriter();
        out.print(1);
        out.flush();
        out.close();

    }

        /**
         * 激活用户
         * @param request
         * @param response
         * @return
         * @throws ServletException
         * @throws IOException
         */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取邮箱中的激活码
        String code=request.getParameter("code");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        try {
            boolean res = userService.activeUser(code);
            if(res){
                out.print("<script type='text/javascript' charset='utf-8'>");
                out.print("alert('激活成功');");
                out.print("window.location.href='http://localhost:8081/'");
                out.print("</script>");
                out.flush();
                out.close();
            }else {
                out.print("<script type='text/javascript' charset='utf-8'>");
                out.print("alert('激活失败或用户已激活');");
                out.print("window.location.href='http://localhost:8081/'");
                out.print("</script>");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            out.print("<script type='text/javascript' charset='utf-8'>");
            out.print("alert('系统故障');");
            out.print("window.location.href='http://localhost:8081/'");
            out.print("</script>");
            out.flush();
            out.close();
        }
    }



        /**
         * 用户登录
         *
         * @param request
         * @param response
         * @return
         * @throws ServletException
         * @throws IOException
         */
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("调用登录");
        PrintWriter out = response.getWriter();
        try {
            //判断验证码
            String sessionYzm = (String) request.getSession().getAttribute("code");

            String yzm = request.getParameter("yzm");

            System.out.println("yzm:"+yzm+", code:"+sessionYzm);

            if (null != yzm && !yzm.isEmpty() && yzm.equals(sessionYzm)) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                boolean res = userService.login(username, password);
                //System.out.println("博导："+res);
                if (res) {
                    ////登录成功，判断是否记住用户名
                    //String remeberUsername = request.getParameter("remeberUsername");
                    //if (remeberUsername=="true") {
                    //    //新建cookie
                    //    Cookie cookie = new Cookie("remeberUsername", username);
                    //    //记住时间
                    //    cookie.setMaxAge(60 * 60 * 24 * 7);
                    //    response.addCookie(cookie);
                    //}
                    ////判断是否勾选自动登录
                    //String autoLogin = request.getParameter("autoLogin");
                    //if (autoLogin =="true") {
                    //    //新建cookie
                    //    Cookie cookie = new Cookie("autoLogin", username + "&&&" + password);
                    //    cookie.setMaxAge(60 * 60 * 24 * 7);
                    //    response.addCookie(cookie);
                    //}
                    //保存user到session
                    User user = userService.findByUserName(username);
                    
                    request.getSession().setAttribute("user",user);

                    System.out.println("封装成功。。。。。");

                    String userJSON = JSON.toJSONString(user);
                    response.setContentType("text/json;charset=utf-8");
                    out.write(userJSON);
                    out.flush();
                    out.close();
                }else{
                    //用户名密码错误
                    out.write("1");
                    out.flush();
                    out.close();
                }
            }else {
                //验证码错误
                out.write("0");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //验证码错误
            out.write("-1");
            out.flush();
            out.close();
        }
    }


    /**
     * 管理员用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void adminLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password=request.getParameter("password");
        response.setContentType("text/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        User user = null;
        try {
            user = userService.findByUserName(username);
            if(user!=null){
                if(password.equals(user.getPassword())){
                    out.write("1");
                    out.flush();
                    out.close();
                }else {
                    //用户名密码错误
                    out.write("0");
                    out.flush();
                    out.close();
                }
            }else {
                out.write("0");
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
