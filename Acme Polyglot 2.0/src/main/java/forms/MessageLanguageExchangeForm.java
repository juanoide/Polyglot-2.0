package forms;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Language;


public class MessageLanguageExchangeForm {
	

	//Constructors -----------------------------
	public MessageLanguageExchangeForm() {
		super();
	}
		
	//Attributes -------------------------------
	private String title;
	private String text;
	private String links;
	private String tag;
	private Language language;
	private int languageExchange;
	
		

	@NotBlank
	public String getTitle() {
		return title;
	}
		
	public void setTitle(String title) {
		this.title = title;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getText() {
		return text;
	}		
	public void setText(String text) {
		this.text = text;
	}
	
	@Min(0)
	public int getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(int languageExchange) {
		this.languageExchange = languageExchange;
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
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}


}
