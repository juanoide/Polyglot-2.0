
package domain;




import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Entity
@Access(AccessType.PROPERTY)
public class Language extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Language() {
		super();


	}

	// Attributes -------------------------------------------------------------


	

	private String code;

	
	
	@Column(unique=true)
	@Pattern(regexp="\\w{2}")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	
	// Relationships ----------------------------------------------------------
		
	private Collection<ExpectedTalk> expectedTalks;	
	private Administrator administrator;
	private Collection<Description> descriptionsOwners;
	private Collection<Description> descriptions;
	private Collection<Banner> banners;
	private Collection<Message> messages;
	
	//@OneToMany(mappedBy="language",fetch=FetchType.EAGER)
	
	@Valid
	@OneToMany(mappedBy="language")
	public Collection<ExpectedTalk> getExpectedTalks() {
		return expectedTalks;
	}
	public void setExpectedTalks(Collection<ExpectedTalk> expectedTalks) {
		this.expectedTalks = expectedTalks;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Administrator getAdministrator() {
		return administrator;
	}
	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}
	
	
	@Valid
	@OneToMany(mappedBy="language")
	public Collection<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Collection<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Description> getDescriptionsOwners() {
		return descriptionsOwners;
	}
	public void setDescriptionsOwners(Collection<Description> descriptionsOwners) {
		this.descriptionsOwners = descriptionsOwners;
	}
	
	@Valid
	@OneToMany(mappedBy="language")
	public Collection<Banner> getBanners() {
		return banners;
	}
	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}
	
	@Valid
	@OneToMany(mappedBy="language")
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
	
	
}