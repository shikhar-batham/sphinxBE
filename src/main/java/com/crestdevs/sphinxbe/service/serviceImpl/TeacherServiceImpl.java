package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Teacher;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.TeacherDto;
import com.crestdevs.sphinxbe.repository.TeacherRepo;
import com.crestdevs.sphinxbe.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TeacherDto createTeacher(TeacherDto teacherDto) {

        Teacher teacher = this.modelMapper.map(teacherDto, Teacher.class);

        Teacher savedTeacher = this.teacherRepo.save(teacher);

        return this.modelMapper.map(savedTeacher, TeacherDto.class);
    }

    @Override
    public TeacherDto updateTeacher(TeacherDto teacherDto, Integer teacherId) {

        Teacher fetchedTeacher = this.teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "teacher_id", teacherId));

        fetchedTeacher.setFirstName(teacherDto.getFirstName());
        fetchedTeacher.setLastName(teacherDto.getLastName());
        fetchedTeacher.setGender(teacherDto.getGender());
        fetchedTeacher.setDepartment(teacherDto.getDepartment());
        fetchedTeacher.setUserName(teacherDto.getDepartment());

        Teacher updatedTeacher = this.teacherRepo.save(fetchedTeacher);

        return this.modelMapper.map(updatedTeacher, TeacherDto.class);
    }

    @Override
    public TeacherDto getTeacherById(Integer teacherId) {

        Teacher fetchedTeacher = this.teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "teacher_id", teacherId));

        return this.modelMapper.map(fetchedTeacher, TeacherDto.class);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {

        List<Teacher> fetchedTeacherList = this.teacherRepo.findAll();

        return fetchedTeacherList.stream().map(teacher -> this.modelMapper.map(teacher, TeacherDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteTeacher(Integer teacherId) {

        Teacher fetchedTeacher = this.teacherRepo.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "teacher_id", teacherId));

        this.teacherRepo.delete(fetchedTeacher);
    }
}
