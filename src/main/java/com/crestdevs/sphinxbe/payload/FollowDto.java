package com.crestdevs.sphinxbe.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowDto {

    private UserDto follower;
    private UserDto following;
}
