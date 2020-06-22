package com.ruixun.shopcarts.service;

import com.ruixun.shopcarts.pojo.User;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/27
 */
public interface UserService {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username) throws Exception;

    /**
     * 用户注册
     * @param user
     */
    boolean  register(User user) throws Exception;

    /**
     * 激活用户
     * @param code
     * @return
     */
    boolean activeUser(String code) throws Exception;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    boolean login(String username,String password) throws Exception;
}
