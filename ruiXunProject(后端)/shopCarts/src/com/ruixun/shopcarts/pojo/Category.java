package com.ruixun.shopcarts.pojo;

import java.io.Serializable;

/**
 * 商品种类--实体类
 * 由于ehcache做该类的缓存所以该类需要实现序列化接口
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/7
 */
public class Category implements Serializable {
    //序列号
    private long SerializableID=6546513164132654L;
    private String cid;//分类id
    private String cname;//商品种类名

    public Category() {
    }

    @Override
    public String toString() {
        return "商品类型{" +
                "类型id=" + cid +
                ", 类型名='" + cname + '\'' +
                '}';
    }

    public Category(String cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
