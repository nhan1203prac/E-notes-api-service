package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository  extends JpaRepository<Todo,Integer> {
    List<Todo> findByCreatedBy(Integer createdBy);
}
