package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIsActiveTrueAndIsDeletedFalse();
    Optional<Category> findByIdAndIsDeletedFalse(Integer id);
    Optional<Category> findById(Integer id);
    List<Category> findByIsDeletedFalse();
}
