package com.solomennicova.AuthTemplate.Controller;

import com.solomennicova.AuthTemplate.Dto.Authentication.UserDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserInfoDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserUpdateDto;
import com.solomennicova.AuthTemplate.Dto.Exception.ErrorDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsUser;
import com.solomennicova.AuthTemplate.Exception.DontImageException;
import com.solomennicova.AuthTemplate.Exception.ImageDontLoad;
import com.solomennicova.AuthTemplate.Exception.RoleNotFoundException;
import com.solomennicova.AuthTemplate.Security.UserDetailsServiceImpl;
import com.solomennicova.AuthTemplate.Service.StoreService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    private final MappingUtilsUser mappingUtils;

    private final StoreService storeService;

    public UserController(UserDetailsServiceImpl userDetailsService, MappingUtilsUser mappingUtils, StoreService storeService) {
        this.userDetailsService = userDetailsService;
        this.mappingUtils = mappingUtils;
        this.storeService = storeService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all")
    public ResponseEntity<List<UserInfoDto>> getUsersInfo() {
        return ResponseEntity.ok(userDetailsService.loadAllUserDto());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) throws RoleNotFoundException {
        userDetailsService.deleteUser(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(schema = @Schema(implementation = ErrorDto.class))
            })
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Validated UserUpdateDto userDto) throws RoleNotFoundException {
        return ResponseEntity.ok(userDetailsService.updateUser(mappingUtils.UserUpdateDtoToUser(userDto)));
    }

    @PostMapping(path ="/load", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> loadImage(@RequestParam("name") String name,
                                            @RequestPart("file") MultipartFile file) throws DontImageException, ImageDontLoad {
        return ResponseEntity.ok(storeService.loadImage(file, name));
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

}
