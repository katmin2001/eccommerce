package com.vti.ecommerce.Model.DTO;

import com.vti.ecommerce.Model.CartItem;

import java.util.List;

public class CartDTO {
    private Long userId;
    private List<CartItem> cartItemList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
