package com.bis.sourcelead.enums;

public enum OnboardStatus {

	NEW("new"),
	DRAFT("draft"),
	SUBMITTED("submitted"),
	APPROVED("approved"),
	ACCEPTED("accepted"),
	PENDING("accepted"),
	REJECTED("rejected");
	
	
    private String value;
	private OnboardStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public static OnboardStatus getNameByValue(String scenarioId){
	   	  for(OnboardStatus onboardStatus : OnboardStatus.values()){
	   	    if(scenarioId.equalsIgnoreCase(onboardStatus.value)) 
	   	    	return onboardStatus;
	   	  }
	   	  return null;
	}
}
