package com.deroahe.jasypt.controller;

import com.deroahe.jasypt.model.User;
import com.deroahe.jasypt.service.EncryptionService;
import com.deroahe.jasypt.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserUiController {

    private UserService userService;

    @Resource
    private EncryptionService encryptionService;

    @Autowired
    public UserUiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users-ui")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/delete-user/{id}")
    public String removeUser(@PathVariable("id") String id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping(value = {"/edit-add-user/{id}", "/edit-add-user"})
    public String editUser(@PathVariable("id") Optional<String> id, Model model) {
        User user;
        if (id.isPresent()) {
            user = userService.findUserById(id.get()).get();
            user.setPassword(encryptionService.decrypt(user.getPassword()));
        } else {
            user = new User();
        }
        model.addAttribute("user", user);
        return "add-edit";
    }

    @PostMapping({"/save-user", "/save-user/{id}"})
    public String editUser(@PathVariable("id") Optional<String> id, @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-edit";
        }

        if (id.isPresent()) {
            user.setId(id.get());
        }

        userService.saveUser(user);
        return "redirect:/users-ui";
    }
}
