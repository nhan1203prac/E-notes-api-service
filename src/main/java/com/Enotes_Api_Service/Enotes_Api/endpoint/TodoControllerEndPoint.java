package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.Enotes_Api_Service.Enotes_Api.Utils.Constant.ROLE_USER;

@Tag(name = "Todos ", description = "All the Todos Operation APIs")
@RequestMapping("/api/v1/todo")
public interface TodoControllerEndPoint {

    @Operation(summary = "Saved todo", tags = {"Todos"})
    @PostMapping("/")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotfoundException;

    @Operation(summary = "Get todo By id", tags = {"Todos"})
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotfoundException;

    @Operation(summary = "Get All User's Todos", tags = {"Todos"})
    @GetMapping("/list")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllTodoUser() throws ResourceNotfoundException;
}
