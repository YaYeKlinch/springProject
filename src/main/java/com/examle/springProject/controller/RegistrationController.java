package com.examle.springProject.controller;

import com.examle.springProject.domain.Role;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/registration")
    public String refistration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user , Map<String,Object> model){
        User userFromDb =  userRepo.findByUsername(user.getUsername());

        if(userFromDb !=null){
            model.put("message" , "User exists");
            return  "registration";
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        userRepo.save(user);
        return "redirect:/login";
    }
}
