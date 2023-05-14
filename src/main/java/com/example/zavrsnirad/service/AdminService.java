package com.example.zavrsnirad.service;

import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<Object> getAllUsers(String authorization);

    ResponseEntity<Object> getUserById(String authorization, Long id);

    ResponseEntity<Object> deleteUserById(String authorization, Long id);

    ResponseEntity<Object> disableUserById(String authorization, Long id);

    ResponseEntity<Object> enableUserById(String authorization, Long id);

    ResponseEntity<Object> promoteUserById(String authorization, Long id);

    ResponseEntity<Object> demoteUserById(String authorization, Long id);

    ResponseEntity<Object> changePassword(String authorization, Long id, String newPassword);
}
