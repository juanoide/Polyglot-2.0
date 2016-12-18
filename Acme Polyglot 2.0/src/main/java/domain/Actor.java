/* Actor.java
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package domain;





import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Actor() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private String name;
	private String surname;
	private String email;
	private String phone;


	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@NotBlank
	@Pattern(regexp = "(\\d+){9}")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	// Relationships ----------------------------------------------------------
	private UserAccount userAccount;
	private Collection <Folder> folders;
	Collection <Search> searchs;
	
	@NotNull
	@Valid	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	

	@Valid
	@NotNull
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, mappedBy="actor")
	public Collection <Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection <Folder> folders) {
		this.folders = folders;
	}
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, mappedBy="actor")
	public Collection <Search> getSearchs() {
		return searchs;
	}

	public void setSearchs(Collection <Search> searchs) {
		this.searchs = searchs;
	}

}

