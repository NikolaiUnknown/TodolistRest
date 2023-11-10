package com.todo.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
@Data
@Entity
@Table(name = "task")
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private Date date;
    @Column(name = "owner")
    private long owner;

    public Task(String name, Date date, long owner) {
        this.name = name;
        this.date = date;
        this.owner = owner;
    }
}
