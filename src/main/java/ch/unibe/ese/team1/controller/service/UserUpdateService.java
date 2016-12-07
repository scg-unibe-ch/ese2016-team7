package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

/** Handles updating a user's profile. */
@Service
public class UserUpdateService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	/** Handles updating an existing user in the database. */
	@Transactional
	public void updateFrom(@Valid EditProfileForm editProfileForm) {
		
		User currentUser = userService.findUserByUsername(editProfileForm.getUsername());
		
		currentUser.setFirstName(editProfileForm.getFirstName());
		currentUser.setLastName(editProfileForm.getLastName());
		currentUser.setPassword(editProfileForm.getPassword());
		currentUser.setAboutMe(editProfileForm.getAboutMe());
		currentUser.setCreditCardNumber(editProfileForm.getCreditCardNumber());
		if (editProfileForm.getCreditCardNumber() != null)
			currentUser.setHasCreditCard(true);
        else
            currentUser.setHasCreditCard(false);
        currentUser.setCreditCardExpireMonth(editProfileForm.getCreditCardExpireMonth());
		currentUser.setCreditCardExpireYear(editProfileForm.getCreditCardExpireYear());
		currentUser.setSecurityCode(editProfileForm.getSecurityCode());

		userDao.save(currentUser);
	}

    public void deleteCreditCardFromUser(long userId) {
		User user = userDao.findUserById(userId);
		user.setCreditCardNumber(null);
		user.setHasCreditCard(false);
        user.setCreditCardExpireMonth(0);
        user.setCreditCardExpireYear(0);
        user.setSecurityCode("000");
		userDao.save(user);
    }

    public void addCreditCardToUser(long userId) {
        User user = userDao.findUserById(userId);
        user.setCreditCardNumber("0000000000000000");
        user.setHasCreditCard(true);
        user.setCreditCardExpireMonth(0);
        user.setCreditCardExpireYear(0);
        user.setSecurityCode("000");
        userDao.save(user);
    }
}
