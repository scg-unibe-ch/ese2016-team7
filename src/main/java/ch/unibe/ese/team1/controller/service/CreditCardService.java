package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardService {

    @Autowired
    private UserDao userDao;

    private static final Logger logger = Logger.getLogger("logger");

    public boolean newPremiumAd(User user){
        if(!checkCreditCard(user)) {
            logger.info(String.format("Cannot place premium ad for user %s: User has no credit card.",
                    user.getEmail()));
            return false;
        }
        // Do transaction
        // return true if transaction was successful
        // return false otherwise
        User system = userDao.findByUsername("system");
        system.addPremiumAdMoney(5);
        userDao.save(system);
        logger.info(String.format("Can place premium ad for user %s.",
                user.getEmail()));
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
