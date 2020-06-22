package com.ruixun.shopcarts.pojo;

/**
 * 购物车条目
 * @version 1.0
 * @auther hwy
 * @Date 2020/5/8
 */
public class CartItem {
    private Product product;//购买的商品信息

    private Integer count;//购买的商品数量
    private  Double subTotal;//小计

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getSubTotal() {
        return this.product.getShop_price()*this.count;
    }

}
