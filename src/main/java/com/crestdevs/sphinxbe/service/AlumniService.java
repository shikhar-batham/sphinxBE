package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.AlumniDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    Boolean uploadAlumniProfileImage(Integer alumniId, String path, MultipartFile file) throws IOException;

    void downloadAlumniProfileImageById(Integer alumniId, String path, HttpServletResponse response) throws IOException;

    Boolean uploadAlumniProfileImageByEmail(String email, String path, MultipartFile file) throws IOException;

    void downloadAlumniProfileImageByEmail(String email, String path, HttpServletResponse response) throws IOException;
}
