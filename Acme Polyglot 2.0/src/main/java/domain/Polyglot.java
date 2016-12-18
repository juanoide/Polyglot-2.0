

package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;


@Entity
@Access(AccessType.PROPERTY)
public class Polyglot extends Actor {

	// Constructors -----------------------------------------------------------

	public Polyglot() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Collection<LanguageExchange> organiseExchanges;
	private Collection<JoinExchange> joinExchanges;	

	

	@Valid
	@OneToMany(mappedBy="polyglotOrganise")
	public Collection<LanguageExchange> getOrganiseExchanges() {
		return organiseExchanges;
	}
	public void setOrganiseExchanges(Collection<LanguageExchange> organiseExchanges) {
		this.organiseExchanges = organiseExchanges;
	}
	
	@Valid
	@OneToMany(mappedBy="polyglot")
	public Collection<JoinExchange> getJoinExchanges() {
		return joinExchanges;
	}
	public void setJoinExchanges(Collection<JoinExchange> joinExchanges) {
		this.joinExchanges = joinExchanges;
	}
	
}
