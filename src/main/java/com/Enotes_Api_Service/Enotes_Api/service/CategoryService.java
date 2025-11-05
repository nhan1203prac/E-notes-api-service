package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(CategoryDto category);
    public List<CategoryDto> getAllCategory();

    List<CategoryResponse> getActiveCategory();

    CategoryDto getCategoryById(Integer id) throws ResourceNotfoundException;
    Boolean deleteCategoryById(Integer id);
}
