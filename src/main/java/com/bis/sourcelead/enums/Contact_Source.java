package com.bis.sourcelead.enums;


public enum Contact_Source {
	SELF("SELF"),
	FACEBOOK("FACEBOOK"),
	LINKEDIN("LINKEDIN"),
	GOOGLE("GOOGLE"),
	YAHOO("YAHOO"),
	HOTMAIL("HOTMAIL"),
	TWITTER("TWITTER"),
	FOURSQUARE("FOURSQUARE"),
	TUMBLR("TUMBLR"),
	EVENTBRITE("EVENTBRITE"),
	MAILCHIMP("MAILCHIMP"),
	SKYROCK("SKYROCK");
	
	
    private String value;
	private Contact_Source(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public static Contact_Source getNameByValue(String scenarioId){
	   	  for(Contact_Source project_Status : Contact_Source.values()){
	   	    if(scenarioId.equalsIgnoreCase(project_Status.value)) 
	   	    	return project_Status;
	   	  }
	   	  return null;
	}
}
