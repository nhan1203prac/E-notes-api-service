package com.Enotes_Api_Service.Enotes_Api.endpoint;

import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/todo")
public interface TodoControllerEndPoint {

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotfoundException;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotfoundException;


    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<?> getAllTodoUser() throws ResourceNotfoundException;
}
