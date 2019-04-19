package com.bis.sourcelead.entity.performance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "PERFORMANCE_GOALS_ASSIGN_TO")
public class PerformanceGoalsAssignTo implements Serializable {
	
	 private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID", nullable = false)
		private Long id;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="GOAL_ID" )        
	    private PerformanceGoals goalId;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="ASSIGNED_USER_ID")        
	    private User assignedUserId;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="ORG_LOCATION_ID")        
	    private OrgLocations orgLocationId;
		
		@Column(name = "UUID")
		private String uuid;
		
		@Column(name = "STATUS ")
		private Integer status;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="CREATED_BY")        
	    private User createdBy;
		
		@Column(name = " UPDATED_DATE ")
		private Date updatedDate;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="UPDATED_BY")        
	    private User updatedBy;
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public PerformanceGoals getGoalId() {
			return goalId;
		}

		public void setGoalId(PerformanceGoals goalId) {
			this.goalId = goalId;
		}

		public User getAssignedUserId() {
			return assignedUserId;
		}

		public void setAssignedUserId(User assignedUserId) {
			this.assignedUserId = assignedUserId;
		}

		public OrgLocations getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(OrgLocations orgLocationId) {
			this.orgLocationId = orgLocationId;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public User getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(User createdBy) {
			this.createdBy = createdBy;
		}

		public Date getUpdatedDate() {
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}

		public User getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(User updatedBy) {
			this.updatedBy = updatedBy;
		}

}
