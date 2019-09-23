package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class AddAssociateDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer maxTsRowNum;
	
	private List<AssociateDTO> locationAssociates;
	

	public Integer getMaxTsRowNum() {
		return maxTsRowNum;
	}

	public void setMaxTsRowNum(Integer maxTsRowNum) {
		this.maxTsRowNum = maxTsRowNum;
	}

	public List<AssociateDTO> getLocationAssociates() {
		return locationAssociates;
	}

	public void setLocationAssociates(List<AssociateDTO> locationAssociates) {
		this.locationAssociates = locationAssociates;
	}

}
