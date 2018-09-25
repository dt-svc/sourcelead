package com.bis.sourcelead.enums;

public enum ApplicantSubmissionDisplayStatus {

	SUBMISSION("Submission"),
	SUBMISSION_ACCEPT("Submission Accepted"),
	SHORTLIST("ShortList"),
	INTERVIEW("Interview"),
	INTERVIEW_ACCEPT("Interview Accept"),
	INTERVIEW_REJECT("Interview Reject"),
	INTERVIEW_TENTATIVE("Interview Tentative"),
	CLIENT_INTERVIEW("Client Interview"),
	/*OFFER("offer"),*/
	REQUEST_SENT("Request Sent"),
	REJECTED("Rejected"),
	ONHOLD("On Hold"),
	NORESPONSE("NoResponse"),
	BACKOUT("Backout"),
    SUBMIT_TO_CLIENT("Submit To Client"),
    OFFERMADE("Offer Made"),
    OFFERACCEPTED("Offer Accepted"),
    OFFERREJECTED("Offer Rejected"),
    OFFERHIRED("Hired"),
    NEW("New"),
    JOINED("Joined"),
    SELECTED_ON_INTERVIEW("Selected on Interview"),
    
    CLIENT_OFFERMADE("Client Offer Made"),
    CLIENT_OFFERACCEPTED("Client Offer Accepted"),
    CLIENT_OFFERREJECTED("Client Offer Rejected"),
    CLIENT_OFFERHIRED("Client Offer Hired");
   
	
    private String value;
	private ApplicantSubmissionDisplayStatus(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static ApplicantSubmissionDisplayStatus getNameByValue(String scenarioId){
	   	  for(ApplicantSubmissionDisplayStatus submissionStatus : ApplicantSubmissionDisplayStatus.values()){
	   	    if(scenarioId.equalsIgnoreCase(submissionStatus.value)) 
	   	    	return submissionStatus;
	   	  }
	   	  return null;
	    }
}
