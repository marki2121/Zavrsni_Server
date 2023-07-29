package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.getUserById(Authorization, id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.deleteUserById(Authorization, id);
    }

    @PutMapping("/user/{id}/disable")
    public ResponseEntity<Object> disableUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.disableUserById(Authorization, id);
    }

    @PutMapping("/user/{id}/enable")
    public ResponseEntity<Object> enableUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.enableUserById(Authorization, id);
    }

    @PutMapping("/user/{id}/promote")
    public ResponseEntity<Object> promoteUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.promoteUserById(Authorization, id);
    }

    @PutMapping("/user/{id}/demote")
    public ResponseEntity<Object> demoteUserById(@RequestHeader String Authorization, @PathVariable Long id){
        return adminService.demoteUserById(Authorization, id);
    }

    @PutMapping("/user/{id}/change-password")
    public ResponseEntity<Object> changePassword(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody String newPassword){
        return adminService.changePassword(Authorization, id, newPassword);
    }
}
