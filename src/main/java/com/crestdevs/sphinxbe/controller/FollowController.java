package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.ApiResponse;
import com.crestdevs.sphinxbe.payload.FollowDto;
import com.crestdevs.sphinxbe.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/")
    public ResponseEntity<FollowDto> createFollower(@RequestParam Integer followerId, @RequestParam Integer followingId) {

        FollowDto followDto = this.followService.createFollower(followerId, followingId);

        return new ResponseEntity<>(followDto, HttpStatus.CREATED);
    }

    @PutMapping("/unfollowUser")
    public ResponseEntity<ApiResponse> unfollowUser(@RequestParam Integer follower, @RequestParam Integer following) {

        Boolean res = this.followService.unfollowUser(follower, following);

        if (res) {
            return new ResponseEntity<>(new ApiResponse("unfollowed successfully", true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse("you don't follow this user", false), HttpStatus.OK);
        }
    }
}
