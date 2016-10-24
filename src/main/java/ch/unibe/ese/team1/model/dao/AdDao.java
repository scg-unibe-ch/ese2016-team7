package ch.unibe.ese.team1.model.dao;

import org.springframework.data.repository.CrudRepository;

import ch.unibe.ese.team1.model.Ad;
import ch.unibe.ese.team1.model.User;

public interface AdDao extends CrudRepository<Ad, Long> {
	
	/** this will be used if both rooms AND studios are searched */
	public Iterable<Ad> findByPriceLessThan (int price);

	/** this will be used if only rooms or studios are searched */
	public Iterable<Ad> findByPropertyAndPriceLessThan(boolean studio,
                                                       int i);


	public Iterable<Ad> findByUser(User user);
}