package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.repository.CategoryRepository;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import com.Enotes_Api_Service.Enotes_Api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceIml implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Category saveCategory(CategoryDto categoryDto) {
//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsAvtive(categoryDto.getIsActive());

        Category category = modelMapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedOn(LocalDateTime.now());
        category.setCreatedBy(1);

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> listDto = categoryList.stream()
                .map(cat->modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categoryList = categoryRepository.findByIsActiveTrue();
        return categoryList.stream()
                .map(cat -> modelMapper.map(cat, CategoryResponse.class))
                .collect(Collectors.toList());
    }
}
