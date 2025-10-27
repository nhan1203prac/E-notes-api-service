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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceIml implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryService categoryService;

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
        List<Category> categoryList = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categoryList.stream()
                .map(cat -> modelMapper.map(cat, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Optional<Category> cate = categoryRepository.findByIdAndIsDeletedFalse(id);
        if(cate.isPresent()) {
            return modelMapper.map(cate.get(), CategoryDto.class);
        }
        return null;
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setIsDeleted(true);
            category.setUpdatedOn(LocalDateTime.now());
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
