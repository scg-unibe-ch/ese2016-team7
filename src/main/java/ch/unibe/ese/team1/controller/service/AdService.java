package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.controller.pojos.forms.SearchForm;
import ch.unibe.ese.team1.model.*;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AlertDao;
import ch.unibe.ese.team1.model.dao.MessageDao;
import ch.unibe.ese.team1.model.dao.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static ch.unibe.ese.team1.logger.LogInterceptor.exceptionLog;

/**
 * Handles all persistence operations concerning ad placement and retrieval.
 */
@Service
public class AdService {

    @Autowired
    private AdDao adDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AlertDao alertDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserService userService;

    @Autowired
    private GeoDataService geoDataService;

    private static final Logger logger = Logger.getLogger("logger");

    public boolean check(Principal principal, long id){
        Ad ad = adDao.findOne(id);
        return ad.getUser().getEmail().equals(principal.getName());
    }

    /**
     * Handles persisting a new ad to the database.
     *
     * @param placeAdForm the form to take the data from a
     *                    list of the file paths the pictures are saved under the
     *                    currently logged in user
     */
    @Transactional
    public Ad saveFrom(PlaceAdForm placeAdForm, List<String> filePaths,
                       User user) {

        Ad ad = new Ad();

        Date now = new Date();
        ad.setCreationDate(now);

        // Set expire date to 3 days after creation
        Date expire = new Date();
        expire.setTime(expire.getTime() + TimeUnit.DAYS.toMillis(3));
        ad.setExpireDate(expire);

        ad.setTitle(placeAdForm.getTitle());

        ad.setStreet(placeAdForm.getStreet());

        ad.setProperty(placeAdForm.getProperty());

        // take the zipcode - first four digits
        String zip = placeAdForm.getCity().substring(0, 4);
        ad.setZipcode(Integer.parseInt(zip));
        ad.setCity(placeAdForm.getCity().substring(7));

        Calendar calendar = Calendar.getInstance();
        // java.util.Calendar uses a month range of 0-11 instead of the
        // XMLGregorianCalendar which uses 1-12
        try {
            if (placeAdForm.getMoveInDate().length() >= 1) {
                int dayMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
                        .substring(0, 2));
                int monthMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
                        .substring(3, 5));
                int yearMoveIn = Integer.parseInt(placeAdForm.getMoveInDate()
                        .substring(6, 10));
                calendar.set(yearMoveIn, monthMoveIn - 1, dayMoveIn);
                ad.setMoveInDate(calendar.getTime());
            }

        } catch (NumberFormatException e) {
            exceptionLog("Place ad", "Adservice, saveFrom()", "NumberFormatException", e, "MoveInDate contains " +
                    "non-numerical characters");
        }

        ad.setPrice(placeAdForm.getPrice());
        ad.setSquareFootage(placeAdForm.getSquareFootage());
        ad.setNumberRooms(placeAdForm.getNumberRooms());

        ad.setRoomDescription(placeAdForm.getRoomDescription());

        // ad description values
        ad.setSmokers(placeAdForm.isSmokers());
        ad.setAnimals(placeAdForm.isAnimals());
        ad.setGarden(placeAdForm.getGarden());
        ad.setBalcony(placeAdForm.getBalcony());
        ad.setCellar(placeAdForm.getCellar());
        ad.setFurnished(placeAdForm.isFurnished());
        ad.setGarage(placeAdForm.getGarage());
        ad.setDishwasher(placeAdForm.getDishwasher());
        ad.setWashingMachine(placeAdForm.getWashingMachine());

        // Save instant buy price
        ad.setInstantBuyPrice(placeAdForm.getInstantBuyPrice());
        /*
		 * Save the paths to the picture files, the pictures are assumed to be
		 * uploaded at this point!
		 */
        List<AdPicture> pictures = new ArrayList<>();
        for (String filePath : filePaths) {
            AdPicture picture = new AdPicture();
            picture.setFilePath(filePath);
            pictures.add(picture);
        }
        ad.setPictures(pictures);

        // visits
        List<Visit> visits = new LinkedList<>();
        List<String> visitStrings = placeAdForm.getVisits();
        if (visitStrings != null) {
            for (String visitString : visitStrings) {
                Visit visit = new Visit();
                // format is 28-02-2014;10:02;13:14
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String[] parts = visitString.split(";");
                String startTime = parts[0] + " " + parts[1];
                String endTime = parts[0] + " " + parts[2];
                Date startDate = null;
                Date endDate = null;
                try {
                    startDate = dateFormat.parse(startTime);
                    endDate = dateFormat.parse(endTime);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    exceptionLog("Place Ad", "AdService, saveFrom()", "ParseException", ex, "VisitString could not be " +
                            "parsed");
                }

                visit.setStartTimestamp(startDate);
                visit.setEndTimestamp(endDate);
                visit.setAd(ad);
                visits.add(visit);
            }
            ad.setVisits(visits);
        }

        ad.setPremium(placeAdForm.getPremium());

        ad.setUser(user);

        adDao.save(ad);

        return ad;
    }

    /**
     * Changes the price of an ad.
     */
    @Transactional
    public void changePrice(Ad ad, int amount) {
        ad.setPrice(amount);
        adDao.save(ad);
    }


    /**
     * Gets the ad that has the given id.
     *
     * @param id the id that should be searched for
     * @return the found ad or null, if no ad with this id exists
     */
    @Transactional
    public Ad getAdById(long id) {
        return adDao.findOne(id);
    }

    /**
     * Returns all ads in the database
     */
    @Transactional
    public Iterable<Ad> getAllAds() {
        return adDao.findAll();
    }

    /**
     * Returns the newest ads in the database. Parameter 'newest' says how many.
     */
    @Transactional
    public Iterable<Ad> getNewestAds(int newest, boolean premium) {
        Iterable<Ad> allAds;
        if (!premium) allAds = adDao.findByPremiumAndExpired(false, false);
        else allAds = adDao.findByPremiumAndExpired(true, false);
        List<Ad> ads = new ArrayList<Ad>();
        for (Ad ad : allAds)
            ads.add(ad);
        if (ads.size() == 0) {
            if(premium) {
                logger.info(String.format("Failed finding %d newest non-expired premium ads: All ads expired.", newest));
            }
            else{
                logger.info(String.format("Failed finding %d newest non-expired ads: All ads expired.", newest));
            }
            return null;
        }
        Collections.sort(ads, new Comparator<Ad>() {
            @Override
            public int compare(Ad ad1, Ad ad2) {
                return ad2.getCreationDate().compareTo(ad1.getCreationDate());
            }
        });
        List<Ad> fourNewest = new ArrayList<Ad>();
        int k=1;
        for (int i = 0; i < ads.size() && i < newest; i++) {
            fourNewest.add(ads.get(i));
            k++;
        }
        if(premium) {
            logger.info(String.format("Successful finding %d of the requested %d newest non-expired premium ads: " +
                    "If there is a difference in the numbers, there aren't enough non-expired ads.", k, newest));
        }
        else{
            logger.info(String.format("Successful finding %d of the requested %d newest non-expiredads: " +
                    "If there is a difference in the numbers, there aren't enough non-expired ads.", k, newest));
        }
        return fourNewest;
    }

    /**
     * Returns all ads that match the parameters given by the form. This list
     * can possibly be empty.
     *
     * @param searchForm the form to take the search parameters from
     * @return an Iterable of all search results
     */
    @Transactional
    public Iterable<Ad> queryResults(SearchForm searchForm) {
        Iterable<Ad> results = null;

        //If no type is selected then just select all.
        if (!searchForm.getHouse() && !searchForm.getApartment() && !searchForm.getStudio()) {
            searchForm.setApartment(true);
            searchForm.setHouse(true);
            searchForm.setStudio(true);
        }

        /* We just get all ads by price less than in the search form and then filter them later in this function.
        If we need to make this process faster we could make separate searches depending on which property is checked, and then append them.
        */
        results = adDao.findByPriceLessThanAndExpired(searchForm.getPrice() + 1, false);

        List<Ad> filteredResults = new ArrayList<>();
        for (Ad ad : results) {
            if (searchForm.getHouse() && (ad.getProperty() == Property.HOUSE)) {
                filteredResults.add(ad);
                continue;
            }
            if (searchForm.getApartment() && (ad.getProperty() == Property.APARTMENT)) {
                filteredResults.add(ad);
                continue;
            }
            if (searchForm.getStudio() && (ad.getProperty() == Property.STUDIO)) {
                filteredResults.add(ad);
                continue;
            }
        }

        results = filteredResults;


        // filter out zipcode
        String city = searchForm.getCity().substring(7);

        // get the location that the user searched for and take the one with the
        // lowest zip code
        Location searchedLocation = geoDataService.getLocationsByCity(city)
                .get(0);

        // create a list of the results and of their locations
        List<Ad> locatedResults = new ArrayList<>();
        for (Ad ad : results) {
            locatedResults.add(ad);
        }

        final int earthRadiusKm = 6380;
        List<Location> locations = geoDataService.getAllLocations();
        double radSinLat = Math.sin(Math.toRadians(searchedLocation
                .getLatitude()));
        double radCosLat = Math.cos(Math.toRadians(searchedLocation
                .getLatitude()));
        double radLong = Math.toRadians(searchedLocation.getLongitude());

		/*
		 * calculate the distances (Java 8) and collect all matching zipcodes.
		 * The distance is calculated using the law of cosines.
		 * http://www.movable-type.co.uk/scripts/latlong.html
		 */
        List<Integer> zipcodes = locations
                .parallelStream()
                .filter(location -> {
                    double radLongitude = Math.toRadians(location
                            .getLongitude());
                    double radLatitude = Math.toRadians(location.getLatitude());
                    double distance = Math.acos(radSinLat
                            * Math.sin(radLatitude) + radCosLat
                            * Math.cos(radLatitude)
                            * Math.cos(radLong - radLongitude))
                            * earthRadiusKm;
                    return distance < searchForm.getRadius();
                }).map(location -> location.getZip())
                .collect(Collectors.toList());

        locatedResults = locatedResults.stream()
                .filter(ad -> zipcodes.contains(ad.getZipcode()))
                .collect(Collectors.toList());


        // prepare date filtering - by far the most difficult filter
        Date earliestInDate = null;
        Date latestInDate = null;

        // parse move-in and move-out dates
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            earliestInDate = formatter.parse(searchForm
                    .getEarliestMoveInDate());
        } catch (Exception e) {
            exceptionLog("Get search results", "AdService, queryResults()", "Exception", e, "Move-in date could not " +
                    "be formatted");
        }
        try {
            latestInDate = formatter
                    .parse(searchForm.getLatestMoveInDate());
        } catch (Exception e) {
            exceptionLog("Get search results", "AdService, queryResults()", "Exception", e, "Move-out date could not " +
                    "be formatted");
        }

        // filtering by dates
        locatedResults = validateDate(locatedResults, true, earliestInDate,
                latestInDate);

        // filtering for the rest
        // smokers
        if (searchForm.getSmokers()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getSmokers())
                    iterator.remove();
            }
        }

        // animals
        if (searchForm.getAnimals()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getAnimals())
                    iterator.remove();
            }
        }

        // garden
        if (searchForm.getGarden()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getGarden())
                    iterator.remove();
            }
        }

        // balcony
        if (searchForm.getBalcony()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getBalcony())
                    iterator.remove();
            }
        }

        // cellar
        if (searchForm.getCellar()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getCellar())
                    iterator.remove();
            }
        }

        // furnished
        if (searchForm.getFurnished()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getFurnished())
                    iterator.remove();
            }
        }

        // garage
        if (searchForm.getGarage()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getGarage())
                    iterator.remove();
            }
        }

        // dishwasher
        if (searchForm.getDishwasher()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getDishwasher())
                    iterator.remove();
            }
        }

        // washingMachine
        if (searchForm.getWashingMachine()) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (!ad.getWashingMachine())
                    iterator.remove();
            }
        }

        // Filter for instant buy price
        if (searchForm.getInstantBuyPrice() > 0) {
            Iterator<Ad> iterator = locatedResults.iterator();
            while (iterator.hasNext()) {
                Ad ad = iterator.next();
                if (ad.getInstantBuyPrice() > searchForm.getInstantBuyPrice() || ad.getInstantBuyPrice() == 0)
                {
                    iterator.remove();
                }
            }
        }


        return locatedResults;
    }

    /**
     * Sort Iterable so that Premium Ads are at the front
     *
     * @return the other Iterable
     */
    public ArrayList<List<Ad>> filterPremiumAds(Iterable<Ad> ads, int number) {
        // Filter
        List<Ad> premiumAds = new ArrayList<>();
        List<Ad> nonPremiumAds = new ArrayList<>();
        for (Ad ad : ads) {
            if (ad.getPremium()) {
                premiumAds.add(ad);
            } else {
                nonPremiumAds.add(ad);
            }
        }
        // Filter number
        List<Ad> numberedPremiumAds = new ArrayList<>();
        List<Ad> otherAds = new ArrayList<>();
        for (int i = 0; i < number && i < premiumAds.size(); i++) {
            numberedPremiumAds.add(premiumAds.get(i));
            premiumAds.remove(i);
        }
        for (Ad ad : premiumAds) {
            otherAds.add(ad);
        }
        for (Ad ad : nonPremiumAds) {
            otherAds.add(ad);
        }

        // Pack into ArrayList
        ArrayList<List<Ad>> list = new ArrayList<>();
        list.add(numberedPremiumAds);
        list.add(otherAds);

        return list;
    }


    private List<Ad> validateDate(List<Ad> ads, boolean inOrOut,
                                  Date earliestDate, Date latestDate) {
        if (ads.size() > 0) {
            // Move-in dates
            // Both an earliest AND a latest date to compare to
            if (earliestDate != null) {
                if (latestDate != null) {
                    Iterator<Ad> iterator = ads.iterator();
                    while (iterator.hasNext()) {
                        Ad ad = iterator.next();
                        if (ad.getDate(inOrOut).compareTo(earliestDate) < 0
                                || ad.getDate(inOrOut).compareTo(latestDate) > 0) {
                            iterator.remove();
                        }
                    }
                }
                // only an earliest date
                else {
                    Iterator<Ad> iterator = ads.iterator();
                    while (iterator.hasNext()) {
                        Ad ad = iterator.next();
                        if (ad.getDate(inOrOut).compareTo(earliestDate) < 0)
                            iterator.remove();
                    }
                }
            }
            // only a latest date
            else if (latestDate != null && earliestDate == null) {
                Iterator<Ad> iterator = ads.iterator();
                while (iterator.hasNext()) {
                    Ad ad = iterator.next();
                    if (ad.getDate(inOrOut).compareTo(latestDate) > 0)
                        iterator.remove();
                }
            } else {
            }
        }
        return ads;
    }

    /**
     * Returns all ads that were placed by the given user.
     */
    public Iterable<Ad> getAdsByUser(User user) {
        return adDao.findByUser(user);
    }

    /**
     * Checks if the email of a user is already contained in the given string.
     *
     * @param email        the email string to search for
     * @param alreadyAdded the string of already added emails, which should be searched
     *                     in
     * @return true if the email has been added already, false otherwise
     */
    public Boolean checkIfAlreadyAdded(String email, String alreadyAdded) {
        email = email.toLowerCase();
        alreadyAdded = alreadyAdded.replaceAll("\\s+", "").toLowerCase();
        String delimiter = "[:;]+";
        String[] toBeTested = alreadyAdded.split(delimiter);
        for (int i = 0; i < toBeTested.length; i++) {
            if (email.equals(toBeTested[i])) {
                return true;
            }
        }
        return false;
    }

    public int getAdsCount() {
        return (int) adDao.count();
    }

    public int getCountByExpired(boolean expired) {
        return (int) adDao.countByExpired(expired);
    }

    public int getCountByProperty(Property property) {
        return (int) adDao.countByProperty(property);
    }

    public int getCountBySmokers(boolean smokers) {
        return (int) adDao.countBySmokers(smokers);
    }

    public int getCountByAnimals(boolean animals) {
        return (int) adDao.countByAnimals(animals);
    }

    public int getCountByGarden(boolean garden) {
        return (int) adDao.countByGarden(garden);
    }

    public int getCountByBalcony(boolean balcony) {
        return (int) adDao.countByBalcony(balcony);
    }

    public int getCountByCellar(boolean cellar) {
        return (int) adDao.countByCellar(cellar);
    }

    public int getCountByFurnished(boolean furnished) {
        return (int) adDao.countByFurnished(furnished);
    }

    public int getCountByGarage(boolean garage) {
        return (int) adDao.countByGarage(garage);
    }

    public int getCountByDishwasher(boolean dishwasher) {
        return (int) adDao.countByDishwasher(dishwasher);
    }

    public int getCountByWashingMachine(boolean washingMachine) {
        return (int) adDao.countByWashingMachine(washingMachine);
    }
}