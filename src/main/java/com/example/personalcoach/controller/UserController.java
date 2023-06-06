package com.example.personalcoach.controller;

import com.example.personalcoach.repository.RoleRepository;
import com.example.personalcoach.service.UserDetailsServiceImpl;
import com.example.personalcoach.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private UserDetailsServiceImpl userDetailsService;
    private JwtUtils jwtUtils;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils, RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserInfo
            (@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token) {

        return ResponseEntity.ok(jwtUtils.getUserNameFromJwtToken(token));
    }
}
