package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LCA_VISA_CLARIFICATIONS")
public class LcaVisaClarifications implements Serializable {

	private static final long serialVersionUID = 1L;
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne() 
	@JoinColumn(name = "LCA_ID")
	private Lca lcaId;
	
	@Column(name = "NEW_EMPLOYMENT")
	private String newEmployment;
	
	@Column(name = "NEW_EMPLOYMENT_COUNT")
	private Integer newEmploymentCount;
	
	@Column(name = "CONCURRENT_EMPLOYMENT")
	private String concurrentEmployment;
	
	@Column(name = "CONCURRENT_EMPLOYMENT_COUNT")
	private Integer concurrentEmploymentCount;
	
	@Column(name = "EXTENTION")
	private String extention;
	
	@Column(name = "EXTENTION_COUNT")
	private Integer extentionCount;
	
	@Column(name = "CHANGE_IN_EMPLOYER")
	private String changeInEmployer;
	
	@Column(name = "CHANGE_IN_EMPLOYER_COUNT")
	private Integer changeInEmployerCount;
	
	@Column(name = "CHANGE_IN_PREVIOUS_APPROVED_EMPLOYMENT")
	private String changeInPreApproEmployment;
	
	@Column(name = "CHANGE_IN_PREVIOUS_APPROVED_EMPLMT_COUNT")
	private Integer changeInPreApproEmplmtCount;
	
	@Column(name = "AMENDMENT")
	private String amendment;
	
	@Column(name = "AMENDMENT_COUNT")
	private Integer amendmentCount;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Lca getLcaId() {
		return lcaId;
	}

	public void setLcaId(Lca lcaId) {
		this.lcaId = lcaId;
	}

	public String getNewEmployment() {
		return newEmployment;
	}

	public void setNewEmployment(String newEmployment) {
		this.newEmployment = newEmployment;
	}

	public String getConcurrentEmployment() {
		return concurrentEmployment;
	}

	public void setConcurrentEmployment(String concurrentEmployment) {
		this.concurrentEmployment = concurrentEmployment;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public String getChangeInEmployer() {
		return changeInEmployer;
	}

	public void setChangeInEmployer(String changeInEmployer) {
		this.changeInEmployer = changeInEmployer;
	}

	public String getChangeInPreApproEmployment() {
		return changeInPreApproEmployment;
	}

	public void setChangeInPreApproEmployment(String changeInPreApproEmployment) {
		this.changeInPreApproEmployment = changeInPreApproEmployment;
	}

	public String getAmendment() {
		return amendment;
	}

	public void setAmendment(String amendment) {
		this.amendment = amendment;
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

	public Integer getNewEmploymentCount() {
		return newEmploymentCount;
	}

	public void setNewEmploymentCount(Integer newEmploymentCount) {
		this.newEmploymentCount = newEmploymentCount;
	}

	public Integer getConcurrentEmploymentCount() {
		return concurrentEmploymentCount;
	}

	public void setConcurrentEmploymentCount(Integer concurrentEmploymentCount) {
		this.concurrentEmploymentCount = concurrentEmploymentCount;
	}

	public Integer getExtentionCount() {
		return extentionCount;
	}

	public void setExtentionCount(Integer extentionCount) {
		this.extentionCount = extentionCount;
	}

	public Integer getChangeInEmployerCount() {
		return changeInEmployerCount;
	}

	public void setChangeInEmployerCount(Integer changeInEmployerCount) {
		this.changeInEmployerCount = changeInEmployerCount;
	}

	public Integer getChangeInPreApproEmplmtCount() {
		return changeInPreApproEmplmtCount;
	}

	public void setChangeInPreApproEmplmtCount(Integer changeInPreApproEmplmtCount) {
		this.changeInPreApproEmplmtCount = changeInPreApproEmplmtCount;
	}

	public Integer getAmendmentCount() {
		return amendmentCount;
	}

	public void setAmendmentCount(Integer amendmentCount) {
		this.amendmentCount = amendmentCount;
	}
	
}
