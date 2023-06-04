package com.example.personalcoach.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String getAll(){
        return "public API";
    }

    @GetMapping("/user")
    public String getUserApi(){
        return "User Api";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    public String getModeratorApi(){
        return "Moderator API";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdministratorApi(){
        return "Administrator API";
    }
}
