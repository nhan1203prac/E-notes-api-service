package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.Category;
import com.Enotes_Api_Service.Enotes_Api.response.CategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByIsActiveTrue();
}
