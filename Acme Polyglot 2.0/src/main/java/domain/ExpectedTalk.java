

package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class ExpectedTalk extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public ExpectedTalk() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Language language;
	private LanguageExchange languageExchange;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public LanguageExchange getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(LanguageExchange languageExchange) {
		this.languageExchange = languageExchange;
	}
	
	
}
