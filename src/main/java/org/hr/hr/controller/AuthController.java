package org.hr.hr.controller;

import org.hr.hr.dto.LoginRequest;
import org.hr.hr.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest request) {

        if ("admin".equals(request.getUsername())
                && "password".equals(request.getPassword())
            ){
            String token = jwtUtil.generateToken(request.getUsername());

            return ResponseEntity.ok(Map.of("token" , token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message" , "帳號或密碼錯誤"));
    }
}
