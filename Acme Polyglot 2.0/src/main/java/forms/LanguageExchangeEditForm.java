package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Language;



@Embeddable
@Access(AccessType.PROPERTY)
public class LanguageExchangeEditForm {
	
	private Date date;
	private String place;
	private Collection<Language> languages;

	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@URL
	@NotBlank
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	@NotNull
	public Collection<Language> getLanguages() {
		return languages;
	}
	public void setLanguages(Collection<Language> languages) {
		this.languages = languages;
	}
	
	
	
}
