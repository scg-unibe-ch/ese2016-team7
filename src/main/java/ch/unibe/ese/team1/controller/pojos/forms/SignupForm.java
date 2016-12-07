package ch.unibe.ese.team1.controller.pojos.forms;

import ch.unibe.ese.team1.model.Gender;

import javax.validation.constraints.*;

/** This form is used when a user want to sign up for an account. */
public class SignupForm {
	
	@Size(min = 6, message = "Password must be at least 6 characters long")
	@NotNull
	private String password;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Must be valid email address")
	@NotNull
	private String email;

	@Pattern(regexp = "[a-zA-Z]+", message = "First name must be a valid name")
	@NotNull
	private String firstName;

	@Pattern(regexp = "[a-zA-Z]*", message = "Last name must be a valid name")
	@NotNull
	private String lastName;
	
	@NotNull
	private Gender gender;

	/*Check out http://www.regular-expressions.info/creditcard.html*/
	// TODO bring regex from webpage above to work
	@Pattern(regexp = "[0-9]{16}|null|"
			, message = "Please enter a valid Credit Card Number")
	private String creditCardNumber;

	@NotNull
    private boolean hasCreditCard;

	@Max(value = 12, message = "Please enter a valid Month")
	@Min(value = 0, message = "Please enter a valid Month")
	@NotNull
	private int creditCardExpireMonth;

	@NotNull
	@Min(value = 0, message = "Please enter a valid year")
	@Max(value = 99, message = "Please enter a valid year (e.g. 16")
	private int creditCardExpireYear;

	@Pattern(regexp = "[0-9]{3}", message = "Please enter a valid securtiy code")
	@NotNull(message = "Please enter a valid securtiy code")
	private String securityCode;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public boolean getHasCreditCard() {
		return hasCreditCard;
	}

    public void setHasCreditCard(boolean hasCreditCard) {
        this.hasCreditCard = hasCreditCard;
    }
}
