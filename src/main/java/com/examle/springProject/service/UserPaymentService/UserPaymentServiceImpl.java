package com.examle.springProject.service.UserPaymentService;

import com.examle.springProject.controller.Dto.UserPaymentDTO;
import com.examle.springProject.domain.*;
import com.examle.springProject.exceptions.UserPaymentException;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.repos.UserPaymentRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(UserPaymentServiceImpl.class);
    @Autowired
    UserPaymentRepo userPaymentRepo;
    @Autowired
    AccountRepo accountRepo;

    @Override
   public Page<UserPayment> findAllByUser(Long id , Optional<Integer> page , Optional<Integer> size , Sort sort){
        PageRequest pageRequest = null;
        int currentPage = page.orElse(1);
        int sizeOfPage = size.orElse(5);
        logger.debug("Trying to find all userPayments from user with id:"
                + id + "on page" + currentPage + "with size of page" + sizeOfPage);
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
        logger.info(" user with id " + user.getId() +"trying to create and save userPayment with payment property "
                +payment.getProperty() +
                "with credit card number " + userPaymentDTO.getNumber());
       UserPayment userPaymentToCreate = new UserPayment();
        if(!findCard(user,userPaymentDTO.getNumber()).isPresent()){
            logger.info("tried to create userPayment with not existing credit card , throwing UserPaymentException");
            throw new UserPaymentException(
                    "There is no card with this number: "
                            +  userPaymentDTO.getNumber());
        }
        CreditCard creditCard = findCard(user,userPaymentDTO.getNumber()).get();
        if(!isPinCorrect(userPaymentDTO.getPin() , creditCard.getPin())){
            logger.info("tried to create userPayment with incorrect pin , throwing UserPaymentException");
            throw new UserPaymentException(
                    "Pin is incorrect");
        }
        if(creditCard.getAccount().isBlocked()){
            logger.info("tried to create userPayment with blocked card , throwing UserPaymentException");
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
       logger.info("trying to find card with number" +number +" from user with id" + user.getId());
      return user.getAccounts().stream().map(Account::getCreditCards)
               .flatMap(Collection::stream)
               .filter(creditCard -> creditCard.getNumber().equals(number)).findAny();
   }
}
