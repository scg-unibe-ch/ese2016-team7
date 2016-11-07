package ch.unibe.ese.team1.controller;

import java.security.Principal;

import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Property;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.service.AdService;

/**
 * This controller handles request concerning the home page and several other
 * simple pages.
 */
@Controller
public class IndexController {

	@Autowired
	private AdService adService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserService userService;

	/** Displays the home page. */
	@RequestMapping(value = "/")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("index");
		model.addObject("newest", adService.getNewestAds(4,false));
		model.addObject("premium", adService.getNewestAds(2,true));
		return model;
	}

	/** Displays the about us page. */
	@RequestMapping(value = "/about")
	public ModelAndView about() {
		return new ModelAndView("about");
	}

	/** Displays the disclaimer page. */
	@RequestMapping(value = "/disclaimer")
	public ModelAndView disclaimer() {
		return new ModelAndView("disclaimer");
	}

	/** Displays the insights us page. */
	@RequestMapping(value = "/admin/insights")
	public ModelAndView insights(Principal principal) {
		ModelAndView model = new ModelAndView("insights");
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        User system = userDao.findByUsername("system");

		// Gathering Data for stats
		Integer usersCount = userService.countUsers();
		Integer adsCount = adService.getAdsCount();
		Integer expiredAdsCount = adService.getCountByExpired(true);
		Integer activeAdsCount = adService.getCountByExpired(false);
		Integer houseCount = adService.getCountByProperty(Property.HOUSE);
		Integer apartmentCount = adService.getCountByProperty(Property.APARTMENT);
		Integer studioCount = adService.getCountByProperty(Property.STUDIO);

		// Even more specific data
		Integer smokersCount = adService.getCountBySmokers(true);
		Integer animalsCount = adService.getCountByAnimals(true);
		Integer gardenCount = adService.getCountByGarden(true);
		Integer balconyCount = adService.getCountByBalcony(true);
		Integer cellarCount = adService.getCountByCellar(true);
		Integer furnishedCount = adService.getCountByFurnished(true);
		Integer garageCount = adService.getCountByGarage(true);
        Integer provisionsMade = system.getMoneyEarned();
		Integer moneySpent = getTotalMoneySpent();


		model.addObject("smokersCount", smokersCount);
		model.addObject("animalsCount", animalsCount);
		model.addObject("gardenCount", gardenCount);
		model.addObject("balconyCount", balconyCount);
		model.addObject("cellarCount", cellarCount);
		model.addObject("furnishedCount", furnishedCount);
		model.addObject("garageCount", garageCount);
		model.addObject("houseCount", houseCount);
		model.addObject("apartmentCount", apartmentCount);
		model.addObject("studioCount", studioCount);
		model.addObject("expiredAdsCount", expiredAdsCount);
		model.addObject("activeAdsCount", activeAdsCount);
		model.addObject("adsCount", adsCount);
		model.addObject("usersCount", usersCount);
        model.addObject("currentUser", user);
        model.addObject("moneySpent", moneySpent);
        model.addObject("userName", user.getUsername());
        model.addObject("provisionsMade",provisionsMade);

		return model;
	}

	private Integer getTotalMoneySpent() {
		Iterable<User> users =userDao.findAll();
		int totalMoneySpent = 0;

		for (User user: users){
			totalMoneySpent+=user.getMoneySpent();
		}
		return totalMoneySpent;
	}

	/** Displays the balance us page. */
	@RequestMapping(value = "/profile/balance")
	public ModelAndView balance(Principal principal) {
		ModelAndView model = new ModelAndView("balance");
		String username = principal.getName();
		User user = userService.findUserByUsername(username);

		model.addObject("moneyEarned", user.getMoneyEarned());
		model.addObject("moneySpent", user.getMoneySpent());

		return model;
	}
}