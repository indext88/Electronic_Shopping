package com.ruixun.shopcarts.pojo;

/**
 * 订单明细--实体类
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/9
 */
public class OrderItem {
    private String itemid;//明细号
    private Long count;//数量
    private Double subtotal;//小计
    private String pid;//商品id
    private String oid;//订单号

    private Product product;//商品信息

    public OrderItem(){}

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
