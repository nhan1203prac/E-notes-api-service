package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.repository.CategoryRepository;
import com.Enotes_Api_Service.Enotes_Api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceIml implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedOn(LocalDateTime.now());
        category.setCreatedBy(1);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList;
    }
}
