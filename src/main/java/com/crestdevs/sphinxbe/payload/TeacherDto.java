package com.crestdevs.sphinxbe.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {

    private Integer teacherId;
    private String type;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String college;
    private String userName;
    private String password;
    private String registrationDate;
    private String department;
    private String profileImage;
    private UserDto user;
}
