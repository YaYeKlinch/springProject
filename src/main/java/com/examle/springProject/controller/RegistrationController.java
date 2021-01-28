package com.examle.springProject.controller;

import com.examle.springProject.domain.Role;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/registration")
    public String refistration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user ,  Model model){
        User userFromDb =  userRepo.findByUsername(user.getUsername());
        if(userFromDb !=null){
            model.addAttribute("name", "User exists");
            return "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepo.save(user);
        return "redirect:/login";
    }
}
