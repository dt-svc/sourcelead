package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class AssociateContributionDTO {
	
	private String id;
	
	@Required (desc = "Contribution Type")
	private String contriType;
	
	@Required (desc = "Amount")
	private String contriAmt;
	
	@Required (desc = "Method")
	private String contricontributionMethod;
	
	private String userId;
	
	private String saveType;
	
	private List<ContributionDTO> contributionDTOList;

	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getContriType()
	{
		return contriType;
	}

	public void setContriType(String contriType)
	{
		this.contriType = contriType;
	}

	public String getContriAmt()
	{
		return contriAmt;
	}

	public void setContriAmt(String contriAmt)
	{
		this.contriAmt = contriAmt;
	}

	public String getContricontributionMethod()
	{
		return contricontributionMethod;
	}

	public void setContricontributionMethod(String contricontributionMethod)
	{
		this.contricontributionMethod = contricontributionMethod;
	}

	public List<ContributionDTO> getContributionDTOList()
	{
		return contributionDTOList;
	}

	public void setContributionDTOList(List<ContributionDTO> contributionDTOList)
	{
		this.contributionDTOList = contributionDTOList;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	
}
