package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
