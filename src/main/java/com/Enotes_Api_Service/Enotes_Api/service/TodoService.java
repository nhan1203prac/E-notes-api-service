package com.Enotes_Api_Service.Enotes_Api.service;

import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;

import java.util.List;

public interface TodoService {
    public Boolean saveTodod(TodoDto todoDto) throws ResourceNotfoundException;
    public TodoDto getTodoById(Integer id) throws ResourceNotfoundException;
    public List<TodoDto> getTodoByUser();
}
