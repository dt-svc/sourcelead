package com.bis.sourcelead.enums;

public enum ApplicantSubmissionStatus {

	SUBMISSION("submission"),
	INTERVIEW("interview"),
	OFFER("offer"),
	REJECTED("rejected");
	
    private String value;
	private ApplicantSubmissionStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static ApplicantSubmissionStatus getNameByValue(String scenarioId){
	   	  for(ApplicantSubmissionStatus submissionStatus : ApplicantSubmissionStatus.values()){
	   	    if(scenarioId.equalsIgnoreCase(submissionStatus.value)) 
	   	    	return submissionStatus;
	   	  }
	   	  return null;
	    }
}
