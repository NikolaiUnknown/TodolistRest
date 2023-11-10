package com.todo.todolist.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @Size(min = 4,max = 12,message = "login must be between 4 and 12!")
    private String login;
    @Min(value = 4,message = "password must be greater than 4!")
    private String password;
    @NotNull
    private String name;
}
