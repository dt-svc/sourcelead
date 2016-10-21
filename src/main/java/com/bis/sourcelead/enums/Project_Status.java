package com.bis.sourcelead.enums;

public enum Project_Status {
	STARTED("Started"),
	INPROGRESS("In-Progress"),
	COMPLETED("Completed"),
	ONHOLD("On-Hold");
	
	
    private String value;
	private Project_Status(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static Project_Status getNameByValue(String scenarioId){
	   	  for(Project_Status project_Status : Project_Status.values()){
	   	    if(scenarioId.equalsIgnoreCase(project_Status.value)) 
	   	    	return project_Status;
	   	  }
	   	  return null;
	    }
}
