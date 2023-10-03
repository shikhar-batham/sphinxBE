package com.crestdevs.sphinxbe.service;

import com.crestdevs.sphinxbe.payload.FeedDto;
import com.crestdevs.sphinxbe.payload.PostDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FeedService {

    FeedDto  getFeedByPostId(Integer postId);

    FeedDto updateFeedByPostId(FeedDto feedDto,Integer postId);

    List<FeedDto>getAllFeeds();

    void downloadFeedImageByPostId(Integer PostId, String path, HttpServletResponse response) throws IOException;



}
