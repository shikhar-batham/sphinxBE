package com.crestdevs.sphinxbe.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;
}
