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

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    RoleRepository roleRepository;

    public boolean userExists(String username) {
        return getUser(username) != null;
    }

    public void updateUser(User user) {
        User fullUser = getUser(user.getUsername());
        fullUser.setFirstName(user.getFirstName());
        fullUser.setLastName(user.getLastName());

        PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        fullUser.setPassword(pe.encode(user.getPassword()));
        userRepository.saveUser(fullUser);
    }

    public void registerUser(User user) {
        PasswordEncoder pe = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(pe.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.saveUser(user);

        addRoleToUser(user.getId(), "USER");
    }

    public void addRoleToUser(Integer userId, String roleName){
        Role role = new Role();
        role.setUser(getUser(userId));
        role.setName(roleName);
        roleRepository.save(role);
    }

    public void removeRoleFromUser(Integer userId, String roleName){
        roleRepository.deleteByUserIdAndName(userId, roleName);
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

    public Boolean hasRoles(Integer userId, String roleName) {
        User user = userRepositoryJpa.getOne(userId);

        if(user != null)
            for(Role role : user.getRoles())
                if(role.getName().equals(roleName))
                    return true;

        return false;
    }

    public User getUser(Integer userId) {
        return userRepositoryJpa.getOne(userId);
    }
}
