package ch.unibe.ese.team1.controller;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.service.*;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

/**
 * This controller handles all requests concerning displaying ads,
 * bookmarking and bidding on them.
 */
@Controller
public class AdController {

	@Autowired
	private AdService adService;

    @Autowired
    private UserDao userDao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private BidService bidService;


	/** Gets the ad description page for the ad with the given id. */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public ModelAndView ad(@RequestParam("id") long id, Principal principal) {
		receivedRequest("AdController", "/ad");
		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());

		String loggedInUserEmail = (principal == null) ? "" : principal.getName();
        User user = userDao.findByUsername(loggedInUserEmail);
        // if user does not exist (= not logged in), he has no credit card
        boolean hasCreditCard = user != null && user.getHasCreditCard();

		model.addObject("loggedInUserEmail", loggedInUserEmail);
		model.addObject("loggedInUserHasCreditCard", hasCreditCard);
		model.addObject("visits", visitService.getVisitsByAd(ad));
		model.addObject("bids", bidService.getBidsByAd(ad));
		model.addObject("numBids", bidService.getNumBidsByAd(ad));

		handledRequestSuccessfully("AdController", "/ad");
		return model;
	}

	/**
	 * Gets the ad description page for the ad with the given id and also
	 * validates and persists the message passed as post data.
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public ModelAndView messageSent(@RequestParam("id") long id,
			@Valid MessageForm messageForm, BindingResult bindingResult) {
		receivedRequest("AdController", "/ad");

		ModelAndView model = new ModelAndView("adDescription");
		Ad ad = adService.getAdById(id);
		model.addObject("shownAd", ad);
		model.addObject("messageForm", new MessageForm());


		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
		}
        handledRequestSuccessfully("AdController", "/ad");
		return model;
	}






	/**
	 * Checks if the adID passed as post parameter is already inside user's
	 * List bookmarkedAds. In case it is present, true is returned changing
	 * the "Bookmark Ad" button to "Bookmarked". If it is not present it is
	 * added to the List bookmarkedAds.
	 * 
	 * @return 0 and 1 for errors; 3 to update the button to bookmarked 3 and 2
	 *         for bookmarking or undo bookmarking respectively 4 for removing
	 *         button completely (because its the users ad)
	 */
	@RequestMapping(value = "/bookmark", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public int isBookmarked(@RequestParam("id") long id,
			@RequestParam("screening") boolean screening,
			@RequestParam("bookmarked") boolean bookmarked, Principal principal) {
		receivedRequest("AdController", "/bookmark");
		// should never happen since no bookmark button when not logged in
		if (principal == null) {
            handlingRequestFailed("AdController", "/bookmark", "Principal is null; user not logged in; invalid action.");
			return 0;
		}
		String username = principal.getName();
		User user = userService.findUserByUsername(username);
		if (user == null) {
			// that should not happen...
            handlingRequestFailed("AdController", "/bookmark", "Principal is null; user not logged in; invalid action.");
			return 1;
		}
		List<Ad> bookmarkedAdsIterable = user.getBookmarkedAds();
		if (screening) {
			for (Ad ownAdIterable : adService.getAdsByUser(user)) {
				if (ownAdIterable.getId() == id) {
                    handledRequestSuccessfully("AdController", "/bookmark");
					return 4;
				}
			}
			for (Ad adIterable : bookmarkedAdsIterable) {
				if (adIterable.getId() == id) {
                    handledRequestSuccessfully("AdController", "/bookmark");
					return 3;
				}
			}
            handledRequestSuccessfully("AdController", "/bookmark");
			return 2;
		}

		Ad ad = adService.getAdById(id);

        handledRequestSuccessfully("AdController", "/bookmark");
		return bookmarkService.getBookmarkStatus(ad, bookmarked, user);
	}

	/**
	 * Fetches information about bookmarked rooms and own ads and attaches this
	 * information to the myRooms page in order to be displayed.
	 */
	@RequestMapping(value = "/profile/myRooms", method = RequestMethod.GET)
	public ModelAndView myRooms(Principal principal) {
		receivedRequest("AdController", "/profile/myRooms");
		ModelAndView model;
		User user;
		if (principal != null) {
			model = new ModelAndView("myRooms");
			String username = principal.getName();
			user = userService.findUserByUsername(username);

			Iterable<Ad> ownAds = adService.getAdsByUser(user);

			model.addObject("bookmarkedAdvertisements", user.getBookmarkedAds());
			model.addObject("ownAdvertisements", ownAds);
			return model;
		} else {
			model = new ModelAndView("home");
		}

        handledRequestSuccessfully("AdController", "/profile/myRooms");
		return model;
	}

}