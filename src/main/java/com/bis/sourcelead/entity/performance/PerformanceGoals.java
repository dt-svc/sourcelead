package com.bis.sourcelead.entity.performance;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PERFORMANCE_GOALS")
public class PerformanceGoals implements Serializable{
	
	 private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID", nullable = false)
		private Long id;
		
		@Column(name = "GOAL_NAME")
		private String goalName;
		
		@Column(name = "START_DATE")
		private Date startDate;
		
		@Column(name = "END_DATE")
		private Date endDate;
		
		@Column(name = "DESCRIPTION", columnDefinition = "LONGTEXT default NULL")
		private String description ;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
	    private OrgLocations orgLocationId;
		
		@Column(name = "GOAL_TYPE")
		private String goalType;
		
		@Column(name = "PERCENTAGE_DONE ")
		private Integer percentageDone;
		
		@Column(name = "UUID")
		private String uuid;
		
		@Column(name = "STATUS")
		private Integer status;
		
		@Column(name = "CREATED_DATE")
		private Date createdDate;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="CREATED_BY", nullable=false )        
	    private User createdBy;
		
		@Column(name = " UPDATED_DATE ")
		private Date updatedDate;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="UPDATED_BY", nullable=false )        
	    private User updatedBy;
		
		@OneToMany(mappedBy = "goalId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonBackReference
		private Set<PerformanceGoalsAssignTo> performanceGoalsAssignTo = new HashSet<PerformanceGoalsAssignTo>(0);
		
		@OneToMany(mappedBy = "goalId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@JsonBackReference
		private Set<PerformanceGoalsAttachments> performanceGoalsAttachments = new HashSet<PerformanceGoalsAttachments>(0);
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getGoalName() {
			return goalName;
		}

		public void setGoalName(String goalName) {
			this.goalName = goalName;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public OrgLocations getOrgLocationId() {
			return orgLocationId;
		}

		public void setOrgLocationId(OrgLocations orgLocationId) {
			this.orgLocationId = orgLocationId;
		}

		public String getGoalType() {
			return goalType;
		}

		public void setGoalType(String goalType) {
			this.goalType = goalType;
		}

		public Integer getPercentageDone() {
			return percentageDone;
		}

		public void setPercentageDone(Integer percentageDone) {
			this.percentageDone = percentageDone;
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

		public Set<PerformanceGoalsAssignTo> getPerformanceGoalsAssignTo() {
			return performanceGoalsAssignTo;
		}

		public void setPerformanceGoalsAssignTo(Set<PerformanceGoalsAssignTo> performanceGoalsAssignTo) {
			this.performanceGoalsAssignTo = performanceGoalsAssignTo;
		}

		public Set<PerformanceGoalsAttachments> getPerformanceGoalsAttachments() {
			return performanceGoalsAttachments;
		}

		public void setPerformanceGoalsAttachments(Set<PerformanceGoalsAttachments> performanceGoalsAttachments) {
			this.performanceGoalsAttachments = performanceGoalsAttachments;
		}
		

}
