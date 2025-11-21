package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    Page<Notes> findByCreatedBy(Integer createdBy, Pageable pageable);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer createdBy, Pageable pageable);
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer createdBy);

    List<Notes> findAllByIsDeletedAndDeletedOnBefore(boolean b, Instant cutOffDate);
    @Query("select n from Notes n where Lower(n.title) like lower(concat('%',:keyword,'%')) " +
            "or Lower(n.description) like lower(concat('%',:keyword,'%')) " +
            "or Lower(n.category.name) like lower(concat('%',:keyword,'%')) " +
            "and n.isDeleted=false " +
            "and n.createdBy=:userId")
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userId") Integer userId, Pageable pageable);
}
