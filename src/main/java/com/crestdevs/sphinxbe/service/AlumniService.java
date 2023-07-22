package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.AlumniDto;

import java.util.List;

public interface AlumniService {

    //create alumni
    AlumniDto createAlumni(AlumniDto alumniDto);

    //update alumni
    AlumniDto updateAlumni(AlumniDto alumniDto, Integer alumniId);

    //get alumni by in
    AlumniDto getAlumniById(Integer alumniId);

    //get all alumni
    List<AlumniDto> getAllAlumni();

    //deleteAlumni
    void deleteAlumni(Integer alumniId);
}
