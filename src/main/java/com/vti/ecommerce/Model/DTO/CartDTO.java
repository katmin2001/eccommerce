package com.vti.ecommerce.Model.DTO;

import com.vti.ecommerce.Model.CartItem;
import com.vti.ecommerce.Model.UserPayment;

import java.util.List;

public class CartDTO {
    private List<Long> cartItemList;
    private Long userPaymentId;
    private Long couponId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getUserPaymentId() {
        return userPaymentId;
    }

    public void setUserPaymentId(Long userPaymentId) {
        this.userPaymentId = userPaymentId;
    }

    public List<Long> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<Long> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
