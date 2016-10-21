package com.bis.sourcelead.enums;

public enum Submission_ApprovalStatus {
	NEW("new"),
	SUBMITTEDFORAPPROVAL("submitted for approval"),
	APPROVED("approved"),
	REJECTED("rejected"),
	REOPENED("reopened");
	
	
    private String value;
	private Submission_ApprovalStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static Submission_ApprovalStatus getNameByValue(String scenarioId){
	   	  for(Submission_ApprovalStatus project_Status : Submission_ApprovalStatus.values()){
	   	    if(scenarioId == project_Status.value) 
	   	    	return project_Status;
	   	  }
	   	  return null;
	    }
}
