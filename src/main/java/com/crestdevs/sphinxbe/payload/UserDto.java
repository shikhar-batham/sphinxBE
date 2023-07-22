package com.crestdevs.sphinxbe.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Integer userId;
    private String type;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String college;
    private String username;
    private String password;
    private String registrationDate;
}
