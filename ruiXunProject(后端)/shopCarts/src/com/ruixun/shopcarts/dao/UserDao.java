package com.ruixun.shopcarts.dao;

import com.ruixun.shopcarts.pojo.User;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/4/27
 */
public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUserName(String username);

    /**
     * 添加新用户
     * @param user
     */
    void  save(User user);

    /**
     * 修改用户状态
     * @param code
     * @return
     */
    int updateState(String code);

}
