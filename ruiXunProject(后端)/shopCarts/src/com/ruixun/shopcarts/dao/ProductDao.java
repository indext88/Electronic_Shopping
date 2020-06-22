package com.ruixun.shopcarts.dao;

import com.ruixun.shopcarts.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Dao
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
public interface ProductDao {
    /**
     * 查询所有热门商品（生产日期升序查询前10条数据）
     * @return
     * @throws Exception
     */
    List<Product>findHotProductList()throws  Exception;

    /**
     * 分页查询某类商品
     * @param cid
     * @param beginRowIndex
     * @param pageSize
     * @return
     */
    List<Product>findPageByCid(@Param("cid") String cid,@Param("beginRowIndex") Integer beginRowIndex,@Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 查询某类商品的上架/下架总数
     * @param cid
     * @param pflag
     * @return
     */
    int getTotalCount(@Param("cid") String cid,@Param("pflag") Integer pflag) throws Exception;

    /**
     * 根据商品id查询商品详情
     * @param pid
     * @return
     * @throws Exception
     */
    Product findById(@Param("pid") String pid)throws Exception;

    /**
     * 分页查询所有商品
     * @param beginRowIndex
     * @param pageSize
     * @return
     */
    List<Product>findPage(@Param("beginRowIndex") Integer beginRowIndex,@Param("pageSize") Integer pageSize) throws Exception;

    /**
     * 修改商品的“在售状态”
     * @param pid
     * @param pflag
     */
    void updatePFlag(@Param("pid") String pid,@Param("pflag") Long pflag) throws Exception;

    /**
     * 新增商品
     * @param product
     */
    void save(Product product) throws Exception;

    /**
     * 修改商品信息
     * @param product
     * @return
     * @throws Exception
     */
    void updateProduct(Product product) throws Exception;

    /**
     * 修改商品图片
     * @param img
     * @param pid
     */
    void updateProductImg(@Param("img") String img,@Param("pid")String pid) throws Exception;

    /**
     * 删除商品
     */
    void delProducts(@Param("pid") String pid) throws Exception;
}
