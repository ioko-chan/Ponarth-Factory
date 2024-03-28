package com.solomennicova.AuthTemplate.Dto.Authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDto {

    @NotEmpty(message = "IDs не должно быть пустым")
    private Long id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 15, message = "Имя должно быть от 2 до 25 символов длиной")
    private String username;

    @Email
    @NotEmpty(message = "Email не должно быть пустым")
    private String email;

    @NotEmpty(message = "Пароль не должно быть пустым")
    @Size(min = 2, max = 15, message = "Пароль должен быть от 2 до 25 символов длиной")
    private String password;
}
