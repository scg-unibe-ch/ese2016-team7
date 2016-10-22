package ch.unibe.ese.team1.test.testData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

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
 * This inserts some visits test data into the database.
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

        // Visits for Ad 1
        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(testerMuster);
        bid.setAmount(300);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);


        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(oprah);
        bid.setAmount(360);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2015"));
        bidDao.save(bid);


        bid = new Bid();
        bid.setAd(ad1);
        bid.setUser(janeDoe);
        bid.setAmount(650);
        bid.setTimestamp(dateFormat.parse("14:04 27.04.2016"));
        bidDao.save(bid);




        // Visits for Ad 1
        bid = new Bid();
        bid.setAd(ad2);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad3);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad4);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad5);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad6);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad7);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad8);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad9);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad10);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad11);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

        bid = new Bid();
        bid.setAd(ad12);
        bid.setUser(testerMuster);
        bid.setTimestamp(dateFormat.parse("14:00 26.12.2014"));
        bidDao.save(bid);

    }

}
