/* Administrator.java
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
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Administrator extends Actor {

	// Constructors -----------------------------------------------------------

	public Administrator() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Collection<Language> languages;
	
	
	@Valid
	@OneToMany(mappedBy="administrator")
	public Collection<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(Collection<Language> languages) {
		this.languages = languages;
	}
	
}
