package ch.unibe.ese.team1.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ch.unibe.ese.team1.controller.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
 * This controller handles all requests concerning editing ads.
 */
@Controller
public class EditAdController {

	private final static String IMAGE_DIRECTORY = PlaceAdController.IMAGE_DIRECTORY;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private AdService adService;

	@Autowired
	private EditAdService editAdService;

	@Autowired
	private UserService userService;

	@Autowired
	private AlertService alertService;

    @Autowired
    private VisitService visitService;

	private PictureUploader pictureUploader;

	private ObjectMapper objectMapper;

    /**
	 * Serves the page that allows the user to edit the ad with the given id.
	 */
	@RequestMapping(value = "/profile/editAd", method = RequestMethod.GET)
	public ModelAndView editAdPage(@RequestParam long id, Principal principal) {
		receivedRequest("EditAdController", "/profile/editAd");
		if(!adService.check(principal,id)){
			return new ModelAndView("redirect:/access-denied");
		}

		ModelAndView model = new ModelAndView("editAd");
		Ad ad = adService.getAdById(id);
		model.addObject("ad", ad);
        model.addObject("visits", visitService.getVisitsByAd(ad));

		PlaceAdForm form = editAdService.fillForm(ad);

		model.addObject("placeAdForm", form);

		String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
		if (pictureUploader == null) {
			pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
		}

        handledRequestSuccessfully("EditAdController", "/profile/editAd");
		return model;
	}

	/**
	 * Processes the edit ad form and displays the result page to the user.
	 */
	@RequestMapping(value = "/profile/editAd", method = RequestMethod.POST)
	public ModelAndView editAdPageWithForm(@Valid PlaceAdForm placeAdForm,
										   BindingResult result, Principal principal,
										   RedirectAttributes redirectAttributes, @RequestParam long adId) {
        receivedRequest("EditAdController", "/profile/editAd");
		ModelAndView model = new ModelAndView("placeAd");
		if (!result.hasErrors()) {
			String username = principal.getName();
			User user = userService.findUserByUsername(username);

			String realPath = servletContext.getRealPath(IMAGE_DIRECTORY);
			if (pictureUploader == null) {
				pictureUploader = new PictureUploader(realPath, IMAGE_DIRECTORY);
			}
			List<String> fileNames = pictureUploader.getFileNames();
			Ad ad = editAdService.saveFrom(placeAdForm, fileNames, user, adId);

			// triggers all alerts that match the placed ad
			alertService.triggerAlerts(ad);

			// reset the picture uploader
			this.pictureUploader = null;

			model = new ModelAndView("redirect:/ad?id=" + ad.getId());
			redirectAttributes.addFlashAttribute("confirmationMessage",
					"Ad edited successfully. You can take a look at it below.");
		}

        handledRequestSuccessfully("EditAdController", "/profile/editAd");
		return model;
	}

	/**
	 * Deletes the ad picture with the given id from the list of pictures from
	 * the ad, but not from the server.
	 */
	@RequestMapping(value = "/profile/editAd/deletePictureFromAd", method = RequestMethod.POST)
	public
	@ResponseBody
	void deletePictureFromAd(@RequestParam long adId,
							 @RequestParam long pictureId) {
        receivedRequest("EditAdController", "/profile/editAd/deletePictureFromAd");
		editAdService.deletePictureFromAd(adId, pictureId);
        handledRequestSuccessfully("EditAdController", "/profile/editAd/deletePictureFromAd");
	}

    /**
     * Deletes the ad visit with the given id from the list of visits from
     * the ad, but not from the server.
     */
    @RequestMapping(value = "/profile/editAd/deleteVisitFromAd", method = RequestMethod.POST)
    public
    @ResponseBody
    void deleteVisitFromAd(@RequestParam long adId,
                           @RequestParam long visitId) {
        receivedRequest("EditAdController", "/profile/editAd/deleteVisitFromAd");
        editAdService.deleteVisitFromAd(adId, visitId);
        handledRequestSuccessfully("EditAdController", "/profile/editAd/deleteVisitFromAd");
    }


    /**
	 * Gets the descriptions for the pictures that were uploaded with the
	 * current picture uploader.
	 *
	 * @return a list of picture descriptions or null if no pictures were
	 * uploaded
	 */
	@RequestMapping(value = "/profile/editAd/getUploadedPictures", method = RequestMethod.POST)
	public
	@ResponseBody
	List<PictureMeta> getUploadedPictures() {
        receivedRequest("EditAdController", "/profile/editAd/getUploadedPictures");
		if (pictureUploader == null) {
			return null;
		}
        handledRequestSuccessfully("EditAdController", "/profile/editAd/getUploadedPictures");
		return pictureUploader.getUploadedPictureMetas();
	}

	/**
	 * Uploads the pictures that are attached as multipart files to the request.
	 * The JSON representation, that is returned, is generated manually because
	 * the jQuery Fileupload plugin requires this special format.
	 *
	 * @return A JSON representation of the uploaded files
	 */
	@RequestMapping(value = "/profile/editAd/uploadPictures", method = RequestMethod.POST)
	public
	@ResponseBody
	String uploadPictures(
			MultipartHttpServletRequest request) {
        receivedRequest("EditAdController", "/profile/editAd/uploadPictures");
		List<MultipartFile> pictures = new LinkedList<>();
		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			pictures.add(request.getFile(iter.next()));
		}

		List<PictureMeta> uploadedPicturesMeta = pictureUploader
				.upload(pictures);

		objectMapper = new ObjectMapper();
		String jsonResponse = "{\"files\": ";
		try {
			jsonResponse += objectMapper
					.writeValueAsString(uploadedPicturesMeta);
		} catch (JsonProcessingException e) {
            exceptionLog("/profile/editAd/uploadPictures", "EditAdController, uploadPictures()",
                    "JsonProcessingException", e, "");
			e.printStackTrace();
		}
		jsonResponse += "}";
        handledRequestSuccessfully("EditAdController", "/profile/editAd/uploadPictures");
		return jsonResponse;
	}

	/**
	 * Deletes the uploaded picture at the given relative url (relative to the
	 * webapp folder).
	 */
	@RequestMapping(value = "/profile/editAd/deletePicture", method = RequestMethod.POST)
	public
	@ResponseBody
	void deleteUploadedPicture(@RequestParam String url) {
        receivedRequest("EditAdController", "/profile/editAd/deletePicture");
		if (pictureUploader != null) {
			String realPath = servletContext.getRealPath(url);
			pictureUploader.deletePicture(url, realPath);
		}
        handledRequestSuccessfully("EditAdController", "/profile/editAd/deletePicture");
	}

}
