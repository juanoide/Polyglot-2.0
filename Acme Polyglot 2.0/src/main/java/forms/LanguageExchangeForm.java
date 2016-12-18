package forms;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Language;



@Embeddable
@Access(AccessType.PROPERTY)
public class LanguageExchangeForm {
	
	private Date date;
	private String place;
	private Collection<Language> languages;
	private String title;
	private String text;
	private String links;
	private String tag;
	private Language descriptionLanguage;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	@Future
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
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	public String getLinks() {
		return links;
	}
	
	public void setLinks(String links) {
		this.links = links;
	}
	
	@SafeHtml(whitelistType= WhiteListType.NONE)
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	@NotNull
	@Valid
	public Language getDescriptionLanguage() {
		return descriptionLanguage;
	}
	public void setDescriptionLanguage(Language descriptionLanguage) {
		this.descriptionLanguage = descriptionLanguage;
	}
	
}
