package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.StudentDto;

import java.util.List;

public interface StudentService {

    //create student
    StudentDto createStudent(StudentDto studentDto);

    //update student
    StudentDto updateStudent(StudentDto studentDto, Integer studentId);

    //get student by id
    StudentDto getStudentById(Integer studentId);

    //get all students
    List<StudentDto> getAllStudents();

    //delete student
    void deleteStudent(Integer StudentId);
}
