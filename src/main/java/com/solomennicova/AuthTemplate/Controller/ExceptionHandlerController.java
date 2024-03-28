package com.solomennicova.AuthTemplate.Controller;

import com.solomennicova.AuthTemplate.Dto.Exception.ErrorDto;
import com.solomennicova.AuthTemplate.Exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController

public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ IncorrectTokenException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleIncorrectTokenException(IncorrectTokenException ex) {

        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler({ IncorrectUsernameOrPasswordException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleIncorrectUsernameOrPasswordException(IncorrectUsernameOrPasswordException ex) {

        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler({ RoleNotFoundException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleRoleNotFoundException(RoleNotFoundException ex) {

        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler({ UserAlreadyExistsException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {

        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(ex.getClass().getSimpleName(), ex.getMessage()));
    }

    @ExceptionHandler({ UserDeletedException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleUserAlreadyExistsException(UserDeletedException ex) {

        logger.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(ex.getClass().getSimpleName(), ex.getMessage()));
    }

}
