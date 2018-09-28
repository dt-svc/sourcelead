package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Shivayogi
 *
 */
@Entity
@Table(name = "CANDIDATE_EDUCATION")
public class CandidateEducation implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orglocationId;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "SPECIALIZATION")
	private String specialization;
	 
	@Column(name = "STARTDATE")
	private Date startDate;
	
	@Column(name = "ENDDATE")
	private Date endDate;

	@Column(name = "GRADE")
	private String grade;
	
	@Column(name = "GRADE_TYPE")
	private String gradeType;
	
	@Column(name = "INSTITUTE")
	private String institute;
	
	@Column(name = "ADDRESS")
	private String address;
	
	// immigration related files purpose start
	
	@Column(name = "EDUCATION_FILE_CONTENT_TYPE")
	private String educationFileContentType;
	
	@Column(name = "EDUCATION_FILE_URL",columnDefinition = "TEXT default NULL")
	private String educationFileUrl;
	
	@Column(name = "EDUCATION_FILE_NAME")
	private String educationFileName;
	
	// immigration related files purpose end
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = " STATUS")
	private Integer status;
	
	@OneToMany(mappedBy = "candidateEducation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<CandidateEducationHistory> candidateEducationHistoryList = new HashSet<CandidateEducationHistory>(0);
	
	@Transient
    private long candidateEduId;
	
	@Transient
	@JsonProperty
    private String startDateStr;
	
	@Transient
	private String msg;
	
	@Transient
	@JsonProperty
	private String endDateStr;
	
	@Transient
	private List<LookUpData> educationList;
	
	@Transient
	 private LinkedHashSet<String> allAndCandidateEducations;
	
	public long getCandidateEduId() {
		return candidateEduId;
	}

	public void setCandidateEduId(long candidateEduId) {
		this.candidateEduId = candidateEduId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrglocationId() {
		return orglocationId;
	}

	public void setOrglocationId(OrgLocations orglocationId) {
		this.orglocationId = orglocationId;
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

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradeType() {
		return gradeType;
	}

	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public List<LookUpData> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<LookUpData> educationList) {
		this.educationList = educationList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LinkedHashSet<String> getAllAndCandidateEducations() {
		return allAndCandidateEducations;
	}

	public void setAllAndCandidateEducations(LinkedHashSet<String> allAndCandidateEducations) {
		this.allAndCandidateEducations = allAndCandidateEducations;
	}

	public String getEducationFileContentType() {
		return educationFileContentType;
	}

	public void setEducationFileContentType(String educationFileContentType) {
		this.educationFileContentType = educationFileContentType;
	}

	public String getEducationFileUrl() {
		return educationFileUrl;
	}

	public void setEducationFileUrl(String educationFileUrl) {
		this.educationFileUrl = educationFileUrl;
	}

	public String getEducationFileName() {
		return educationFileName;
	}

	public void setEducationFileName(String educationFileName) {
		this.educationFileName = educationFileName;
	}

	public Set<CandidateEducationHistory> getCandidateEducationHistoryList() {
		return candidateEducationHistoryList;
	}

	public void setCandidateEducationHistoryList(Set<CandidateEducationHistory> candidateEducationHistoryList) {
		this.candidateEducationHistoryList = candidateEducationHistoryList;
	}
	
	
	
}
