package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "ASSOCIATE_PF_ESI_DETAILS")
public class AssociatePfEsiDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User userId;
	

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidateId;
	
	 
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "ELIGIBLE_FOR_PF")
	private String eligibleForPf;
	
	@Column(name = "UAN_NUMBER")
	private String uanNumber;
	
	@Column(name = "PF_NUMBER")
	private String pfNumber;
	
	@Column(name = "ENROLLED_DATE")
	private Date enroleDate;

	@Column(name = "ELIGIBLE_FOR_ESI")
	private String eligibleForEsi;
 
	@Column(name = "ESI_NUMBER")
	private String esinumber;

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

	public User getUserId() {
		return userId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public String getEligibleForPf() {
		return eligibleForPf;
	}

	public String getUanNumber() {
		return uanNumber;
	}

	public String getPfNumber() {
		return pfNumber;
	}

	public Date getEnroleDate() {
		return enroleDate;
	}

	public String getEligibleForEsi() {
		return eligibleForEsi;
	}

	public String getEsinumber() {
		return esinumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public void setEligibleForPf(String eligibleForPf) {
		this.eligibleForPf = eligibleForPf;
	}

	public void setUanNumber(String uanNumber) {
		this.uanNumber = uanNumber;
	}

	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}

	public void setEnroleDate(Date enroleDate) {
		this.enroleDate = enroleDate;
	}

	public void setEligibleForEsi(String eligibleForEsi) {
		this.eligibleForEsi = eligibleForEsi;
	}

	public void setEsinumber(String esinumber) {
		this.esinumber = esinumber;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}
}
