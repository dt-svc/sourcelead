package com.bis.sourcelead.dto;

import java.util.List;
import java.util.Map;

public class EmployerRegistrationDTO {
	 private long id;
     private String firstName;
     private String lastName;
     private String phoneNumber;
     private String email;
     private String companyName;
     private String companyWebsite;
     private String companyAddress;
     private String country;
     private String modulesList;
     private String demoDate;
     private String demoTime;
     private String timeZone;
     private String orgType;
     private String searchValue;
     private String sortColumn;
 	 private String sortOrder;
 	 private List<Map<String, String>> order;
 	 private String requestedStatus;
 	 private int start;
	 private int length;
	 private String requestedBy;
	 private String requestedModules;
	 private String orgRequestStatus;
	 private String filterStatus;
     
		public long getId() {
		  return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public String getEmail() {
			return email;
		}
		public String getCompanyName() {
			return companyName;
		}
		public String getCompanyWebsite() {
			return companyWebsite;
		}
		public String getCompanyAddress() {
			return companyAddress;
		}
		public String getCountry() {
			return country;
		}
		 
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public void setCompanyWebsite(String companyWebsite) {
			this.companyWebsite = companyWebsite;
		}
		public void setCompanyAddress(String companyAddress) {
			this.companyAddress = companyAddress;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getModulesList() {
			return modulesList;
		}
		public void setModulesList(String modulesList) {
			this.modulesList = modulesList;
		}
		public String getTimeZone() {
			return timeZone;
		}
		public void setTimeZone(String timeZone) {
			this.timeZone = timeZone;
		}
		public String getOrgType() {
			return orgType;
		}
		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}
		public String getDemoDate() {
			return demoDate;
		}
		public String getDemoTime() {
			return demoTime;
		}
		public void setDemoDate(String demoDate) {
			this.demoDate = demoDate;
		}
		public void setDemoTime(String demoTime) {
			this.demoTime = demoTime;
		}
		public String getSearchValue() {
			return searchValue;
		}
		public void setSearchValue(String searchValue) {
			this.searchValue = searchValue;
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
		public List<Map<String, String>> getOrder() {
			return order;
		}
		public void setOrder(List<Map<String, String>> order) {
			this.order = order;
		}
		public String getRequestedStatus() {
			return requestedStatus;
		}
		public void setRequestedStatus(String requestedStatus) {
			this.requestedStatus = requestedStatus;
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
		public String getRequestedBy() {
			return requestedBy;
		}
		public void setRequestedBy(String requestedBy) {
			this.requestedBy = requestedBy;
		}
		public String getRequestedModules() {
			return requestedModules;
		}
		public void setRequestedModules(String requestedModules) {
			this.requestedModules = requestedModules;
		}
		public String getOrgRequestStatus() {
			return orgRequestStatus;
		}
		public void setOrgRequestStatus(String orgRequestStatus) {
			this.orgRequestStatus = orgRequestStatus;
		}

		public String getFilterStatus() {
			return filterStatus;
		}

		public void setFilterStatus(String filterStatus) {
			this.filterStatus = filterStatus;
		}
}