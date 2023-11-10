package com.todo.todolist.service.task;

import com.todo.todolist.repository.TaskRepository;
import com.todo.todolist.repository.TaskValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {


    private final TaskRepository taskRepository;
    private final TaskValueRepository valueRepository;

    @Autowired
    public DeleteService(TaskRepository taskRepository, TaskValueRepository valueRepository) {
        this.taskRepository = taskRepository;
        this.valueRepository = valueRepository;
    }

    public void delete(long id){
        taskRepository.deleteById(id);
        valueRepository.deleteById(id+"mongoid");
    }
}
