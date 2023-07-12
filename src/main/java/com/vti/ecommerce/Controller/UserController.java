package com.vti.ecommerce.Controller;

import com.vti.ecommerce.Model.DTO.UserDTO;
import com.vti.ecommerce.Model.Result;
import com.vti.ecommerce.Service.UserService;
import com.vti.ecommerce.config.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                                            @RequestBody Integer quantity,
                                            @PathVariable("productId") Long productId){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);
        return userService.addToCart(productId, username, quantity);
    }
}
