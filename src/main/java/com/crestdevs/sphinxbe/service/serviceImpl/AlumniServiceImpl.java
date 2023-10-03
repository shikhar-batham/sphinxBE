package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Alumni;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.AlumniDto;
import com.crestdevs.sphinxbe.repository.AlumniRepo;
import com.crestdevs.sphinxbe.service.AlumniService;
import com.crestdevs.sphinxbe.service.FileService;
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
public class AlumniServiceImpl implements AlumniService {

    @Autowired
    private AlumniRepo alumniRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Override
    public AlumniDto createAlumni(AlumniDto alumniDto) {

        Alumni alumni = this.modelMapper.map(alumniDto, Alumni.class);

        Alumni savedAlumni = this.alumniRepo.save(alumni);

        return this.modelMapper.map(savedAlumni, AlumniDto.class);
    }

    @Override
    public AlumniDto updateAlumni(AlumniDto alumniDto, Integer alumniId) {

        Alumni fetchedAlumni = this.alumniRepo.findById(alumniId)
                .orElseThrow(() -> new ResourceNotFoundException("Alumni", "alumni_id", alumniId));

        fetchedAlumni.setFirstName(alumniDto.getFirstName());
        fetchedAlumni.setLastName(alumniDto.getLastName());
        fetchedAlumni.setGender(alumniDto.getGender());
        fetchedAlumni.setBranch(alumniDto.getBranch());
        fetchedAlumni.setUserName(alumniDto.getUserName());
        fetchedAlumni.setPassOutYear(alumniDto.getPassOutYear());
        fetchedAlumni.setCurrentWorkingCompany(alumniDto.getCurrentWorkingCompany());
        fetchedAlumni.setWorkingCountry(alumniDto.getWorkingCountry());
        fetchedAlumni.setRole(alumniDto.getRole());
        fetchedAlumni.setPosition(alumniDto.getPosition());

        Alumni savedAlumni = this.alumniRepo.save(fetchedAlumni);

        return this.modelMapper.map(savedAlumni, AlumniDto.class);
    }

    @Override
    public AlumniDto getAlumniById(Integer alumniId) {

        Alumni fetchedAlumni = this.alumniRepo.findById(alumniId)
                .orElseThrow(() -> new ResourceNotFoundException("Alumni", "alumni_id", alumniId));

        return this.modelMapper.map(fetchedAlumni, AlumniDto.class);
    }

    @Override
    public List<AlumniDto> getAllAlumni() {

        List<Alumni> fetchedAlumniList = this.alumniRepo.findAll();

        return fetchedAlumniList.stream().map(alumni -> this.modelMapper.map(alumni, AlumniDto.class)).collect(Collectors.toList());

    }

    @Override
    public void deleteAlumni(Integer alumniId) {

        Alumni fetchedAlumni = this.alumniRepo.findById(alumniId)
                .orElseThrow(() -> new ResourceNotFoundException("Alumni", "alumni_id", alumniId));

        this.alumniRepo.delete(fetchedAlumni);
    }

    @Override
    public Boolean uploadAlumniProfileImage(Integer alumniId, String path, MultipartFile file) throws IOException {

        Alumni fethchedAlumni = this.alumniRepo.findById(alumniId).orElseThrow(() -> new ResourceNotFoundException("Alumni", "Alumni_id", alumniId));

        String image = this.fileService.uploadImage(path, file);
        fethchedAlumni.setProfileImage(image);

        this.alumniRepo.save(fethchedAlumni);

        return true;
    }

    @Override
    public void downloadAlumniProfileImageById(Integer alumniId, String path, HttpServletResponse response) throws IOException {

        Alumni fetchedAlumni = this.alumniRepo.findById(alumniId).
                orElseThrow(() -> new ResourceNotFoundException("Alumni", "Alumni_Id", alumniId));

        String image = fetchedAlumni.getProfileImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }

    @Override
    public Boolean uploadAlumniProfileImageByEmail(String email, String path, MultipartFile file) throws IOException {

        Alumni fetchedAlumni = null;

        try {
            fetchedAlumni = this.alumniRepo.findByEmail(email);
        } catch (Exception ignored) {
        }

        String image = this.fileService.uploadImage(path, file);
        assert fetchedAlumni != null;
        fetchedAlumni.setProfileImage(image);

        this.alumniRepo.save(fetchedAlumni);

        return true;
    }

    @Override
    public void downloadAlumniProfileImageByEmail(String email, String path, HttpServletResponse response) throws IOException {

        Alumni fetchedAlumni = this.alumniRepo.findByEmail(email);
        assert fetchedAlumni != null;

        String image = fetchedAlumni.getProfileImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }
}
