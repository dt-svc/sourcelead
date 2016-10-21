package com.bis.sourcelead.enums;

public enum HIERARCHY_TYPE {
	SEQUENTIAL("Sequential"),
	PARALLEL("Parallel");
	
	
    private String value;
	private HIERARCHY_TYPE(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	 public static HIERARCHY_TYPE getNameByValue(String scenarioId){
	   	  for(HIERARCHY_TYPE hirerarchy_Type : HIERARCHY_TYPE.values()){
	   	    if(scenarioId.equalsIgnoreCase(hirerarchy_Type.value)) 
	   	    	return hirerarchy_Type;
	   	  }
	   	  return null;
	    }
}
