package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.AdPicture;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.Visit;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AdPictureDao;
import ch.unibe.ese.team1.model.dao.VisitDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static ch.unibe.ese.team1.logger.LogInterceptor.exceptionLog;

/** Provides services for editing ads in the database. */
@Service
public class EditAdService {

	@Autowired
	private AdService adService;

	@Autowired
	private AdDao adDao;

	@Autowired
	private AdPictureDao adPictureDao;

    @Autowired
    private VisitDao visitDao;

	@Autowired
	private UserService userService;

    @Autowired
    private VisitService visitService;

	private static final Logger logger = Logger.getLogger("logger");

	/**
	 * Handles persisting an edited ad to the database.
	 * 
	 * @param placeAdForm
	 *            the form to take the data from a
	 *            list of the file paths the pictures are saved under the
	 *            currently logged in user
	 */
	@Transactional
	public Ad saveFrom(PlaceAdForm placeAdForm, List<String> filePaths,
			User user, long adId) {

		Ad ad = adService.getAdById(adId);

		Date now = new Date();
		ad.setCreationDate(now);

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
			exceptionLog("Edit Ad", "EditAdService, saveFrom()", "NumberFormatException", e, "Move-in date contains"
            + " non-numerical characters");
		}

		ad.setPrice(placeAdForm.getPrice());
		ad.setSquareFootage(placeAdForm.getSquareFootage());

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
		// add existing pictures
		for (AdPicture picture : ad.getPictures()) {
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

			// add existing visit
			for (Visit visit : ad.getVisits()) {
				visits.add(visit);
			}
			ad.setVisits(visits);
		}

		ad.setUser(user);

		adDao.save(ad);

		return ad;
	}

	/**
	 * Removes the picture with the given id from the list of pictures in the ad
	 * with the given id.
	 */
	@Transactional
	public void deletePictureFromAd(long adId, long pictureId) {
		Ad ad = adService.getAdById(adId);
		List<AdPicture> pictures = ad.getPictures();
		AdPicture picture = adPictureDao.findOne(pictureId);
		if (!pictures.contains(picture))
			logger.error(String.format("Failed deleting picture %d from ad %d: Picture not found for this ad!",
					pictureId, adId));
		pictures.remove(picture);
		ad.setPictures(pictures);
		adDao.save(ad);
	}

    /**
     * Removes the visit with the given id from the list of visits in the ad
     * with the given id.
     */
    @Transactional
    public void deleteVisitFromAd(long adId, long visitId) {
        Ad ad = adService.getAdById(adId);
        Iterable<Visit> visits = visitDao.findByAd(ad);
        Visit visit = visitDao.findOne(visitId);
        for (Visit vis:visits) {
            if (visit.getId()==vis.getId()) {
                vis.setAd(null);
                visitDao.save(vis);
            }
        }
        ad.setVisits(new ArrayList<>());
        adDao.save(ad);
    }

	/**
	 * Fills a Form with the data of an ad.
	 */
	public PlaceAdForm fillForm(Ad ad) {
		PlaceAdForm adForm = new PlaceAdForm();

		adForm.setRoomDescription(ad.getRoomDescription());

		return adForm;
	}
}
