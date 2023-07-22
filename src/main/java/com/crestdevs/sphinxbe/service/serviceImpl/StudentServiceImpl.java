package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Student;
import com.crestdevs.sphinxbe.payload.StudentDto;
import com.crestdevs.sphinxbe.repository.StudentRepo;
import com.crestdevs.sphinxbe.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {

        Student student = this.modelMapper.map(studentDto, Student.class);

        Student savedStudent = this.studentRepo.save(student);

        return this.modelMapper.map(savedStudent, StudentDto.class);
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto, Integer studentId) {
        return null;
    }

    @Override
    public StudentDto getStudentById(Integer studentId) {
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return null;
    }

    @Override
    public void deleteStudent(Integer StudentId) {

    }
}
