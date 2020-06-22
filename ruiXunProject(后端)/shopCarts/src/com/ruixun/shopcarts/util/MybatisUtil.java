package com.ruixun.shopcarts.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * Mybatis工具类
 * @version 1.0
 * @auther hwy
 * @Date 2020/3/14
 */
public class MybatisUtil {
/*Mybatis创建一个"会话工厂"*/
    public static SqlSessionFactory sessionFactory;
    static {
        //加载核心配置文件
        try {
            Reader reader = Resources.getResourceAsReader("Mybatis.xml");
            //创建工厂
            SqlSessionFactoryBuilder sessionFactoryBuilder=new SqlSessionFactoryBuilder();
            sessionFactory=sessionFactoryBuilder.build(reader);

            System.out.println("Mybatis会话工厂创建成功。。。");
        } catch (IOException e) {
            System.out.println("核心配置文件加载异常。。。");
        }
    }

    /**
     * 获取会话工厂
     * @return
     */
    public  static SqlSession getSession(){
        System.out.println("获取新的会话。。。");
        return sessionFactory.openSession();
    }


    /**
     * 关闭会话
     * @param sqlSession
     */
    public static void CloseSession(SqlSession sqlSession) throws Exception{
        if(null!=sqlSession){
            sqlSession.close();
        }
    }

}
