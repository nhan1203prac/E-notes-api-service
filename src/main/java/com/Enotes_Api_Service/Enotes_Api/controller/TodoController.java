package com.Enotes_Api_Service.Enotes_Api.controller;

import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.handler.CommonUtil;
import com.Enotes_Api_Service.Enotes_Api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) throws ResourceNotfoundException {
        Boolean saveTodo = todoService.saveTodod(todoDto);
        if(saveTodo){
            return CommonUtil.createBuildResponseMessage("Todo saved success", HttpStatus.CREATED);
        }
        else {
            return CommonUtil.createErrorResponseMessage("Todo not save", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws ResourceNotfoundException {
        TodoDto todoDto = todoService.getTodoById(id);
        return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoUser() throws ResourceNotfoundException {
        List<TodoDto> todoDto = todoService.getTodoByUser();
        if(CollectionUtils.isEmpty(todoDto)){
            return ResponseEntity.noContent().build();
        }
        return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
    }


}
