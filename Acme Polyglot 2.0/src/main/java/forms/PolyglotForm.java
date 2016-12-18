package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class PolyglotForm {
	
	private String name, surname, phoneNumber, email;
	
	private String username;
	private String password;
	private String repeatPassword;
	private Boolean valid;

	

	@SafeHtml(whitelistType= WhiteListType.NONE)
	@Size(min = 5, max = 32)
	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@Size(min = 5, max = 32)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@SafeHtml(whitelistType= WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@Pattern(regexp = "(\\d+){9}")
	@NotBlank
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@Email
	@NotBlank
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	

}
