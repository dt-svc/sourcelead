package com.bis.sourcelead.payroll.dto;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.SearchDTO;


@SuppressWarnings("serial")
public class ImmigrationReportViewDTO extends SearchDTO{
	
	private String id;
	
	private List<String> petitionType;
	private String petitionTypeName;
	private List<String> attorneyId;
	
	private List<String> petitionStatus;
	
	private String currUscisStatus;
	
	private String freqStartDate;
	
	private String freqEndDate;
	
	private String lcaStartDate;
	
	private String lcaEndDate;
	
	private String generatedId;
	
	 private String orgName;
	 
	 private String dateFrequency;
	
	//Candidate details for petition
	
	private String name;
	
	private String email;
	
	private String phoneNumber;
	
	private String country;
	
	 private String openDateStr;
	 
	 private String lcaStartDateStr;
	 
	 private String lcaEndDateStr;
	 
	 private String orgNameSel;
	 
	 private String spocName;
	 
	 private Date[] freqDates;
	 
	 private String searchValue;
	 
	 private String exportType;
	 
	 private String orglocationId;
	 
	 private String immigrationUUID;
	 
	 private String candidateUUID;
	 
	 private String immiTemplateUUID;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getPetitionType() {
		return petitionType;
	}

	public void setPetitionType(List<String> petitionType) {
		this.petitionType = petitionType;
	}

	public List<String> getPetitionStatus() {
		return petitionStatus;
	}

	public void setPetitionStatus(List<String> petitionStatus) {
		this.petitionStatus = petitionStatus;
	}

	public String getFreqStartDate() {
		return freqStartDate;
	}

	public void setFreqStartDate(String freqStartDate) {
		this.freqStartDate = freqStartDate;
	}

	public String getFreqEndDate() {
		return freqEndDate;
	}

	public void setFreqEndDate(String freqEndDate) {
		this.freqEndDate = freqEndDate;
	}

	public String getLcaStartDate() {
		return lcaStartDate;
	}

	public void setLcaStartDate(String lcaStartDate) {
		this.lcaStartDate = lcaStartDate;
	}

	public String getLcaEndDate() {
		return lcaEndDate;
	}

	public void setLcaEndDate(String lcaEndDate) {
		this.lcaEndDate = lcaEndDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<String> getAttorneyId() {
		return attorneyId;
	}

	public void setAttorneyId(List<String> attorneyId) {
		this.attorneyId = attorneyId;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getOpenDateStr() {
		return openDateStr;
	}

	public void setOpenDateStr(String openDateStr) {
		this.openDateStr = openDateStr;
	}

	public String getLcaStartDateStr() {
		return lcaStartDateStr;
	}

	public void setLcaStartDateStr(String lcaStartDateStr) {
		this.lcaStartDateStr = lcaStartDateStr;
	}

	public String getLcaEndDateStr() {
		return lcaEndDateStr;
	}

	public void setLcaEndDateStr(String lcaEndDateStr) {
		this.lcaEndDateStr = lcaEndDateStr;
	}
	
	public String getCurrUscisStatus() {
		return currUscisStatus;
	}

	public void setCurrUscisStatus(String currUscisStatus) {
		this.currUscisStatus = currUscisStatus;
	}


	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgNameSel() {
		return orgNameSel;
	}

	public void setOrgNameSel(String orgNameSel) {
		this.orgNameSel = orgNameSel;
	}

	public String getSpocName() {
		return spocName;
	}

	public void setSpocName(String spocName) {
		this.spocName = spocName;
	}

	public String getDateFrequency() {
		return dateFrequency;
	}

	public void setDateFrequency(String dateFrequency) {
		this.dateFrequency = dateFrequency;
	}

	public Date[] getFreqDates() {
		return freqDates;
	}

	public void setFreqDates(Date[] freqDates) {
		this.freqDates = freqDates;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getPetitionTypeName() {
		return petitionTypeName;
	}

	public void setPetitionTypeName(String petitionTypeName) {
		this.petitionTypeName = petitionTypeName;
	}

	public String getOrglocationId() {
		return orglocationId;
	}

	public void setOrglocationId(String orglocationId) {
		this.orglocationId = orglocationId;
	}

	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getImmiTemplateUUID() {
		return immiTemplateUUID;
	}

	public void setImmiTemplateUUID(String immiTemplateUUID) {
		this.immiTemplateUUID = immiTemplateUUID;
	}

	public String getImmigrationUUID() {
		return immigrationUUID;
	}

	public void setImmigrationUUID(String immigrationUUID) {
		this.immigrationUUID = immigrationUUID;
	}
	
	
}