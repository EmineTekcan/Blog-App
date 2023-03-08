package com.eminetekcan.BlogApp.controller;

import com.eminetekcan.BlogApp.payload.ApiResponse;
import com.eminetekcan.BlogApp.payload.CategoryDto;
import com.eminetekcan.BlogApp.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@Valid @PathVariable Integer categoryId){
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId),HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable Integer categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto ){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId),HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@Valid @PathVariable Integer categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse("Category successfully deleted",true),HttpStatus.OK);
    }
}
