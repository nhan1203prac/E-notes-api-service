package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    Page<Notes> findByCreatedBy(Integer createdBy, Pageable pageable);
}
