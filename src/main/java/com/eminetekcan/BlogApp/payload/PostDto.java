package com.eminetekcan.BlogApp.payload;

import com.eminetekcan.BlogApp.entity.Category;
import com.eminetekcan.BlogApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    private String postTitle;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDto category;

    private UserDto user;
}
