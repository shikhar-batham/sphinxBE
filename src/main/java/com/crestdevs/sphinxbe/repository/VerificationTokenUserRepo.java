package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.User;
import com.crestdevs.sphinxbe.entity.VerificationTokenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenUserRepo extends JpaRepository<VerificationTokenUser, Integer> {

    VerificationTokenUser findByUser(User user);
}
