package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.entity.Feed;
import com.crestdevs.sphinxbe.entity.Post;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.FeedDto;
import com.crestdevs.sphinxbe.payload.PostDto;
import com.crestdevs.sphinxbe.repository.FeedRepo;
import com.crestdevs.sphinxbe.repository.PostRepo;
import com.crestdevs.sphinxbe.service.FeedService;
import com.crestdevs.sphinxbe.service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepo feedRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Override
    public FeedDto getFeedByPostId(Integer postId) {

        this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        Feed fetchedFeed = null;
        try {
            fetchedFeed = this.feedRepo.findByPostId(postId);
        } catch (Exception ignored) {
        }

        return this.modelMapper.map(fetchedFeed, FeedDto.class);
    }

    @Override
    public FeedDto updateFeedByPostId(FeedDto feedDto, Integer postId) {

        Post fetchedPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
        PostDto postDto = this.modelMapper.map(fetchedPost, PostDto.class);

        Feed fetchedFeed = null;
        try {
            fetchedFeed = this.feedRepo.findByPostId(postId);
        } catch (Exception ignored) {
        }

        assert fetchedFeed != null;

        fetchedFeed.setDescription(feedDto.getDescription());
        fetchedFeed.setImage(postDto.getImage());

        Feed updatedFeed = this.feedRepo.save(fetchedFeed);

        return this.modelMapper.map(updatedFeed, FeedDto.class);
    }

    @Override
    public List<FeedDto> getAllFeeds() {

        List<Feed> fetchedFeedList = this.feedRepo.findAll();

        return fetchedFeedList.stream().map(feed -> this.modelMapper.map(feed, FeedDto.class)).collect(Collectors.toList());
    }

    @Override
    public void downloadFeedImageByPostId(Integer postId, String path, HttpServletResponse response) throws IOException {

        Feed feed = this.feedRepo.findByPostId(postId);

        String image = feed.getImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }
}
