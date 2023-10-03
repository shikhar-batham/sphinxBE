package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.FollowDto;
import com.crestdevs.sphinxbe.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
