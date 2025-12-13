package com.swe2.repository;

import com.swe2.model.Enum.Role;
import com.swe2.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    User findById(int id);

    boolean existsByEmail(String email);

    List<User> findByRole(Role role);
}