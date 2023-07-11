package com.vti.ecommerce.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/get-content")
    public ResponseEntity<String> getAdminHome(){
        return ResponseEntity.ok("This content for admin only");
    }
}
