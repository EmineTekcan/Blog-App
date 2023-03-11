package com.eminetekcan.BlogApp.repository;

import com.eminetekcan.BlogApp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
