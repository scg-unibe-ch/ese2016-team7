package ch.unibe.ese.team1.controller.service;


import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Bid;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.BidDao;
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

    /**
     * Searches every second for finished auctions
     * and sends messages to the corresponding users
     */
    @Transactional
    @Scheduled(fixedRate = 1000)
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
        message += "on your Ad:";
        message += ""; //TODO add link to show ad
        message += "To place a new ad with the same Infromation click here"; // TODO add link to reinstate the ad
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
        messageBuilder.append("Congratulation, you have won the auction on the Ad:");
        messageBuilder.append(""); // TODO add link
        messageBuilder.append("You will be contacted by the owner");
        messageBuilder.append("If you have any Questions to ask here is his email:");
        messageBuilder.append(owner.getEmail());
        messageService.sendMessage(owner,winner,"You have won the auction!",messageBuilder.toString());

        messageBuilder = new StringBuilder();
        messageBuilder.append("The auction on the Ad:");
        messageBuilder.append(""); // TODO add link
        messageBuilder.append("has finished.");
        messageBuilder.append("The winner is:");
        messageBuilder.append(winner.getFirstName());
        messageBuilder.append(winner.getLastName());
        messageBuilder.append(winner.getEmail());
        messageBuilder.append("Please contact him as soon as possible");
        messageService.sendMessage(winner,owner,"Your action was successfully completed!",messageBuilder.toString());
    }
}
