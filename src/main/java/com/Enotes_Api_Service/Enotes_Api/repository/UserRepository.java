package com.Enotes_Api_Service.Enotes_Api.repository;

import com.Enotes_Api_Service.Enotes_Api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Boolean existsByEmail(String email);

    User findByEmail(String email);
}
