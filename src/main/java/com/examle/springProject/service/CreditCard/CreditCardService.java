package com.examle.springProject.service.CreditCard;

import com.examle.springProject.domain.CreditCard;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.repos.CreditCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CreditCardService {
    private static final Long START_CARD = 1000000000000000L;
    private static final int CART_DURATION = 4;
    private static final int CVV_LENGTH = 3;
    @Autowired
    CreditCardRepo creditCardRepo;
    @Autowired
    AccountRepo accountRepo;



    @Transactional
    public void createAndSaveCard(Long accountId , int pin){
        CreditCard cardToCreate = new CreditCard();
        cardToCreate.setAccount(accountRepo.findById(accountId).get());
        cardToCreate.setNumber(generateRandomNumber());
        cardToCreate.setCvv(generateRandomCvv());
        cardToCreate.setPin(pin);
        cardToCreate.setDate(LocalDate.now().plusYears(CART_DURATION));
        creditCardRepo.save(cardToCreate);
    }

    private Long generateRandomNumber(){
        Optional<CreditCard> maxNumberCard = creditCardRepo.findFirstByOrderByNumberAsc();
        return maxNumberCard.map(creditCard -> creditCard.getNumber() + 1L).orElse(START_CARD);
    }

    private String generateRandomCvv(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<CVV_LENGTH; i++){
            int x = (int) (Math.random()*10);
            sb.append(x);
        }
        return sb.toString();
    }

}
