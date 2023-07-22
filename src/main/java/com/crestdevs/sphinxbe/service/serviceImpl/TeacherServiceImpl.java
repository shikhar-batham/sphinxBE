package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Teacher;
import com.crestdevs.sphinxbe.payload.TeacherDto;
import com.crestdevs.sphinxbe.repository.TeacherRepo;
import com.crestdevs.sphinxbe.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public TeacherDto getTeacherById(Integer teacherId) {
        return null;
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        return null;
    }

    @Override
    public void deleteTeacher(Integer teacherId) {

    }
}
