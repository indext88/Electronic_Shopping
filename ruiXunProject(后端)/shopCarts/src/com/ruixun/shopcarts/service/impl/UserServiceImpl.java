package com.ruixun.shopcarts.service.impl;

import com.ruixun.shopcarts.dao.UserDao;
import com.ruixun.shopcarts.pojo.User;
import com.ruixun.shopcarts.service.UserService;
import com.ruixun.shopcarts.util.MailUtils;
import com.ruixun.shopcarts.util.MybatisUtil;
import com.ruixun.shopcarts.util.UuidUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/27
 */
public class UserServiceImpl implements UserService{

    //查询用户名
    @Override
    public User findByUserName(String username) throws Exception {
        SqlSession session=null;
        try {
            System.out.println("查询条件："+username);
            session= MybatisUtil.getSession();
            System.out.println("错误");
            UserDao userDao=session.getMapper(UserDao.class);
            return userDao.findByUserName(username);
        }finally {
            MybatisUtil.CloseSession(session);
        }
    }

    //注册
    @Override
    public boolean register(User user) throws Exception {
        //判断用户注册是否成功
        boolean result=false;
        SqlSession session=null;
        try {
            session=MybatisUtil.getSession();
            //新用户的有些属性是工具类自动生成的
            user.setUid(UuidUtils.getUUID());
            user.setState(0);
            user.setCode(UuidUtils.getUUID());

            UserDao userDao=session.getMapper(UserDao.class);

            userDao.save(user);
            session.commit();

            System.out.println("邮箱："+user.getEmail());
            //注册业务返回之前，发送激活邮件到用户注册时留下的邮箱
            MailUtils.sendMail(user.getEmail(),user.getCode());

            result=true;
        }catch (Exception e){
            if(null!=session){
                session.rollback();
            }
        }finally {
            MybatisUtil.CloseSession(session);
            return result;
        }
    }

    //激活用户
    @Override
    public boolean activeUser(String code) throws Exception{
        SqlSession session=null;
        //判断更新的数量
        int updateCount=0;
        try {
            session=MybatisUtil.getSession();
            UserDao userDao=session.getMapper(UserDao.class);

            updateCount= userDao.updateState(code);
            session.commit();
        }catch (Exception e){
            if(null!=session){
                session.rollback();
            }
        }finally {
            MybatisUtil.CloseSession(session);
            return updateCount>0;
        }
    }

    //用户登录
    @Override
    public boolean login(String username, String password) throws Exception{
        //判断用户登录是否成功
        boolean result=false;
        SqlSession session=null;
        try {
            session=MybatisUtil.getSession();
            UserDao userDao=session.getMapper(UserDao.class);
            User user = userDao.findByUserName(username);
            //System.out.println(user.toString());
            //System.out.println("zzzzzzzz"+user.getPassword().equals(password));
            //System.out.println("mmmm"+(user.getState()==1)+"");
           result=(null!=user&&user.getPassword().equals(password)&&(user.getState()==1));

        }finally {
            MybatisUtil.CloseSession(session);
            return result;
        }
    }
}
