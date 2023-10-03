package com.crestdevs.sphinxbe.payload;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedDto {

    private String postId;
    private String title;
    private String description;
    private String image;
    private Date addedDate;
    private UserDto user;
    private Long milliDate;
}
