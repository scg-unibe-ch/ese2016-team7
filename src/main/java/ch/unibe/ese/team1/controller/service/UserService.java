package ch.unibe.ese.team1.controller.service;

import ch.unibe.ese.team1.model.Gender;
import ch.unibe.ese.team1.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team1.model.User;
import ch.unibe.ese.team1.model.dao.UserDao;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * Handles all database actions concerning users.
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	/** Gets the user with the given username. */
	@Transactional
	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	/** Gets the user with the given id. */
	@Transactional
	public User findUserById(long id) {
		return userDao.findUserById(id);
	}

	/** Gets the user with the given id. */
	@Transactional
	public int countUsers() {
		return (int) userDao.count();
	}

	@Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;


    @PostConstruct
    public void makeFlatFindrUser(){
        User user = new User();
        user.setUsername("FlatFindr");
        user.setPassword("ese");
        user.setEmail("flat@findr.ch");
        user.setFirstName("FlatFindr");
        user.setLastName("FlatFindr");
        user.setEnabled(true);
        user.setGender(Gender.ADMIN);
        Set<UserRole> userRoles = new HashSet<>();
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        role.setUser(user);
        userRoles.add(role);
        user.setUserRoles(userRoles);
		user.setCreditCardNumber("404040404040");
		user.setCreditCardExpireMonth(0);
		user.setCreditCardExpireYear(0);
        userDao.save(user);
    }

	@Transactional
	public void login(String email) {
		User user = userDao.findByUsername(email);
		Authentication request = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		Authentication result = authenticationManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
	}

}
