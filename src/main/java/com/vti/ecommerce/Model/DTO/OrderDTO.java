package com.vti.ecommerce.Model.DTO;

import com.vti.ecommerce.Model.Coupon;
import com.vti.ecommerce.Model.OrderItem;
import com.vti.ecommerce.Model.User;
import com.vti.ecommerce.Model.UserPayment;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Long id;
    private User user;
    private UserPayment userPayment;
    private Date createdDate;
    private String statusShipping;
    private Double totalPrice;
    private List<OrderItem> orderItemList;

    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPayment getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(UserPayment userPayment) {
        this.userPayment = userPayment;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatusShipping() {
        return statusShipping;
    }

    public void setStatusShipping(String statusShipping) {
        this.statusShipping = statusShipping;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
