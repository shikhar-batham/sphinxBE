package com.crestdevs.sphinxbe.service;


import com.crestdevs.sphinxbe.payload.PostDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PostService {

    //create post
    PostDto createPost(PostDto postDto, Integer userId) throws IOException;

    //update post
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete post
    void deletePost(Integer postId);

    //get post by id
    PostDto getPostById(Integer postId);

    //get all post
//    PostResponse getAllPostPagination(Integer pageNumber, Integer pageSize,String sortBy);


    //get all posts by user_id
    List<PostDto> getAllPostsByUserId(Integer userId);

    //search post
    List<PostDto> searchPost(String keyword);

    //get all post by userId
    List<PostDto> getAllPostByUserId(Integer userId);

    void downloadPostImageByPostId(Integer postId,String path, HttpServletResponse response) throws  IOException;
}
