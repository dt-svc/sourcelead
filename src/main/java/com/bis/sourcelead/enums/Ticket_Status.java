package com.bis.sourcelead.enums;

public enum Ticket_Status {
	OPEN("Ticket has been initiated"),
	IN_PROGRESS("Ticket is being worked out"),
	CLOSED("Task is being completed");
	
	private String value;

	public String getValue() {
		return value;
	}

	private Ticket_Status(String value) {
		this.value = value;
	}
}
