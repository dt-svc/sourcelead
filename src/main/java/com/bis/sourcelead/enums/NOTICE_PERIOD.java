package com.bis.sourcelead.enums;

public enum NOTICE_PERIOD {
	IMMEDIATELY_AVAILABLE("Immediately Available"),
	DAYS_15("15 Days"),
	MONTH_1("1 Month"),
	MONTHS_2("2 Months"),
	MONTHS_3("3 Months");

	private String value;

	public String getValue() {
		return value;
	}

	private NOTICE_PERIOD(String value) {
		this.value = value;
	}

}
