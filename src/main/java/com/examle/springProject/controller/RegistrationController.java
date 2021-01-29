package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.User;
import com.examle.springProject.service.UserAlreadyExistException;
import com.examle.springProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class RegistrationController {
    @Autowired
    UserService userService;
    @GetMapping("/registration")
    public String registration(Model model ){
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(UserDTO user ,  Model model){
        Optional<User> registered = Optional.empty();
        try{
            registered = userService.registerUser(user);
        }catch (UserAlreadyExistException ex){
            model.addAttribute("name" , "User exists");
            model.addAttribute("user", user);
            return "registration";
        }
        return "redirect:/login";
    }
}
