package ch.unibe.ese.team1.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Property;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.service.AdService;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

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
	public ModelAndView index(Principal principal) {
		receivedRequest("IndexController", "/");
		ModelAndView model = new ModelAndView("index");
		model.addObject("newest", adService.getNewestAds(2,false));
		model.addObject("premium", adService.getNewestAds(2,true));

		String name;
		Iterable<Ad> bookmarks;

		try{
			name = principal.getName();
			User user = userService.findUserByUsername(name);
			bookmarks = user.getBookmarkedAds();
		}catch (Exception e){
			bookmarks = new ArrayList<Ad>();
		}

		model.addObject("bookmarks", bookmarks);
		handledRequestSuccessfully("IndexController", "/");
		return model;
	}

	/** Displays the about us page. */
	@RequestMapping(value = "/about")
	public ModelAndView about() {
		receivedRequest("IndexController", "/about");
		handledRequestSuccessfully("IndexController", "/about");
		return new ModelAndView("about");
	}

	/** Displays the disclaimer page. */
	@RequestMapping(value = "/disclaimer")
	public ModelAndView disclaimer() {
		receivedRequest("IndexController", "/disclaimer");
		handledRequestSuccessfully("IndexController", "/disclaimer");
		return new ModelAndView("disclaimer");
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