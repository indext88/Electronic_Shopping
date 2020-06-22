package com.ruixun.shopcarts.service;

import com.ruixun.shopcarts.pojo.Page;
import com.ruixun.shopcarts.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
public interface ProductService {
    /**
     * 查询所有热门商品（生产日期升序查询前10条数据）
     * @return
     * @throws Exception
     */
    List<Product> findHotProductList()throws  Exception;

    /**
     * 根据Cid查询分页数据(Page对象中的数据)
     * @param cid
     * @param currPage
     * @return
     */
    Page findListByCid(String cid,String currPage) throws Exception;

    /**
     * 根据商品id查询商品详情
     * @param pid
     * @return
     * @throws Exception
     */
    Product findById(@Param("pid") String pid)throws Exception;

    /**
     * 分页查询所有商品
     * @param currPage
     * @return
     * @throws Exception
     */
    Page findByPage(String currPage) throws Exception;

    /**
     * 修改商品的“在售状态”
     * @param pid
     * @param pflag
     */
    void updatePFlag(String pid, Long pflag) throws Exception;

    /**
     * 新增商品
     * @param product
     */
    void save(Product product) throws Exception;

    /**
     * 修改商品信息
     * @param product
     * @throws Exception
     */
    void updateProduct(Product product) throws Exception;

    /**
     * 修改商品图片
     * @param img
     * @param pid
     */
    void updateProductImg(@Param("img") String img,@Param("pid")String pid)throws Exception;

    /**
     * 删除商品
     */
    void delProducts(@Param("pid") String pid) throws Exception;
}
