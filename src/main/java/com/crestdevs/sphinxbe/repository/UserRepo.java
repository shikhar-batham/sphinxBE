package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String username);

//    @Query(value = "SELECT * FROM users WHERE LOWER(first_name) LIKE LOWER(?1) OR LOWER(last_name) LIKE LOWER(?2)", nativeQuery = true)
    //@Query(value = "SELECT * FROM users WHERE LOWER(first_name) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(last_name) LIKE LOWER(CONCAT('%', ?2, '%'))", nativeQuery = true)
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<User>findByFirstNameStartingWithIgnoreCase(String firstName);

    List<User>findByLastNameStartingWithIgnoreCase(String  lastName);
}

//findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase
//findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase
