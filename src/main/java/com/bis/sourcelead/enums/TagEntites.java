package com.bis.sourcelead.enums;

public enum TagEntites {
	
	CANDIDATES("Candidates"),
	CANDIDATES_GLOBAL("CandidatesGlobal"),
	JOBS("Jobs"),
	CLIENTS("Clients"),
	VENDORS("Vendors"),
	ASSOCIATES("Associates"),
	TASKS("Tasks"),
	CONTRACT("Contract"),
	ASSETMANAGEMENT("AssetManagement"),
	INVOICES("Invoices"),
	BILLS("Bills"),
	PROJECTS("Projects");
	
    private String value;
	private TagEntites(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public static TagEntites getNameByValue(String tagName){
	   	  for(TagEntites tagEntites : TagEntites.values()){
	   	    if(tagName.equalsIgnoreCase(tagEntites.value)) 
	   	    	return tagEntites;
	   	  }
	   	  return null;
	}
}
