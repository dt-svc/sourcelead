package com.bis.sourcelead.enums;

public enum Ticket_Priority {
	HIGH("Ticket which is very critical for moving forward"),
	MEDIUM("Ticket which is important,mostly dependency with other functionalities"),
	LOW("Ticket Which is normal"),
	NORMAL("Ticket Which is low priority in current functionality");
	
	private String value;

	public String getValue() {
		return value;
	}

	private Ticket_Priority(String value) {
		this.value = value;
	}
}
