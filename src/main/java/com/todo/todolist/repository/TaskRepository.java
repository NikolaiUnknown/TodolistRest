package com.todo.todolist.repository;

import com.todo.todolist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findTaskByOwner(long id);


    Optional<Task> findTaskById(Long id);

    void deleteById(long id);

}
