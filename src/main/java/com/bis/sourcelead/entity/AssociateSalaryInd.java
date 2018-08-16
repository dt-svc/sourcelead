package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ASSOCIATE_SALARY_IND")
public class AssociateSalaryInd {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@ManyToOne
    @JoinColumn(name="CANDIDATE")        
    private Candidate candidate;
	
	@Column(name = "CTC")
	private BigDecimal ctc;
	
	@Column(name = "FBP")
	private BigDecimal fbp;
	
	@Column(name = "VARIABLE_PAY")
	private BigDecimal variablePay;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "UUID")
	private String uuid;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "CREATED_BY")
  	private User createdBy;
    
    @Column(name = "CREATED_DATE")
  	private Date createdDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
   	@JoinColumn(name = "UPDATED_BY")
  	private User updatedBy;
    
    @Column(name = "UPDATED_DATE")
  	private Date updatedDate;
    
    @NotFound(action = NotFoundAction.IGNORE)
	@OneToMany(mappedBy = "associateSalaryInd", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SELECT)
	private Set<AssociateSalaryPayheadInd> associateSalaryPayheadIndList = new HashSet<AssociateSalaryPayheadInd>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Set<AssociateSalaryPayheadInd> getAssociateSalaryPayheadIndList() {
		return associateSalaryPayheadIndList;
	}

	public void setAssociateSalaryPayheadIndList(Set<AssociateSalaryPayheadInd> associateSalaryPayheadIndList) {
		this.associateSalaryPayheadIndList = associateSalaryPayheadIndList;
	}
}
