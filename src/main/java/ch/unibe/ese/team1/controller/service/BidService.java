package ch.unibe.ese.team1.controller.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ch.unibe.ese.team1.model.dao.BidDao;

/** Provides operations for getting and saving bids */
@Service
public class BidService {

    @Autowired
    private BidDao bidDao;

    @Autowired
    private AdService adService;

    private static final Logger logger = Logger.getLogger("logger");

    /**
     * Returns all bids for an advertisement.
     * Sorted by timestamp in descending order.
     * @return an Iterable of all matching bids
     */
    @Transactional
    public Iterable<Bid> getBidsByAd(Ad ad) {
        //return bidDao.findByAd(ad);
        return bidDao.findByAdOrderByTimestampDesc(ad);

    }

    public boolean isBiggerThan(int amount, Ad ad){
        Bid bid = bidDao.findTop1ByAdOrderByIdDesc(ad);
        if(bid == null) return true;
        return amount > bid.getAmount();
    }

    public Bid getHighestBid(Ad ad){
        return bidDao.findTop1ByAdOrderByIdDesc(ad);
    }

    public long getNumBidsByAd(Ad ad) {
        return bidDao.countByAd(ad);
    }


    /** Saves a new bid with the given parameters in the DB.
     */
    @Transactional
    public void makeBid(Integer amount, User user, Ad ad) {
        // Only allow making bids when auction is not over yet.
        //(So that people can't use a direct link)
        if(!ad.getExpired() && user.getHasCreditCard()) {
            Bid bid = new Bid();
            bid.setAd(ad);
            bid.setTimestamp(new Date());
            bid.setUser(user);
            bid.setAmount(amount);
            bidDao.save(bid);
            adService.changePrice(ad, amount);
            logger.info(String.format("Successful bid of %s for ad no. %d.", user.getEmail(), ad.getId()));
        }
        else if(ad.getExpired())
            logger.error(String.format("Failed bid of %s for ad no. %d: " +
                    "Ad already expired!", user.getEmail(), ad.getId()));
        else if(!user.getHasCreditCard())
            logger.info(String.format("Failed bid of %s for ad no. %d: " +
                    "User %s has no credit card.", user.getEmail(), ad.getId(), user.getEmail()));
    }


}
