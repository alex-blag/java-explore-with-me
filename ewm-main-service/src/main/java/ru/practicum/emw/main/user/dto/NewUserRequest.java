package ru.practicum.emw.main.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}
