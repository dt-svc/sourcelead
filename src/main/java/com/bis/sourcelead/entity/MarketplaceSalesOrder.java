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


@Entity
@Table(name = "CANDIDATE_MARKET_PLACE_SALES_ORDER")
public class MarketplaceSalesOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="MARKET_PLACE_ID", nullable = false)
	private  CandidateMarketPlace  candidateMarketPlace;
	
	@Column(name = "COMPANY_NAME" )
	private String companyName;
	
	@Column(name = "ORDER_NO" , columnDefinition = "TEXT default NULL")
	private String orderNo;
	
	@Column(name = "PAYMENT_STATUS" )
	private String paymentStatus;
	
	@Column(name = "TOTAL_RESUME_PRICE")
	private Double totalResumePrice;
	
	@Column(name = "NO_OF_RESUMES")
	private Double noOfResumes;
	
	@Column(name = "PAYMENT_ID" , columnDefinition = "TEXT default NULL")
	private String paymentId;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "SALES_UUID ")
	private String salesUuid;


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


	public CandidateMarketPlace getCandidateMarketPlace() {
		return candidateMarketPlace;
	}

	public void setCandidateMarketPlace(CandidateMarketPlace candidateMarketPlace) {
		this.candidateMarketPlace = candidateMarketPlace;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getTotalResumePrice() {
		return totalResumePrice;
	}

	public void setTotalResumePrice(Double totalResumePrice) {
		this.totalResumePrice = totalResumePrice;
	}

	public Double getNoOfResumes() {
		return noOfResumes;
	}

	public void setNoOfResumes(Double noOfResumes) {
		this.noOfResumes = noOfResumes;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public String getSalesUuid() {
		return salesUuid;
	}

	public void setSalesUuid(String salesUuid) {
		this.salesUuid = salesUuid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

	