package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.bis.sourcelead.annotations.Required;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PayrollPayheadDTO implements Serializable{
	
	 	private static final long serialVersionUID = 1L;
	 	
	 	private Long id;
	 	
	 	@Required(desc="name")
		private String name;
		
	 	@Required(desc="shortCode")
		private String shortCode;
		
		private String effectiveDate;
		
		@Required(desc="type")
		private String type;
		
	    private String payType;
	    
	    private Long payTypeId;
		
		private String form16;
		
		private String tax;
		
		private String applicableFor;
		
		private String experienceYears;
		
		private String experienceMonths;
		
		private String component;
		
		private String paidComponent;
		
		private String partOfGratitutiy;
		
		private String fbpComponent;
		
		private String leaveDependent;
		
		private String showInPayslip;
		
		private String pfApplicable;
		
		private String esiApplicable;
		
		private String ptApplicable;

		private String method;
			
		private String formula;
		
	    private String orgLocationId;
	    
		private String uuid;
	    
	    private int start;
		
		private int length;
			
		private String searchValue;
			
		private Map<String, String> search;
			
		private List<Map<String, String>> order;
			
		private String sortColumn;
			
		private String sortOrder;
		
		private String listType;
		
		private List<PayrollPayheadSalarySetupDTO> salarySetup;
		
		private Long candidateId;
		
		private String isFormulaEditable;
		
		private BigDecimal totalForfrequencyLevel;
		
		private String status;
		
		private String active;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getShortCode() {
			return shortCode;
		}

		public void setShortCode(String shortCode) {
			this.shortCode = shortCode;
		}

		public String getEffectiveDate() {
			return effectiveDate;
		}

		public void setEffectiveDate(String effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public String getForm16() {
			return form16;
		}

		public void setForm16(String form16) {
			this.form16 = form16;
		}

		public String getTax() {
			return tax;
		}

		public void setTax(String tax) {
			this.tax = tax;
		}

		public String getApplicableFor() {
			return applicableFor;
		}

		public void setApplicableFor(String applicableFor) {
			this.applicableFor = applicableFor;
		}

		public String getExperienceYears() {
			return experienceYears;
		}

		public void setExperienceYears(String experienceYears) {
			this.experienceYears = experienceYears;
		}

		public String getExperienceMonths() {
			return experienceMonths;
		}

		public void setExperienceMonths(String experienceMonths) {
			this.experienceMonths = experienceMonths;
		}

		public String getComponent() {
			return component;
		}

		public void setComponent(String component) {
			this.component = component;
		}

		public String getPaidComponent() {
			return paidComponent;
		}

		public void setPaidComponent(String paidComponent) {
			this.paidComponent = paidComponent;
		}

		public String getPartOfGratitutiy() {
			return partOfGratitutiy;
		}

		public void setPartOfGratitutiy(String partOfGratitutiy) {
			this.partOfGratitutiy = partOfGratitutiy;
		}

		public String getFbpComponent() {
			return fbpComponent;
		}

		public void setFbpComponent(String fbpComponent) {
			this.fbpComponent = fbpComponent;
		}

		public String getLeaveDependent() {
			return leaveDependent;
		}

		public void setLeaveDependent(String leaveDependent) {
			this.leaveDependent = leaveDependent;
		}

		public String getShowInPayslip() {
			return showInPayslip;
		}

		public void setShowInPayslip(String showInPayslip) {
			this.showInPayslip = showInPayslip;
		}

		public String getPfApplicable() {
			return pfApplicable;
		}

		public void setPfApplicable(String pfApplicable) {
			this.pfApplicable = pfApplicable;
		}

		public String getEsiApplicable() {
			return esiApplicable;
		}

		public void setEsiApplicable(String esiApplicable) {
			this.esiApplicable = esiApplicable;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getFormula() {
			return formula;
		}

		public void setFormula(String formula) {
			this.formula = formula;
		}

		public String getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(String orgLocationId) {
			this.orgLocationId = orgLocationId;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getSearchValue() {
			return searchValue;
		}

		public void setSearchValue(String searchValue) {
			this.searchValue = searchValue;
		}

		public Map<String, String> getSearch() {
			return search;
		}

		public void setSearch(Map<String, String> search) {
			this.search = search;
		}

		public List<Map<String, String>> getOrder() {
			return order;
		}

		public void setOrder(List<Map<String, String>> order) {
			this.order = order;
		}

		public String getSortColumn() {
			return sortColumn;
		}

		public void setSortColumn(String sortColumn) {
			this.sortColumn = sortColumn;
		}

		public String getSortOrder() {
			return sortOrder;
		}

		public void setSortOrder(String sortOrder) {
			this.sortOrder = sortOrder;
		}

		public String getListType() {
			return listType;
		}

		public void setListType(String listType) {
			this.listType = listType;
		}

		public List<PayrollPayheadSalarySetupDTO> getSalarySetup() {
			return salarySetup;
		}

		public void setSalarySetup(List<PayrollPayheadSalarySetupDTO> salarySetup) {
			this.salarySetup = salarySetup;
		}

		public Long getCandidateId() {
			return candidateId;
		}

		public void setCandidateId(Long candidateId) {
			this.candidateId = candidateId;
		}

		public String getIsFormulaEditable() {
			return isFormulaEditable;
		}

		public void setIsFormulaEditable(String isFormulaEditable) {
			this.isFormulaEditable = isFormulaEditable;
		}


		public Long getPayTypeId() {
			return payTypeId;
		}

		public void setPayTypeId(Long payTypeId) {
			this.payTypeId = payTypeId;
		}

		public BigDecimal getTotalForfrequencyLevel() {
			return totalForfrequencyLevel;
		}

		public void setTotalForfrequencyLevel(BigDecimal totalForfrequencyLevel) {
			this.totalForfrequencyLevel = totalForfrequencyLevel;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getActive() {
			return active;
		}

		public void setActive(String active) {
			this.active = active;
		}
		public String getPtApplicable() {
			return ptApplicable;
		}

		public void setPtApplicable(String ptApplicable) {
			this.ptApplicable = ptApplicable;
		}
}
