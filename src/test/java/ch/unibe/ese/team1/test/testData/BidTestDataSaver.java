package ch.unibe.ese.team1.test.testData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import ch.unibe.ese.team1.controller.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Bid;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import ch.unibe.ese.team1.model.dao.BidDao;

/**
 * This inserts some bids test data into the database.
 */
@Service
public class BidTestDataSaver{

    private User testerMuster;
    private User bernerBaer;
    private User janeDoe;
    private User oprah;

    private Ad ad1;
    private Ad ad2;
    private Ad ad3;
    private Ad ad4;
    private Ad ad5;
    private Ad ad6;
    private Ad ad7;
    private Ad ad8;
    private Ad ad9;
    private Ad ad10;
    private Ad ad11;
    private Ad ad12;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdDao adDao;

    @Autowired
    private BidDao bidDao;

    @Autowired
    private BidService bidService;

    @Transactional
    public void saveTestData() throws Exception {

        // load users
        bernerBaer = userDao.findByUsername("user@bern.com");
        testerMuster = userDao.findByUsername("ese@unibe.ch");
        janeDoe = userDao.findByUsername("jane@doe.com");
        oprah = userDao.findByUsername("oprah@winfrey.com");

        // load ads
        ad1 = adDao.findOne(1L);
        ad2 = adDao.findOne(2L);
        ad3 = adDao.findOne(3L);
        ad4 = adDao.findOne(4L);
        ad5 = adDao.findOne(5L);
        ad6 = adDao.findOne(6L);
        ad7 = adDao.findOne(7L);
        ad8 = adDao.findOne(8L);
        ad9 = adDao.findOne(9L);
        ad10 = adDao.findOne(10L);
        ad11 = adDao.findOne(11L);
        ad12 = adDao.findOne(12L);


        Bid bid;
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(testerMuster);
        bid.setAmount(130000);
        bid.setTimestamp(dateFormat.parse("13:25 22.10.2016"));
        bidDao.save(bid);


        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(oprah);
        bid.setAmount(135000);
        bid.setTimestamp(dateFormat.parse("14:00 26.10.2016"));
        bidDao.save(bid);


        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(janeDoe);
        bid.setAmount(145000);
        bid.setTimestamp(dateFormat.parse("14:04 27.10.2016"));
        bidDao.save(bid);

    }

}
