package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByUsernameContaining(String username);

    List<User> findAllByRole(Role role);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_subject where user_subject.user_id=?1",
            nativeQuery = true)
    void deleteConnections(Long id);
}
