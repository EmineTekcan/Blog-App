package com.eminetekcan.BlogApp.service;

import com.eminetekcan.BlogApp.entity.Category;
import com.eminetekcan.BlogApp.entity.Post;
import com.eminetekcan.BlogApp.entity.User;
import com.eminetekcan.BlogApp.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
    public PostDto updatePost(PostDto postDto, Integer postId);
    public void deletePost(Integer postId);
    public PostDto getPostById(Integer postId);
    public List<PostDto> getAllPost();
    public List<PostDto> getPostsByUser(Integer userId);
    public List<PostDto> getPostsByCategory(Integer categoryId);
    public List<PostDto> searchPosts(String keyword);
}
