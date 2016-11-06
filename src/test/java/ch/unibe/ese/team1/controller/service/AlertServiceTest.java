package ch.unibe.ese.team1.controller.service;

import static ch.unibe.ese.team1.model.Property.HOUSE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.Alert;
import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.UserRole;
import ch.unibe.ese.team1.model.dao.AdDao;
import ch.unibe.ese.team1.model.dao.AlertDao;
import ch.unibe.ese.team1.model.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/springMVC.xml",
		"file:src/main/webapp/WEB-INF/config/springData.xml",
		"file:src/main/webapp/WEB-INF/config/springSecurity.xml"})
@WebAppConfiguration
public class AlertServiceTest {
	
	@Autowired
	AdDao adDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AlertDao alertDao;
	
	@Autowired
	AlertService alertService;
	
	@Test
	public void createAlerts() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();
		
		// Create user Adolf Ogi
		User adolfOgi = createUser("humo@ogi.ch", "password", "Adolf", "Ogi",
				Gender.MALE, "4040404040404040",12,12);
		adolfOgi.setAboutMe("Wallis rocks");
		userDao.save(adolfOgi);
		
		// Create 2 alerts for Adolf Ogi
		Alert alert = new Alert();
		alert.setUser(adolfOgi);
		alert.setStudio(true);
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(adolfOgi);
		alert.setHouse(true);
		alert.setApartment(true);
		alert.setStudio(true);
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);
		
		//copy alerts to a list
		Iterable<Alert> alerts = alertService.getAlertsByUser(adolfOgi);
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);
		
		//begin the actual testing
		assertEquals(2, alertList.size());
		assertEquals(adolfOgi, alertList.get(0).getUser());
		assertEquals("Bern", alertList.get(1).getCity());
		assertTrue(alertList.get(0).getRadius() > alertList.get(1).getRadius());
	}
	
	@Test
	public void mismatchChecks() {
		ArrayList<Alert> alertList = new ArrayList<Alert>();
		
		User thomyF = createUser("thomy@k.ch", "password", "Thomy", "F",
				Gender.MALE, "4040404040404040",12,12);
		thomyF.setAboutMe("Supreme hustler");
		userDao.save(thomyF);
		
		// Create 2 alerts for Thomy F
		Alert alert = new Alert();
		alert.setUser(thomyF);
		alert.setCity("Bern");
		alert.setZipcode(3000);
		alert.setPrice(1500);
		alert.setRadius(100);
		alert.setApartment(true);
		alert.setHouse(false);
		alertDao.save(alert);
		
		alert = new Alert();
		alert.setUser(thomyF);
		alert.setStudio(false);
		alert.setHouse(true);
		alert.setApartment(false);
		alert.setCity("Bern");
		alert.setZipcode(3002);
		alert.setPrice(1000);
		alert.setRadius(5);
		alertDao.save(alert);
		
		Iterable<Alert> alerts = alertService.getAlertsByUser(userDao.findByUsername("thomy@k.ch"));
		for(Alert returnedAlert: alerts)
			alertList.add(returnedAlert);
		
		//save an ad
		Date date = new Date();
		Ad oltenResidence= new Ad();
		oltenResidence.setZipcode(4600);
		oltenResidence.setMoveInDate(date);
		oltenResidence.setCreationDate(date);
		oltenResidence.setPrice(1200);
		oltenResidence.setSquareFootage(42);
		oltenResidence.setProperty(HOUSE);
		oltenResidence.setSmokers(true);
		oltenResidence.setAnimals(false);
		oltenResidence.setRoomDescription("blah");
		oltenResidence.setUser(thomyF);
		oltenResidence.setTitle("Olten Residence");
		oltenResidence.setStreet("Florastr. 100");
		oltenResidence.setCity("Olten");
		oltenResidence.setGarden(false);
		oltenResidence.setBalcony(false);
		oltenResidence.setCellar(false);
		oltenResidence.setFurnished(false);
		oltenResidence.setGarage(false);
		oltenResidence.setExpireDate(new Date());
		adDao.save(oltenResidence);
		
		assertFalse(alertService.radiusMismatch(oltenResidence, alertList.get(0)));
		assertTrue(alertService.radiusMismatch(oltenResidence, alertList.get(1)));
		assertTrue(alertService.typeMismatch(oltenResidence, alertList.get(0)));
		assertFalse(alertService.typeMismatch(oltenResidence, alertList.get(1)));
	}
	
	//Lean user creating method
	User createUser(String email, String password, String firstName,
			String lastName, Gender gender, String number, int month, int yeas) {
		User user = new User();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEnabled(true);
		user.setGender(gender);
		Set<UserRole> userRoles = new HashSet<>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRoles.add(role);
		user.setUserRoles(userRoles);
		user.setCreditCardNumber(number);
		user.setCreditCardExpireMonth(month);
		user.setCreditCardExpireYear(yeas);
		return user;
	}
}
