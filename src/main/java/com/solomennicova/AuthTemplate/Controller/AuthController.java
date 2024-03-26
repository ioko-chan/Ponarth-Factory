package com.solomennicova.AuthTemplate.Controller;

import com.solomennicova.AuthTemplate.Dto.Authentication.AuthenticationDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.PayloadDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.TokensDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserDto;
import com.solomennicova.AuthTemplate.Dto.Exception.ErrorDto;
import com.solomennicova.AuthTemplate.Exception.*;
import com.solomennicova.AuthTemplate.Security.AuthProvider;
import com.solomennicova.AuthTemplate.Service.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(path="/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthProvider authProvider;

    public AuthController(AuthService authService, AuthProvider authProvider) {
        this.authService = authService;
        this.authProvider = authProvider;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error on user create", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PostMapping("/registration")
    public ResponseEntity<TokensDto> regUser(@RequestBody @Validated UserDto userDto) throws ExecutionException, InterruptedException, RoleNotFoundException, ValidationException, UserAlreadyExistsException {
        return ResponseEntity.ok(authService.regUser(userDto).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Error login", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @PostMapping("/login")
    public ResponseEntity<TokensDto> loginUser(@RequestBody @Validated AuthenticationDto authUser) throws IncorrectUsernameOrPasswordException, ExecutionException, InterruptedException, UserDeletedException {
        return ResponseEntity.ok(authService.loginUser(authUser).get());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/validateToken")
    public ResponseEntity<PayloadDto> validateToken(HttpServletRequest request) throws IncorrectTokenException, UserDeletedException {
        return ResponseEntity.ok(authProvider.getPayload(request));
    }

}
