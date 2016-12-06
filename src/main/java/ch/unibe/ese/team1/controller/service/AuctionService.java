package ch.unibe.ese.team1.controller.service;


import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Bid;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.BidDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * This service serves primary to check whether or not some auctions
 * have finished to the send messages to the winner and owner
 */
@Service
@EnableScheduling
public class AuctionService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdService adService;

    @Autowired
    private AdDao adDao;

    @Autowired
    private BidDao bidDao;

    @Autowired
    private UserDao userDao;

    private static final Logger logger = Logger.getLogger("logger");

    private static double provision=0.05;

    /**
     * Searches every 10 seconds for finished auctions
     * and sends messages to the corresponding users
     */
    @Transactional
    @Scheduled(fixedRate = 10000)
    public void checkForFinishedAuctions(){
        Iterable<Ad> expiredAds = adDao.findByExpireDateLessThanAndExpired(new Date(),false);
        for(Ad ad : expiredAds){
            ad.setExpired(true);
            adDao.save(ad);
            if(bidDao.countByAd(ad) == 0){
                sendNoBidsMessage(ad);
            }else{
                updateBalance(ad);
                sendSuccessMessages(ad);
            }
        }
    }

    /**
     * Sets expired to true
     * Sends message to winner
     * @param id of ad
     */
    @Transactional
    public void instantBuy(long id, User winner){
        // Mark as expired

        Ad ad = adService.getAdById(id);
        if(!ad.getExpired() && winner.getHasCreditCard()) {
            ad.setExpired(true);
            adDao.save(ad);

            // Send messages
            User owner = ad.getUser();

            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Dear " + winner.getFirstName() + ",</br></br>");
            messageBuilder.append("Thank you for buying the " + ad.getPropertyString());
            messageBuilder.append(" <a href= ../ad?id=" + ad.getId() + " style=\"color: #0000ff\">" + ad.getTitle() + "</a>.</br>");
            messageBuilder.append(owner.getFirstName() + " " + owner.getLastName() + " will contact you with the details.</br>");
            messageBuilder.append(" If you have any questions please contact us by email at " + "support@flatfindr.com" + ".");
            messageBuilder.append(" We hope you will continue to enjoy using Flatfindr.</br></br>");
            messageBuilder.append("This message was automatically generated. Please do not reply.</br>");
            messageBuilder.append("Your Flatfindr team");

            messageService.sendMessage(userDao.findByUsername("FlatFindr"), winner, "Purchase confirmation", messageBuilder.toString());

            messageBuilder = new StringBuilder();
            messageBuilder.append("Dear " + owner.getFirstName() + ",</br></br>");
            messageBuilder.append("You just sold the " + ad.getPropertyString());
            messageBuilder.append(" <a href= ../ad?id=" + ad.getId() + " style=\"color: #0000ff\">" + ad.getTitle() + "</a>");
            messageBuilder.append(" to " + winner.getFirstName() + " " + winner.getLastName() + " for " + ad.getInstantBuyPrice() + " CHF.</br>");
            messageBuilder.append("Please contact him as soon as possible.</br>");
            messageBuilder.append(" If you have any questions please contact us by email at " + "support@flatfindr.com" + ".");
            messageBuilder.append(" We hope you will continue to enjoy using Flatfindr.</br></br>");
            messageBuilder.append("This message was automatically generated. Please do not reply.</br>");
            messageBuilder.append("Your Flatfindr team");

            messageService.sendMessage(userDao.findByUsername("FlatFindr"), owner, "You sold a " + ad.getPropertyString(), messageBuilder.toString());

            // update balance
            long amount = ad.getInstantBuyPrice();

            owner.addMoneyEarned((int) amount);
            winner.addMoneySpent((int) amount);
            User system = userDao.findByUsername("system");
            system.addMoneyEarned((int) (amount * provision));
            userDao.save(owner);
            userDao.save(winner);
            userDao.save(system);
            logger.info(String.format("Successful instant buy of user %s for ad no. %d.", winner.getEmail(), ad.getId()));
        }
        else if(ad.getExpired())
            logger.error(String.format("Failed instant buy of user %s for ad no. %d: " +
                    "Ad already expired!", winner.getEmail(), ad.getId()));
        else if(!winner.getHasCreditCard())
            logger.info(String.format("Failed instant buy of user %s for ad no. %d: " +
                    "User %s has no credit card.", winner.getEmail(), ad.getId(), winner.getEmail()));
    }

    /**
     * Sends message to user who placed the ad
     * so that he could place a new auction
     * with the same parameters
     * @param ad Ad whichs auction has finished
     */
    private void sendNoBidsMessage(Ad ad){
        User user = ad.getUser();
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Dear "+user.getFirstName()+",</br></br>");
        messageBuilder.append("We are sorry to inform you that your auction expired and no one placed a bid on your " + ad.getPropertyString());
        messageBuilder.append(" <a href= ../ad?id="+ad.getId()+" style=\"color: #0000ff\">"+ad.getTitle()+"</a>.</br>");
        messageBuilder.append("To place a new ad with the same information <a href= ../profile/placeAd?id="+ad.getId()+" style=\"color: #0000ff\">click here</a>.</br></br>"); // TODO add link to reinstate the ad
        messageBuilder.append(" If you have any questions please contact us by email at " + "support@flatfindr.com" + ".");
        messageBuilder.append(" We hope you will continue to enjoy using Flatfindr.</br></br>");
        messageBuilder.append("This message was automatically generated. Please do not reply.</br>");
        messageBuilder.append("Your Flatfindr team");
        messageService.sendMessage(user,user,"Auction Expired",messageBuilder.toString());

    }

    /**
     * Sends Message to winner and user who placed the ad,
     * both containing information about the ad and
     * contact information to be able to finish the deal
     * @param ad Ad on which was bidden
     */
    private void sendSuccessMessages(Ad ad){
        Bid latestBid = bidDao.findTop1ByAdOrderByIdDesc(ad);

        User owner = ad.getUser();
        User winner = latestBid.getUser();

        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Dear "+winner.getFirstName()+",</br></br>");
        messageBuilder.append("Congratulations! You won the auction on the " + ad.getPropertyString());
        messageBuilder.append(" <a href= ../ad?id="+ad.getId()+" style=\"color: #0000ff\">"+ad.getTitle()+"</a>.</br>");
        messageBuilder.append(owner.getFirstName()+" "+owner.getLastName()+" will contact you with the details.</br>");
        messageBuilder.append(" If you have any questions please contact us by email at " + "support@flatfindr.com" + ".");
        messageBuilder.append(" We hope you will continue to enjoy using Flatfindr.</br></br>");
        messageBuilder.append("This message was automatically generated. Please do not reply.</br>");
        messageBuilder.append("Your Flatfindr team");
        messageService.sendMessage(userDao.findByUsername("FlatFindr"),winner,"Purchase confirmation",messageBuilder.toString());

        messageBuilder = new StringBuilder();
        messageBuilder.append("Dear "+owner.getFirstName()+",</br></br>");
        messageBuilder.append("You just sold the " + ad.getPropertyString());
        messageBuilder.append(" <a href= ../ad?id="+ad.getId()+" style=\"color: #0000ff\">"+ad.getTitle()+"</a>");
        messageBuilder.append(" to " + winner.getFirstName()+ " " + winner.getLastName() + " for "+ ad.getPrice() + " CHF.</br>");
        messageBuilder.append("Please contact him/her as soon as possible.</br>");
        messageBuilder.append(" If you have any questions please contact us by email at " + "support@flatfindr.com" + ".");
        messageBuilder.append(" We hope you will continue to enjoy using Flatfindr.</br></br>");
        messageBuilder.append("This message was automatically generated. Please do not reply.</br>");
        messageBuilder.append("Your Flatfindr team");
        messageService.sendMessage(userDao.findByUsername("FlatFindr"),owner,"You sold a " + ad.getPropertyString(),messageBuilder.toString());
    }

    /**
     * Updates the balance of the owner of the ad and the winner of the auction.
     * This has nothing to do with the credit card! This is used to update the balance
     * of the owner and winner.
     * @param ad Ad on which was bidden.
     */
    private void updateBalance(Ad ad){
        Bid latestBid = bidDao.findTop1ByAdOrderByIdDesc(ad);

        User owner = ad.getUser();
        User winner = latestBid.getUser();
        int amount = ad.getPrice();

        owner.addMoneyEarned(amount);
        winner.addMoneySpent(amount);
        User system = userDao.findByUsername("system");
        system.addMoneyEarned((int) (amount*provision));
        userDao.save(owner);
        userDao.save(winner);
        userDao.save(system);
    }


    /**
     * Sends Message to User who has been overbid
     * @param ad Ad to bid on
     * @param user User who overbids the last one
     */
    public void sendOverbiddenMessage(Ad ad, User user){
        Bid bid = bidDao.findTop1ByAdOrderByIdDesc(ad);

        if (bid == null)
            logger.error(String.format("Failed sending overbidden message of ad no. %d to user %s: " +
                    "No bids found!", ad.getId(), user.getEmail()));
        if(bid != null) {
            User receiver = bid.getUser();
            messageService.sendMessage(userDao.findByUsername("FlatFindr"), receiver, "Overbid",
                    "You have been overbid by "+user.getFirstName()+
                    " on the " + ad.getPropertyString() + " " + "<a href= ../ad?id="+ad.getId()+" style=\"color: #0000ff\">"+ad.getTitle() +"</a>.</br>"+
                    "The new highest bid is "+ad.getPrice());
        }
    }
}
