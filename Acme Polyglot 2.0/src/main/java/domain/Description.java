
package domain;




import java.util.Collection;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToOne;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;



@Entity
@Access(AccessType.PROPERTY)
public class Description extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Description() {
		super();


	}

	// Attributes -------------------------------------------------------------


	
	private String title;
	private String text;
	private Collection<String> links;
	private Collection<String> tag;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ElementCollection
	public Collection<String> getLinks() {
		return links;
	}
	
	
	public void setLinks(Collection<String> links) {
		this.links = links;
	}
	
	@ElementCollection
	public Collection<String> getTag() {
		return tag;
	}
	
	public void setTag(Collection<String> tag) {
		this.tag = tag;
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