package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class RequirementRateSubmissionNegotiationDTO {
	
	@Required(desc = "rateProposal")
    private String rateProposal;
    
	@Required(desc = "rateType")
    private String rateType;
    
    private String comment;
    
    private String rateComment;

	public String getRateProposal()
	{
		return rateProposal;
	}

	public void setRateProposal(String rateProposal)
	{
		this.rateProposal = rateProposal;
	}

	public String getRateType()
	{
		return rateType;
	}

	public void setRateType(String rateType)
	{
		this.rateType = rateType;
	}

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}
	
	public String getRateComment() {
		return rateComment;
	}

	public void setRateComment(String rateComment) {
		this.rateComment = rateComment;
	}
    
}
