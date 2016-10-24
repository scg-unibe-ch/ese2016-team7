package ch.unibe.ese.team1.controller.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ch.unibe.ese.team1.model.dao.BidDao;

/** Provides operations for getting and saving bids */
@Service
public class BidService {

    @Autowired
    private BidDao bidDao;

    /**
     * Returns all bids for an advertisement.
     *
     * @return an Iterable of all matching bids
     */
    @Transactional
    public Iterable<Bid> getBidsByAd(Ad ad) {
        //return bidDao.findByAd(ad);
        return bidDao.findByAdOrderByTimestampDesc(ad);

    }


    /** Saves a new bid with the given parameters in the DB.
     */
    public void makeBid(Integer amount, User user, Ad ad) {
        Bid bid = new Bid();
        bid.setAd(ad);
        bid.setTimestamp(new Date());
        bid.setUser(user);
        bid.setAmount(amount);
        bidDao.save(bid);
    }


}
