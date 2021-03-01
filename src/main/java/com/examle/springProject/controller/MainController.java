package com.examle.springProject.controller;
import com.examle.springProject.controller.Dto.AccountDTO;
import com.examle.springProject.controller.utility.ControllerUtils;
import com.examle.springProject.controller.utility.CostValidator;
import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.domain.User;
import com.examle.springProject.exceptions.CostValidateException;
import com.examle.springProject.service.Acсount.AccountAlreadyExistsException;
import com.examle.springProject.service.Acсount.AccountService;
import com.examle.springProject.service.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    AccountService accountService;
    @Autowired
    PaymentService paymentServiceImpl;

    @GetMapping("/")
    public String greeting(  @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model)
    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Payment> payments = paymentServiceImpl.findAll(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = payments.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("payments" , payments);
        return "greeting.html";
    }


    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User owner,
                       @RequestParam("page") Optional<Integer> page,
                       @RequestParam("size") Optional<Integer> size,
                       @RequestParam(value = "sort", required = false) String sortBy,
                       @RequestParam(value = "nameBy", required = false) String nameBy,
                       Model model){
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page<Account> accounts = accountService.findAllByOwner(owner,page,size,sort);
        int totalPages = accounts.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("accounts" , accounts);
        return "main";
    }


    @GetMapping("/add-account")
    public String addAccountForm( Model model){
        AccountDTO accountDTO  = new AccountDTO();
        model.addAttribute("account" , accountDTO);
        return "addAccount";
    }
    @PostMapping("/add-account")
    public ModelAndView addAccount(
            @ModelAttribute("account") @Valid AccountDTO accountDTO,
            BindingResult bindingResult,
            @AuthenticationPrincipal User owner,
            Model model)
    {
        if(bindingResult.hasErrors()){
            return new ModelAndView("addAccount", "account",  accountDTO);
        }
        try {
            accountService.createAccount(accountDTO , owner);
        } catch (AccountAlreadyExistsException ex) {
            model.addAttribute("name" , ex.getMessage());
            return new ModelAndView("addAccount", "account",  accountDTO);
        }
        return new ModelAndView("redirect:/main","account",  accountDTO);
    }
    @GetMapping("/main/{accountId}")
    public String increaseCostsForm( @PathVariable("accountId") Account account ,Model model ){
        model.addAttribute("accountId" , account.getId());
        if(account.isBlocked()){
            return "redirect:/main";
        }
        return "increaseCosts";
    }
    @PostMapping("/main/{accountId}")
    public String increaseCosts(
            @PathVariable("accountId") Account account,
            @NotEmpty Integer costs,
            Model model) {
        try {
            CostValidator.validateCost(costs);
            model.addAttribute("costs", costs);
            accountService.increaseCosts(account , costs);
        } catch (CostValidateException|NullPointerException ex){
            model.addAttribute("Error" , "Costs must be less then" + CostValidator.MAX_COST);
            return "increaseCosts";
        }
        return "redirect:/main";

    }
}