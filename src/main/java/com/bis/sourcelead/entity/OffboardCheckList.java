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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "OFFBOARD_CHECKLIST")
public class OffboardCheckList implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CANDIDATE_OFF_BOARDING_ID", nullable = false)
   	private CandidateOffBoarding candidateOffBoardingId;
    
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OFF_BOARDING_TEMPLATE_ID", nullable = false)
	private OffBoardingTemplate offboardingTemplateId;
    
    @Column(name = "CHECK_LIST_JSON_STRING")
	@Type(type="text")
	private String checkListJsonString;
    
    @Column(name = "CHECK_LIST_JSON_VALUE_STRING")
   	@Type(type="text")
   	private String checkListJsonValueString;
    
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

   	
   	
   	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandidateOffBoarding getCandidateOffBoardingId() {
		return candidateOffBoardingId;
	}

	public void setCandidateOffBoardingId(CandidateOffBoarding candidateOffBoardingId) {
		this.candidateOffBoardingId = candidateOffBoardingId;
	}

	public OffBoardingTemplate getOffboardingTemplateId() {
		return offboardingTemplateId;
	}

	public void setOffboardingTemplateId(OffBoardingTemplate offboardingTemplateId) {
		this.offboardingTemplateId = offboardingTemplateId;
	}

	public String getCheckListJsonString() {
		return checkListJsonString;
	}

	public void setCheckListJsonString(String checkListJsonString) {
		this.checkListJsonString = checkListJsonString;
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

	public String getCheckListJsonValueString() {
		return checkListJsonValueString;
	}

	public void setCheckListJsonValueString(String checkListJsonValueString) {
		this.checkListJsonValueString = checkListJsonValueString;
	}
   	
	    

}
