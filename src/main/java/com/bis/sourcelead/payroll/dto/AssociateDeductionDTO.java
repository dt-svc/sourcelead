package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class AssociateDeductionDTO {
	
	private String id;
	
	@Required(desc = "Deduction Type")
	private String dedType;
	
	@Required(desc = "Amount")
	private String amt;
	
	@Required(desc = "method")
	private String mthd;
	
	List<DeductionDTO>  deductionDTOList;

	private String userId;
	
	private String saveType;
	
	public String getDedType()
	{
		return dedType;
	}

	public void setDedType(String dedType)
	{
		this.dedType = dedType;
	}

	public String getAmt()
	{
		return amt;
	}

	public void setAmt(String amt)
	{
		this.amt = amt;
	}

	public String getMthd()
	{
		return mthd;
	}

	public void setMthd(String mthd)
	{
		this.mthd = mthd;
	}

	public List<DeductionDTO> getDeductionDTOList()
	{
		return deductionDTOList;
	}

	public void setDeductionDTOList(List<DeductionDTO> deductionDTOList)
	{
		this.deductionDTOList = deductionDTOList;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	
}
