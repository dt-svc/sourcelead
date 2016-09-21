package com.bis.sourcelead.enums;

public enum REQUIREMENT_APPROVER_HIERARCHY_STATUS {

	ACCEPTED("Accepted"),
	REJECTED("Rejected"),
	POSTED("Posted");
	
	
    private String value;
	private REQUIREMENT_APPROVER_HIERARCHY_STATUS(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static REQUIREMENT_APPROVER_HIERARCHY_STATUS getNameByValue(String scenarioId){
	   	  for(REQUIREMENT_APPROVER_HIERARCHY_STATUS hirerarchy_Status : REQUIREMENT_APPROVER_HIERARCHY_STATUS.values()){
	   	    if(scenarioId.equalsIgnoreCase(hirerarchy_Status.value)) 
	   	    	return hirerarchy_Status;
	   	  }
	   	  return null;
	    }
}
