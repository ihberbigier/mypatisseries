package br.com.bb.my_patisseries.model.dto;

public class FormErrorDTO {

	private String field;
	
	private String message;
	
	public FormErrorDTO(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
	
	
}
