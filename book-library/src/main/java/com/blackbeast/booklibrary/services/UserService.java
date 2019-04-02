package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Role;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.dto.UserDto;
import com.blackbeast.booklibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser(String username, String password) {
        if(username != null && password != null) {
            PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

            User user = new User(username, pe.encode(password));
            userRepository.addUser(user);
        }
    }

    public void addRoleToUser(String username, String roleName){
        if(username != null && roleName != null) {
            Role role = new Role(roleName);
            userRepository.addRoleToUser(username, role);
        }
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    public User getLoggedUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            String username = auth.getName();
            return getUser(username);
        }else
            return null;
    }

    public UserDto convert(User user){
        if(user == null)
            return null;

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());
        return userDto;
    }
}
