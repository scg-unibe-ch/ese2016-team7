package ch.unibe.ese.team1.controller.service;


import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Bid;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.BidDao;
import ch.unibe.ese.team1.model.dao.UserDao;
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
    private AdDao adDao;

    @Autowired
    private BidDao bidDao;

    @Autowired
    private UserDao userDao;

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
                sendSuccessMessages(ad);
            }
        }
    }

    /**
     * Sends message to user who placed the ad
     * so that he could place a new auction
     * with the same parameters
     * @param ad Ad whichs auction has finished
     */
    private void sendNoBidsMessage(Ad ad){
        User user = ad.getUser();
        String message = "We are sorry to inform you, that no one placed a bid";
        message += "on your Ad: ";
        message += "<a href=../ad?id="+ad.getId()+">+"+ad.getTitle()+". </a>"; //TODO add link to show ad
        message += "To place a new ad with the same Infromation <a href= ../click here"; // TODO add link to reinstate the ad
        messageService.sendMessage(user,user,"No one has placed a Bid",message);

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
        messageBuilder.append("Congratulation, you have won the auction on the Ad: ");
        messageBuilder.append("<a href= ../ad?id="+ad.getId()+">"+ad.getTitle()+"</a></br>");
        messageBuilder.append(" You will be contacted by ");
        messageBuilder.append(owner.getFirstName()+" "+owner.getLastName()+". ");
        messageBuilder.append("If you have any Questions to ask here is the email:");
        messageBuilder.append(owner.getEmail());
        messageService.sendMessage(userDao.findByUsername("FlatFindr"),winner,"You have won the auction!",messageBuilder.toString());

        messageBuilder = new StringBuilder();
        messageBuilder.append("The auction on the Ad: ");
        messageBuilder.append("<a href= ../ad?id="+ad.getId()+">"+ad.getTitle()+"</a><");
        messageBuilder.append("has finished.</br>");
        messageBuilder.append("The winner is:");
        messageBuilder.append(winner.getFirstName()+" ");
        messageBuilder.append(winner.getLastName()+", ");
        messageBuilder.append(winner.getEmail()+"</br>");
        messageBuilder.append("He bid "+ad.getPrice()+"swiss franks for your property. </br>");
        messageBuilder.append("Please contact him as soon as possible");
        messageService.sendMessage(userDao.findByUsername("FlatFindr"),owner,"Your action was successfully completed!",messageBuilder.toString());
    }


    /**
     * Sends Message to User who has been overbid
     * @param ad Ad to bid on
     * @param user User who overbids the last one
     */
    public void sendOverbiddenMessage(Ad ad, User user){
        Bid bid = bidDao.findTop1ByAdOrderByIdDesc(ad);

        //TODO: This should notify ALL users that where overbidden, not just the most recent one.
        //Added null check in case there is no bid. (It didn't work without any bids before)
        if(bid != null) {
            User receiver = bid.getUser();
            messageService.sendMessage(userDao.findByUsername("FlatFindr"), receiver, "Overbid",
                    "You have been overbid by "+user.getFirstName()+
                    "at "+"<a href= ../ad?id="+ad.getId()+">this ad! </a></br>"+
                    "New highest bid is "+ad.getPrice());
        }
    }
}
