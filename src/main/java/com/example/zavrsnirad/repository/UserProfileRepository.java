package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Ova klasa predstavlja repozitorij korisnickog profila koji se koristi za pristup podacima u bazi podataka (CRUD operacije)
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
}
