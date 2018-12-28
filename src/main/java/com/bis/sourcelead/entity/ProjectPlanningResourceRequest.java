package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_PLANNING_RESOURCE_REQUEST")
public class ProjectPlanningResourceRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "PROJECT_PLANNING_REQUREST_ID")
	@JsonBackReference
  	private ProjectPlanningRequest projectPlanningRequestId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CANDIDATE_ID")
	@JsonBackReference
  	private Candidate candidateId;
	
	@Column(name = "CANDIDATE_NAME")
	private String candidateName;
	
	@Column(name="PAY_RATE")
    private Double payRate;
	
	@Column(name = "PAY_CURRENCY")
	private String payRateCurrency;
	
	@Column(name = "PAY_RATE_TYPE")
	private String payRateType;
	
	@Column(name="BILL_RATE")
    private Double billRate;
	
	@Column(name = "BILL_RATE_CURRENCY")
	private String billRateCurrency;
	
	@Column(name = "BILL_RATE_TYPE")
	private String billRateType;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "CONSULTING_TYPE")
	private String consultingType;
	
    @Column(name="ESTIMATED_HOURS")
    private Double estimatedHours;
    
    @Column(name="ESTIMATED_BUDGET")
    private Double estimatedBudget;
    
    @Column(name = "ESTIMATED_BUDGET_CURRENCY")
   	private String estimatedBudgetCurrency;
    
    @Column(name = "STATUS")
   	private Integer status;
    
    @Column(name="REJECTED_REASON", columnDefinition = "LONGTEXT default NULL")
   	private String rejectedReason;
    
    @Column(name = "RESOURCE_STATUS")
   	private String resourceStatus;
    
    @Column(name = "REQUEST_UUID")
   	private String requestUUID;
    
    @Column(name ="PERCENTAGE_OF_WORK")
    private BigDecimal percentageOfWork;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "ORG_LOCATION_ID")
    @JsonBackReference
  	private OrgLocations orgLocationId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
   	@JoinColumn(name = "APPROVER_USER_ID")
  	private User approverUser;
    
    @Column(name = "APPROVER_NAME")
	private String approverName;
    
    @Column(name = "REQUEST_STATUS")
   	private String requestStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
	
	@Transient
	@JsonProperty
	private String startDateStr;
	
	@Transient
	@JsonProperty
	private String endDateStr;
	
	@Transient
	@JsonProperty
	private Long requestedCandidateId;
	
	@Transient
	@JsonProperty
	private String payRateStr;
	
	@Transient
	@JsonProperty
	private String billRateStr;
	
    @Transient
    @JsonProperty
    private String candidateLocation;
    
    @Transient
    @JsonProperty
    private String requestedLocation;

    @Transient
	@JsonProperty
	private String requestedUser;
    
	

	public BigDecimal getPercentageOfWork() {
		return percentageOfWork;
	}

	public void setPercentageOfWork(BigDecimal percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProjectPlanningRequest getProjectPlanningRequestId() {
		return projectPlanningRequestId;
	}

	public void setProjectPlanningRequestId(ProjectPlanningRequest projectPlanningRequestId) {
		this.projectPlanningRequestId = projectPlanningRequestId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public Double getPayRate() {
		return payRate;
	}

	public void setPayRate(Double payRate) {
		this.payRate = payRate;
	}

	public String getPayRateCurrency() {
		return payRateCurrency;
	}

	public void setPayRateCurrency(String payRateCurrency) {
		this.payRateCurrency = payRateCurrency;
	}

	public String getPayRateType() {
		return payRateType;
	}

	public void setPayRateType(String payRateType) {
		this.payRateType = payRateType;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public String getBillRateType() {
		return billRateType;
	}

	public void setBillRateType(String billRateType) {
		this.billRateType = billRateType;
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

	public String getConsultingType() {
		return consultingType;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}

	public Double getEstimatedHours() {
		if(estimatedHours == null){
			estimatedHours = 0d;
		}
		return estimatedHours;
	}

	public void setEstimatedHours(Double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Double getEstimatedBudget() {
		if(estimatedBudget == null){
			estimatedBudget = 0d;
		}
		return estimatedBudget;
	}

	public void setEstimatedBudget(Double estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}

	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getStartDateStr() {
		if(startDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			startDateStr = dateFormat.format(startDate);;	
		}else{
			startDateStr = "";
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		if(endDate != null){
			SimpleDateFormat dateFormat = orgLocationId.getLocFullFormDateFormatter();
			endDateStr = dateFormat.format(endDate);	
		}else{
			endDateStr = "";
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Long getRequestedCandidateId() {
		if(candidateId != null && candidateId.getId() != null){
			requestedCandidateId = candidateId.getId();	
		}
		return requestedCandidateId;
	}

	public void setRequestedCandidateId(Long requestedCandidateId) {
		this.requestedCandidateId = requestedCandidateId;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getRequestUUID() {
		return requestUUID;
	}

	public void setRequestUUID(String requestUUID) {
		this.requestUUID = requestUUID;
	}

	public String getPayRateStr() {
		
		if(payRate != null){
			StringBuilder payRateBuilder = new StringBuilder();
			payRateStr = payRateBuilder.append(payRateCurrency).append(" ").append(payRate).append(" ").append(payRateType).toString();
		}else{
			payRateStr = "-------";
		}
		
		return payRateStr;
	}

	public void setPayRateStr(String payRateStr) {
		this.payRateStr = payRateStr;
	}

	public String getBillRateStr() {
		
		if(billRate != null){
			StringBuilder billRateBuilder = new StringBuilder();
			billRateStr = billRateBuilder.append(billRateCurrency).append(" ").append(billRate).append(" ").append(billRateType).toString();
		}else{
			billRateStr = "-------";
		}
		
		return billRateStr;
	}

	public void setBillRateStr(String billRateStr) {
		this.billRateStr = billRateStr;
	}

	public String getCandidateLocation() {
		StringBuilder loc = new StringBuilder();
		if(candidateId.getCity()!= null && !candidateId.getCity().isEmpty()){
			candidateLocation = loc.append(candidateId.getOrgLocationId().getOrgLocationName()).append(", ").append(candidateId.getCity()).append(", ").append(candidateId.getState()).append(", ").append(candidateId.getCountry()).toString();
		}else if(candidateId.getState() != null && !candidateId.getState().isEmpty() && candidateId.getCity() == null){
			candidateLocation = loc.append(candidateId.getOrgLocationId().getOrgLocationName()).append(", ").append(candidateId.getState()).append(", ").append(candidateId.getCountry()).toString();
		}else if(candidateId.getCountry() != null && !candidateId.getCountry().isEmpty() && candidateId.getState() == null && candidateId.getCity() == null){
			candidateLocation = loc.append(candidateId.getOrgLocationId().getOrgLocationName()).append(", ").append(candidateId.getCountry()).toString();
		}else{
			candidateLocation = loc.append(candidateId.getOrgLocationId().getOrgLocationName()).toString();
		}
		return candidateLocation;
	}

	public void setCandidateLocation(String candidateLocation) {
		this.candidateLocation = candidateLocation;
	}

	public String getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(String requestedUser) {
		this.requestedUser = requestedUser;
	}

	public User getApproverUser() {
		return approverUser;
	}

	public void setApproverUser(User approverUser) {
		this.approverUser = approverUser;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getRequestedLocation() {

		StringBuilder loc = new StringBuilder();
		if(orgLocationId.getCity()!= null && !orgLocationId.getCity().isEmpty()){
			requestedLocation = loc.append(orgLocationId.getOrgLocationName()).append(", ").append(orgLocationId.getCity()).append(", ").append(orgLocationId.getState()).append(", ").append(orgLocationId.getCountry()).toString();
		}else if(orgLocationId.getState() != null && !orgLocationId.getState().isEmpty() && orgLocationId.getCity() == null){
			requestedLocation = loc.append(orgLocationId.getOrgLocationName()).append(", ").append(orgLocationId.getState()).append(", ").append(orgLocationId.getCountry()).toString();
		}else if(orgLocationId.getCountry() != null && !orgLocationId.getCountry().isEmpty() && orgLocationId.getState() == null && orgLocationId.getCity() == null){
			requestedLocation = loc.append(orgLocationId.getOrgLocationName()).append(", ").append(orgLocationId.getCountry()).toString();
		}else{
			requestedLocation = loc.append(orgLocationId.getOrgLocationName()).toString();
		}
		return requestedLocation;
	}

	public void setRequestedLocation(String requestedLocation) {
		this.requestedLocation = requestedLocation;
	}
}
