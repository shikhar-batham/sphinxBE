package com.crestdevs.sphinxbe.repository;

import com.crestdevs.sphinxbe.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    List<Student> findByIgnoreCaseCollege(String collegeName);
}
