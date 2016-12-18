

package domain;


import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;


@Entity
@Access(AccessType.PROPERTY)
public class Agent extends Actor {

	// Constructors -----------------------------------------------------------

	public Agent() {
		super();
	}
	
	// Attributes -------------------------------------------------------------
	
	// Relationships ----------------------------------------------------------
	private Collection<Sponsorship> sponsorships;


	

	@Valid
	@OneToMany(mappedBy="agent")
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}
	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
	
	
	
}
