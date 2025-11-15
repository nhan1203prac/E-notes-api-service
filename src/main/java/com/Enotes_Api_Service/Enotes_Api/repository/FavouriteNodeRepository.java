package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteNodeRepository extends JpaRepository<FavouriteNote, Integer> {

    List<FavouriteNote> findByUserId(Integer userId);
}
