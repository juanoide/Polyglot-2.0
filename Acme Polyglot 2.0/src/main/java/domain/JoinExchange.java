

package domain;



import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Entity
@Access(AccessType.PROPERTY)
public class JoinExchange extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public JoinExchange() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Polyglot polyglot;
	private LanguageExchange languageExchange;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Polyglot getPolyglot() {
		return polyglot;
	}
	public void setPolyglot(Polyglot polyglot) {
		this.polyglot = polyglot;
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
