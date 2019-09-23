package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.bis.sourcelead.annotations.Required;

public class AssetServiceDTO extends SearchDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	    
	    @Required(desc="assetId") 
	    private Long assetId;
	
	    @Required(desc="serviceDate")
	    private String serviceDate;
	
	    @Required(desc="receivedDate")
		private String receivedDate;
		
	    @Required(desc="cost")
		private BigDecimal cost;

	    @Required(desc="description")
		private String description;
	    
	    @Required(desc="workingStatus") 
	    private String workingStatus;

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getServiceDate() {
			return serviceDate;
		}

		public String getReceivedDate() {
			return receivedDate;
		}

		public BigDecimal getCost() {
			return cost;
		}

		public String getDescription() {
			return description;
		}

		public void setServiceDate(String serviceDate) {
			this.serviceDate = serviceDate;
		}

		public void setReceivedDate(String receivedDate) {
			this.receivedDate = receivedDate;
		}

		public void setCost(BigDecimal cost) {
			this.cost = cost;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Long getAssetId() {
			return assetId;
		}

		public void setAssetId(Long assetId) {
			this.assetId = assetId;
		}

		public String getWorkingStatus() {
			return workingStatus;
		}

		public void setWorkingStatus(String workingStatus) {
			this.workingStatus = workingStatus;
		}
}