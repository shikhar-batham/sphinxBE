package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.StudentDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    Boolean uploadStudentProfileImageByEmail(String email, String path, MultipartFile file) throws IOException;

    void downloadStudentProfileImageByEmail(String email, String path, HttpServletResponse response) throws IOException;

    void downloadStudentImageById(Integer studentId, String path, HttpServletResponse response) throws IOException;

    //get all student by college name from users table
    List<StudentDto>getAllStudentByCollegeName(String collegeName);

    //get all students by year from student table

    //get all students by branch from student table

    // get all students by branch and year from student table
}
