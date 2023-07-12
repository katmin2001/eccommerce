package com.vti.ecommerce.Service.Impl;

import com.vti.ecommerce.Model.Cart;
import com.vti.ecommerce.Model.CartItem;
import com.vti.ecommerce.Model.DTO.ProductDTO;
import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Model.User;
import com.vti.ecommerce.Repository.*;
import com.vti.ecommerce.Service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public ResponseEntity<Result> getUserDetail(String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());
            userDTO.setPhone(user.getPhone());
            userDTO.setAddress(user.getAddress());
            return ResponseEntity.ok(new Result("SUCCESS","OK",userDTO));
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> editInfoUser(UserDTO userDTO,String username) {
        try{
            User user = userRepository.findByUsername(username).orElseThrow(null);
            if(userDTO.getName() != null){
                user.setName(userDTO.getName());
            }
            if(userDTO.getEmail() != null){
                user.setEmail(userDTO.getEmail());
            }
            if(userDTO.getPhone() != null){
                user.setPhone(userDTO.getPhone());
            }
            if(userDTO.getAddress() != null){
                user.setAddress(userDTO.getAddress());
            }
            user.setUpdate_date(new Date());
            return ResponseEntity.ok(new Result("SUCCESS","OK",userRepository.save(user)));
        }catch(NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result("NOT FOUND USER","NOT_FOUND",null));
        }
    }

    @Override
    public ResponseEntity<Result> getAllOrder() {
        return null;
    }

    @Override
    public ResponseEntity<Result> getOrderDetail(Long orderId) {
        return null;
    }

    @Override
    public ResponseEntity<Result> addToCart(Long productId, String username, Integer quantity) {
        Cart cart = cartRepository.findCartByUserId(userRepository.findByUsername(username).orElse(null).getId());
        if(cart == null){
            cart.setUser_id(userRepository.findByUsername(username).orElse(null).getId());
            cart.setCreated_date(new Date());
            cartRepository.save(cart);
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart_id(cart.getId());
        cartItem.setQuantity(quantity);
        cartItem.setProduct_id(productId);
        cartItem.setCreated_date(new Date());
        return ResponseEntity.ok(new Result("SUCCESS","OK",cartItemRepository.save(cartItem)));
    }

    @Override
    public ResponseEntity<Result> getCart() {
        return null;
    }

    @Override
    public ResponseEntity<Result> removeItemInCart(Long cartItemId) {
        return null;
    }

    @Override
    public ResponseEntity<Result> addQuantityProduct(Integer quantity) {
        return null;
    }

    @Override
    public ResponseEntity<Result> orderProduct(List<CartItem> cartItems) {
        return null;
    }
}
