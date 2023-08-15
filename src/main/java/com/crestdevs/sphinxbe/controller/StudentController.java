package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.ApiResponse;
import com.crestdevs.sphinxbe.payload.StudentDto;
import com.crestdevs.sphinxbe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Value("${project.studentProfileImages}")
    private String path;

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable("studentId") Integer studentId) {

        StudentDto updatedStudentDto = this.studentService.updateStudent(studentDto, studentId);

        return new ResponseEntity<>(updatedStudentDto, HttpStatus.CREATED);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("studentId") Integer studentId) {

        StudentDto studentDto = this.studentService.getStudentById(studentId);

        return new ResponseEntity<>(studentDto, HttpStatus.FOUND);
    }

    @GetMapping("/getAllStudent")
    public ResponseEntity<List<StudentDto>> getAllStudents() {

        List<StudentDto> studentDtoList = this.studentService.getAllStudents();

        return new ResponseEntity<>(studentDtoList, HttpStatus.FOUND);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable("studentId") Integer studentId) {

        this.studentService.deleteStudent(studentId);

        return new ResponseEntity<>(new ApiResponse("Student was deleted!!", true), HttpStatus.OK);
    }

    @PostMapping("/uploadImage/{studentId}")
    public ResponseEntity<ApiResponse> uploadStudentProfileImage(@PathVariable("studentId") Integer studentId,
                                                                 @RequestParam("image") MultipartFile image) throws IOException {

        Boolean res = this.studentService.uploadStudentProfileImage(studentId, path, image);

        if (res)
            return new ResponseEntity<>(new ApiResponse("Image uploaded successfully!!", true), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiResponse("Something went wrong. Failed to upload you image!!", false), HttpStatus.CONFLICT);
    }

    @GetMapping(value = "/getImage/{studentId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadStudentImageById(@PathVariable Integer studentId, HttpServletResponse response) {

        try {
            this.studentService.downloadStudentImageById(studentId, path, response);
        } catch (Exception ignored) {

        }
    }

    @PostMapping("/uploadImageByEmail/{email}")
    public ResponseEntity<ApiResponse> uploadStudentProfileImageByEmail(@PathVariable("email") String email,
                                                                        @RequestParam("image") MultipartFile image) throws IOException {


        Boolean res = this.studentService.uploadStudentProfileImageByEmail(email, path, image);

        if (res)
            return new ResponseEntity<>(new ApiResponse("Image uploaded successfully!!", true), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ApiResponse("Something went wrong. Failed to upload you image!!", false), HttpStatus.CONFLICT);
    }
}
