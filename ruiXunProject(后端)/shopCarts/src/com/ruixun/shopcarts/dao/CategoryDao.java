package com.ruixun.shopcarts.dao;

import com.ruixun.shopcarts.pojo.Category;

import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/7
 */
public interface CategoryDao {
    /**
     * 查询所有商品类型
     */
    List<Category>findAll();
}
