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
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT default NULL")
	private String description;
	
	@Column(name = "PAYMENT_STATUS" )
	private String paymentStatus;
	
	@Column(name = "TOTAL_RESUME_PRICE")
	private Double totalPrice;
	
	@Column(name = "NO_OF_RESUMES")
	private String totalResume;
	
	@Column(name = "PAYMENT_ID" , columnDefinition = "TEXT default NULL")
	private String paymentId;
	
	@Column(name = "ORDER_DATE ")
	private Date orderDate;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "COUNTRY_CODE")
	private String countryCode;
	
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

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
		return totalPrice;
	}

	public void setTotalResumePrice(Double totalResumePrice) {
		this.totalPrice = totalResumePrice;
	}

	public String getNoOfResumes() {
		return totalResume;
	}

	public void setNoOfResumes(String noOfResumes) {
		this.totalResume = noOfResumes;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
}

	