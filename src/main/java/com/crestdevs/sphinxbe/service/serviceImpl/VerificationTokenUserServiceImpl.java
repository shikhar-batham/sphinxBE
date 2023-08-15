package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.User;
import com.crestdevs.sphinxbe.entity.VerificationTokenUser;
import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.payload.VerificationTokenUserDto;
import com.crestdevs.sphinxbe.repository.VerificationTokenUserRepo;
import com.crestdevs.sphinxbe.service.VerificationTokenUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class VerificationTokenUserServiceImpl implements VerificationTokenUserService {

    @Autowired
    private VerificationTokenUserRepo verificationTokenUserRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VerificationTokenUserDto createVerificationToken(UserDto userDto, String token) {

        VerificationTokenUserDto verificationTokenUserDto = new VerificationTokenUserDto();
        verificationTokenUserDto.setUser(userDto);
        verificationTokenUserDto.setToken(token);

        VerificationTokenUser verificationTokenUser = this.modelMapper.map(verificationTokenUserDto, VerificationTokenUser.class);

        User user = this.modelMapper.map(userDto, User.class);

        VerificationTokenUser verificationTokenUserCheck = this.verificationTokenUserRepo.findByUser(user);

        VerificationTokenUserDto verificationTokenUserCheckDto = null;

        if (verificationTokenUserCheck != null) {
            verificationTokenUserCheckDto = this.modelMapper.map(verificationTokenUserCheck, VerificationTokenUserDto.class);
        }

        Calendar calendar = Calendar.getInstance();

        if (verificationTokenUserCheck == null) {
            this.verificationTokenUserRepo.save(verificationTokenUser);
        } else {
            if (calendar.getTime().getTime() - verificationTokenUserCheckDto.getExpiration().getTime() > 0) {
                updateVerificationToken(userDto, token);
            }
        }

        return verificationTokenUserDto;
    }

    @Override
    public VerificationTokenUserDto getVerificationToken(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        VerificationTokenUser verificationTokenUser = this.verificationTokenUserRepo.findByUser(user);

        if (verificationTokenUser == null)
            return null;

        return this.modelMapper.map(verificationTokenUser, VerificationTokenUserDto.class);
    }

    @Override
    public String updateVerificationToken(UserDto userDto, String token) {

        User user = this.modelMapper.map(userDto, User.class);

        VerificationTokenUser verificationTokenUser = this.verificationTokenUserRepo.findByUser(user);

        VerificationTokenUserDto verificationTokenUserDto = this.modelMapper.map(verificationTokenUser, VerificationTokenUserDto.class);

        verificationTokenUserDto.setExpiration(new VerificationTokenUserDto().getExpiration());
        verificationTokenUserDto.setToken(token);

        VerificationTokenUser verificationTokenUserUpdated = this.modelMapper.map(verificationTokenUserDto, VerificationTokenUser.class);

        this.verificationTokenUserRepo.save(verificationTokenUser);

        return verificationTokenUserUpdated.getToken();
    }

    @Override
    public void deleteVerificationToken(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        VerificationTokenUser verificationTokenUser = this.verificationTokenUserRepo.findByUser(user);

        this.verificationTokenUserRepo.delete(verificationTokenUser);
    }
}
