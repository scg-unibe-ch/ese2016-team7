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
    private boolean smokers;

    @Column
    private boolean animals;

    @Column
    private boolean garden;

    @Column
    private boolean balcony;

    @Column
    private boolean cellar;

    @Column
    private boolean furnished;

    @Column
    private boolean garage;

    @Column
    private boolean dishwasher;

    @Column
    private boolean washingMachine;

    @Column
    private String latestMoveInDate;

    @Column
    private String earliestMoveInDate;


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


    public void setEarliestMoveInDate(String erliestMoveInDate) {this.earliestMoveInDate = erliestMoveInDate;}
    public String getEarliestMoveInDate() {return this.earliestMoveInDate;}
    public void setLatestMoveInDate(String latestMoveInDate) { this.latestMoveInDate = latestMoveInDate;}
    public String getLatestMoveInDate() {return this.latestMoveInDate;}

    public void setSmokers(boolean smokers) { this.smokers = smokers; }
    public boolean getSmokers() { return this.smokers; }
    public void setAnimals(boolean animals) { this.animals = animals; }
    public boolean getAnimals() { return this.animals; }
    public void setGarden(boolean garden) { this.garden = garden; }
    public boolean getGarden() { return this.garden; }
    public void setBalcony(boolean balcony) { this.balcony = balcony; }
    public boolean getBalcony() { return this.balcony; }
    public void setCellar(boolean cellar) { this.cellar = cellar; }
    public boolean getCellar() { return this.cellar; }
    public void setFurnished(boolean furnished) { this.furnished = furnished; }
    public boolean getFurnished() { return this.furnished; }
    public void setGarage(boolean garage) { this.garage = garage; }
    public boolean getGarage() { return this.garage; }
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
}
