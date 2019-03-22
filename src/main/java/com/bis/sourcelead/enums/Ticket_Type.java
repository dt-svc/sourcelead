package com.bis.sourcelead.enums;

public enum Ticket_Type {
	GENERAL("General Ticket Type"), 
	INFORMATION("Ticket raised describes the information about category"),
	ENHANCEMENT("Enhancement which can be implemented in existing category"),
	BUG("Bug is the consequence/outcome of a coding/Functionality fault while testing");
	
	private String value;

	public String getValue() {
		return value;
	}

	private Ticket_Type(String value) {
		this.value = value;
	}
}
