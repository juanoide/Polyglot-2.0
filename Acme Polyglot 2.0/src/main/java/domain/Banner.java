
package domain;




import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;



@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Banner() {
		super();


	}

	// Attributes -------------------------------------------------------------



	private String img;

	@URL
	@NotBlank
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	// Relationships ----------------------------------------------------------
		private Language language;
	
		
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}

	
}