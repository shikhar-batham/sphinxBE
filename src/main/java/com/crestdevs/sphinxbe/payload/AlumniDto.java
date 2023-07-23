package com.crestdevs.sphinxbe.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumniDto {

    private Integer alumniId;
    private String type;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String college;
    private String userName;
    private String password;
    private String registrationDate;
    private String passOutYear;
    private String branch;
    private String currentWorkingCompany;
    private String role;
    private String position;
    private String workingCountry;
    private String profileImage;
    private UserDto user;
}
