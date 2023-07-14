package com.vti.ecommerce.Model.DTO;

import com.vti.ecommerce.Model.OrderItem;
import com.vti.ecommerce.Model.User;
import com.vti.ecommerce.Model.UserPayment;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private User user;
    private UserPayment userPayment;
    private Date createdDate;
    private String statusShipping;
    private Double totalPrice;
    private List<OrderItem> orderItemList;

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
