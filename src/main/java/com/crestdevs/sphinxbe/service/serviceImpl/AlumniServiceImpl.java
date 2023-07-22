package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Alumni;
import com.crestdevs.sphinxbe.payload.AlumniDto;
import com.crestdevs.sphinxbe.repository.AlumniRepo;
import com.crestdevs.sphinxbe.service.AlumniService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public AlumniDto getAlumniById(Integer alumniId) {
        return null;
    }

    @Override
    public List<AlumniDto> getAllAlumni() {
        return null;
    }

    @Override
    public void deleteAlumni(Integer alumniId) {

    }
}
