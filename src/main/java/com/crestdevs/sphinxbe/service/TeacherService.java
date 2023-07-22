package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.TeacherDto;

import java.util.List;

public interface TeacherService {

    //create teacher
    TeacherDto createTeacher(TeacherDto teacherDto);

    //update teacher
    TeacherDto updateTeacher(TeacherDto teacherDto, Integer teacherId);

    //get teacher by id
    TeacherDto getTeacherById(Integer teacherId);

    //get all teachers
    List<TeacherDto> getAllTeachers();

    //delete teacher by id
    void deleteTeacher(Integer teacherId);
}
