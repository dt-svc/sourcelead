package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;

public class AssociateLoanAndAdvancesHistoryDTO implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
		private Long id;
		
		private Long associateLoanAndAdvancesId;
		
		private Long indEmployeePayrollId;
		
		private Long orgLocationsId;
		
		private String payrollType;
		
		private Double deductAmount;
		
		private Double paidAmount;
		
		private Double dueAmount;
		
		private Integer schedule;
		
		private Date scheduleStartDate;
		
		private Date scheduleEndDate;
		
		private Date createdDate;
		
		private String createdBy;
		
		private Date updatedDate;
		
		private String updatedBy;

	    private String name;
		
	    private String type;
		
	    private Double totalAmount;
		
	    private String requestedTo;
		
	    private String requestedOn;
		
	    private Integer noOfShedules;
		
	    private Double lastPaidOn;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getAssociateLoanAndAdvancesId() {
			return associateLoanAndAdvancesId;
		}

		public void setAssociateLoanAndAdvancesId(Long associateLoanAndAdvancesId) {
			this.associateLoanAndAdvancesId = associateLoanAndAdvancesId;
		}

		public Long getIndEmployeePayrollId() {
			return indEmployeePayrollId;
		}

		public void setIndEmployeePayrollId(Long indEmployeePayrollId) {
			this.indEmployeePayrollId = indEmployeePayrollId;
		}

		public Long getOrgLocationsId() {
			return orgLocationsId;
		}

		public void setOrgLocationsId(Long orgLocationsId) {
			this.orgLocationsId = orgLocationsId;
		}

		public String getPayrollType() {
			return payrollType;
		}

		public void setPayrollType(String payrollType) {
			this.payrollType = payrollType;
		}

		public Double getDeductAmount() {
			return deductAmount;
		}

		public void setDeductAmount(Double deductAmount) {
			this.deductAmount = deductAmount;
		}

		public Double getPaidAmount() {
			return paidAmount;
		}

		public void setPaidAmount(Double paidAmount) {
			this.paidAmount = paidAmount;
		}

		public Double getDueAmount() {
			return dueAmount;
		}

		public void setDueAmount(Double dueAmount) {
			this.dueAmount = dueAmount;
		}

		public Integer getSchedule() {
			return schedule;
		}

		public void setSchedule(Integer schedule) {
			this.schedule = schedule;
		}

		public Date getScheduleStartDate() {
			return scheduleStartDate;
		}

		public void setScheduleStartDate(Date scheduleStartDate) {
			this.scheduleStartDate = scheduleStartDate;
		}

		public Date getScheduleEndDate() {
			return scheduleEndDate;
		}

		public void setScheduleEndDate(Date scheduleEndDate) {
			this.scheduleEndDate = scheduleEndDate;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Date getUpdatedDate() {
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}

		public String getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy) {
			this.updatedBy = updatedBy;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public String getRequestedTo() {
			return requestedTo;
		}

		public void setRequestedTo(String requestedTo) {
			this.requestedTo = requestedTo;
		}

		public String getRequestedOn() {
			return requestedOn;
		}

		public void setRequestedOn(String requestedOn) {
			this.requestedOn = requestedOn;
		}

		public Integer getNoOfShedules() {
			return noOfShedules;
		}

		public void setNoOfShedules(Integer noOfShedules) {
			this.noOfShedules = noOfShedules;
		}

		public Double getLastPaidOn() {
			return lastPaidOn;
		}

		public void setLastPaidOn(Double lastPaidOn) {
			this.lastPaidOn = lastPaidOn;
		}
}
