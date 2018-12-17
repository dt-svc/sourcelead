package com.bis.sourcelead.payroll.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bis.sourcelead.entity.OrgLocations;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "US_PAY_ITEM")
public class PayItem {
	
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", nullable = false)
		private Long id;

		@ManyToOne
		@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
		private OrgLocations orgLocationId;

		@Column(name = "PAY_ITEM_NAME")
		private String payItemName;
		
		@Column(name = "PAY_SCHEDULE")
		private String paySchedule;
		
		@Column(name = "ALLOW_EMPLOYEEMENT_TYPE")
		private String allowEmployeementType;

		@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
		private String description;
	
		@Column(name = "ALLOW_OVER_TIME")
		private String allowOverTime;
		
		@Column(name = "PAY_ITEM_UUID")
		private String payItemUuid;
		
		@Column(name = "STATUS")
		private Integer status;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;

		@Column(name = "CREATED_BY")
		private String createdBy;

		@Column(name = "UPDATED_DATE")
		private Date updatedDate;

		@Column(name = "UPDATED_BY")
		private String updatedBy;
		
		@Transient
		@JsonProperty
		private List<String> empType;

		public Long getId()
		{
			return id;
		}

		public void setId(Long id)
		{
			this.id = id;
		}

		public OrgLocations getOrgLocationId()
		{
			return orgLocationId;
		}

		public void setOrgLocationId(OrgLocations orgLocationId)
		{
			this.orgLocationId = orgLocationId;
		}

		public String getPayItemName()
		{
			return payItemName;
		}

		public void setPayItemName(String payItemName)
		{
			this.payItemName = payItemName;
		}

		public String getPaySchedule()
		{
			return paySchedule;
		}

		public void setPaySchedule(String paySchedule)
		{
			this.paySchedule = paySchedule;
		}

		public String getAllowEmployeementType()
		{
			return allowEmployeementType;
		}

		public void setAllowEmployeementType(String allowEmployeementType)
		{
			this.allowEmployeementType = allowEmployeementType;
		}

		public String getDescription()
		{
			return description;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public String getAllowOverTime()
		{
			return allowOverTime;
		}

		public void setAllowOverTime(String allowOverTime)
		{
			this.allowOverTime = allowOverTime;
		}

		public Integer getStatus()
		{
			return status;
		}

		public void setStatus(Integer status)
		{
			this.status = status;
		}

		public Date getCreatedDate()
		{
			return createdDate;
		}

		public void setCreatedDate(Date createdDate)
		{
			this.createdDate = createdDate;
		}

		public String getCreatedBy()
		{
			return createdBy;
		}

		public void setCreatedBy(String createdBy)
		{
			this.createdBy = createdBy;
		}

		public Date getUpdatedDate()
		{
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate)
		{
			this.updatedDate = updatedDate;
		}

		public String getUpdatedBy()
		{
			return updatedBy;
		}

		public void setUpdatedBy(String updatedBy)
		{
			this.updatedBy = updatedBy;
		}

		public String getPayItemUuid()
		{
			return payItemUuid;
		}

		public void setPayItemUuid(String payItemUuid)
		{
			this.payItemUuid = payItemUuid;
		}

		public List<String> getEmpType()
		{
			return empType;
		}

		public void setEmpType(List<String> empType)
		{
			this.empType = empType;
		}
		
}
