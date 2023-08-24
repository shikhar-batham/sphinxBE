
package com.crestdevs.sphinxbe.controller;

import com.crestdevs.sphinxbe.payload.ApiResponse;
import com.crestdevs.sphinxbe.payload.PostDto;
import com.crestdevs.sphinxbe.service.FileService;
import com.crestdevs.sphinxbe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

    @Value("${project.postImages}")
    private String postImagePath;

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @PostMapping("/{userId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable("userId") Integer userId

    ) throws IOException {

        PostDto createdPost = this.postService.createPost(postDto, userId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId) {

        PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }


//    @GetMapping("/getPostsByUserId/{userId}")
//    public ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable("userId") Integer userId) {
//
//        List<PostDto> postDtos = this.postService.getAllPostsByUserId(userId);
//
//        return new ResponseEntity<>(postDtos, HttpStatus.OK);
//    }
//
//    @GetMapping("/")
//    public ResponseEntity<PostResponse> getAllPostPagination(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
//                                                             @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy) {
//
//        PostResponse postResponse = this.postService.getAllPostPagination(pageNumber, pageSize, sortBy);
//
//        return new ResponseEntity<>(postResponse, HttpStatus.OK);
//    }

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId) {

        PostDto postDto = this.postService.getPostById(postId);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<ApiResponse> deletePost(@RequestParam Integer postId) {

        this.postService.deletePost(postId);

        return new ResponseEntity<>(new ApiResponse("Post was deleted!", true), HttpStatus.OK);
    }

    @GetMapping("/searchPost/")
    public ResponseEntity<List<PostDto>> searchPost(@RequestParam("keyword") String keyword) {

        List<PostDto> postDtoList = this.postService.searchPost(keyword);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable("postId") Integer postId) throws IOException {

        String uploadedImage = this.fileService.uploadImage(postImagePath, image);

        PostDto fetchedPostDto = this.postService.getPostById(postId);
        fetchedPostDto.setImage(uploadedImage);

        PostDto updatedPostDto = this.postService.updatePost(fetchedPostDto, postId);

        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @GetMapping("/getAllPost/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostByUserId(@PathVariable("userId") Integer userId) {

        List<PostDto> postDtoList = this.postService.getAllPostByUserId(userId);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/getPostImage/{userId}")
    public void downloadPostImageByPostId(@PathVariable("userId") Integer userId, HttpServletResponse response) throws IOException {

        try {
            this.postService.downloadPostImageByPostId(userId, postImagePath, response);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        } catch (Exception ignored) {

        }
    }
}
