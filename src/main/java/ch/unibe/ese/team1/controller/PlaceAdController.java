package ch.unibe.ese.team1.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import ch.unibe.ese.team1.controller.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.unibe.ese.team1.controller.pojos.PictureUploader;
import ch.unibe.ese.team1.controller.pojos.forms.PlaceAdForm;
import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.PictureMeta;
import ch.unibe.ese.team1.model.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

/**
 * This controller handles all requests concerning placing ads.
 *
 */
@Controller
public class PlaceAdController {

	public static final String IMAGE_DIRECTORY = "/img/ads";

	/** Used for generating a JSON representation of a given object. */
	private ObjectMapper objectMapper;

	/**
	 * Used for uploading ad pictures. As long as the user did not place the ad
	 * completely, the same picture uploader is used. Once the ad was placed,
	 * this uploader is renewed.
	 */
	private PictureUploader pictureUploader;

	/**
	 * The place ad form that is shared between several requests, so that the
	 * user only has to enter the data once. If an ad is placed, this form is
	 * reset.
	 */
	private PlaceAdForm placeAdForm;

	@Autowired
	private AlertService alertService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private MessageService messageService;

	@Autowired
	private VisitService visitService;

	@Autowired
	private BookmarkService bookmarkService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdService adService;

	@Autowired
	private CreditCardService creditCardService;

	private static final Logger logger = Logger.getLogger("logger");

	/** Shows the place ad form. */
	@RequestMapping(value = {"","/profile/placeAd"},method = RequestMethod.GET, params="id")
	public ModelAndView placeAd(@RequestParam("id") long id) throws IOException {
        receivedRequest("PlaceAdController", "/profile/placeAd");
		ModelAndView model = new ModelAndView("placeAd");

		String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
		if (pictureUploader == null) {
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}

		if(id!=0){
			Ad ad  =adService.getAdById(id);
			PlaceAdForm adForm  = new PlaceAdForm();
			adForm.setTitle(ad.getTitle());
			adForm.setAnimals(ad.getAnimals());
			adForm.setBalcony(ad.getBalcony());
			adForm.setCellar(ad.getCellar());
			adForm.setCity(ad.getCity());
			adForm.setFurnished(ad.getFurnished());
			adForm.setGarage(ad.getGarage());
			adForm.setGarden(ad.getGarden());
			adForm.setMoveInDate(ad.getMoveInDate().toString());
			adForm.setNumberRooms(ad.getNumberRooms());
			adForm.setPrice(ad.getPrice());
			adForm.setRoomDescription(ad.getRoomDescription());
			adForm.setNumberRooms(ad.getNumberRooms());
			adForm.setSquareFootage(ad.getSquareFootage());
			adForm.setSmokers(ad.getSmokers());
			adForm.setProperty(ad.getProperty());
			//this.placeAdForm = adForm;
			model.addObject("placeAdForm",adForm);
		}
		handledRequestSuccessfully("PlaceAd", "/profile/placeAd");
		return model;
	}

    /** Shows the place ad form.*/
    @RequestMapping(value = {"","/profile/placeAd"},method = RequestMethod.GET)
    public ModelAndView placeAd() throws IOException {
        receivedRequest("PlaceAdController", "/profile/placeAd");
        ModelAndView model = new ModelAndView("placeAd");

        String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
        if (pictureUploader == null) {
            pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
        }
        handledRequestSuccessfully("PlaceAd", "/profile/placeAd");
        return model;
    }


	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 * 
	 * @return A JSON representation of the uploaded files
	 */
	@RequestMapping(value = "/profile/placeAd/uploadPictures", method = RequestMethod.POST)
	public @ResponseBody String uploadPictures(
			MultipartHttpServletRequest request) {
        receivedRequest("PlaceAdController", "/profile/placeAd/uploadPictures");
		List<MultipartFile> pictures = new LinkedList<>();
		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			pictures.add(request.getFile(iter.next()));
		}

		if (pictureUploader == null) {
			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}
		List<PictureMeta> uploadedPicturesMeta = pictureUploader
				.upload(pictures);

		objectMapper = new ObjectMapper();
		String jsonResponse = "{\"files\": ";
		try {
			jsonResponse += objectMapper
					.writeValueAsString(uploadedPicturesMeta);
		} catch (JsonProcessingException e) {
			logger.warn("Request: Upload pictues; Location: PlaceAdController, uploadPictures(); JsonProcessingException" +
					" thrown.");
			logger.warn(""+e.getStackTrace());
			e.printStackTrace();
		}
		jsonResponse += "}";
        handledRequestSuccessfully("PlaceAd", "/profile/placeAd/uploadPictures");
		return jsonResponse;
	}

	/**
	 * Validates the place ad form and persists the ad if successful. On
	 * success, a redirect to the ad description page of the just created ad is
	 * issued. If there were validation errors, the place ad form is displayed
	 * again.
	 */
	@RequestMapping(value = "/profile/placeAd", method = RequestMethod.POST)
	public ModelAndView create(@Valid PlaceAdForm placeAdForm,
			BindingResult result, RedirectAttributes redirectAttributes,
			Principal principal) {
        receivedRequest("PlaceAdController", "/profile/placeAd");
		ModelAndView model = new ModelAndView("placeAd");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);

			if(placeAdForm.getPremium()){
				creditCardService.newPremiumAd(user);
			}

			//TODO: pictureUploader can be null here, if we use back button on browser. fix this. (Maybe browser dependant)
			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			if (pictureUploader == null) {
				pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
			}

			List<String> fileNames = pictureUploader.getFileNames();
			Ad ad = adService.saveFrom(placeAdForm, fileNames, user);

			// triggers all alerts that match the placed ad
			alertService.triggerAlerts(ad);

			// reset the place ad form
			this.placeAdForm = null;
			// reset the picture uploader
			this.pictureUploader = null;


			model = new ModelAndView("redirect:/ad?id=" + ad.getId());
			redirectAttributes.addFlashAttribute("confirmationMessage",
					"Ad placed successfully. You can take a look at it below.");
            handledRequestSuccessfully("PlaceAd", "/profile/placeAd");
		} else {
			model = new ModelAndView("placeAd");
            handlingRequestFailed("PlaceAd", "/profile/placeAd", "BindingResult error");
		}
		return model;
	}

	/**
	 * Gets the descriptions for the pictures that were uploaded with the
	 * current picture uploader.
	 * 
	 * @return a list of picture descriptions or null if no pictures were
	 *         uploaded
	 */
	@RequestMapping(value = "/profile/placeAd/getUploadedPictures", method = RequestMethod.POST)
	public @ResponseBody List<PictureMeta> getUploadedPictures() {
        receivedRequest("PlaceAdController", "/profile/placeAd/getUploadedPictures");
		if (pictureUploader == null) {
			return null;
		}
        handledRequestSuccessfully("PlaceAd", "/profile/placeAd/getUploadedPictures");
		return pictureUploader.getUploadedPictureMetas();
	}

	/**
	 * Deletes the uploaded picture at the given relative url (relative to the
	 * webapp folder).
	 */
	@RequestMapping(value = "/profile/placeAd/deletePicture", method = RequestMethod.POST)
	public @ResponseBody void deleteUploadedPicture(@RequestParam String url) {
        receivedRequest("PlaceAdController", "/profile/placeAd/deletePicture");
		if (pictureUploader != null) {
			String realPath = servletContext.getRealPath(url);
			pictureUploader.deletePicture(url, realPath);
		}
        handledRequestSuccessfully("PlaceAd", "/profile/placeAd/deletePicture");
	}

	/**
	 * Checks if the email passed as post parameter is a valid email. In case it
	 * is valid, the email address is returned. If it is not, a error message is
	 * returned.
	 */
	@RequestMapping(value = "/profile/placeAd/validateEmail", method = RequestMethod.POST)
	@ResponseBody
	public String validateEmail(@RequestParam String email,
			@RequestParam String alreadyIn) {
        receivedRequest("PlaceAdController", "/profile/placeAd/validateEmail");
		User user = userService.findUserByUsername(email);

		Boolean isAdded = adService.checkIfAlreadyAdded(email, alreadyIn);

        handledRequestSuccessfully("PlaceAd", "/profile/placeAd/validateEmail");
		if (user == null) {
			return "This user does not exist, did your roommate register?";
		}
		if (isAdded) {
			return "You already added this person.";
		} else {
			return user.getEmail();
		}
	}

	@ModelAttribute("placeAdForm")
	public PlaceAdForm placeAdForm() {
		if (placeAdForm == null) {
			placeAdForm = new PlaceAdForm();
		}
		return placeAdForm;
	}

}
