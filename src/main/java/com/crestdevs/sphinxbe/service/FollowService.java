package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.FollowDto;

import java.util.List;

public interface FollowService {

    FollowDto createFollower(Integer follower, Integer following);

    Boolean unfollowUser(Integer followerId, Integer followingId);

    //get all followers of a user
    List<FollowDto> getAllFollowerByUserId(Integer userId);



}
