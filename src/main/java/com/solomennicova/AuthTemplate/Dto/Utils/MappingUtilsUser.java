package com.solomennicova.AuthTemplate.Dto.Utils;

import com.solomennicova.AuthTemplate.Dto.Authentication.UserDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserInfoDto;
import com.solomennicova.AuthTemplate.Dto.Authentication.UserUpdateDto;
import com.solomennicova.AuthTemplate.Entity.Role;
import com.solomennicova.AuthTemplate.Entity.User;
import com.solomennicova.AuthTemplate.Exception.RoleNotFoundException;
import com.solomennicova.AuthTemplate.Repository.RoleRepository;
import com.solomennicova.AuthTemplate.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MappingUtilsUser {

    private final RoleRepository roleRepository;

    public MappingUtilsUser(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public User UserUpdateDtoToUser(UserUpdateDto userDto){

        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public User UserDtoToUser(UserDto userDto) throws RoleNotFoundException {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        for (String role : userDto.getRoles()) {
            Role newRole = roleRepository.findByName(role);
            if (newRole == null) {
                throw new RoleNotFoundException("Role not found");
            }
            user.addRole(newRole);
        }
        return user;
    }

    public UserDto UserToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());

        Set<String> rolesDto = new HashSet<>();

        for(Role role : user.getRoles()){
            rolesDto.add(role.getName());
        }
        userDto.setRoles(rolesDto);
        return userDto;
    }

    public UserInfoDto UserToUserInfoDto(User user) {
        UserInfoDto userDto = new UserInfoDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        Set<String> rolesDto = new HashSet<>();

        for(Role role : user.getRoles()){
            rolesDto.add(role.getName());
        }
        userDto.setRoles(rolesDto);
        return userDto;
    }
}
