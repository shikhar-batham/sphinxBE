package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.FeedDto;
import com.crestdevs.sphinxbe.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feed")
public class FeedController {

    @Value("${project.postImages}")
    private String postImagePath;

    @Autowired
    private FeedService feedService;

    @GetMapping("/getAllFeeds")
    public ResponseEntity<List<FeedDto>> getAllFeeds() {

        List<FeedDto> feedDtos = this.feedService.getAllFeeds();

        return new ResponseEntity<>(feedDtos, HttpStatus.OK);
    }

    @GetMapping("/getFeedImage/{postId}")
    public void downloadFeedImageByPostId(@PathVariable("postId") Integer feedId, HttpServletResponse response) {

        try {
            this.feedService.downloadFeedImageByPostId(feedId, postImagePath, response);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        } catch (Exception ignored) {

        }
    }

}
