package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={
		@Index(columnList=("name"))})
public class Folder extends DomainEntity{
	
	//Constructors -----------------------------
	public Folder() {
		super();
	}
		
	//Attributes -------------------------------
	private String name;
	private Boolean systemFolder;
		
	@NotBlank
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
	@NotNull
	public Boolean getSystemFolder() {
		return systemFolder;
	}
	public void setSystemFolder(Boolean systemFolder) {
		this.systemFolder = systemFolder;
	}
		
		
	//Relationships --------------------------
		

	private Actor actor;
	private LanguageExchange languageExchange;
	private Collection <Message> messages;
		
	@Valid
	@ManyToOne(optional=true)
	public LanguageExchange getLanguageExchange() {
		return languageExchange;
	}
	public void setLanguageExchange(LanguageExchange languageExchange) {
		this.languageExchange = languageExchange;
	}
	
	@Valid
	@ManyToOne(optional=false)
	@NotNull
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Valid
	@OneToMany(mappedBy="folder")
	@NotNull
	public Collection <Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection <Message> messages) {
		this.messages = messages;
	}
		
		

}