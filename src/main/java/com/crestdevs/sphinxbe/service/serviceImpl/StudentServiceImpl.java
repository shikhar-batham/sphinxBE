package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Student;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.StudentDto;
import com.crestdevs.sphinxbe.repository.StudentRepo;
import com.crestdevs.sphinxbe.service.FileService;
import com.crestdevs.sphinxbe.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        Student student = this.modelMapper.map(studentDto, Student.class);

        Student savedStudent = this.studentRepo.save(student);

        return this.modelMapper.map(savedStudent, StudentDto.class);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Integer studentId) {

        Student fetchedStudent = this.studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

        fetchedStudent.setFirstName(studentDto.getFirstName());
        fetchedStudent.setLastName(studentDto.getLastName());
        fetchedStudent.setEmail(studentDto.getEmail());
        fetchedStudent.setGender(studentDto.getGender());
        fetchedStudent.setCollege(studentDto.getCollege());
        fetchedStudent.setBranch(studentDto.getBranch());
        fetchedStudent.setPassingYear(studentDto.getPassingYear());

        Student updatedStudent = this.studentRepo.save(fetchedStudent);

        return this.modelMapper.map(updatedStudent, StudentDto.class);
    }

    @Override
    public StudentDto getStudentById(Integer studentId) {

        Student fetchedStudent = this.studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));


        return this.modelMapper.map(fetchedStudent, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {

        List<Student> fetchedStudentList = this.studentRepo.findAll();

        return fetchedStudentList.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Integer studentId) {

        Student fetchedStudent = this.studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

        this.studentRepo.delete(fetchedStudent);
    }

    @Override
    public Boolean uploadStudentProfileImage(Integer studentId, String path, MultipartFile file) throws IOException {

        Student fetchedStudent = this.studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

        String image = this.fileService.uploadImage(path, file);
        fetchedStudent.setProfileImage(image);

        this.studentRepo.save(fetchedStudent);

        return true;
    }

    @Override
    public Boolean uploadStudentProfileImageByEmail(String email, String path, MultipartFile file) throws IOException {

        Student fetchedStudent = null;
        try {
            fetchedStudent = this.studentRepo.findByEmail(email);
        } catch (Exception ignored) {
        }
        String image = this.fileService.uploadImage(path, file);
        fetchedStudent.setProfileImage(image);

        this.studentRepo.save(fetchedStudent);

        return true;
    }

    @Override
    public void downloadStudentProfileImageByEmail(String email, String path, HttpServletResponse response) throws IOException {

        Student fetchedStudent = this.studentRepo.findByEmail(email);

        if (fetchedStudent == null)
            return;

        String image = fetchedStudent.getProfileImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }

    @Override
    public void downloadStudentImageById(Integer studentId, String path, HttpServletResponse response) throws IOException {

        Student fetchedStudent = this.studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "student_id", studentId));

        String image = fetchedStudent.getProfileImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }

    @Override
    public List<StudentDto> getAllStudentByCollegeName(String collegeName) {

        List<Student> fetchedStudentsByCollege = this.studentRepo.findByIgnoreCaseCollege(collegeName);

        return fetchedStudentsByCollege.stream().map(student -> this.modelMapper.map(student, StudentDto.class)).collect(Collectors.toList());

    }
}
