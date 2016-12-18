package forms;

import org.hibernate.validator.constraints.NotBlank;

public class FolderForm {
	
	//Constructors -----------------------------
	public FolderForm() {
		super();
	}
		
	//Attributes -------------------------------
	private String name;

	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
