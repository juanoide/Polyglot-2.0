
package domain;




import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;



@Entity
@Access(AccessType.PROPERTY)
public class Search extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Search() {
		super();


	}

	// Attributes -------------------------------------------------------------



	private String name;
	private Integer count;

	
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Min(1)
	@NotNull
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	// Relationships ----------------------------------------------------------
		private Actor actor;
	
		
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	
}