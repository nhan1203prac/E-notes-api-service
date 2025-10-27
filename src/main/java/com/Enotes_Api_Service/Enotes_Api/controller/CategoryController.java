package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import com.Enotes_Api_Service.Enotes_Api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category) {
        try {
            Category saveCategory = categoryService.saveCategory(category);
            return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> categoryList = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.noContent().build();
        }else{
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryDetailById(@PathVariable("id") Integer id) {
        CategoryDto response = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(response)){
            return new ResponseEntity<>("Category not found with id "+id,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteCategoryById(@PathVariable("id") Integer id) {
        Boolean deleted = categoryService.deleteCategoryById(id);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not found with id "+id,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
