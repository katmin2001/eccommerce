package com.vti.ecommerce.Service;


import com.vti.ecommerce.Model.CartItem;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Model.Order;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<Result> getUserDetail(String username);
    public ResponseEntity<Result> editInfoUser(UserDTO userDTO, String username);
    public ResponseEntity<Result>  getAllOrder();
    public ResponseEntity<Result> getOrderDetail(Long orderId);
    public ResponseEntity<Result> addToCart(Long productId, String username, Integer quantity);
    public ResponseEntity<Result> getCart();
    public ResponseEntity<Result> removeItemInCart(Long cartItemId);
    public ResponseEntity<Result> addQuantityProduct(Integer quantity);
    public ResponseEntity<Result> orderProduct(List<CartItem> cartItems);


}
