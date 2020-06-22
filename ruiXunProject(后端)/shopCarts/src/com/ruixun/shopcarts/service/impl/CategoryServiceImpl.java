package com.ruixun.shopcarts.service.impl;

import com.ruixun.shopcarts.dao.CategoryDao;
import com.ruixun.shopcarts.pojo.Category;
import com.ruixun.shopcarts.service.CategoryService;
import com.ruixun.shopcarts.util.CacheUtil;
import com.ruixun.shopcarts.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * 商品类型业务处理--业务类
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/7
 */
public class CategoryServiceImpl implements CategoryService {
    /**
     * 查询所有商品类型
     * @return
     */
    @Override
    public List<Category> findAll() {
        //通过chcache缓存来取出分类数据
        List<Category>categoryList= (List<Category>) CacheUtil.get("categoryList");
        //判断缓存是否存有分类信息数据
        if(null==categoryList){
            SqlSession session = MybatisUtil.getSession();
            CategoryDao categoryDao=session.getMapper(CategoryDao.class);
            //查询到数据库的信息放入到缓存中
            categoryList=categoryDao.findAll();
            CacheUtil.put("categoryList",categoryList);
        }
        return categoryList;
    }
}
