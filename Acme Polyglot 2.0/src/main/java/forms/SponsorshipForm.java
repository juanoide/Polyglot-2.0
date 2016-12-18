package forms;



import javax.persistence.Access;
import javax.persistence.AccessType;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Language;
import domain.LanguageExchange;



@Embeddable
@Access(AccessType.PROPERTY)
public class SponsorshipForm {
	
	private String title;
	private String text;
	private String links;
	private String tag;
	private Language descriptionLanguage;
	private String img;
	private Language bannerLanguage;
	private LanguageExchange languageExchange;
	
	
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
	
	@URL
	@NotBlank
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@NotNull
	@Valid
	public Language getDescriptionLanguage() {
		return descriptionLanguage;
	}
	public void setDescriptionLanguage(Language descriptionLanguage) {
		this.descriptionLanguage = descriptionLanguage;
	}
	
	@NotNull
	@Valid
	public Language getBannerLanguage() {
		return bannerLanguage;
	}
	public void setBannerLanguage(Language bannerLanguage) {
		this.bannerLanguage = bannerLanguage;
	}
	
	@NotNull
	@Valid
	public LanguageExchange getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(LanguageExchange languageExchange) {
		this.languageExchange = languageExchange;
	}
	
	
}
