package com.vti.ecommerce.Model.DTO;

import com.vti.ecommerce.Model.CartItem;

import java.util.List;

public class CartDTO {
    private List<Long> cartItemList;

    public List<Long> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<Long> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
