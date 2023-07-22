package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto userDto) {

        UserDto registeredNewUser = this.userService.registerNewUser(userDto);

        if (registeredNewUser == null)
            return new ResponseEntity<>("User already exist!", HttpStatus.CONFLICT);

        return new ResponseEntity<>(registeredNewUser, HttpStatus.CREATED);
    }
}
