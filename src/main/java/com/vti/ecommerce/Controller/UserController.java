package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.CartItem;
import com.vti.ecommerce.Model.DTO.CartDTO;
import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Service.UserService;
import com.vti.ecommerce.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @GetMapping("/info")
    public ResponseEntity<Result> getUserDetail(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.getUserDetail(username);
    }
    @PostMapping("/info/edit")
    public ResponseEntity<Result> editInfoUser(HttpServletRequest httpServletRequest,
                                               @RequestBody UserDTO userDTO){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.editInfoUser(userDTO,username);
    }
    @PostMapping("/add-to-cart/{productId}")
    public ResponseEntity<Result> addToCart(HttpServletRequest httpServletRequest,
                                            @PathVariable("productId") Long productId){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.addToCart(productId, username);
    }
    @GetMapping("/cart/product-list")
    public ResponseEntity<Result> getCart(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.getCart(username);
    }
    @PostMapping("/cart/remove-product/{cartItemId}")
    public ResponseEntity<Result> removeItemInCart(HttpServletRequest httpServletRequest,
                                                   @PathVariable("cartItemId") Long cartItemId){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.removeItemInCart(username,cartItemId);
    }
    @PostMapping("/cart/update-quantity/{cartItemId}")
    public ResponseEntity<Result> updateQuantityProduct(HttpServletRequest httpServletRequest,
                                                     @PathVariable("cartItemId") Long cartItemId,
                                                     @RequestBody CartItem cartItem){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.updateQuantityProduct(username,cartItem,cartItemId);
    }
    @PostMapping("/cart/order")
    public ResponseEntity<Result> orderProduct(HttpServletRequest httpServletRequest,
                                               @RequestBody CartDTO cartDTO){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.orderProduct(username, cartDTO);
    }
    @GetMapping("/order/all")
    public ResponseEntity<Result> getAllOrder(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.getAllOrder(username);
    }
    @GetMapping("/order/detail/{orderId}")
    public ResponseEntity<Result> getOrderDetail(HttpServletRequest httpServletRequest,
                                                 @PathVariable("orderId") Long orderId){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.getOrderDetail(username, orderId);
    }
}
