package com.bis.sourcelead.enums;

public enum Approval_Mechanism_Type {
	SEQUENTIAL("Started"),
	PARALLEL("On-Hold");
	
	
    private String value;
	private Approval_Mechanism_Type(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static Approval_Mechanism_Type getNameByValue(String scenarioId){
	   	  for(Approval_Mechanism_Type project_Status : Approval_Mechanism_Type.values()){
	   	    if(scenarioId.equalsIgnoreCase(project_Status.value)) 
	   	    	return project_Status;
	   	  }
	   	  return null;
	    }
}
