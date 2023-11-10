package com.todo.todolist.repository;

import com.todo.todolist.entity.TaskValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskValueRepository extends MongoRepository<TaskValue,String> {
    Optional<TaskValue> findById(String taskid);
    void deleteById(String taskid);

}
