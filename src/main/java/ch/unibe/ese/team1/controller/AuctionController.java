package ch.unibe.ese.team1.controller;

import ch.unibe.ese.team1.controller.service.*;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

/**
 * This controller is responsible for
 * Bids and the instant-buy option for auctions
 */
@Controller
public class AuctionController {

    @Autowired
    private AdService adService;

    @Autowired
    private UserService userService;

    @Autowired
    private BidService bidService;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AuctionService auctionService;

    @RequestMapping(value = "/ad/makeBid", method = RequestMethod.POST)
    public @ResponseBody
    void makeBid(@RequestParam("amount") Integer amount, @RequestParam("id") long id,
                 Principal principal) {
        receivedRequest("AuctionController", "/ad/makeBid");
        User user = userService.findUserByUsername(principal.getName());
        Ad ad = adService.getAdById(id);
        auctionService.sendOverbiddenMessage(ad,user); // do this first to get the latest bid and user before
        bidService.makeBid(amount,user,ad);

        // triggers all alerts that match the placed ad.
        alertService.triggerAlerts(ad);
        handledRequestSuccessfully("AuctionController", "/ad/makeBid");
    }

    /**
     * Ends auction
     * Sends messages to the guy who bought the estate
     */
    @RequestMapping(value = "/instantBuy", method = RequestMethod.POST)
    public @ResponseBody void instantBuy(@RequestParam("id") long id, Principal principal){
        receivedRequest("AuctionController", "/instantBuy");
        User user = userService.findUserByUsername(principal.getName());
        auctionService.instantBuy(id, user);
        handledRequestSuccessfully("AuctionController", "/instantBuy");
    }
}
