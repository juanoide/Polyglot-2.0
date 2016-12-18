
package domain;

import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;




@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Sponsorship() {
		super();


	}

	// Attributes -------------------------------------------------------------


	



	
	
	
	// Relationships ----------------------------------------------------------
		

	
	private LanguageExchange languageExchange;
	private Agent agent;
	private Collection<Banner> banners;	
	private Collection<Description> descriptions;
	

	
	
	
	@Valid
	@ManyToOne(optional = true)
	public LanguageExchange getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(LanguageExchange languageExchange) {
		this.languageExchange = languageExchange;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Banner> getBanners() {
		return banners;
	}
	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}
	
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Collection<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
		

	
}