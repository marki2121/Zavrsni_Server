package com.example.zavrsnirad.entity;

import com.example.zavrsnirad.appenum.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

// Ova klasa predstavlja entitet korisnika u bazi podataka
// Ova klasa implementira interfejs UserDetails koji je dio Spring Security-a
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(
            name = "username",
            unique = true,
            nullable = false
    )
    private String username;
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "role",
            nullable = false
    )
    private Role role;
    @Column(
            name = "enabled",
            nullable = false
    )
    private Boolean enabled;
    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;
    @ManyToMany(mappedBy = "students")
    private Set<Subject> subjects;


    // Implementacija metoda iz interfejsa UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Constructor
    public User() {
    }

    public User(Long id, String username, String password, Role role, Boolean enabled, UserProfile userProfile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.userProfile = userProfile;
    }

    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }
}
