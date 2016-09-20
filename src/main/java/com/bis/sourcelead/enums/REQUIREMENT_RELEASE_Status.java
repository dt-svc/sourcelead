package com.bis.sourcelead.enums;

public enum REQUIREMENT_RELEASE_Status {
	NEW("NEW"),
	IMPORTED("Imported");
	
    private String value;
	private REQUIREMENT_RELEASE_Status(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static REQUIREMENT_RELEASE_Status getNameByValue(String scenarioId){
	   	  for(REQUIREMENT_RELEASE_Status requirementRelease : REQUIREMENT_RELEASE_Status.values()){
	   	    if(scenarioId.equalsIgnoreCase(requirementRelease.value)) 
	   	    	return requirementRelease;
	   	  }
	   	  return null;
	    }
}
