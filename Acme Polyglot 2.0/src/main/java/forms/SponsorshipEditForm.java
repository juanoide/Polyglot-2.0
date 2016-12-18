package forms;



import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


import domain.LanguageExchange;



@Embeddable
@Access(AccessType.PROPERTY)
public class SponsorshipEditForm {
	
	
	private LanguageExchange languageExchange;
	
	
	
	@NotNull
	@Valid
	public LanguageExchange getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(LanguageExchange languageExchange) {
		this.languageExchange = languageExchange;
	}
	
	
}
