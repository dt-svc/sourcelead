package com.bis.sourcelead.enums;

public enum Submission_Status {
	
	NEW("new"),
	DRAFT("draft"),
	REOPENED("reopened"),
	SUBMITTED("submitted"),
	APPROVED("approved"),
	REJECTED("rejected");
	
	
    private String value;
	private Submission_Status(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public static Submission_Status getNameByValue(String scenarioId){
	   	  for(Submission_Status project_Status : Submission_Status.values()){
	   	    if(scenarioId.equalsIgnoreCase(project_Status.value)) 
	   	    	return project_Status;
	   	  }
	   	  return null;
	}
}
