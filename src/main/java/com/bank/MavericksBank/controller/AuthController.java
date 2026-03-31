package com.bank.MavericksBank.controller;

import com.bank.MavericksBank.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @GetMapping("/login")
    public ResponseEntity<?> login(Principal principal){
        String loggin = principal.getName();
        Map<String,String> map = new HashMap<>();
        map.put("token", jwtUtil.generateToken(loggin));
        return ResponseEntity.status(HttpStatus.CREATED).body(map);

    }
}
