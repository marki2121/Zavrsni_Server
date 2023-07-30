package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Ova klasa predstavlja repozitorij korisnika koji se koristi za pristup podacima u bazi podataka (CRUD operacije)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Metoda koja pronalazi korisnika po username-u
    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContaining(String username);
}
