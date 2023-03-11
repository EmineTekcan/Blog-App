package com.eminetekcan.BlogApp.service;

import com.eminetekcan.BlogApp.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);
    void deleteComment(Integer commentId);
}
