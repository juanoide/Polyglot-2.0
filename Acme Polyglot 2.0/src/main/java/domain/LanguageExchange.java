
package domain;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Access(AccessType.PROPERTY)
public class LanguageExchange extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public LanguageExchange() {
		super();


	}

	// Attributes -------------------------------------------------------------


	

	private Date date;
	private String place;
	private boolean cancel;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@NotNull
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@URL
	@NotBlank
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	
	
	public boolean getCancel(){
		return cancel;
	}
	
	public void setCancel(boolean cancel){
		this.cancel =cancel;
	}
	
	// Relationships ----------------------------------------------------------
		
	private Collection<ExpectedTalk> expectedTalks;	
	private Collection<JoinExchange> joinExchanges;	
	private Polyglot polyglotOrganise;
	private Collection<Description> descriptions;
	private Collection<Sponsorship> sponsorships;
	private Collection <Folder> folders;

	@Valid
	@OneToMany(mappedBy="languageExchange")
	public Collection <Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection <Folder> folders) {
		this.folders = folders;
	}
	
	@Valid
	@OneToMany(mappedBy="languageExchange")
	public Collection<ExpectedTalk> getExpectedTalks() {
		return expectedTalks;
	}
	public void setExpectedTalks(Collection<ExpectedTalk> expectedTalks) {
		this.expectedTalks = expectedTalks;
	}
	
	@Valid
	@OneToMany(mappedBy="languageExchange")
	public Collection<JoinExchange> getJoinExchanges() {
		return joinExchanges;
	}
	public void setJoinExchanges(Collection<JoinExchange> joinExchanges) {
		this.joinExchanges = joinExchanges;
	}
	
	@NotNull
	@Valid
	@ManyToOne(optional = true)
	public Polyglot getPolyglotOrganise() {
		return polyglotOrganise;
	}
	public void setPolyglotOrganise(Polyglot polyglotOrganise) {
		this.polyglotOrganise = polyglotOrganise;
	}

	
	@NotNull
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Description> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Collection<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	@Valid
	@OneToMany(mappedBy="languageExchange")
	public Collection<Sponsorship> getSponsorships() {
		return sponsorships;
	}
	public void setSponsorships(Collection<Sponsorship> sponsorships) {
		this.sponsorships = sponsorships;
	}
		

	
}