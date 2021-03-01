package com.examle.springProject.service.UserPaymentService;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.domain.*;
import com.examle.springProject.exceptions.UserPaymentException;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.repos.UserPaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{
    @Autowired
    UserPaymentRepo userPaymentRepo;
    @Autowired
    AccountRepo accountRepo;

    @Override
   public Page<UserPayment> findAllByUser(Long id , Optional<Integer> page , Optional<Integer> size , Sort sort){
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        if(sort==null){
            pageRequest = PageRequest.of(currentPage -1, sizeOfPage);
        }
        else {
            pageRequest = PageRequest.of(currentPage - 1 , sizeOfPage , sort);
        }
      return   userPaymentRepo.findAllByUser_id(id , pageRequest);
    }


    @Override
    @Transactional
    public void createAndSaveUserPayment(UserPaymentDTO userPaymentDTO, Payment payment, User user)
     throws UserPaymentException{
       UserPayment userPaymentToCreate = new UserPayment();
        if(!findCard(user,userPaymentDTO.getNumber()).isPresent()){
            throw new UserPaymentException(
                    "There is no card with this number: "
                            +  userPaymentDTO.getNumber());
        }
        CreditCard creditCard = findCard(user,userPaymentDTO.getNumber()).get();
        if(!isPinCorrect(userPaymentDTO.getPin() , creditCard.getPin())){
            throw new UserPaymentException(
                    "Pin is incorrect");
        }
        if(creditCard.getAccount().isBlocked()){
            throw new UserPaymentException(
                    "Account is blocked");
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

   private Optional<CreditCard> findCard(User user , Long number){
      return user.getAccounts().stream().map(Account::getCreditCards)
               .flatMap(Collection::stream)
               .filter(creditCard -> creditCard.getNumber().equals(number)).findAny();
   }
}
