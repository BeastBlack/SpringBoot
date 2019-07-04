package com.blackbeast.booklibrary.services;

import com.blackbeast.booklibrary.domain.Role;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.dto.UserDto;
import com.blackbeast.booklibrary.repository.RoleRepository;
import com.blackbeast.booklibrary.repository.UserRepository;
import com.blackbeast.booklibrary.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.font.TrueTypeFont;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    RoleRepository roleRepository;

    public void createUser(String username, String password) {
        if(username != null && password != null) {
            PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();

            User user = new User(username, pe.encode(password));
            userRepository.addUser(user);
        }
    }

    public void saveUser(User user) {
        PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(pe.encode(user.getPassword()));
        user.setEnabled(true);
        userRepositoryJpa.save(user);

        Role role = new Role();
        role.setUser(user);
        role.setName("USER");

        roleRepository.save(role);
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

    public UserDto convert(User user) {
        if(user == null)
            return null;

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFirstName() + " " + user.getLastName());

        return userDto;
    }

    public List<User> getAll(){
        return userRepositoryJpa.findAll();
    }
}
