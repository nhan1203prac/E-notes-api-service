package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    public Category saveCategory(CategoryDto category);
    public List<Category> getAllCategory();

    List<CategoryResponse> getActiveCategory();
}
