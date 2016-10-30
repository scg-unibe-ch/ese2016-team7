package ch.unibe.ese.team1.controller.pojos.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import ch.unibe.ese.team1.model.Gender;
import org.hibernate.validator.constraints.NotBlank;

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

	@Pattern(regexp = "[a-zA-Z]+", message = "Last name must be a valid name")
	@NotNull
	private String lastName;
	
	@NotNull
	private Gender gender;

	/*Check out http://www.regular-expressions.info/creditcard.html*/
	// TODO bring regex from webpage above to work
	@Pattern(regexp = "[0-9]{16}"
			, message = "Please enter a valid Credit Card Number")
	private String creditCardNumber;

	@Max(value = 12, message = "Please enter a valid Month")
	private int creditCardexpireMonth;

	@NotNull
	private int creditCardexpireYear;

	@NotNull
	private int securityCode;

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

	public int getCreditCardexpireMonth() {
		return creditCardexpireMonth;
	}

	public void setCreditCardexpireMonth(int creditCardexpireMonth) {
		this.creditCardexpireMonth = creditCardexpireMonth;
	}

	public int getCreditCardexpireYear() {
		return creditCardexpireYear;
	}

	public void setCreditCardexpireYear(int creditCardexpireYear) {
		this.creditCardexpireYear = creditCardexpireYear;
	}



	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
}
