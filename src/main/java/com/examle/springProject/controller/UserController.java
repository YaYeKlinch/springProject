package com.examle.springProject.controller;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.service.Acсount.AccountService;
import com.examle.springProject.service.User.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public String userList(@AuthenticationPrincipal User user,
                           Model model){
        logger.debug("requested /user get method");
        List<User> users = userService.findAll();
        users.remove(user);
        model.addAttribute("users" , users);
        logger.debug("returning userList.html to user");
        return "userList";
    }
    @GetMapping("/{user}")
    public String userEditForm( @AuthenticationPrincipal User owner,@PathVariable("user") User user, Model model){
        logger.debug("requested /user/"+ user.getId() +" get method");
        if(owner.equals(user)){
            logger.debug("regeisterd user tried to open on page with his own userEdit page /"+ user.getId() +
                    " redirecting on /user");
            return "redirect:/user";
        }
        model.addAttribute("user" , user);
        model.addAttribute("userAccounts" , user.getAccounts());
        logger.debug("returning userEdit.html to user");
        return "userEdit";
    }
    @PostMapping("")
    public String userSave(User user) {
        logger.debug("requested /user post method");
        userService.update(user);
        logger.debug("redirecting /user");
        return "redirect:/user";
    }
    @GetMapping("/change-permission/{account}")
    public String changePermission(@PathVariable("account") Account account){
        logger.debug("requested /user/change-permission/"+ account.getId() +" post method");
        accountService.changePermission(account);
        logger.debug("redirecting /user");
        return "redirect:/user";
    }
}
