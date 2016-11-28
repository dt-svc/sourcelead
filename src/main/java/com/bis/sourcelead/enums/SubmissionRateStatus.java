package com.bis.sourcelead.enums;

public enum SubmissionRateStatus {

	REJECTED("rejected"),
    ACCEPTED("accepted");
	
    private String value;
	private SubmissionRateStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static SubmissionRateStatus getNameByValue(String scenarioId){
	   	  for(SubmissionRateStatus submissionStatus : SubmissionRateStatus.values()){
	   	    if(scenarioId.equalsIgnoreCase(submissionStatus.value)) 
	   	    	return submissionStatus;
	   	  }
	   	  return null;
	    }
}
