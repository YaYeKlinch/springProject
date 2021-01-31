package com.examle.springProject.controller;

import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String userList(Model model, User user){
        model.addAttribute("users" , userRepo.findAll());
        return "userList";
    }
    @GetMapping("/{user}")
    public String userEditForm( @PathVariable("user") User user, Model model){
        model.addAttribute("user" , user);
        return "userEdit";
    }
    @PostMapping("")
    public String userSave(User user) {
        userRepo.save(user);
        return "redirect:/user";
    }
}
