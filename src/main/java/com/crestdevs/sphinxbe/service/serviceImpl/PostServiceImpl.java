package com.crestdevs.sphinxbe.service.serviceImpl;


import com.crestdevs.sphinxbe.entity.Feed;
import com.crestdevs.sphinxbe.entity.Post;
import com.crestdevs.sphinxbe.entity.User;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.FeedDto;
import com.crestdevs.sphinxbe.payload.PostDto;
import com.crestdevs.sphinxbe.repository.FeedRepo;
import com.crestdevs.sphinxbe.repository.PostRepo;
import com.crestdevs.sphinxbe.repository.UserRepo;
import com.crestdevs.sphinxbe.service.FileService;
import com.crestdevs.sphinxbe.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Value("${project.postImages}")
    private String postImagePath;
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileService fileService;

    @Autowired
    private FeedRepo feedRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId) throws IOException {

        User fetchedUser = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        long currentTime = System.currentTimeMillis();

        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setMilliDate(currentTime);
        post.setUser(fetchedUser);

        Post cratedPost = this.postRepo.save(post);

        PostDto postDtoForFeed = this.modelMapper.map(cratedPost, PostDto.class);
        this.createFeed(postDtoForFeed);

        return this.modelMapper.map(cratedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post fetchedPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        fetchedPost.setTitle(postDto.getTitle());
        fetchedPost.setDescription(postDto.getDescription());
        fetchedPost.setImage(postDto.getImage());


        Post updatedPost = this.postRepo.save(fetchedPost);

        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        this.postRepo.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post fetchedPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        return this.modelMapper.map(fetchedPost, PostDto.class);
    }

//    @Override
//    public PostResponse getAllPostPagination(Integer pageNumber, Integer pageSize, String soryBy) {
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(soryBy).descending());
//
//        Page<Post> postPage = this.postRepo.findAll(pageable);
//
//        List<Post> postList = postPage.getContent();
//
//        List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//
//        PostResponse postResponse = new PostResponse();
//
//        postResponse.setPageNumber(postPage.getNumber());
//        postResponse.setPageSize(postPage.getSize());
//        postResponse.setLastPage(postPage.isLast());
//        postResponse.setTotalPages(postPage.getTotalPages());
//        postResponse.setContent(postDtoList);
//        postResponse.setTotalElement(postPage.getTotalPages());
//
//
//        return postResponse;
//    }

    @Override
    public List<PostDto> getAllPostsByUserId(Integer userId) {

        User fetchedUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        List<Post> postList = this.postRepo.findByUser(fetchedUser);

        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> postList = this.postRepo.findByTitleContaining(keyword);

        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostByUserId(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        List<Post> fetchedPostList = this.postRepo.findAllByUserId(userId);

        return fetchedPostList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public void downloadPostImageByPostId(Integer postId, String path, HttpServletResponse response) throws IOException {

        Post fetchedPost = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));

        String image = fetchedPost.getImage();

        if (!image.isEmpty()) {
            InputStream resource = this.fileService.getResource(path, image);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource, response.getOutputStream());
        }
    }

    @Override
    public List<PostDto> getAllPost() {

        List<Post> postList = this.postRepo.findAll();

        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    public void createFeed(PostDto postDto) {

        long currentTime = System.currentTimeMillis();

        FeedDto feedDto = new FeedDto();
        feedDto.setPostId(postDto.getPostId());
        feedDto.setDescription(postDto.getDescription());
        feedDto.setAddedDate(postDto.getAddedDate());
        feedDto.setImage(postDto.getImage());
        feedDto.setMilliDate(currentTime);
        feedDto.setUser(postDto.getUser());

        Feed feed = this.modelMapper.map(feedDto, Feed.class);
        this.feedRepo.save(feed);
    }
}
