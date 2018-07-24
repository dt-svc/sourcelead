package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;

public class PaysheetFrequencyDTO  extends SearchDTO{
	
	private String groupId;
	
	private String payPeriod;
	
	private String orgLocationId;
	
	private List<USPaysheetDTO> uSPaysheetDTO;
	
	private String payFrequency;
	private String payDate;
	private String paySheetFrequencyType;
	private BigDecimal total;
	private String paySheetFrequencyuuid;
	
	
	public String getGroupId()
	{
		return groupId;
	}
	public String getPayPeriod()
	{
		return payPeriod;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public List<USPaysheetDTO> getuSPaysheetDTO()
	{
		return uSPaysheetDTO;
	}
	public void setuSPaysheetDTO(List<USPaysheetDTO> uSPaysheetDTO)
	{
		this.uSPaysheetDTO = uSPaysheetDTO;
	}
	public String getPayFrequency() {
		return payFrequency;
	}
	public void setPayFrequency(String payFrequency) {
		this.payFrequency = payFrequency;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getPaySheetFrequencyType() {
		return paySheetFrequencyType;
	}
	public void setPaySheetFrequencyType(String paySheetFrequencyType) {
		this.paySheetFrequencyType = paySheetFrequencyType;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getPaySheetFrequencyuuid() {
		return paySheetFrequencyuuid;
	}
	public void setPaySheetFrequencyuuid(String paySheetFrequencyuuid) {
		this.paySheetFrequencyuuid = paySheetFrequencyuuid;
	}
}
