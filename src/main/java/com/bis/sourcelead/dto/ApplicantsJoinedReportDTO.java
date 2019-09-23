package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class ApplicantsJoinedReportDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	

	private BigInteger userId;

	private String name;
	

	private BigInteger orglocationId;
	
	private BigInteger reqSubId;
	
	private String incentAmountType;
	
	private String incentJoiners;
	
	private BigInteger requirementID;
	
	private BigInteger requirementCount;
	
	private BigInteger reqClientCount;
	
	private BigInteger submissionCount;

	private BigInteger joinedCount;
	
	private String incentAmount;
	
	private String finalRate;
	
	private String OfferAmount;
	
	private BigInteger offerCount;
	
	private String clients;
	
	private String type;
	
	private Date reqSubCreatedDate; 
	
	private Date fromDate;
	
	private Date toDate;
	
	private BigInteger clientId;
	
	private String orgTeams;
	
	private String createdBy;
	
	private Set<String> datesList;
	
	private String monthName;
	
	private int monthNumber;
	
	private int start;
	
	private int length;
	
	private String searchValue;

	private int recordsTotal;
	
	private int recordsFiltered;
	
	private BigInteger totalRequirementCount;
	
	public String getMonthName()
	{
		return monthName;
	}

	public void setMonthName(String monthName)
	{
		this.monthName = monthName;
	}

	public int getMonthNumber()
	{
		return monthNumber;
	}

	public void setMonthNumber(int monthNumber)
	{
		this.monthNumber = monthNumber;
	}

	public Set<String> getDatesList()
	{
		return datesList;
	}

	public void setDatesList(Set<String> datesList)
	{
		this.datesList = datesList;
	}

	public BigInteger getUserId()
	{
		return userId;
	}

	public void setUserId(BigInteger userId)
	{
		this.userId = userId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public BigInteger getOrglocationId()
	{
		return orglocationId;
	}

	public void setOrglocationId(BigInteger orglocationId)
	{
		this.orglocationId = orglocationId;
	}

	public BigInteger getRequirementID()
	{
		return requirementID;
	}

	public void setRequirementID(BigInteger requirementID)
	{
		this.requirementID = requirementID;
	}

	public BigInteger getRequirementCount()
	{
		return requirementCount;
	}

	public void setRequirementCount(BigInteger requirementCount)
	{
		this.requirementCount = requirementCount;
	}

	public BigInteger getSubmissionCount()
	{
		return submissionCount;
	}

	public void setSubmissionCount(BigInteger submissionCount)
	{
		this.submissionCount = submissionCount;
	}

	public BigInteger getJoinedCount()
	{
		return joinedCount;
	}

	public void setJoinedCount(BigInteger joinedCount)
	{
		this.joinedCount = joinedCount;
	}

	public BigInteger getOfferCount()
	{
		return offerCount;
	}

	public void setOfferCount(BigInteger offerCount)
	{
		this.offerCount = offerCount;
	}



	public String getOrgTeams()
	{
		return orgTeams;
	}

	public void setOrgTeams(String orgTeams)
	{
		this.orgTeams = orgTeams;
	}

	public String getClients()
	{
		return clients;
	}

	public void setClients(String clients)
	{
		this.clients = clients;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Date getReqSubCreatedDate()
	{
		return reqSubCreatedDate;
	}

	public void setReqSubCreatedDate(Date reqSubCreatedDate)
	{
		this.reqSubCreatedDate = reqSubCreatedDate;
	}

	public Date getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}

	public Date getToDate()
	{
		return toDate;
	}

	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
	}

	public BigInteger getClientId()
	{
		return clientId;
	}

	public void setClientId(BigInteger clientId)
	{
		this.clientId = clientId;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public BigInteger getReqSubId()
	{
		return reqSubId;
	}

	public void setReqSubId(BigInteger reqSubId)
	{
		this.reqSubId = reqSubId;
	}

	public String getIncentAmountType()
	{
		return incentAmountType;
	}

	public void setIncentAmountType(String incentAmountType)
	{
		this.incentAmountType = incentAmountType;
	}

	public String getIncentJoiners()
	{
		return incentJoiners;
	}

	public void setIncentJoiners(String incentJoiners)
	{
		this.incentJoiners = incentJoiners;
	}

	

	public String getIncentAmount()
	{
		return incentAmount;
	}

	public void setIncentAmount(String incentAmount)
	{
		this.incentAmount = incentAmount;
	}

	public String getFinalRate()
	{
		return finalRate;
	}

	public void setFinalRate(String finalRate)
	{
		this.finalRate = finalRate;
	}

	public String getOfferAmount()
	{
		return OfferAmount;
	}

	public void setOfferAmount(String offerAmount)
	{
		OfferAmount = offerAmount;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public int getRecordsTotal()
	{
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal)
	{
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered()
	{
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered)
	{
		this.recordsFiltered = recordsFiltered;
	}

	public BigInteger getReqClientCount()
	{
		return reqClientCount;
	}

	public void setReqClientCount(BigInteger reqClientCount)
	{
		this.reqClientCount = reqClientCount;
	}

	public BigInteger getTotalRequirementCount()
	{
		return totalRequirementCount;
	}

	public void setTotalRequirementCount(BigInteger totalRequirementCount)
	{
		this.totalRequirementCount = totalRequirementCount;
	}
	
	
	
}
