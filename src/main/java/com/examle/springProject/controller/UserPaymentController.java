package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.controller.utility.ControllerUtils;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.domain.User;
import com.examle.springProject.domain.UserPayment;
import com.examle.springProject.exceptions.UserPaymentException;
import com.examle.springProject.service.UserPaymentService.UserPaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserPaymentController {
    @Autowired
    UserPaymentServiceImpl userPaymentServiceImpl;

    @GetMapping("/payments")
    public String paymentListForm(@AuthenticationPrincipal User user,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  Model model){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page <UserPayment> userPayments =
                userPaymentServiceImpl.findAllByUser(user.getId(), PageRequest.of(currentPage - 1, pageSize) );
        int totalPages = userPayments.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("user_payments", userPayments);
        return "userPaymentList";
    }
    @GetMapping("/add-user-payment/{payment}")
    public String addUserPaymentForm(@PathVariable("payment")Payment payment,
            Model model){
        UserPaymentDTO userPaymentDTO = new UserPaymentDTO();
        model.addAttribute("userPayment" , userPaymentDTO);
        model.addAttribute("paymentId" , payment.getId());
        return "addUserPayment";
    }
    @PostMapping("/add-user-payment/{payment}")
    public String addUserPayment(@AuthenticationPrincipal User user ,
                                 @PathVariable("payment")Payment payment,
                                 UserPaymentDTO userPaymentDTO,
                                 Model model){
        try{
            model.addAttribute("userPayment",userPaymentDTO);
            userPaymentServiceImpl.createAndSaveUserPayment(userPaymentDTO,payment,user);
        }catch (UserPaymentException ex){
            model.addAttribute("Exception" , ex.getMessage());
            return "addUserPayment";
        }
        catch (Exception ex){
            model.addAttribute("Error" , ex.getMessage());
            return "addUserPayment";
        }
        return "redirect:/";

    }

}
