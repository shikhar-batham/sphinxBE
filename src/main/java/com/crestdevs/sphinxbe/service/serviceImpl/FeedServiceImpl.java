package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Post;
import com.crestdevs.sphinxbe.payload.PostDto;
import com.crestdevs.sphinxbe.repository.FeedRepo;
import com.crestdevs.sphinxbe.repository.PostRepo;
import com.crestdevs.sphinxbe.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepo feedRepo;

    @Autowired
    private PostRepo postRepo;

    @Override
    public void createFeed(PostDto postDto) {

    }
}
