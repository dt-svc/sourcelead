package com.bis.sourcelead.enums;

public enum ConnectionStatus {

	REQUEST_SENT("request_sent"), REQUEST_RECEIVED("request_received"), CONNECTED("connected"), REQUEST_REJECTED(
			"request_rejected");

	private String value;

	private ConnectionStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static ConnectionStatus getNameByValue(String statusId) {
		for (ConnectionStatus connectionStatus : ConnectionStatus.values()) {
			if (statusId.equalsIgnoreCase(connectionStatus.value))
				return connectionStatus;
		}
		return null;
	}
}
