package com.todo.todolist.service.task;

import com.todo.todolist.DTO.TaskResponse;
import com.todo.todolist.entity.Task;
import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.TaskValueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadTaskService {

    private final TaskRepository taskRepository;
    private final TaskValueRepository taskValueRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReadTaskService(TaskRepository taskRepository, TaskValueRepository taskValueRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.taskValueRepository = taskValueRepository;
        this.modelMapper = modelMapper;
    }
    public TaskResponse getFullTask(long id){
        TaskResponse taskResponse = null;
        taskResponse = modelMapper.map(taskRepository.findTaskById(id),TaskResponse.class);
        taskResponse.setDescription(taskValueRepository.findById(id+"mongoid").get().getDescription());
        return taskResponse;
    }
    public List<Task> getAllTasks(long owner){
        return taskRepository.findTaskByOwner(owner);
    }

    public Optional<Task> getTask(long id){
        return taskRepository.findTaskById(id);
    }

}
