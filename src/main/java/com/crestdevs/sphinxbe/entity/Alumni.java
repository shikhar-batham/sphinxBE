package com.crestdevs.sphinxbe.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "alumni")
public class Alumni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
