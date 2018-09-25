package com.bis.sourcelead.enums;

public enum ApplicantSubmissionStatus {

	SUBMISSION("submission"),
	SUBMISSION_ACCEPT("submission_accept"),
	SHORTLIST("shortList"),
	INTERVIEW("interview"),
	INTERVIEW_ACCEPT("interview_accept"),
	INTERVIEW_REJECT("interview_reject"),
	INTERVIEW_TENTATIVE("interview_tentative"),
	CLIENT_INTERVIEW("client_interview"),
	/*OFFER("offer"),*/
	REQUEST_SENT("request_sent"),
	REJECTED("rejected"),
	ONHOLD("On Hold"),
	NORESPONSE("NoResponse"),
	BACKOUT("backout"),
    SUBMIT_TO_CLIENT("submit_to_client"),
    OFFERMADE("offer_made"),
    OFFERACCEPTED("offer_accepted"),
    OFFERREJECTED("offer_rejected"),
    OFFERHIRED("hired"),
    FEEDBACK("feedback"),
    NEW("New"),
    ATTENDING("Attending"),
    NOT_ATTENDING("Not_Attending"),
    MAYBE("Maybe"),
    JOINED("Joined"),
    SELECTED_ON_INTERVIEW("selected_on_Interview"),
    
    CLIENT_OFFERMADE("client_offer_made"),
    CLIENT_OFFERACCEPTED("client_offer_accepted"),
    CLIENT_OFFERREJECTED("client_offer_rejected"),
    CLIENT_OFFERHIRED("client_offer_hired");
   
	
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
