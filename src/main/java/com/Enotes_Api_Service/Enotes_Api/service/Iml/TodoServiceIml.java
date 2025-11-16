package com.Enotes_Api_Service.Enotes_Api.service.Iml;

import com.Enotes_Api_Service.Enotes_Api.Enum.TodoStatus;
import com.Enotes_Api_Service.Enotes_Api.Utils.Validation;
import com.Enotes_Api_Service.Enotes_Api.dto.TodoDto;
import com.Enotes_Api_Service.Enotes_Api.entity.Todo;
import com.Enotes_Api_Service.Enotes_Api.exception.ResourceNotfoundException;
import com.Enotes_Api_Service.Enotes_Api.repository.TodoRepository;
import com.Enotes_Api_Service.Enotes_Api.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceIml implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validation validation;

    @Override
    public Boolean saveTodod(TodoDto todoDto) throws ResourceNotfoundException {
        validation.todoValidation(todoDto);
        Todo todo = modelMapper.map(todoDto,Todo.class);
        todo.setStatusId(todoDto.getStatus().getId());
        Todo savedTodo = todoRepository.save(todo);
        if(!ObjectUtils.isEmpty(savedTodo)){
            return true;
        }
        return false;
    }

    @Override
    public TodoDto getTodoById(Integer id) throws ResourceNotfoundException {
        Todo todo = todoRepository.findById(id).orElseThrow(()->
                new ResourceNotfoundException("Todo not found, Id invalid"));
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        setStatus(todoDto,todo);
        return todoDto;
    }

    private void setStatus(TodoDto dto, Todo todo){
        for(TodoStatus ts : TodoStatus.values()){
            if(ts.getId().equals(todo.getStatusId())){
                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(ts.getId())
                        .name(ts.getName())
                        .build();
                dto.setStatus(statusDto);
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {
        Integer userId = 1;
        List<Todo> todos = todoRepository.findByCreatedBy(userId);
        return todos.stream().map(td->modelMapper.map(td, TodoDto.class)).toList();
    }
}
