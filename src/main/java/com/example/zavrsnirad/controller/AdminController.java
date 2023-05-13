package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user/all")
    public ResponseEntity<Object> getAllUsers(@RequestHeader String Authorization){
        return adminService.getAllUsers(Authorization);
    }
}
