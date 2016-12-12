package ch.unibe.ese.team1.controller.pojos.forms;

import ch.unibe.ese.team1.model.Property;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/** This form is used when a user wants to place a new ad. */
public class PlaceAdForm {
	
	@NotBlank(message = "Required")
	private String title;
	
	@NotBlank(message = "Required")
	private String street;
	
	@Pattern(regexp = "^[0-9]{4} - [-\\w\\s\\u00C0-\\u00FF;]*", message = "Please pick a city from the list")
	private String city;
	
	@NotBlank(message = "Required")
    @Pattern(regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{4}$", message = "Please enter a valid date.")
    private String moveInDate;

	@Min(value = 1, message = "Has to be equal to 1 or more")
    @NotNull(message = "Has to be a valid number.")
	private Integer price;

	@Min(value = 1, message = "Has to be equal to 1 or more")
    @NotNull(message = "Has to be a valid number.")
    private Integer squareFootage;

    @Min(value = 1, message = "Has to be equal to 1 or more")
	@NotNull(message = "Has to be a valid number.")
    private Float numberRooms;

	@NotBlank(message = "Required")
	private String roomDescription;

	private boolean premium;

	private Property property;
	
	private boolean smokers;
	private boolean animals;
	private boolean garden;
	private boolean balcony;
	private boolean cellar;
	private boolean furnished;
	private boolean garage;
	private boolean dishwasher;
	private boolean washingMachine;

	private List<String> visits;

    @NotNull(message = "Has to be a valid number.")
    private Integer instantBuyPrice;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public Integer getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(Integer squareFootage) {
		this.squareFootage = squareFootage;
	}

    public float getNumberRooms() {
		if (numberRooms != null) {
			return numberRooms;
		}
		else
			return 1;
    }

    public void setNumberRooms(float numberRooms) {
        this.numberRooms = numberRooms;
    }

	public boolean isSmokers() {
		return smokers;
	}

	public void setSmokers(boolean smoker) {
		this.smokers = smoker;
	}

	public boolean isAnimals() {
		return animals;
	}

	public void setAnimals(boolean animals) {
		this.animals = animals;
	}
	
	public boolean getGarden() {
		return garden;
	}

	public void setGarden(boolean garden) {
		this.garden = garden;
	}

	public boolean getBalcony() {
		return balcony;
	}

	public void setBalcony(boolean balcony) {
		this.balcony = balcony;
	}
	
	public boolean getCellar() {
		return cellar;
	}

	public void setCellar(boolean cellar) {
		this.cellar = cellar;
	}
	
	public boolean isFurnished() {
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

    public boolean getDishwasher() {
        return dishwasher;
    }

    public void setDishwasher(boolean dishwasher) {
        this.dishwasher = dishwasher;
    }

    public boolean getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(boolean washingMachine) {
        this.washingMachine = washingMachine;
    }

	public String getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(String moveInDate) {
		this.moveInDate = moveInDate;
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
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}


	public List<String> getVisits() {
		return visits;
	}

	public void setVisits(List<String> visits) {
		this.visits = visits;
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

    public boolean getPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }


	public Integer getInstantBuyPrice() {
		return instantBuyPrice;
	}

	public void setInstantBuyPrice(Integer instantBuyPrice) {
		this.instantBuyPrice = instantBuyPrice;
	}
}
