package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username);
}
