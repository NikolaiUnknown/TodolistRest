package com.todo.todolist.controller;

import com.todo.todolist.DTO.LoginRequest;
import com.todo.todolist.DTO.RegisterRequest;
import com.todo.todolist.security.JwtCore;
import com.todo.todolist.security.UserDetailsImpl;
import com.todo.todolist.service.user.RegService;
import com.todo.todolist.util.UserErrorResponse;
import com.todo.todolist.util.UserNotCreatedException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final RegService regService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, RegService regService, ModelMapper mapper, JwtCore jwtCore) {
        this.authenticationManager = authenticationManager;
        this.regService = regService;
        this.jwtCore = jwtCore;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
        }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtCore.generateToken(authentication);
            return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }
        regService.register(registerRequest);
        return ResponseEntity.ok("User " + registerRequest.getLogin() + " is register!");
    }
    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleExeption(UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
