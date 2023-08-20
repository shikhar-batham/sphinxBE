package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.exception.ApiException;
import com.crestdevs.sphinxbe.payload.JwtAuthRequest;
import com.crestdevs.sphinxbe.payload.JwtAuthResponse;
import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.payload.VerificationTokenUserDto;
import com.crestdevs.sphinxbe.security.JWTTokenHelper;
import com.crestdevs.sphinxbe.service.UserService;
import com.crestdevs.sphinxbe.service.VerificationTokenUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.TimeZone;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private VerificationTokenUserService verificationTokenUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody UserDto userDto) {

        UserDto registeredNewUser = this.userService.registerNewUser(userDto);

        if (registeredNewUser == null)
            return new ResponseEntity<>("User already exist!", HttpStatus.CONFLICT);

        return new ResponseEntity<>(registeredNewUser, HttpStatus.CREATED);
    }

    @PostMapping("/login/user")
    public ResponseEntity<JwtAuthResponse> createUserToken(@RequestBody JwtAuthRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {

        JwtAuthResponse response;
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"));
        calendar.getTimeZone();
        String username;

        UserDto userDtoByUsername = this.userService.getUserByUsername(request.getUsername());

        if (userDtoByUsername != null)
            username = userDtoByUsername.getEmail();
        else
            username = request.getUsername();

        this.authenticate(username, request.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        UserDto userDto = this.userService.getUserByEmail(username);

        if (userDto == null)
            return new ResponseEntity<>(new JwtAuthResponse(), HttpStatus.NOT_FOUND);

        VerificationTokenUserDto verificationTokenUserDto = this.verificationTokenUserService.getVerificationToken(userDto);

        if (verificationTokenUserDto != null) {

            //if token is expired
            if (verificationTokenUserDto.getExpiration().getTime() - calendar.getTime().getTime() > 0) {

                response = new JwtAuthResponse();
                response.setToken(verificationTokenUserDto.getToken());

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        String token = this.jwtTokenHelper.generateToken(userDetails.getUsername());

        this.verificationTokenUserService.createVerificationToken(userDto, token);

        response = new JwtAuthResponse();

        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }
    }
}
