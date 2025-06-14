package com.example.helloword.repository;

import com.example.helloword.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByPassword(String password);
}
