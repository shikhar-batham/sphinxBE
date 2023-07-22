package com.crestdevs.sphinxbe.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {

    private Integer studentId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String college;
    private String userName;
    private String password;
    private String registrationDate;
    private String branch;
    private String passingYear;
    private UserDto user;
}
