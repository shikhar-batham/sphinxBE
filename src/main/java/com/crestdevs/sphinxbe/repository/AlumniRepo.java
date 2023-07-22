package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniRepo extends JpaRepository<Alumni, Integer> {
}
