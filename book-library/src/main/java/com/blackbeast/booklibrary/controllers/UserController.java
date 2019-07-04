package com.blackbeast.booklibrary.controllers;

import com.blackbeast.booklibrary.domain.Book;
import com.blackbeast.booklibrary.domain.User;
import com.blackbeast.booklibrary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    private Boolean editMode = Boolean.FALSE;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute(new User());
        return "user";
    }

    @RequestMapping(value = "/register/ok", method = RequestMethod.GET)
    public String registerSuccess(Model model){
        model.addAttribute("showMessage", "REGISTER_OK");
        model.addAttribute(new User());
        return "user";
    }

    @RequestMapping(value = "/register/exist", method = RequestMethod.GET)
    public String registerFail(Model model){
        model.addAttribute("showMessage", "REGISTER_EXISTS");
        model.addAttribute(new User());
        return "user";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveBook(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user";
        }else {
            if(this.editMode) {
                userService.updateUser(user);
                this.editMode = Boolean.FALSE;
                return "redirect:/books";
            } else
                if(userService.userExists(user.getUsername()))
                    return "redirect:/register/exist";
                else {
                    userService.registerUser(user);
                    return "redirect:/register/ok";
                }
        }
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String showProfile(Model model){
        User user = userService.getLoggedUser();
        model.addAttribute(user);

        this.editMode = Boolean.TRUE;

        return "user";
    }

    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String showUsers(Model model){
        List<User> users = userService.getAll();
        User user = userService.getLoggedUser();

        model.addAttribute("users", users);
        model.addAttribute("loggedUser", user);

        return "users";
    }

    @RequestMapping(value = "/admin/users/permissions/{id}", method = RequestMethod.GET)
    public String showPermission(@PathVariable("id") Integer id, Model model){
        User user = userService.getUser(id);

        model.addAttribute("user", user);
        model.addAttribute("isAdmin", userService.hasRoles(id, "ADMIN"));
        model.addAttribute("isUser", userService.hasRoles(id, "USER"));
        model.addAttribute("isDev", userService.hasRoles(id, "DEV"));

        return "permissions";
    }

    @RequestMapping(value = "/admin/users/permissions/{operation}/{roleName}/{id}", method = RequestMethod.GET)
    public String managePermission(@PathVariable("id") Integer userId, @PathVariable("roleName") String roleName,
                                  @PathVariable("operation") String operation) {
        if(operation.equals("grant"))
            userService.addRoleToUser(userId, roleName.toUpperCase());

        if(operation.equals("revoke"))
            userService.removeRoleFromUser(userId, roleName.toUpperCase());


        return "redirect:/admin/users/permissions/" + userId;
    }
}
