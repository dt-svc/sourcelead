package com.bis.sourcelead.payroll.dto;

public enum LocalTaxState {
	
	ALABAMA("Alabama"),
	COLORADO("Colorado"), 
	DELAWARE("Delaware"), 
	INDIANA_COUNTY_TAX("Indiana County Tax"),
	KENTUCKY("Kentucky"), 
	MARYLAND("Maryland"), 
	MICHIGAN("Michigan"),
	MISSOURI("Missouri"),
	NEW_YORK("New York"),
	OHIO("Ohio"),
	OREGON("Oregon"),
	PENNSYLVANIA("Pennsylvania"),
	WASHINGTON("Washington"),
	WEST_VIRGINIA("West Virginia");
	
    private String stateName;

    LocalTaxState(String stateName) {
        this.stateName = stateName;
    }

	public String getStateName()
	{
		return stateName;
	}


}
