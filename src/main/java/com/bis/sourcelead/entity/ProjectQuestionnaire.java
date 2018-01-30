package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_QUESTIONNAIRE")
public class ProjectQuestionnaire implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projectId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG_LOCATIONS_ID", nullable=false )	
	private OrgLocations orgLocationId;
	
	@Column(name = "PROJECT_LOCATION")
	@Type(type="text")
	private String projectLocation;
	
	@Column(name = "INDUSTRY")
	private String industry;
	
	@Column(name = "INDUSTRY_OTHER_TYPE")
	private String industryOtherType;
	
	@Column(name = "PROJECT_SCOPE")
	private String projectScope;
	
	@Column(name = "PROJECT_OTHER_SCOPE")
	private String projectOtherScope;
	
	@Column(name = "EMPLOYMENT_CATEGORY")
	private String employmentCategory;
	
	@Column(name = "TEAM_SIZE")
	private Integer teamSize;
	
	@Column(name = "FRONT_END")
	@Type(type="text")
	private String frontEnd;
	
	@Column(name = "BACK_END")
	@Type(type="text")
	private String backEnd;
	
	@Column(name = "DATA_BASE")
	@Type(type="text")
	private String dataBase;
	
	@Column(name = "OTHER_TECHNOLOGIES")
	@Type(type="text")
	private String otherTechnologies;
	
	@Column(name = "TEST_MANAGEMENT")
	@Type(type="text")
	private String testManagement;
	
	@Column(name = "EXECUTION_MODEL")
	@Type(type="text")
	private String executionModel;
	
	@Column(name = "AGILE_METHODOLOGIES")
	@Type(type="text")
	private String agileMethodologies;
	
	@Column(name = "UUID")
    private String uuid;
	
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
    private String mailToProjectMembers;
    
    @Transient
    @JsonProperty
    private String mailToProjectManagers;
    
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String projectLocation) {
		this.projectLocation = projectLocation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustryOtherType() {
		return industryOtherType;
	}

	public void setIndustryOtherType(String industryOtherType) {
		this.industryOtherType = industryOtherType;
	}

	public String getProjectScope() {
		return projectScope;
	}

	public void setProjectScope(String projectScope) {
		this.projectScope = projectScope;
	}

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory(String employmentCategory) {
		this.employmentCategory = employmentCategory;
	}


	public String getProjectOtherScope() {
		return projectOtherScope;
	}

	public void setProjectOtherScope(String projectOtherScope) {
		this.projectOtherScope = projectOtherScope;
	}

	public Integer getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(Integer teamSize) {
		this.teamSize = teamSize;
	}

	public String getFrontEnd() {
		return frontEnd;
	}

	public void setFrontEnd(String frontEnd) {
		this.frontEnd = frontEnd;
	}

	public String getBackEnd() {
		return backEnd;
	}

	public void setBackEnd(String backEnd) {
		this.backEnd = backEnd;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getOtherTechnologies() {
		return otherTechnologies;
	}

	public void setOtherTechnologies(String otherTechnologies) {
		this.otherTechnologies = otherTechnologies;
	}

	public String getTestManagement() {
		return testManagement;
	}

	public void setTestManagement(String testManagement) {
		this.testManagement = testManagement;
	}

	public String getExecutionModel() {
		return executionModel;
	}

	public void setExecutionModel(String executionModel) {
		this.executionModel = executionModel;
	}

	public String getAgileMethodologies() {
		return agileMethodologies;
	}

	public void setAgileMethodologies(String agileMethodologies) {
		this.agileMethodologies = agileMethodologies;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMailToProjectMembers() {
		if(projectId.getIsMailToTeamMembers().equals(Status.Y)){
			mailToProjectMembers = Constants.YES;
		}else{
			mailToProjectMembers = Constants.NO;
		}
		return mailToProjectMembers;
	}

	public void setMailToProjectMembers(String mailToProjectMembers) {
		this.mailToProjectMembers = mailToProjectMembers;
	}

	public String getMailToProjectManagers() {
		if(projectId.getIsMailToProjectManager().equals(Status.Y)){
			mailToProjectManagers = Constants.YES;
		}else{
			mailToProjectManagers = Constants.NO;
		}
		return mailToProjectManagers;
	}

	public void setMailToProjectManagers(String mailToProjectManagers) {
		this.mailToProjectManagers = mailToProjectManagers;
	}
	
}
