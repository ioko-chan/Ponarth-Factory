package com.solomennicova.AuthTemplate.Security;

import com.solomennicova.AuthTemplate.Dto.Authentication.UserDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserInfoDto;
import com.solomennicova.AuthTemplate.Dto.Utils.MappingUtilsUser;
import com.solomennicova.AuthTemplate.Entity.User;
import com.solomennicova.AuthTemplate.Exception.IncorrectUsernameOrPasswordException;
import com.solomennicova.AuthTemplate.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final MappingUtilsUser mappingUtils;

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder encoder, MappingUtilsUser mappingUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mappingUtils = mappingUtils;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetailsImpl(user);
    }

    public User loadUserByUsernameAndPassword(String username, String password) throws IncorrectUsernameOrPasswordException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new IncorrectUsernameOrPasswordException("Invalid login");
        }
        if(!encoder.matches(password, user.getPassword())){
            throw new IncorrectUsernameOrPasswordException("Invalid password");
        }
        return user;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Transactional
    public List<UserInfoDto> loadAllUserDto(){

        List<User> users = userRepository.findAll();
        List<UserInfoDto> usersDto = new ArrayList<>();

        for (User user : users){
            UserInfoDto userDto = mappingUtils.UserToUserInfoDto(user);
            if(user.isEnabled()) {
                usersDto.add(userDto);
            }
        }
        return usersDto;
    }

    public List<User> loadAllUser(){

        return userRepository.findAll();
    }

    public boolean userExistByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public void deleteUser(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        user.setEnabled(false);
        userRepository.save(user);
    }

    public void updateUser(User userUpdate){
        User user = userRepository.findById(userUpdate.getId()).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        if(userUpdate.getUsername() != null && !userUpdate.getUsername().isEmpty()) {
            user.setUsername(userUpdate.getUsername());
        }
        if(userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(userUpdate.getPassword()));
        }
        if(userUpdate.getEmail() != null && !userUpdate.getEmail().isEmpty()) {
            user.setPassword(userUpdate.getEmail());
        }
        userRepository.save(user);
    }
}
