package ch.unibe.ese.team1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/** Describes a user on the platform. */
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private boolean enabled;

    @Column(nullable = false)
    private boolean hasCreditCard;

	private long premiumAdMoney;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles;

	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	private UserPicture picture;
	
	@Column(nullable = true)
	@Lob
	private String aboutMe;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ad> bookmarkedAds;

	@Column(nullable = true)
	private String creditCardNumber;

	@Column(nullable = true)
	private int creditCardExpireMonth;

	@Column(nullable = true)
	private int creditCardExpireYear;

    @Column(nullable = true)
    private String securityCode;

	private int moneySpent;

	private int moneyEarned;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserPicture getPicture() {
		return picture;
	}

	public void setPicture(UserPicture picture) {
		this.picture = picture;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	public List<Ad> getBookmarkedAds() {
		return bookmarkedAds;
	}
	
	public void setBookmarkedAds(List<Ad> bookmarkedAds) {
		this.bookmarkedAds = bookmarkedAds;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

    public boolean getHasCreditCard() {
        updateHasCreditCard();
        return hasCreditCard;
    }

    private void updateHasCreditCard() {
        if (getCreditCardNumber() == "" || getCreditCardNumber() == null)
            setHasCreditCard(false);
        else
            setHasCreditCard(true);
    }

    public void setHasCreditCard(boolean hasCreditCard) {
        this.hasCreditCard = hasCreditCard;
    }

	public int getCreditCardExpireMonth() {
		return creditCardExpireMonth;
	}

	public void setCreditCardExpireMonth(int creditCardExpireMonth) {
		this.creditCardExpireMonth = creditCardExpireMonth;
	}

	public int getCreditCardExpireYear() {
		return creditCardExpireYear;
	}

	public void setCreditCardExpireYear(int creditCardExpireYear) {
		this.creditCardExpireYear = creditCardExpireYear;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

	public int getMoneySpent() {
		return moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getMoneyEarned() {
		return moneyEarned;
	}

	public void setMoneyEarned(int moneyEarned) {
		this.moneyEarned = moneyEarned;
	}

    public void addMoneyEarned(int amount) {
        if (amount >= 0)
            setMoneyEarned(getMoneyEarned()+amount);
    }

	public void addMoneySpent(int amount) {
        if (amount >= 0)
			setMoneySpent(getMoneySpent()+amount);
    }

    public void addPremiumAdMoney(int amount){
		this.premiumAdMoney+=amount;
	}

	public long getPremiumAdMoney(){
		return this.premiumAdMoney;
	}


}
