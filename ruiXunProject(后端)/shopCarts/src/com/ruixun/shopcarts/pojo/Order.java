package com.ruixun.shopcarts.pojo;

import java.util.Date;
import java.util.List;

/**
 * 订单类--实体类
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public class Order {
    private String oid;//订单id
    private Date ordertime=new Date();//创建时间

    private Double total;//总计
    private Long state;//订单状态：0-未支付，1-已支付

    private String address;//收货地址
    private String name;//收货人
    private String telephone;//收货电话

    private String uid;//用户id

    /*对象关联：“一对多”--1个订单包含多个订单明细*/
    private List<OrderItem>orderItemList;

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Order(){}

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
