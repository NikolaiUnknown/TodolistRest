package com.todo.todolist.util;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
