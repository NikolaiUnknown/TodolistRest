package com.todo.todolist.controller;

import com.todo.todolist.DTO.TaskRequest;
import com.todo.todolist.DTO.TaskResponse;
import com.todo.todolist.entity.Task;
import com.todo.todolist.security.UserDetailsImpl;
import com.todo.todolist.service.task.DeleteService;
import com.todo.todolist.service.task.ReadTaskService;
import com.todo.todolist.service.task.RegisterService;
import com.todo.todolist.util.TaskErrorResponse;
import com.todo.todolist.util.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final ReadTaskService readTaskService;
    private final RegisterService registerService;
    private final DeleteService deleteService;
    @Autowired
    public TaskController(ReadTaskService readTaskService, RegisterService registerService, DeleteService deleteService) {
        this.readTaskService = readTaskService;
        this.registerService = registerService;
        this.deleteService = deleteService;
    }


    @GetMapping("/tasks")
    public List<Task> tasks(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return readTaskService.getAllTasks(userDetails.getUser().getId());
    }

    @GetMapping("/task/{id}")
    public TaskResponse task(@PathVariable("id") long id, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if ((readTaskService.getTask(id).isEmpty()) || (readTaskService.getTask(id).get().getOwner() != userDetails.getUser().getId())){
            throw new TaskNotFoundException("Task with id " + id + " wasn't found!");
        }
        return readTaskService.getFullTask(id);
    }
    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") long id, Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if ((readTaskService.getTask(id).isEmpty()) || (readTaskService.getTask(id).get().getOwner() != userDetails.getUser().getId())){
            return ResponseEntity.badRequest().body("Task with id " + id + " wasn't found!");
        }
        deleteService.delete(id);
        return ResponseEntity.ok().body("Task with id " + id + " was deleted!");
    }
    @PostMapping("/task/reg")
    public ResponseEntity<?> reg(Authentication authentication,@RequestBody TaskRequest request){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        registerService.register(request, userDetails.getUser().getId());
        return ResponseEntity.ok("Task with name " + request.getName() + " is created");
    }
    @ExceptionHandler
    private ResponseEntity<TaskErrorResponse> handleExeption(TaskNotFoundException e){
        TaskErrorResponse response = new TaskErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
