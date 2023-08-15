package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {

    Student findByEmail(String email);
}
