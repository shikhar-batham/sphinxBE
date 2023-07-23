package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Alumni;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.AlumniDto;
import com.crestdevs.sphinxbe.repository.AlumniRepo;
import com.crestdevs.sphinxbe.service.AlumniService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlumniServiceImpl implements AlumniService {

    @Autowired
    private AlumniRepo alumniRepo;

    @Autowired
    private ModelMapper modelMapper;

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
}
