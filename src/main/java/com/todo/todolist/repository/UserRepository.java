package com.todo.todolist.repository;

import com.todo.todolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByLogin(String login);

}
