package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.UserDto;

import java.util.List;

public interface UserService {

    //register new user
    UserDto registerNewUser(UserDto userDto);

    //update user
    UserDto updateUser(UserDto userDto, Integer userId);

    //get user by id
    UserDto getUserById(Integer userId);

    //get all users
    List<UserDto> getAllUsers();

    //get user by email
    UserDto getUserByEmail(String email);

    //get user by username
    UserDto getUserByUsername(String username);

    //delete user by id
    void deleteUserById(Integer userId);

    List<UserDto> searchUserByFirstNameOrLastName(String firstName,String lastName);
}
