package com.todo.todolist.service.task;

import com.todo.todolist.DTO.TaskRequest;
import com.todo.todolist.entity.Task;
import com.todo.todolist.entity.TaskValue;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.TaskValueRepository;
import com.todo.todolist.security.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterService {
    private final TaskRepository taskRepository;
    private final TaskValueRepository taskValueRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public RegisterService(TaskRepository taskRepository, TaskValueRepository taskValueRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.taskValueRepository = taskValueRepository;
        this.modelMapper = modelMapper;
    }
    public void register(TaskRequest task, Long userid){

        Task converted = convertToTask(task);
        converted.setDate(new Date());
        converted.setOwner(userid);
        taskRepository.save(converted);
        taskValueRepository.save(new TaskValue(converted.getId()+"mongoid",task.getDescription()));
    }

    private Task convertToTask(TaskRequest task) {
        return modelMapper.map(task,Task.class);
    }
}
