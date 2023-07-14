package com.vti.ecommerce.Service;


import com.vti.ecommerce.Model.CartItem;
import com.vti.ecommerce.Model.DTO.CartDTO;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Model.Order;
import com.vti.ecommerce.Model.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<Result> getUserDetail(String username);
    public ResponseEntity<Result> editInfoUser(UserDTO userDTO, String username);
    public ResponseEntity<Result>  getAllOrder(String username);
    public ResponseEntity<Result> getOrderDetail(String username, Long orderId);
    public ResponseEntity<Result> addToCart(Long productId, String username);
    public ResponseEntity<Result> getCart(String username);
    public ResponseEntity<Result> removeItemInCart(String username, Long itemCartId);
    public ResponseEntity<Result> updateQuantityProduct(String username, CartItem cartItem, Long cartItemId);
    public ResponseEntity<Result> orderProduct(String username, CartDTO cartDTO);


}
