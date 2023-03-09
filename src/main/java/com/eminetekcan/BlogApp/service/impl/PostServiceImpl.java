package com.eminetekcan.BlogApp.service.impl;

import com.eminetekcan.BlogApp.entity.Category;
import com.eminetekcan.BlogApp.entity.Post;
import com.eminetekcan.BlogApp.entity.User;
import com.eminetekcan.BlogApp.exception.ResourceNotFoundException;
import com.eminetekcan.BlogApp.payload.PostDto;
import com.eminetekcan.BlogApp.payload.PostResponse;
import com.eminetekcan.BlogApp.repository.CategoryRepository;
import com.eminetekcan.BlogApp.repository.PostRepository;
import com.eminetekcan.BlogApp.repository.UserRepository;
import com.eminetekcan.BlogApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
        Post post=modelMapper.map(postDto,Post.class);
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));

        post.setImageName("default.jpg");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost=postRepository.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
        post.setTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost=postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        postRepository.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> page=postRepository.findAll(pageable);
        List<Post> posts=page.getContent();
        List<PostDto> postDtos=posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return PostResponse.builder()
                .postDtos(postDtos)
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalPages())
                .lastPage(page.isLast())
                .build();
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        List<Post> posts=postRepository.findAllByUser(user);
        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts=postRepository.findAllByCategory(category);
        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts=postRepository.findByTitleContaining(keyword);
        return posts.stream().map(post -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
    }
}
