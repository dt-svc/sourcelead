package com.bis.sourcelead.enums;

public enum REQUIREMENT_SUBMISSION_STATUS {

	
	NEW("new"),
	DRAFT("draft"),
	SUBMITTED("submitted"),
	APPROVED("approved"),
	REJECTED("rejected"),
	OPEN("open"),
	ONHOLD("onhold"),
	FILLEDCLOSED("filledClosed"),
	UNFILLEDCLOSED("unFilledClosed"),
	CANCEL("cancel");
	
	
    private String value;
	private REQUIREMENT_SUBMISSION_STATUS(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static REQUIREMENT_SUBMISSION_STATUS getNameByValue(String scenarioId){
	   	  for(REQUIREMENT_SUBMISSION_STATUS hirerarchy_Status : REQUIREMENT_SUBMISSION_STATUS.values()){
	   	    if(scenarioId.equalsIgnoreCase(hirerarchy_Status.value)) 
	   	    	return hirerarchy_Status;
	   	  }
	   	  return null;
	    }
}
