package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.CategoryDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.ROLE_ADMIN;
import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.ROLE_ADMIN_USER;
@Tag(name = "Category", description = "All the Category operation APIS")
@RequestMapping("/api/v1/category")
public interface CategoryControllerEndPoint {
    @Operation(summary = "Save Category Endpoint", tags = {"Category"})
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto category);

    @Operation(summary = "Get ALL Category Endpoint", tags = {"Category"})
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get Active Category Endpoint", tags = {"Category"})
    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category By ID Endpoint", tags = {"Category"})
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getCategoryDetailById(@PathVariable("id") Integer id)
            throws ResourceNotfoundException;

    @Operation(summary = "Delete Category Endpoint", tags = {"Category"})
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> DeleteCategoryById(@PathVariable("id") Integer id);
}
