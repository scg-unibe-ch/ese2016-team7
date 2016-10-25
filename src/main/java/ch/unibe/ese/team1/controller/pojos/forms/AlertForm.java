package ch.unibe.ese.team1.controller.pojos.forms;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ch.unibe.ese.team1.model.User;

/** This form is used when a user wants to create a new alert. */
public class AlertForm {
	
	private User user;

	private boolean studio;
	private boolean house;
	private boolean apartment;
    private boolean bothHouseAndStudio;
    private boolean bothApartmentAndHouse;
    private boolean bothApartmenteAndStudio;
    private boolean apartmentHouseAndStudio;
	private boolean noProperty;

	@NotBlank(message = "Required")
	@Pattern(regexp = "^[0-9]{4} - [-\\w\\s\\u00C0-\\u00FF]*", message = "Please pick a city from the list")
	private String city;

	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "Please enter a positive distance")
	private Integer radius;
	
	@NotNull(message = "Requires a number")
	@Min(value = 0, message = "In your dreams.")
	private Integer price;
	
	private int zipCode;


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zip) {
		this.zipCode = zip;
	}

	public Integer getRadius() {
		return radius;
	}

	public void setRadius(Integer radius) {
		this.radius = radius;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public boolean getStudio() {
		return studio;
	}

	public void setStudio(boolean studio) {
		this.studio = studio;
	}

	public boolean getApartment() {
		return apartment;
	}

	public void setApartment(boolean apartment) {
		this.apartment = apartment;
	}

	public boolean getHouse() {
		return house;
	}

	public void setHouse(boolean house) {
		this.house = house;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean getApartmentHouseAndStudio() {
		return apartmentHouseAndStudio;
	}

	public void setApartmentHouseAndStudio(boolean apartmentHouseAndStudio) {
		this.apartmentHouseAndStudio = apartmentHouseAndStudio;
	}

    public boolean getBothHouseAndStudio() {
        return bothHouseAndStudio;
    }

    public void setBothHouseAndStudio(boolean bothHouseAndStudio) {
        this.bothHouseAndStudio = bothHouseAndStudio;
    }

    public boolean getBothApartmentAndHouse() {
        return bothApartmentAndHouse;
    }

    public void setBothApartmentAndHouse(boolean bothApartmentAndHouse) {
        this.bothApartmentAndHouse = bothApartmentAndHouse;
    }

    public boolean getBothApartmenteAndStudio() {
        return bothApartmenteAndStudio;
    }

    public void setBothApartmenteAndStudio(boolean bothApartmenteAndStudio) {
        this.bothApartmenteAndStudio = bothApartmenteAndStudio;
    }

	public boolean getNoProperty() {
		return noProperty;
	}

	public void setNoProperty(boolean noProperty) {
		this.noProperty = noProperty;
	}
}
