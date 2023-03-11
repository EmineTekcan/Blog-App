package com.eminetekcan.BlogApp.service.impl;

import com.eminetekcan.BlogApp.entity.Comment;
import com.eminetekcan.BlogApp.entity.Post;
import com.eminetekcan.BlogApp.exception.ResourceNotFoundException;
import com.eminetekcan.BlogApp.payload.CommentDto;
import com.eminetekcan.BlogApp.repository.CommentRepository;
import com.eminetekcan.BlogApp.repository.PostRepository;
import com.eminetekcan.BlogApp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));

        Comment comment=modelMapper.map(commentDto, Comment.class);
        Comment savedComment=commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);

    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
        commentRepository.delete(comment);
    }
}
