package com.bis.sourcelead.entity.performance;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="GOAL_ID" )        
	    private PerformanceGoals goalId;
		
		@ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name="TEAM_ID" )        
	    private Team teamId;
		
		@Column(name = "PERCENTAGE_DONE ")
		private double percentageDone;
		
		@Column(name = "UUID")
		private String uuid;
		
		@Column(name = "STATUS")
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
		
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "goalId")
		@Fetch(FetchMode.SELECT)
		private Set<PerformanceGoalsAssignTo> performanceGoalsAssignTo;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy = "goalId")
		@Fetch(FetchMode.SELECT)
		private Set<PerformanceGoalsAttachments> performanceGoalsAttachments;
		
		
		@Transient
		@JsonProperty
	    private String goalStartDateStr;
		
		@Transient
		@JsonProperty
	    private String goalEndDateStr;
		
		@Transient
		@JsonProperty
	    private String createdDateStr;
		

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

		public double getPercentageDone() {
			return percentageDone;
		}

		public void setPercentageDone(double percentageDone) {
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

		public String getGoalStartDateStr() {
			String goalStartDateStr = null;
			if(startDate != null){
				SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
				goalStartDateStr = dateFormat.format(startDate);	
			}
			return goalStartDateStr;
		}

		public void setGoalStartDateStr(String goalStartDateStr) {
			this.goalStartDateStr = goalStartDateStr;
		}

		public String getGoalEndDateStr() {
			
			String goalEndDateStr = null;
			if(endDate != null){
				SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
				goalEndDateStr = dateFormat.format(endDate);	
			}
			return goalEndDateStr;
		}

		public void setGoalEndDateStr(String goalEndDateStr) {
			this.goalEndDateStr = goalEndDateStr;
		}

		public PerformanceGoals getGoalId() {
			return goalId;
		}

		public void setGoalId(PerformanceGoals goalId) {
			this.goalId = goalId;
		}

		public String getCreatedDateStr() {
			String createdDateStr = null;
			if(createdDate != null){
				SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
				createdDateStr = dateFormat.format(createdDate);	
			}
			return createdDateStr;
		}

		public void setCreatedDateStr(String createdDateStr) {
			this.createdDateStr = createdDateStr;
		}

		public Team getTeamId() {
			return teamId;
		}

		public void setTeamId(Team teamId) {
			this.teamId = teamId;
		}
		

}
