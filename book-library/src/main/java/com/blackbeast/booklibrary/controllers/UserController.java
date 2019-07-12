package com.blackbeast.booklibrary.controllers;

import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String openRegisterForm(Model model) {
        model.addAttribute("user", new User());

        return "user";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(Model model, @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user";
        }else
            if(user.getId() != null) { //edycja istniejącego użytkownika
                userService.saveUser(user);
                model.addAttribute("user", new User());
                model.addAttribute("showMessage", "EDIT_OK");
                return "user";
            }else //nowy użytkownik
                if(userService.getUser(user.getUsername()) != null) {
                    model.addAttribute("user", user);
                    model.addAttribute("showMessage", "REGISTER_EXISTS");
                    return "user";
                } else {
                    userService.saveUser(user);
                    model.addAttribute("user", new User());
                    model.addAttribute("showMessage", "REGISTER_OK");
                    return "user";
                }
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String editUser(Model model) {
        User user = userService.getLoggedUser();

        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String showUsers(Model model) {
        List<User> users = userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("loggedUser", userService.getLoggedUser());
        return "users";
    }


}
