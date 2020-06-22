package com.ruixun.shopcarts.service.impl;

import com.ruixun.shopcarts.dao.ProductDao;
import com.ruixun.shopcarts.pojo.Page;
import com.ruixun.shopcarts.pojo.Product;
import com.ruixun.shopcarts.service.ProductService;
import com.ruixun.shopcarts.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
public class ProductServiceImpl implements ProductService{

    /**
     * 生产日期降序，显示前十条热门商品的信息
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findHotProductList() throws Exception {
        SqlSession session = null;
        List<Product> hotProductList;
        try {
            session= MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            hotProductList= productDao.findHotProductList();
        }finally {
            MybatisUtil.CloseSession(session);
        }
        return hotProductList;
    }


    /**
     * 根据Cid查询分页数据
     * @param cid
     * @param currPage
     * @return
     */
    @Override
    public Page findListByCid(String cid, String currPage) throws Exception {
        SqlSession session = null;
        Page result=new Page();
        try {
            session = MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);

            if(currPage==null){
                currPage="1";
            }
            //封装数据
            result.setCurrPage(Integer.parseInt(currPage));

            //System.out.println(cid+",  "+currPage);

            int totalCount = productDao.getTotalCount(cid, null);//如果pflag请求为空则查询所有
            //System.out.println(totalCount+"结果");

            result.setTotalCount(totalCount);
            //获取分页查询后的数据
            List<Product> list = productDao.findPageByCid(cid, result.getBeginRows(), result.getPageSize());
            result.setList(list);
        } finally {
            MybatisUtil.CloseSession(session);
        }
        return result;
    }


    /**
     * 根据商品id查询其详情
     * @param pid
     * @return
     * @throws Exception
     */
    @Override
    public Product findById(String pid) throws Exception {
        SqlSession session = null;
        Product product;
        try {
            session= MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            product = productDao.findById(pid);
        }finally {
            MybatisUtil.CloseSession(session);
        }
        return product;
    }


    /**
     * 分页查询所有商品
     * @param currPage
     * @return
     * @throws Exception
     */
    @Override
    public Page findByPage(String currPage) throws Exception {
        SqlSession session = null;
        Page page;
        try {
            session= MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            if(currPage==null){
                currPage="1";
            }
            page=new Page();
            page.setPageSize(8);
            //查询所有商品总数
            int totalCount = productDao.getTotalCount(null, null);
            page.setTotalCount(totalCount);
            page.setCurrPage(Integer.parseInt(currPage));
            List<Product> productList = productDao.findPage(page.getBeginRows(), page.getPageSize());
            page.setList(productList);
        }finally {
            MybatisUtil.CloseSession(session);
        }
        return page;
    }

    /**
     * 修改商品的在线状态
     * @param pid
     * @param pflag
     */
    @Override
    public void updatePFlag(String pid, Long pflag) throws Exception{
        SqlSession session=null;
        try {
            session=MybatisUtil.getSession();
            ProductDao productDao=session.getMapper(ProductDao.class);
            productDao.updatePFlag(pid,pflag);
            session.commit();
        }catch (Exception e){
          if(null!=session){
              session.rollback();
          }
        } finally {
            MybatisUtil.CloseSession(session);
        }
    }

    /**
     * 添加商品
     * @param product
     * @throws Exception
     */
    @Override
    public void save(Product product) throws Exception {
        SqlSession session=null;
        try {
            session=MybatisUtil.getSession();
            ProductDao productDao=session.getMapper(ProductDao.class);
            productDao.save(product);
            session.commit();
        }catch (Exception e){
            if(null!=session){
                session.rollback();
            }
        }finally {
            MybatisUtil.CloseSession(session);
        }
    }


    /**
     * 修改商品信息
     * @param product
     * @throws Exception
     */
    @Override
    public void updateProduct(Product product) throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            productDao.updateProduct(product);
            session.commit();
        } catch (Exception e) {
            if (null != session) {
                session.rollback();
            }
        } finally {
            MybatisUtil.CloseSession(session);
        }
    }

    /**
     * 修改图片
     * @param img
     * @param pid
     * @throws Exception
     */
    @Override
    public void updateProductImg(String img, String pid) throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            productDao.updateProductImg(img,pid);
            session.commit();
        } catch (Exception e) {
            if (null != session) {
                session.rollback();
            }
        } finally {
            MybatisUtil.CloseSession(session);
        }
    }

    /**
     * 删除商品
     * @param pid
     * @throws Exception
     */
    @Override
    public void delProducts( String pid) throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtil.getSession();
            ProductDao productDao = session.getMapper(ProductDao.class);
            productDao.delProducts(pid);
            session.commit();
        } catch (Exception e) {
            if (null != session) {
                session.rollback();
            }
        } finally {
            MybatisUtil.CloseSession(session);
        }
    }

}
