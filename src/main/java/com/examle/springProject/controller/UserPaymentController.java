package com.examle.springProject.controller;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.controller.utility.ControllerUtils;
import com.examle.springProject.domain.Payment;
import com.examle.springProject.domain.User;
import com.examle.springProject.domain.UserPayment;
import com.examle.springProject.exceptions.UserPaymentException;
import com.examle.springProject.service.UserPaymentService.UserPaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private static final Logger logger = LogManager.getLogger(UserPaymentController.class);
    @Autowired
    UserPaymentService userPaymentServiceImpl;

    @GetMapping("/payments")
    public String paymentListForm(@AuthenticationPrincipal User user,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  @RequestParam(value = "sort", required = false) String sortBy,
                                  @RequestParam(value = "nameBy", required = false) String nameBy,
                                  Model model){
        logger.debug("requested /payments get method");
        Sort sort = ControllerUtils.getSort(sortBy , nameBy , model);
        Page <UserPayment> userPayments =
                userPaymentServiceImpl.findAllByUser(user.getId(), page, size, sort );
        int totalPages = userPayments.getTotalPages();
        ControllerUtils.pageNumberCounts(totalPages , model);
        model.addAttribute("user_payments", userPayments);
        logger.debug("returning userPaymentList.html to user");
        return "userPaymentList";
    }
    @GetMapping("/add-user-payment/{payment}")
    public String addUserPaymentForm(@PathVariable("payment")Payment payment,
            Model model){
        logger.debug("requested /add-user-payment"+ payment.getId() +" get method");
        UserPaymentDTO userPaymentDTO = new UserPaymentDTO();
        model.addAttribute("userPayment" , userPaymentDTO);
        model.addAttribute("paymentId" , payment.getId());
        logger.debug("returning addUserPayment.html to user");
        return "addUserPayment";
    }
    @PostMapping("/add-user-payment/{payment}")
    public String addUserPayment(@AuthenticationPrincipal User user ,
                                 @PathVariable("payment")Payment payment,
                                 UserPaymentDTO userPaymentDTO,
                                 Model model){
        logger.debug("requested /add-user-payment"+ payment.getId() +" post method");
        try{
            model.addAttribute("userPayment",userPaymentDTO);
            userPaymentServiceImpl.createAndSaveUserPayment(userPaymentDTO,payment,user);
        }catch (UserPaymentException ex){
            logger.debug("userPaymentDTO has errors returning addUserPayment.html");
            model.addAttribute("Exception" , ex.getMessage());
            return "addUserPayment";
        }
        catch (Exception ex){
            logger.debug("errors happened returning addUserPayment.html");
            model.addAttribute("Error" , ex.getMessage());
            return "addUserPayment";
        }
        logger.debug("redirecting on /");
        return "redirect:/";

    }

}
