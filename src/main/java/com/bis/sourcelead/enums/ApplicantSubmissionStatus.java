package com.bis.sourcelead.enums;

public enum ApplicantSubmissionStatus {

	SUBMISSION("submission"),
	SHORTLIST("shortList"),
	INTERVIEW("interview"),
	/*OFFER("offer"),*/
	REJECTED("rejected"),
    SUBMIT_TO_CLIENT("submit_to_client"),
    OFFERMADE("offer_made"),
    OFFERACCEPTED("offer_accepted"),
    OFFERREJECTED("offer_rejected"),
    OFFERHIRED("hired");
	
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
