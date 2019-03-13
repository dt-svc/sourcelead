package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.dto.UserDTO;

public class PayrollScheduleDTO {
	
	private long id;
	
	private String orgLocationId;
	
	@Required(desc = "Pay Frequency")
	private String payFrequency;
	
	private String payPeriodStartDate;
	
	@Required(desc = "Pay CheckDate")
	private String payCheckDate;
	
	@Required(desc = "Run PayrollDate")
	private String runPayDate;
	
	private String nextPayMonth;
	
	private String notifyMember;
	
	private int status;
	
	private String payrollScheduleUUID;
	
	private  List<Long> notifiMemIds;
	
	private List<UserDTO> approvers;
	
	private List<String> month;
	
	public String getOrgLocationId() {
		return orgLocationId;
	}
	
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public String getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public String getPayCheckDate()
	{
		return payCheckDate;
	}


	public String getNextPayMonth()
	{
		return nextPayMonth;
	}

	public int getStatus()
	{
		return status;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public void setPayPeriodStartDate(String payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayCheckDate(String payCheckDate)
	{
		this.payCheckDate = payCheckDate;
	}


	public void setNextPayMonth(String nextPayMonth)
	{
		this.nextPayMonth = nextPayMonth;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getNotifyMember()
	{
		return notifyMember;
	}

	public void setNotifyMember(String notifyMember)
	{
		this.notifyMember = notifyMember;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getRunPayDate()
	{
		return runPayDate;
	}

	public void setRunPayDate(String runPayDate)
	{
		this.runPayDate = runPayDate;
	}

	public String getPayrollScheduleUUID()
	{
		return payrollScheduleUUID;
	}

	public void setPayrollScheduleUUID(String payrollScheduleUUID)
	{
		this.payrollScheduleUUID = payrollScheduleUUID;
	}

	public List<Long> getNotifiMemIds()
	{
		return notifiMemIds;
	}

	public void setNotifiMemIds(List<Long> notifiMemIds)
	{
		this.notifiMemIds = notifiMemIds;
	}

	public List<UserDTO> getApprovers()
	{
		return approvers;
	}

	public void setApprovers(List<UserDTO> approvers)
	{
		this.approvers = approvers;
	}

	public List<String> getMonth()
	{
		return month;
	}

	public void setMonth(List<String> month)
	{
		this.month = month;
	}
	
	
}
