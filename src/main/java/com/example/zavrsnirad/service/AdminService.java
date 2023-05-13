package com.example.zavrsnirad.service;

import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Object> getAllUsers(String authorization);
}
