package ch.unibe.ese.team1.controller;

import javax.validation.Valid;

import ch.unibe.ese.team1.model.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.controller.service.AdService;
import ch.unibe.ese.team1.controller.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;


/** Handles all requests concerning the search for ads. */
@Controller
public class SearchController {

	@Autowired
	private AdService adService;

	@Autowired
	private UserService userService;

	/**
	 * The search form that is used for searching. It is saved between request
	 * so that users don't have to enter their search parameters multiple times.
	 */
	private SearchForm searchForm;

	/** Shows the search ad page. */
	@RequestMapping(value = "/searchAd", method = RequestMethod.GET)
	public ModelAndView searchAd() {
		receivedRequest("SearchController", "/searchAd");
		ModelAndView model = new ModelAndView("searchAd");
		handledRequestSuccessfully("SearchController", "/searchAd");
		return model;
	}

	/** Processes Quick Search */
	@RequestMapping(value = "/quicksearch", method = RequestMethod.POST)
	public ModelAndView quicksearch(@RequestParam("city") String city){
		receivedRequest("SearchController", "/quicksearch");
		searchForm.setCity(city);
		searchForm.setRadius(500);
		searchForm.setPrice(20000);
		searchForm.setStudio(true);
		searchForm.setHouse(true);
		searchForm.setApartment(true);
		Errors errors = new BeanPropertyBindingResult(searchForm, "searchForm");
		handledRequestSuccessfully("SearchController", "/quicksearch");
        return results(searchForm, (BindingResult) errors);
	}

	/**
	 * Gets the results when filtering the ads in the database by the parameters
	 * in the search form.
	 * Filters 3 Premium ads which will appear on top of the results page an which are static
	 * the rest of the Premium ads found will be displayed normally with all other
	 * search results
	 */
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public ModelAndView results(@Valid SearchForm searchForm, BindingResult result) {
		receivedRequest("SearchController", "/results");
		if (!result.hasErrors()) {
			ModelAndView model = new ModelAndView("results");
			Iterable<Ad> results = adService.queryResults(searchForm);
			ArrayList<List<Ad>> filtered = adService.filterPremiumAds(results , 3);
			model.addObject("results", filtered.get(1));
			model.addObject("premium", filtered.get(0));
			handledRequestSuccessfully("SearchController", "/results");
			return model;
		} else {
			handlingRequestFailed("SearchController", "/results", "BindingResult error");
			// go back
			return searchAd();
		}
	}

	@ModelAttribute
	public SearchForm getSearchForm() {
		if (searchForm == null) {
			searchForm = new SearchForm();
		}
		return searchForm;
	}
}