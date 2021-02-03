package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.UserAlreadyExistException;
import com.examle.springProject.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            BindingResult bindingResult,
            Model model) {
        if(bindingResult.hasErrors()){
            return new ModelAndView("registration", "user", userDto);
        }
        try {
            Optional<User> registered = userService.registerUser(userDto);
        } catch (UserAlreadyExistException ex) {
           model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("registration", "user", userDto);
        }
        return new ModelAndView("login", "user", userDto);
    }
}
