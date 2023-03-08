package com.eminetekcan.BlogApp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Category title must be min of 4 characters !!")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Category description must be max of 10 characters !!")
    private String categoryDescription;
}
