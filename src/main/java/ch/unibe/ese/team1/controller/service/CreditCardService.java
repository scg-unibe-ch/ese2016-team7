package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    private UserDao userDao;


    public boolean newPremiumAd(User user){
        if(!checkCreditCard(user)) return false;
        // Do transaction
        // return true if transaction was successful
        // return false otherwise
        User system = userDao.findByUsername("system");
        system.addPremiumAdMoney(5);
        userDao.save(system);
        return true;
    }

    public boolean checkCreditCard(SignupForm signupForm){
        // Whether Card is valid or not
        // return true if successful
        return true;
    }

    public boolean checkCreditCard(User user){
        // Check if card is valid
        return true;
    }
}
