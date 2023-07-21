package com.vti.ecommerce.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return authenticationService.authenticate(request);
    }

    @PostMapping("/change-password")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return authenticationService.changePassword(request);
    }
}
