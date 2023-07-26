package com.vti.ecommerce.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_order")
public class Order extends BaseEntity{
    @Column(name = "total_price")
    private Double total_price;
    @Column(name = "status_shipping")
    private String status_shipping;
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "user_payment_id")
    private Long user_payment_id;

    @Column(name = "coupon_id")
    private Long couponId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Double total_price) {
        this.total_price = total_price;
    }

    public String getStatus_shipping() {
        return status_shipping;
    }

    public void setStatus_shipping(String status_shipping) {
        this.status_shipping = status_shipping;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getUser_payment_id() {
        return user_payment_id;
    }

    public void setUser_payment_id(Long user_payment_id) {
        this.user_payment_id = user_payment_id;
    }
}
