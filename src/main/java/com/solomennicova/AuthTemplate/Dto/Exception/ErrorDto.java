package com.solomennicova.AuthTemplate.Dto.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String nameError;

    private String errorMessage;
}
