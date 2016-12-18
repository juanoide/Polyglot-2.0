package forms;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;





@Embeddable
@Access(AccessType.PROPERTY)
public class LanguageEditForm {
	
	private String code;
	
	@Column(unique=true)
	@Pattern(regexp="\\w{2}")
	@NotNull
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
