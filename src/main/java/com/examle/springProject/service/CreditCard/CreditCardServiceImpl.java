package com.examle.springProject.service.CreditCard;

import com.examle.springProject.domain.Account;
import com.examle.springProject.domain.CardType;
import com.examle.springProject.domain.CreditCard;
import com.examle.springProject.exceptions.TypeCardException;
import com.examle.springProject.repos.AccountRepo;
import com.examle.springProject.repos.CreditCardRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CreditCardServiceImpl implements CreditCardService{
    private static final Logger logger = LogManager.getLogger(CreditCardServiceImpl.class);
    private static final Long START_CARD = 1000000000000000L;
    private static final int CART_DURATION = 4;
    private static final int CVV_LENGTH = 3;
    @Autowired
    CreditCardRepo creditCardRepo;
    @Autowired
    AccountRepo accountRepo;

    @Override
    @Transactional
    public void createAndSaveCard(Account account , int pin , CardType type) throws TypeCardException{
        logger.info("trying to create and save credit card in account with number " + account.getNumber());
        CreditCard cardToCreate = new CreditCard();
        if(account.getCreditCards().stream().anyMatch(creditCard -> creditCard.getCardType().equals(type)))
        {
            logger.info("tried to create credit card with existing credit card type in account with number "
                    + account.getNumber() + ",throwing TypeCardException");
            throw new TypeCardException(
                    "There is card with type "
                            +  type.toString() + " in account");
        }
        cardToCreate.setAccount(account);
        cardToCreate.setNumber(generateRandomNumber());
        cardToCreate.setCvv(generateRandomCvv());
        cardToCreate.setPin(pin);
        cardToCreate.setDate(LocalDate.now().plusYears(CART_DURATION));
        cardToCreate.setCardType(type);
        creditCardRepo.save(cardToCreate);
    }

    private Long generateRandomNumber(){
        logger.info("trying to generate random number for credit card");
        Optional<CreditCard> maxNumberCard = creditCardRepo.findFirstByOrderByNumberDesc();
        return maxNumberCard.map(creditCard -> creditCard.getNumber() + 1L).orElse(START_CARD);
    }

    private String generateRandomCvv(){
        logger.info("trying to generate random cvv for credit card");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<CVV_LENGTH; i++){
            int x = (int) (Math.random()*10);
            sb.append(x);
        }
        return sb.toString();
    }

}
