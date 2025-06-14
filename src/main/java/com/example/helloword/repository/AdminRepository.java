package com.example.helloword.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.helloword.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);

    Admin findByPassword(String password);
}
