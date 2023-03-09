package com.eminetekcan.BlogApp.controller;

import com.eminetekcan.BlogApp.config.AppConstants;
import com.eminetekcan.BlogApp.entity.Post;
import com.eminetekcan.BlogApp.payload.ApiResponse;
import com.eminetekcan.BlogApp.payload.PostDto;
import com.eminetekcan.BlogApp.payload.PostResponse;
import com.eminetekcan.BlogApp.repository.PostRepository;
import com.eminetekcan.BlogApp.service.impl.FileServiceImpl;
import com.eminetekcan.BlogApp.service.impl.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;
    private final FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;
    private final PostRepository postRepository;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.createPost(postDto,userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NUMBER, value = "pageNumber") Integer pageNumber,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE ,value = "pageSize") Integer pageSize,
            @RequestParam(required = false, defaultValue = AppConstants.SORT_BY ,value = "sortBy") String sortBy,
            @RequestParam(required = false, defaultValue = AppConstants.SORT_DIR , value = "sortDir") String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPost(pageNumber, pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        return new ResponseEntity<>(postService.getPostsByUser(userId),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId, @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePost(postDto,postId),HttpStatus.CONFLICT);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post successfully deleted",true),HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword){
        return new ResponseEntity<>(postService.searchPosts(keyword),HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        String fileName=fileService.uploadImage(path,image);
        PostDto postDto=postService.getPostById(postId);
        postDto.setImageName(fileName);
       PostDto postDto1= postService.updatePost(postDto,postId);
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/imageName",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable(value = "imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream image=fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image, response.getOutputStream());
    }

}
