package ch.unibe.ese.team1.model;

import java.util.Date;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Describes an advertisement that users can place and search for. */
@Entity
public class Ad {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String street;

	@Column(nullable = false)
	private int zipcode;

	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	//@JsonFormat(pattern = "HH:mm, dd.MM.yyyy")
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDate;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date moveInDate;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private int squareFootage;

	@Column(nullable = false)
	private float numberRooms;

	@Column(nullable = false)
	@Lob
	private String roomDescription;

	@Column(nullable = false)
	@Lob
	private String preferences;

	@Column(nullable = false)
	private boolean smokers;

	@Column(nullable = false)
	private boolean animals;

	@Column(nullable = false)
	private boolean garden;

	@Column(nullable = false)
	private boolean balcony;

	@Column(nullable = false)
	private boolean cellar;

	@Column(nullable = false)
	private boolean furnished;

	@Column(nullable = false)
	private boolean garage;

    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Property property;

	@Column(nullable = false)
	private boolean premium = false;

	@Fetch(FetchMode.SELECT)
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<AdPicture> pictures;

	@ManyToOne(optional = false)
	private User user;
	
	@OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Visit> visits;

	@Column(nullable = false)
	private boolean expired = false;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Property getProperty() {
		return property;
	}

	public void  setProperty(String property){
		property.toLowerCase();
		switch (property){
			case "house":
				this.property=Property.HOUSE;
				break;
			case "apartment":
				this.property=Property.APARTMENT;
				break;
			case "studio":
				this.property=Property.STUDIO;
				break;

			}
		}

	public void setProperty(Property property) {
		this.property = property;
	}

	public boolean getSmokers() {
		return smokers;
	}

	public void setSmokers(boolean allowsSmokers) {
		this.smokers = allowsSmokers;
	}

	public boolean getAnimals() {
		return animals;
	}

	public void setAnimals(boolean allowsAnimals) {
		this.animals = allowsAnimals;
	}

	public boolean getGarden() {
		return garden;
	}

	public void setGarden(boolean hasGarden) {
		this.garden = hasGarden;
	}

	public boolean getBalcony() {
		return balcony;
	}

	public void setBalcony(boolean hasBalcony) {
		this.balcony = hasBalcony;
	}

	public boolean getCellar() {
		return cellar;
	}

	public void setCellar(boolean hasCellar) {
		this.cellar = hasCellar;
	}

	public boolean getFurnished() {
		return furnished;
	}

	public void setFurnished(boolean furnished) {
		this.furnished = furnished;
	}

	public boolean getGarage() {
		return garage;
	}

	public void setGarage(boolean garage) {
		this.garage = garage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public Date getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(Date moveInDate) {
		this.moveInDate = moveInDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(int squareFootage) {
		this.squareFootage = squareFootage;
	}

	public float getNumberRooms() {
		return numberRooms;
	}

	public void setNumberRooms(float numberRooms) {
		this.numberRooms = numberRooms;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public String getPreferences() {
		return preferences;
	}

	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}

	public List<AdPicture> getPictures() {
		return pictures;
	}

	public void setPictures(List<AdPicture> pictures) {
		this.pictures = pictures;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getDate(boolean date) {
        return moveInDate;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	// equals method is defined to check for id only
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ad other = (Ad) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public boolean getExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean getPremium() {
		return premium;
	}

	public void setPremium(boolean premium) {
		this.premium = premium;
	}
}