package com.eminetekcan.BlogApp.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostResponse {

    private  List<PostDto> postDtos;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalPage;
    private Integer totalElement;
    private boolean lastPage;
}
