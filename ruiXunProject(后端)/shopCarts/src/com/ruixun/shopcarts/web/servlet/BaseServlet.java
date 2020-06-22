package com.ruixun.shopcarts.web.servlet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    /**
     * 通过method参数调用(反射)不同的处理方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");

        //1. 获取method参数
        String methodName = req.getParameter("method");

        //2. 获取到需要调用的对象
        Class<? extends BaseServlet> aClass = this.getClass();
        System.out.println("要分发请求的Servlet是: " + aClass.getName());

        //3. 通过反射调用方法
        try {
            Method method = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String view = (String) method.invoke(this, req, resp);


            if(view != null)
            {
                //是否有redirect:字符串
                if(view.contains("redirect:"))
                {
                    //redirect:login.jsp
                    //重定向
                    resp.sendRedirect(view.replaceAll("redirect:",""));
                }
                else
                {
                    req.setAttribute("method", methodName);
                    //请求转发
                    req.getRequestDispatcher("/"+view+".jsp").forward(req,resp);
                }

            }
            else{
                System.out.println("没有跳转的目标URL");
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            req.setAttribute("msg",this.getClass().getName()+"中没有找到方法:"+methodName);
            req.getRequestDispatcher("/info.jsp").forward(req,resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("异常数据：");
            Throwable t = e.getTargetException();// 获取目标异常
            t.printStackTrace();
            //e.printStackTrace();
        }
    }


    public String method(HttpServletRequest request, HttpServletResponse response)
    {
        return "";
    }

}
