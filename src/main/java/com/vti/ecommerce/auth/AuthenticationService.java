package com.vti.ecommerce.auth;

import com.vti.ecommerce.Model.Role;
import com.vti.ecommerce.Model.User;
import com.vti.ecommerce.Repository.UserRepository;
import com.vti.ecommerce.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        try {
            if (userRepository.existsUserByUsername(request.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(AuthenticationResponse.builder().message("Username is already used").token(null).build());
            }
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .role(request.getRole().equals("ADMIN") ? Role.ADMIN : Role.CUSTOMER)
                    .build();
            userRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder().message("Register successfully").token(jwtToken).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(null);
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder().message("Login success").token(jwtToken).build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Invalid username or password").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<AuthenticationResponse> changePassword(ChangePasswordRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder().message("Change Password successfully").token(jwtToken).build());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(AuthenticationResponse.builder().message("Invalid username or password").build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
