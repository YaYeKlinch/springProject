package com.examle.springProject.controller;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.repos.UserRepo;
import com.examle.springProject.service.Acсount.AccountService;
import com.examle.springProject.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String userList(@AuthenticationPrincipal User user,
                           Model model){
        List<User> users = userService.findAll();
        users.remove(user);
        model.addAttribute("users" , users);

        return "userList";
    }
    @GetMapping("/{user}")
    public String userEditForm( @AuthenticationPrincipal User owner,@PathVariable("user") User user, Model model){
        if(owner.equals(user)){
            return "redirect:/user";
        }
        model.addAttribute("user" , user);
        model.addAttribute("userAccounts" , user.getAccounts());

        return "userEdit";
    }
    @PostMapping("")
    public String userSave(User user) {
        userService.update(user);
        return "redirect:/user";
    }
    @GetMapping("/change-permission/{account}")
    public String changePermission(@PathVariable("account") Account account){
        accountService.changePermission(account);
        return "redirect:/user";
    }
}
