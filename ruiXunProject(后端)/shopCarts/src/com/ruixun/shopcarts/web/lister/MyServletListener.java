package com.ruixun.shopcarts.web.lister;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import com.ruixun.shopcarts.util.CacheUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Servlet上下监听器
 * 作用：
 * 停止web服务器，进行应用程序服务器级别的“垃圾（无限循环多线程）回收”
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
@WebListener
public class MyServletListener implements ServletContextListener {
    private Driver driver=null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ClassLoader cl=Thread.currentThread().getContextClassLoader();
        //这里如果Web应用拥有多个数据的连接，可以一并关闭
        Enumeration<Driver>drivers= DriverManager.getDrivers();
        while (drivers.hasMoreElements()){
            try {
                driver=drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                //关闭缓存
                CacheUtil.closeCacheManger();
            } catch (SQLException e) {
                System.out.println("错误");
            }
            //高版本mysql服务器jar包
            AbandonedConnectionCleanupThread.checkedShutdown();
        }
    }
}
