package com.todo.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String login;
    private String password;
}
