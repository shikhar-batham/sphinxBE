package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    void deleteStudent(Integer studentId);

    Boolean uploadStudentProfileImage(Integer studentId, String path, MultipartFile file) throws IOException;

    void downloadStudentImageById(Integer studentId, String path, HttpServletResponse response) throws IOException;
}
