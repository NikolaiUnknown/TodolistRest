package com.todo.todolist.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "values")
@AllArgsConstructor
@NoArgsConstructor
public class TaskValue {
    @Id
    private String id;
    private String description;

}
