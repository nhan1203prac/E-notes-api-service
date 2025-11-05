package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.Utils.Validation;
import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.exception.ExistDataException;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.exception.ValidationException;
import com.Enotes_Api_Service.Enotes_Api.repository.CategoryRepository;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import com.Enotes_Api_Service.Enotes_Api.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceIml implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validation validation;


    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
//        validation field
        validation.categoryValidation(categoryDto);
//        Check exist
        boolean isExist = categoryRepository.existsByName(categoryDto.getName());
        if(isExist){
            throw new ExistDataException("Category already exists");
        }
        Category category = modelMapper.map(categoryDto, Category.class);
        if(ObjectUtils.isEmpty(categoryDto.getId())){
            category.setIsDeleted(false);
//            category.setCreatedOn(LocalDateTime.now());
//            category.setCreatedBy(1);

        }else{
            updateCategory(category);
        }
        Category savedCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(savedCategory)){
            return false;
        }

        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> existCategory = categoryRepository.findById(category.getId());
        if(existCategory.isPresent()){
            Category existingCategory = existCategory.get();

            category.setCreatedBy(existingCategory.getCreatedBy());
            category.setCreatedOn(existingCategory.getCreatedOn());
            category.setUpdatedOn(LocalDateTime.now());
//            category.setUpdatedBy(1);
            category.setIsDeleted(existingCategory.getIsDeleted());
        }
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDto> listDto = categoryList.stream()
                .map(cat->modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());
        return listDto;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categoryList = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categoryList.stream()
                .map(cat -> modelMapper.map(cat, CategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws ResourceNotfoundException {
        Category cate = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(()->new ResourceNotfoundException("Category not found with id "+id));
        if(!ObjectUtils.isEmpty(cate)) {
            return modelMapper.map(cate, CategoryDto.class);
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
