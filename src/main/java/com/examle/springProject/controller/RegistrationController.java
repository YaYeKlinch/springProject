package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.UserDTO;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.UserAlreadyExistException;
import com.examle.springProject.service.User.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
    @Autowired
    UserService userService;
    @GetMapping("/registration")
    public String registration(Model model ){
        logger.debug("requested /registration get method");
        UserDTO userDto = new UserDTO();
        model.addAttribute("user", userDto);
        logger.debug("returning registration.html to user");
        return "registration";
    }
    @PostMapping("/registration")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDTO userDto,
            BindingResult bindingResult,
            Model model) {
        if(bindingResult.hasErrors()){
            logger.debug("userDto has errors , returning to registration.html");
            return new ModelAndView("registration", "user", userDto);
        }
        try {
            userService.registerUser(userDto);
        } catch (UserAlreadyExistException ex) {
            logger.debug("user already exists , returning to registration.html");
           model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("registration", "user", userDto);
        }
        logger.debug("returning login.html to user");
        return new ModelAndView("login", "user", userDto);
    }
}
