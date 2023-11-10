package com.todo.todolist.util;

public class UserNotCreatedException extends  RuntimeException{
    public UserNotCreatedException(String msg){
        super(msg);
    }
}
