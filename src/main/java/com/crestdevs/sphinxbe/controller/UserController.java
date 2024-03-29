package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {

        UserDto registeredUser = this.userService.registerNewUser(userDto);

        if (registeredUser != null) {
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/searchUserByFirstNameOrLastName")
    public ResponseEntity<List<UserDto>> searchUserByFirstNameOrLastName(@RequestParam("firstName") String firstName,
                                                                         @RequestParam(value = "lastName") String lastName) {

        List<UserDto> userDtoList = this.userService.searchUserByFirstNameOrLastName(firstName, lastName);

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

}
