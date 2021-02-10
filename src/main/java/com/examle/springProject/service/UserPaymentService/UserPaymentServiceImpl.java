package com.examle.springProject.service.UserPaymentService;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.domain.*;
import com.examle.springProject.exceptions.UserPaymentException;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.repos.CreditCardRepo;
import com.examle.springProject.repos.UserPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{
    @Autowired
    UserPaymentRepo userPaymentRepo;
    @Autowired
    CreditCardRepo creditCardRepo;
    @Autowired
    AccountRepo accountRepo;

    @Override
   public Page<UserPayment> findAllByUser(Long id , Pageable pageable){
      return   userPaymentRepo.findAllByUser_id(id , pageable);
    }


    @Override
    public void createAndSaveUserPayment(UserPaymentDTO userPaymentDTO, Payment payment, User user)
     throws UserPaymentException{
       UserPayment userPaymentToCreate = new UserPayment();
        CreditCard creditCard = creditCardRepo.findByNumber(userPaymentDTO.getNumber()).get();
        if(!isNumberCardExists(user , userPaymentDTO.getNumber())){
            throw new UserPaymentException(
                    "There is no card with this number: "
                            +  userPaymentDTO.getNumber());
        }
        if(!isPinCorrect(userPaymentDTO.getPin() , creditCard.getPin())){
            throw new UserPaymentException(
                    "Pin is incorrect");
        }
        Account account = creditCard.getAccount();
        account.setCosts(account.getCosts() - userPaymentDTO.getCost());
        accountRepo.save(account);
        userPaymentToCreate.setPayment(payment);
        userPaymentToCreate.setLocalDateTime(LocalDateTime.now());
        userPaymentToCreate.setCreditCard(creditCard);
        userPaymentToCreate.setUser(user);
        userPaymentToCreate.setCost(userPaymentDTO.getCost());
        userPaymentRepo.save(userPaymentToCreate);
    }

    private boolean isPinCorrect(int correctPin , int pin){
       return correctPin==pin;
    }
    private boolean isNumberCardExists(User user , Long number){
     return  user.getAccounts().stream().map(Account::getCreditCards)
               .flatMap(Collection::stream)
               .anyMatch(creditCard -> creditCard.getNumber().equals(number));
   }
}
