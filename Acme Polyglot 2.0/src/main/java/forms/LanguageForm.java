package forms;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;


import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Language;



@Embeddable
@Access(AccessType.PROPERTY)
public class LanguageForm {
	
	private String code;
	
	private String title;
	private String text;
	private String links;
	private String tag;
	private Language descriptionLanguage;
	
	@Column(unique=true)
	@Pattern(regexp="\\w{2}")
	@NotNull
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
