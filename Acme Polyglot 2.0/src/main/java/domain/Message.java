package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity{
	
	//Constructors -----------------------------
	public Message() {
		super();
	}


	//Attributes -------------------------------
	private String title;
	private String text;
	private Collection<String> links;
	private Collection<String> tag;
	private Date moment;
	private boolean spam;
	
	@NotNull
	public boolean getSpam() {
		return spam;
	}
	public void setSpam(boolean spam) {
		this.spam = spam;
	}
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
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
		private Folder folder;
		private Actor sender;
		private Actor recipient;
		
		
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
	@ManyToOne(optional=false)
	public Actor getSender() {
		return sender;
	}
	public void setSender(Actor sender) {
		this.sender = sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional=true)
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	
	

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}


}



