package com.solomennicova.AuthTemplate.Service;

import com.solomennicova.AuthTemplate.Dto.Authentication.AuthenticationDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.TokensDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtils;
import com.solomennicova.AuthTemplate.Entity.User;
import com.solomennicova.AuthTemplate.Exception.IncorrectUsernameOrPasswordException;
import com.solomennicova.AuthTemplate.Exception.RoleNotFoundException;
import com.solomennicova.AuthTemplate.Exception.UserAlreadyExistsException;
import com.solomennicova.AuthTemplate.Exception.UserDeletedException;
import com.solomennicova.AuthTemplate.Security.AuthProvider;
import com.solomennicova.AuthTemplate.Security.UserDetailsServiceImpl;
import com.solomennicova.AuthTemplate.Security.Utils.GeneratorPasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.xml.bind.ValidationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@Service
public class AuthService {

    private final PasswordEncoder encoder;

    private final AuthProvider authProvider;

    private final UserDetailsServiceImpl userDetailsService;

    private final MappingUtils mappingUtils;

    private final EmailService emailService;

    private final GeneratorPasswordUtils generatorPasswordUtils;

    public AuthService(PasswordEncoder passwordEncoder, AuthProvider authProvider, UserDetailsServiceImpl userDetailsService, MappingUtils mappingUtils, EmailService emailService, GeneratorPasswordUtils generatorPasswordUtils){
        this.encoder = passwordEncoder;
        this.authProvider = authProvider;
        this.userDetailsService = userDetailsService;
        this.mappingUtils = mappingUtils;
        this.emailService = emailService;
        this.generatorPasswordUtils = generatorPasswordUtils;


        //saveAdminUser();
    }

    public void saveAdminUser() throws ValidationException, MessagingException, RoleNotFoundException, UserAlreadyExistsException {
        UserDto userDto = new UserDto();
        userDto.setUsername("Kvotua");
        userDto.setPassword("");
        userDto.setEmail("solomennicova555@gmail.com");
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        userDto.setRoles(roles);
        regUser(userDto);
    }

    @Async
    public void regUser(UserDto userDto) throws RoleNotFoundException, ValidationException, UserAlreadyExistsException, MessagingException {
        if(userDetailsService.userExistByUsername(userDto.getUsername())){
            throw new UserAlreadyExistsException("User is exist");
        }
        User user = mappingUtils.UserDtoToUser(userDto);
        if(user.getPassword().isEmpty()) {
            String password = generatorPasswordUtils.generatePassword();
            emailService.sendAuth(userDto.getEmail(), "Authorization info", userDto.getUsername(), password);
            user.setPassword(encoder.encode(password));
        }
        else {
            emailService.sendAuth(userDto.getEmail(), "Authorization info", userDto.getUsername(), user.getPassword());
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setEnabled(true);
        Date nowDate = Date.from(ZonedDateTime.now().toInstant());
        System.out.println(nowDate);
        user.setDateRegistration(nowDate);
        User userSave = userDetailsService.saveUser(user);
    }

    @Transactional
    @Async
    public CompletableFuture<TokensDto> loginUser(AuthenticationDto authenticationDto) throws IncorrectUsernameOrPasswordException, UserDeletedException {
        User user = userDetailsService.loadUserByUsernameAndPassword(authenticationDto.getUsername(), authenticationDto.getPassword());
        if(!user.isEnabled()){
            throw new UserDeletedException("User deleted");
        }

        return CompletableFuture.completedFuture(authProvider.generateTokens(user));
    }

}
