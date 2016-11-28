package ch.unibe.ese.team1.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ch.unibe.ese.team1.controller.pojos.forms.MessageForm;
import ch.unibe.ese.team1.controller.service.MessageService;
import ch.unibe.ese.team1.controller.service.UserService;
import ch.unibe.ese.team1.model.Message;
import ch.unibe.ese.team1.model.User;

import static ch.unibe.ese.team1.logger.LogInterceptor.*;

/** This controller handles all requests concerning messaging. */
@Controller
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private UserService userService;

	/**
	 * Shows the messages page for the currently logged in user. The inbox of
	 * the user is shown.
	 */
	@RequestMapping(value = "/profile/messages", method = RequestMethod.GET)
	public ModelAndView messages(Principal principal) {
		receivedRequest("MessageController", "/profile/messages");
		ModelAndView model = new ModelAndView("messages");
		User user = userService.findUserByUsername(principal.getName());
		model.addObject("messageForm", new MessageForm());
		model.addObject("messages", messageService.getInboxForUser(user));
		handledRequestSuccessfully("MessageController", "/profile/messages");
		return model;
	}

	/**
	 * Gets all messages in the inbox for the currently logged in user.
	 */
	@RequestMapping(value = "/profile/message/inbox", method = RequestMethod.POST)
	public @ResponseBody Iterable<Message> getInbox(Principal principal) {
        receivedRequest("MessageController", "/profile/messages/inbox");
		User user = userService.findUserByUsername(principal.getName());
        handledRequestSuccessfully("MessageController", "/profile/messages/inbox");
		return messageService.getInboxForUser(user);
	}

	/** Gets all messages in the sent folder for the currently logged in user. */
	@RequestMapping(value = "/profile/message/sent", method = RequestMethod.POST)
	public @ResponseBody Iterable<Message> getSent(Principal principal) {
        receivedRequest("MessageController", "/profile/messages/sent");
		User user = userService.findUserByUsername(principal.getName());
        handledRequestSuccessfully("MessageController", "/profile/messages/sent");
		return messageService.getSentForUser(user);
	}

	/** Gets the message with the given id */
	@RequestMapping(value = "/profile/messages/getMessage", method = RequestMethod.GET)
	public @ResponseBody Message getMessage(@RequestParam Long id) {
        receivedRequest("MessageController", "/profile/messages/getMessage");
        handledRequestSuccessfully("MessageController", "/profile/messages/getMessage");
		return messageService.getMessage(id);
	}

	/**
	 * Shows the messages page and validates and/or saves the message passed a
	 * post data.
	 */
	@RequestMapping(value = "/profile/messages", method = RequestMethod.POST)
	public ModelAndView messageSent(@Valid MessageForm messageForm,
			BindingResult bindingResult, Principal principal) {
        receivedRequest("MessageController", "/profile/messages");
		ModelAndView model = new ModelAndView("messages");
		if (!bindingResult.hasErrors()) {
			messageService.saveFrom(messageForm);
			User user = userService.findUserByUsername(principal.getName());
			model.addObject("messageForm", new MessageForm());
			model.addObject("messages", messageService.getInboxForUser(user));
		}
        handledRequestSuccessfully("MessageController", "/profile/messages");
		return model;
	}
	
	/**
	 * Sets the MessageState of a given Message to "READ".
	 */
	@RequestMapping(value="/profile/readMessage", method = RequestMethod.GET)
	public @ResponseBody void readMessage(@RequestParam("id") long id) {
        receivedRequest("MessageController", "/profile/messages/readMessage");
		messageService.readMessage(id);
		handledRequestSuccessfully("MessageController", "/profile/messages/readMessage");
	}
	
	/**
	 * Returns the number of unread messages of the logged in user.
	 */
	@RequestMapping(value="/profile/unread", method = RequestMethod.GET)
	public @ResponseBody int unread(Principal principal) {
        receivedRequest("MessageController", "/profile/messages/unread");
		long id = userService.findUserByUsername(principal.getName()).getId();
        handledRequestSuccessfully("MessageController", "/profile/messages/unread");
		return messageService.unread(id);
	}

	/**
	 * Checks if the email passed as post parameter is a valid email. In case it
	 * is valid, the email address is returned. If it is not, a error message is
	 * returned.
	 */
	@RequestMapping(value = "/profile/messages/validateEmail", method = RequestMethod.POST)
	@ResponseBody
	public String validateEmail(@RequestParam String email) {
        receivedRequest("MessageController", "/profile/messages/validateEmail");
		User user = userService.findUserByUsername(email);
		if (user == null) {
            handlingRequestFailed("MessageController", "/profile/messages/validateEmail", "User does not exist");
			return "This user does not exist.";
		} else {
            handledRequestSuccessfully("MessageController", "/profile/messages/validateEmail");
			return user.getEmail();
		}
	}

	/** Sends a message with the passed parameters */
	@RequestMapping(value = "/profile/messages/sendMessage", method = RequestMethod.POST)
	public @ResponseBody void sendMessage(@RequestParam String subject,
			@RequestParam String text, @RequestParam String recipientEmail,
			Principal principal) {
        receivedRequest("MessageController", "/profile/messages/sendMessage");
		User recipient = userService.findUserByUsername(recipientEmail);
		User sender = userService.findUserByUsername(principal.getName());
		messageService.sendMessage(sender, recipient, subject, text);
        handledRequestSuccessfully("MessageController", "/profile/messages/sendMessage");
	}

}
