package ch.unibe.ese.team1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Describes an alert. An alert can be created by a user. If ads matching the
 * criteria of the alert are added to the platform later, the user will be
 * notified.
 */
@Entity
public class Alert {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private int zipcode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int radius;

    @Column
    private boolean house;

    @Column
    private boolean studio;

    @Column
    private boolean apartment;

    @Column
    private boolean bothHouseAndStudio;

    @Column
    private boolean bothHouseAndApartment;

    @Column
    private boolean bothApartmenteAndStudio;

    @Column
    private boolean apartmentHouseAndStudio;

    public Alert() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getStudio() {
        return studio;
    }

    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    public void setHouse(boolean house) {
        this.house = house;
    }

    public boolean getHouse() {
        return house;
    }

    public void setApartment(boolean apartment) {
        this.apartment = apartment;
    }

    public boolean getApartment() {
        return apartment;
    }

    public boolean getApartmentHouseAndStudio() {
        return apartmentHouseAndStudio;
    }

    public void setApartmentHouseAndStudio(boolean apartmentHouseAndStudio) {
        this.apartmentHouseAndStudio = apartmentHouseAndStudio;
    }

    public boolean getBothApartmenteAndStudio() {
        return bothApartmenteAndStudio;
    }

    public void setBothApartmenteAndStudio(boolean bothApartmenteAndStudio) {
        this.bothApartmenteAndStudio = bothApartmenteAndStudio;
    }

    public boolean getBothHouseAndApartment() {
        return bothHouseAndApartment;
    }

    public void setBothHouseAndApartment(boolean bothHouseAndApartment) {
        this.bothHouseAndApartment = bothHouseAndApartment;
    }

    public boolean getBothHouseAndStudio() {
        return bothHouseAndStudio;
    }

    public void setBothHouseAndStudio(boolean bothHouseAndStudio) {
        this.bothHouseAndStudio = bothHouseAndStudio;
    }

    public boolean hasProperty(Property property) {
        switch (property) {
            case HOUSE:
                return house;
            case APARTMENT:
                return apartment;
            case STUDIO:
                return studio;
        }
        // if nothing was matched in the switch statement, return false
        return false;
    }
}
