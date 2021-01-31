package com.examle.springProject.controller;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.User;
import com.examle.springProject.service.Acсount.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/")
    public String greeting()
    {
        return "greeting.html";
    }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User owner, Model model){
        List<Account> accounts = accountService.findAllAccountsByOwner(owner);
        model.addAttribute("accounts" , accounts);
        return "main.html";
    }
}