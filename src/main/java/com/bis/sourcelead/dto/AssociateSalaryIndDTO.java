package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.util.List;

public class AssociateSalaryIndDTO extends CommonDTO {

	private Long id;
	
	private Long candidateId;
	
	private BigDecimal ctc;
	
	private BigDecimal fbp;
	
	private BigDecimal variablePay;
	
	private Integer status;
	
	private String uuid;
    
	private Long createdBy;
    
  	private String createdDate;
    
  	private Long updatedBy;
    
  	private String updatedDate;
    
    private List<AssociateSalaryPayheadIndDTO> associateSalaryPayheadIndList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public BigDecimal getCtc() {
		return ctc;
	}

	public void setCtc(BigDecimal ctc) {
		this.ctc = ctc;
	}

	public BigDecimal getFbp() {
		return fbp;
	}

	public void setFbp(BigDecimal fbp) {
		this.fbp = fbp;
	}

	public BigDecimal getVariablePay() {
		return variablePay;
	}

	public void setVariablePay(BigDecimal variablePay) {
		this.variablePay = variablePay;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<AssociateSalaryPayheadIndDTO> getAssociateSalaryPayheadIndList() {
		return associateSalaryPayheadIndList;
	}

	public void setAssociateSalaryPayheadIndList(List<AssociateSalaryPayheadIndDTO> associateSalaryPayheadIndList) {
		this.associateSalaryPayheadIndList = associateSalaryPayheadIndList;
	}
}
