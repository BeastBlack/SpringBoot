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
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user";
        }else
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


}
