package ch.unibe.ese.team1.controller;

import ch.unibe.ese.team1.controller.pojos.forms.EditProfileForm;
import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.pojos.forms.SignupForm;
import ch.unibe.ese.team1.controller.service.*;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

/**
 * Handles all requests concerning user accounts and profiles.
 */
@Controller
@EnableScheduling
public class ProfileController {

	@Autowired
	@Qualifier("authenticationManager")
	protected AuthenticationManager authenticationManager;

	@Autowired
	private SignupService signupService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserUpdateService userUpdateService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private AdService adService;

	@Autowired
	private CreditCardService creditCardService;

	/** Returns the login page. */
	@RequestMapping(value = "/login")
	public ModelAndView loginPage() {
		receivedRequest("ProfileController", "/login");
		ModelAndView model = new ModelAndView("login");
        handledRequestSuccessfully("ProfileController", "/login");
		return model;
	}

	/** Returns the signup page. */
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signupPage() {
        receivedRequest("ProfileController", "/signup");
		ModelAndView model = new ModelAndView("signup");
		model.addObject("signupForm", new SignupForm());
        handledRequestSuccessfully("ProfileController", "/signup");
		return model;
	}

	@RequestMapping(value = "/googleSignup", method = RequestMethod.POST)
	public @ResponseBody void makeBid(@RequestParam("name") String name, @RequestParam("email") String email,
				 Principal principal) {

        receivedRequest("ProfileController", "/googleSignup");
		boolean existsAlready = signupService.doesUserWithUsernameExist(email);
		if(!existsAlready) {
			SignupForm signupForm = new SignupForm();
			signupForm.setFirstName(name);
			signupForm.setLastName(".");
			signupForm.setEmail(email);

			//Set an unhackable password
			final SecureRandom rand = new SecureRandom();
			String randomPassword = new BigInteger(130, rand).toString(32);
			signupForm.setPassword(randomPassword);
			signupForm.setGender(Gender.MALE);

            //Set no credit card
			signupForm.setSecurityCode("");
			signupForm.setCreditCardNumber("");
			signupForm.setCreditCardExpireMonth(0);
			signupForm.setCreditCardExpireYear(0);
			signupForm.setHasCreditCard(false);
			signupService.saveFrom(signupForm);
		}
			userService.login(email);
            handledRequestSuccessfully("ProfileController", "/googleSignup");
	}



	/** Validates the signup form and on success persists the new user. */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signupResultPage(@Valid SignupForm signupForm,
			BindingResult bindingResult) {
        receivedRequest("ProfileController", "/signup");
		ModelAndView model;
		if (!bindingResult.hasErrors() && creditCardService.checkCreditCard(signupForm)) {
			signupService.saveFrom(signupForm);
			model = new ModelAndView("login");
			model.addObject("confirmationMessage", "Signup complete!");
            handledRequestSuccessfully("ProfileController", "/signup");
		} else {
			model = new ModelAndView("signup");
			model.addObject("signupForm", signupForm);
            handlingRequestFailed("ProfileController", "/signup", "BindingResult error or invalid creditCard");
		}
		return model;

	}

	/** Checks and returns whether a user with the given email already exists. */
	@RequestMapping(value = "/signup/doesEmailExist", method = RequestMethod.POST)
	public @ResponseBody boolean doesEmailExist(@RequestParam String email) {
        receivedRequest("ProfileController", "/doesEmailExist");
        handledRequestSuccessfully("ProfileController", "/doesEmailExist");
		return signupService.doesUserWithUsernameExist(email);
	}

	/** Shows the edit profile page. */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfilePage(Principal principal) {
        receivedRequest("ProfileController", "/profile/editProfile");
		ModelAndView model = new ModelAndView("editProfile");
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
        boolean hasCreditCard = false;
        if (user.getCreditCardNumber() != null && user.getCreditCardNumber() !="" && user.getHasCreditCard())
            hasCreditCard = user != null;
		model.addObject("editProfileForm", new EditProfileForm());
		model.addObject("currentUser", user);
        model.addObject("hasCreditCard", hasCreditCard);
        handledRequestSuccessfully("ProfileController", "/profile/editProfile");
		return model;
	}

    /**
     * Deletes the credit card of the user with the given id.
     */
    @RequestMapping(value = "/profile/editProfile/deleteCreditCardFromUser", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteCreditCardFromUser(@RequestParam long userId) {
        receivedRequest("ProfileController", "/profile/editProfile/deleteCreditCardFromUser");
        userUpdateService.deleteCreditCardFromUser(userId);
        handledRequestSuccessfully("ProfileController", "/profile/editProfile/deleteCreditCardFromUser");
    }

    /**
     * Adds a basic credit card to the user with the given id.
     */
    @RequestMapping(value = "/profile/editProfile/addCreditCardToUser", method = RequestMethod.POST)
    public
    @ResponseBody
    void addCreditCardFromUser(@RequestParam long userId) {
        receivedRequest("ProfileController", "/profile/editProfile/addCreditCardToUser");
        userUpdateService.addCreditCardToUser(userId);
        handledRequestSuccessfully("ProfileController", "/profile/editProfile/addCreditCardToUser");
    }

	/** Handles the request for editing the user profile. */
	@RequestMapping(value = "/profile/editProfile", method = RequestMethod.POST)
	public ModelAndView editProfileResultPage(
			@Valid EditProfileForm editProfileForm,
			BindingResult bindingResult, Principal principal) {
        receivedRequest("ProfileController", "/profile/editProfile");
		ModelAndView model;
		String username = principal.getName();
        User user = userService.findUserByUsername(username);
		if (!bindingResult.hasErrors()) {
			userUpdateService.updateFrom(editProfileForm);
			model = new ModelAndView("updatedProfile");
			model.addObject("message", "Your Profile has been updated!");
			model.addObject("currentUser", user);
            handledRequestSuccessfully("ProfileController", "/profile/editProfile");
			return model;
		} else {
			model = new ModelAndView("editProfile");
			//model.addObject("editProfileForm", editProfileForm);
            handlingRequestFailed("ProfileController", "/profile/editProfile", "BindingResult error");
			return model;
		}
	}

	/** Displays the public profile of the user with the given id. */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView user(@RequestParam("id") long id, Principal principal) {
        receivedRequest("ProfileController", "/user");
		ModelAndView model = new ModelAndView("user");
		User user = userService.findUserById(id);
		if (principal != null) {
			String username = principal.getName();
			User user2 = userService.findUserByUsername(username);
			long principalID = user2.getId();
			model.addObject("principalID", principalID);
		}
		model.addObject("user", user);
		model.addObject("messageForm", new MessageForm());
        handledRequestSuccessfully("ProfileController", "/user");
		return model;
	}

	/** Displays the schedule page of the currently logged in user. */
	@RequestMapping(value = "/profile/schedule", method = RequestMethod.GET)
	public ModelAndView schedule(Principal principal) {
        receivedRequest("ProfileController", "/profile/schedule");
		ModelAndView model = new ModelAndView("schedule");
		User user = userService.findUserByUsername(principal.getName());

		// visits, i.e. when the user sees someone else's property
		Iterable<Visit> visits = visitService.getVisitsForUser(user);
		model.addObject("visits", visits);

		// presentations, i.e. when the user presents a property
		Iterable<Ad> usersAds = adService.getAdsByUser(user);
		ArrayList<Visit> usersPresentations = new ArrayList<Visit>();

		for (Ad ad : usersAds) {
			try {
				usersPresentations.addAll((ArrayList<Visit>) visitService
						.getVisitsByAd(ad));
			} catch (Exception e) {
                exceptionLog("/profile/schedule", "ProfileController", "Exception", e, "Visits couldn't be added to ad");
			}
		}

		model.addObject("presentations", usersPresentations);
        handledRequestSuccessfully("ProfileController", "/profile/editProfile");
		return model;
	}

	/** Returns the visitors page for the visit with the given id. */
	@RequestMapping(value = "/profile/visitors", method = RequestMethod.GET)
	public ModelAndView visitors(@RequestParam("visit") long id) {
        receivedRequest("ProfileController", "/profile/visitors");
		ModelAndView model = new ModelAndView("visitors");
		Visit visit = visitService.getVisitById(id);
		Iterable<User> visitors = visit.getSearchers();

		model.addObject("visitors", visitors);

		Ad ad = visit.getAd();
		model.addObject("ad", ad);
        handledRequestSuccessfully("ProfileController", "/profile/visitors");
		return model;
	}

	/** Displays the balance us page. */
	@RequestMapping(value = "/profile/balance")
	public ModelAndView balance(Principal principal) {
		receivedRequest("IndexController", "/profile/balance");
		handledRequestSuccessfully("IndexController", "/about");
		ModelAndView model = new ModelAndView("balance");
		String username = principal.getName();
		User user = userService.findUserByUsername(username);

		model.addObject("moneyEarned", user.getMoneyEarned());
		model.addObject("moneySpent", user.getMoneySpent());

		handledRequestSuccessfully("IndexController", "/profile/balance");
		return model;
	}
}
