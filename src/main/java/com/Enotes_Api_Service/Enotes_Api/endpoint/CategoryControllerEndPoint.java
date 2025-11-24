package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.ROLE_ADMIN;
import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.ROLE_ADMIN_USER;

@RequestMapping("/api/v1/category")
public interface CategoryControllerEndPoint {
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)

    public ResponseEntity<?> getCategoryDetailById(@PathVariable("id") Integer id)
            throws ResourceNotfoundException;

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> DeleteCategoryById(@PathVariable("id") Integer id);
}
