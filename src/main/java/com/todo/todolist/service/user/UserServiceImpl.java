package com.todo.todolist.service.user;

import com.todo.todolist.entity.User;
import com.todo.todolist.repository.UserRepository;
import com.todo.todolist.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(username));
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new UserDetailsImpl(user.get());
    }
}
