package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.payload.VerificationTokenUserDto;

public interface VerificationTokenUserService {

    //crete verification token
    VerificationTokenUserDto createVerificationToken(UserDto userDto, String token);

    //get verification token
    VerificationTokenUserDto getVerificationToken(UserDto userDto);

    //update verification token
    String updateVerificationToken(UserDto userDto, String token);

    //delete verification token
    void deleteVerificationToken(UserDto userDto);
}
