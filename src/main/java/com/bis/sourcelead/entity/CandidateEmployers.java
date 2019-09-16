package com.bis.sourcelead.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Ajith Matta
 * @create date 25 Aug 2016
 */


@Entity
@Table(name = "CANDIDATE_EMPLOYERS")
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "candidateEmployers")
public class CandidateEmployers implements Serializable{
	
	
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable=false)
	private Candidate candidateId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_GLOBAL_ID", nullable = false)
	private CandidateGlobal candidateGlobalId;
		
	
	@Column(name = "NAME")
	private String name ;
	
	@Column(name = "ISCURRENTPOSITION")
	private String isCurrentPosition ;
	
	@Column(name = "DESIGNATION ")
	private String designation ;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = " STARTMONTH ")
	private String startMonth;
	
	@Column(name = " STARTDATE ")
	private Date startDate;
	
	@Column(name = " ENDMONTH ")
	private String endMonth;
	
	@Column(name = " STARTYEAR ")
	private String startYear;
	
	@Column(name = " ENDYEAR ")
	private String endYear;
	
	@Column(name = " ENDDATE  ")
	private Date endDate;

	// immigration column start
	@Column(name = " TAX_ID")
	private String taxId;
	
	// immigration related files purpose start
	
	@Column(name = "EMPLOYER_FILE_CONTENT_TYPE")
	private String employerFileContentType;
		
	@Column(name = "EMPLOYER_FILE_URL",columnDefinition = "TEXT default NULL")
	private String employerFileUrl;
		
	@Column(name = "EMPLOYER_FILE_NAME")
	private String employerFileName;
		
	// immigration related files purpose end

	@Column(name = " EMPLOYMENT_TYPE")
	private String employmentType;

	@Column(name = " JOB_TITLE")
	private String jobTitle;

	// immigration column end

	@Column(name = "STATUS ")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = " UPDATED_BY")
	private String updatedBy;
	
	@OneToMany(mappedBy = "candidateEmployers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateEmployersHistory> candidateEmployersHistoryList = new HashSet<CandidateEmployersHistory>(0);

	@Transient
	private Long candidateEmployeeId;
	
	@Transient
	private String msg;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}


	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsCurrentPosition() {
		return isCurrentPosition;
	}

	public void setIsCurrentPosition(String isCurrentPosition) {
		this.isCurrentPosition = isCurrentPosition;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	public Long getCandidateEmployeeId() {
		return candidateEmployeeId;
	}

	public void setCandidateEmployeeId(Long candidateEmployeeId) {
		this.candidateEmployeeId = candidateEmployeeId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getEmployerFileContentType() {
		return employerFileContentType;
	}

	public void setEmployerFileContentType(String employerFileContentType) {
		this.employerFileContentType = employerFileContentType;
	}

	public String getEmployerFileUrl() {
		return employerFileUrl;
	}

	public void setEmployerFileUrl(String employerFileUrl) {
		this.employerFileUrl = employerFileUrl;
	}

	public String getEmployerFileName() {
		return employerFileName;
	}

	public void setEmployerFileName(String employerFileName) {
		this.employerFileName = employerFileName;
	}

	public Set<CandidateEmployersHistory> getCandidateEmployersHistoryList() {
		return candidateEmployersHistoryList;
	}

	public void setCandidateEmployersHistoryList(Set<CandidateEmployersHistory> candidateEmployersHistoryList) {
		this.candidateEmployersHistoryList = candidateEmployersHistoryList;
	}

	public CandidateGlobal getCandidateGlobalId() {
		return candidateGlobalId;
	}

	public void setCandidateGlobalId(CandidateGlobal candidateGlobalId) {
		this.candidateGlobalId = candidateGlobalId;
	}
	
}
