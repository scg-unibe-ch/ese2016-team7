package ch.unibe.ese.team1.controller.pojos.forms;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/** This form is used when a user wants to edit their profile. */
public class EditProfileForm {

	@NotBlank(message = "Required")
	private String username;
	
	@NotBlank(message = "Required")
	private String password;

	@NotBlank(message = "Required")
	private String firstName;

	@NotBlank(message = "Required")
	private String lastName;
	
	private String aboutMe;

	private String creditCardNumber;

    @Max(value = 12, message = "Please enter a valid Month")
    private int creditCardExpireMonth;

    @NotNull
    private int creditCardExpireYear;

    @NotNull
    private int securityCode;

    private boolean hasCreditCard;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public boolean getHasCreditCard() {
        return hasCreditCard;
    }

    public void setHasCreditCard(boolean hasCreditCard) {
        this.hasCreditCard = hasCreditCard;
    }

    public int getCreditCardExpireYear() {
        return creditCardExpireYear;
    }

    public void setCreditCardExpireYear(int creditCardExpireYear) {
        this.creditCardExpireYear = creditCardExpireYear;
    }

    public int getCreditCardExpireMonth() {
        return creditCardExpireMonth;
    }

    public void setCreditCardExpireMonth(int creditCardExpireMonth) {
        this.creditCardExpireMonth = creditCardExpireMonth;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }
}
