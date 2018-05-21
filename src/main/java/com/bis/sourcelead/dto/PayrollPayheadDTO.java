package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;

public class PayrollPayheadDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 	private Long id;
	 	
	 	private Long parentPayhead;
	 
	 	@Required(desc="name")
		private String name;
		
	 	@Required(desc="shortCode")
		private String shortCode;
		
		private String effectiveDate;
		
	    private String payType;
		
		private Double amount;
		
		private String formula;
		
		private String form16;
		
		private String tax;
		
		private String applicableFor;
		
		private String component;
		
		private String paidComponent;
		
		private String partofaCTC;
		
		private String fbpComponent;
		
	    private String orgLocationId;
	    
	    private int start;
		
		private int length;
			
		private String searchValue;
			
		private Map<String, String> search;
			
		private List<Map<String, String>> order;
			
		private String sortColumn;
			
		private String sortOrder;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getParentPayhead() {
			return parentPayhead;
		}

		public void setParentPayhead(Long parentPayhead) {
			this.parentPayhead = parentPayhead;
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

		public String getPayType() {
			return payType;
		}

		public void setPayType(String payType) {
			this.payType = payType;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
		}

		public String getFormula() {
			return formula;
		}

		public void setFormula(String formula) {
			this.formula = formula;
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

		public String getPartofaCTC() {
			return partofaCTC;
		}

		public void setPartofaCTC(String partofaCTC) {
			this.partofaCTC = partofaCTC;
		}

		public String getFbpComponent() {
			return fbpComponent;
		}

		public void setFbpComponent(String fbpComponent) {
			this.fbpComponent = fbpComponent;
		}

		public String getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(String orgLocationId) {
			this.orgLocationId = orgLocationId;
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
		

}
