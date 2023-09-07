package com.crestdevs.sphinxbe.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "feeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedId;

    @Column(length = 10000)
    private String description;

    private String image;

    private Date addedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long milliDate;
}
