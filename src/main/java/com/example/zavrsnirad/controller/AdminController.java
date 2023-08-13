package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestHeader String Authorization) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.getAllUsers(Authorization));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.getUserById(Authorization, id));
    }

    @Transactional
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.deleteUserById(Authorization, id));
    }

    @PutMapping("/user/{id}/disable")
    public ResponseEntity<String> disableUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.disableUserById(Authorization, id));
    }

    @PutMapping("/user/{id}/enable")
    public ResponseEntity<String> enableUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.enableUserById(Authorization, id));
    }

    @PutMapping("/user/{id}/promote")
    public ResponseEntity<String> promoteUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.promoteUserById(Authorization, id));
    }

    @PutMapping("/user/{id}/demote")
    public ResponseEntity<String> demoteUserById(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(adminService.demoteUserById(Authorization, id));
    }
}
