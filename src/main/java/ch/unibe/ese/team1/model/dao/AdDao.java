package ch.unibe.ese.team1.model.dao;

import ch.unibe.ese.team1.model.Property;
import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;

import java.util.Date;

public interface AdDao extends CrudRepository<Ad, Long> {
	
	/** this will be used if both rooms AND studios are searched */
	public Iterable<Ad> findByPriceLessThan (int price);

	public Iterable<Ad> findByPriceLessThanAndExpired (int price,boolean expired);


	/** this will be used if only rooms or studios are searched */
	public Iterable<Ad> findByPropertyAndPriceLessThan(Property property,
                                                       int i);

	public Iterable<Ad> findByPropertyAndPropertyAndPriceLessThan(Property property, Property property2, int i);


	public Iterable<Ad> findByUser(User user);

	public Iterable<Ad> findByExpireDateLessThanAndExpired(Date date, boolean expired);
}