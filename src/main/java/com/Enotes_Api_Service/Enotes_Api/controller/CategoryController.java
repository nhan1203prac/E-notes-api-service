package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import com.Enotes_Api_Service.Enotes_Api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto category) {
        Boolean saveCategory = categoryService.saveCategory(category);
        if (saveCategory) {
            return CommonUtil.createBuildResponseMessage("saved success", HttpStatus.CREATED);
//            return new ResponseEntity<>("Category created", HttpStatus.CREATED);

        }
        return CommonUtil.createErrorResponseMessage("save failed", HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> categoryList = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.noContent().build();
        }else{
//            return new ResponseEntity<>(categoryList, HttpStatus.OK);
            return CommonUtil.createBuildResponse(categoryList, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> categoryList = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categoryList)){
            return ResponseEntity.noContent().build();
        }else{
//            return new ResponseEntity<>(categoryList, HttpStatus.OK);
            return CommonUtil.createBuildResponse(categoryList, HttpStatus.OK);

        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> getCategoryDetailById(@PathVariable("id") Integer id) throws ResourceNotfoundException {
        CategoryDto response = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(response)){
//            return new ResponseEntity<>("Category not found with id "+id,HttpStatus.NOT_FOUND);
            return CommonUtil.createErrorResponseMessage("Category not found with id "+id, HttpStatus.NOT_FOUND);
        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
        return CommonUtil.createBuildResponse(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> DeleteCategoryById(@PathVariable("id") Integer id) {
        Boolean deleted = categoryService.deleteCategoryById(id);
        if(deleted){
//            return new ResponseEntity<>(HttpStatus.OK);
            return CommonUtil.createBuildResponseMessage("deleted success", HttpStatus.OK);
        }
//        return new ResponseEntity<>("Category not found with id "+id,HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage("delete failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
