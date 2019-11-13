package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
 * @author Ajith Matta
 *
 */
@Entity
@Table(name = "CREDIT_DEBIT_NOTE")
public class CreditDebitNote implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	
	@Column(name = "INVOICE_BILL_ID")
	private String invoiceId;

	@ManyToOne
	@JoinColumn(name = "ORG_LOCATIONS_ID", nullable = false)
	private OrgLocations orgLocations;
	
	@ManyToOne
    @JoinColumn(name="RESPONDER_LOCATION_ID", nullable=false )     
	private OrgLocations responderLocation;
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )
	private OrgRelations orgRelations;
	
	@Column(name = "CONTACT_USER_ID")
	private String contactUser;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_NOTE_ID")
	private CreditDebitNote parentCreditDebitNote;
	
	@ManyToOne
    @JoinColumn(name="PAYMENT_ACCOUNT_ID", nullable=false )     
	private PaymentAccount paymentAccount;
	
    @Column(name="OWNER_USER_ID")     
	private String ownerUser;
	
	@Column(name = "BILLING_ADDRESS")
	private String billingAddress;
	
	@Column(name = "CREDIT_DEBIT_UUID")
	private String creditDebitUUID;
	
	@Column(name = "APPROVER_CHECK_BOX")
	private String approverCheckBox;
	
	@Column(name = "APPROVERS")
	private String approvers;
	
	@Column(name = "SHIPPING_ADDRESS")
	private String shippingAddress;
	
	@Column(name = "REFERENCE_NUMBER")
	private String referenceNumber;
	
	@Column(name = "NOTE_TYPE")
	private String noteType;
	
	@Column(name = "NOTE_STATUS")
	private String noteStatus;
	
	@Column(name = "GENERATED_ID")
	private String generatedId;
		
	@Column(name = "REASON", columnDefinition = "TEXT default NULL")
	private String reason;
	
	@Column(name = "REJECT_REASON", columnDefinition = "TEXT default NULL")
	private String rejectReason;
	
	@Column(name = "CREDIT_NOTE_DATE")
	private Date date;
	
	@Column(name = "SUB_TOTAL")
	private BigDecimal balance;
	
	@Column(name = "TOTAL")
	private BigDecimal total;
	
	@Column(name = "DISCOUNT_VALUE")
	private BigDecimal discountValue;
	
	@Column(name = "DISCOUNT_TOTAL")
	private BigDecimal discountTotal;

	@Column(name = "DISCOUNT_TYPE")
	private String discountType;

	@Column(name = "STATUS")
	private String status;
	
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
	
	@Column(name = "LOGIN_CURRENCY_CODE")
	private String loginCurrencyCode;
	
	@Column(name = "VENDOR_CURRENCY_CODE")
	private String vendorCurrencyCode;
	
	@Column(name = "EXCHANGE_CURRENCY_RATE")
	private BigDecimal exchangeCurrencyRate;
	
	@Column(name = "AFTER_EXCHANGE_GRANDTOTAL")
	private BigDecimal afterExchangeGrandTotal;
	
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creditDebitNote")
	private Set<CreditDebitLineItems> creditDebitLineItems;
	
	@Transient
	@JsonProperty
	private List<CreditDebitLineItems> listOfCreditDebitLineItems;
	
	@Transient
	@JsonProperty
	private String invoiceMultipleIds;
	
	@Transient
	@JsonProperty
	private String approversMultipleNames;
	
	@Transient
	@JsonProperty
	private String contactsMultipleEmails;
	
	@Transient
	@JsonProperty
	private String contactsMultipleNames;
	
	@Transient
	@JsonProperty
	private String clientOrVendorName;
	
	@Transient
	@JsonProperty
	private String creditDebitNoteDateStr;
	
	@Transient
	@JsonProperty
	private String ownerNames;
	
	@Transient
	@JsonProperty
	private String isNewRecord;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInvoice() {
		return invoiceId;
	}

	public void setInvoice(String invoice) {
		this.invoiceId = invoice;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
	}

	public OrgLocations getResponderLocation() {
		return responderLocation;
	}

	public void setResponderLocation(OrgLocations responderLocation) {
		this.responderLocation = responderLocation;
	}

	public OrgRelations getOrgRelations() {
		return orgRelations;
	}

	public void setOrgRelations(OrgRelations orgRelations) {
		this.orgRelations = orgRelations;
	}

	public String getContactUser() {
		return contactUser;
	}

	public void setContactUser(String contactUser) {
		this.contactUser = contactUser;
	}

	public CreditDebitNote getParentCreditDebitNote() {
		return parentCreditDebitNote;
	}

	public void setParentCreditDebitNote(CreditDebitNote parentCreditDebitNote) {
		this.parentCreditDebitNote = parentCreditDebitNote;
	}


	public String getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(String ownerUser) {
		this.ownerUser = ownerUser;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCreditDebitUUID() {
		return creditDebitUUID;
	}

	public void setCreditDebitUUID(String creditDebitUUID) {
		this.creditDebitUUID = creditDebitUUID;
	}

	public String getApproverCheckBox() {
		return approverCheckBox;
	}

	public void setApproverCheckBox(String approverCheckBox) {
		this.approverCheckBox = approverCheckBox;
	}

	public String getApprovers() {
		return approvers;
	}

	public void setApprovers(String approvers) {
		this.approvers = approvers;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getNoteType() { 
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreditNoteDate() {
		return date;
	}

	public void setCreditNoteDate(Date creditNoteDate) {
		this.date = creditNoteDate;
	}

	public BigDecimal getSubTotal() {
		return balance;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.balance = subTotal;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<CreditDebitLineItems> getCreditDebitLineItems() {
		return creditDebitLineItems;
	}

	public void setCreditDebitLineItems(Set<CreditDebitLineItems> creditDebitLineItems) {
		this.creditDebitLineItems = creditDebitLineItems;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public BigDecimal getDiscountTotal() {
		return discountTotal;
	}

	public void setDiscountTotal(BigDecimal discountTotal) {
		this.discountTotal = discountTotal;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<CreditDebitLineItems> getListOfCreditDebitLineItems() {
		return listOfCreditDebitLineItems;
	}

	public void setListOfCreditDebitLineItems(List<CreditDebitLineItems> listOfCreditDebitLineItems) {
		this.listOfCreditDebitLineItems = listOfCreditDebitLineItems;
	}

	public String getInvoiceMultipleIds() {
		return invoiceMultipleIds;
	}

	public void setInvoiceMultipleIds(String invoiceMultipleIds) {
		this.invoiceMultipleIds = invoiceMultipleIds;
	}

	public String getClientOrVendorName() {
		return clientOrVendorName;
	}

	public void setClientOrVendorName(String clientOrVendorName) {
		this.clientOrVendorName = clientOrVendorName;
	}

	public String getApproversMultipleNames() {
		return approversMultipleNames;
	}

	public void setApproversMultipleNames(String approversMultipleNames) {
		this.approversMultipleNames = approversMultipleNames;
	}

	public String getContactsMultipleEmails() {
		return contactsMultipleEmails;
	}

	public void setContactsMultipleEmails(String contactsMultipleEmails) {
		this.contactsMultipleEmails = contactsMultipleEmails;
	}

	public String getContactsMultipleNames() {
		return contactsMultipleNames;
	}

	public void setContactsMultipleNames(String contactsMultipleNames) {
		this.contactsMultipleNames = contactsMultipleNames;
	}

	public String getCreditDebitNoteDateStr() {
		return creditDebitNoteDateStr;
	}

	public void setCreditDebitNoteDateStr(String creditDebitNoteDateStr) {
		this.creditDebitNoteDateStr = creditDebitNoteDateStr;
	}

	public String getOwnerNames() {
		return ownerNames;
	}

	public void setOwnerNames(String ownerNames) {
		this.ownerNames = ownerNames;
	}

	public String getIsNewRecord() {
		return isNewRecord;
	}

	public void setIsNewRecord(String isNewRecord) {
		this.isNewRecord = isNewRecord;
	}

	public String getLoginCurrencyCode() {
		return loginCurrencyCode;
	}

	public void setLoginCurrencyCode(String loginCurrencyCode) {
		this.loginCurrencyCode = loginCurrencyCode;
	}

	public String getVendorCurrencyCode() {
		return vendorCurrencyCode;
	}

	public void setVendorCurrencyCode(String vendorCurrencyCode) {
		this.vendorCurrencyCode = vendorCurrencyCode;
	}

	public BigDecimal getExchangeCurrencyRate() {
		return exchangeCurrencyRate;
	}

	public void setExchangeCurrencyRate(BigDecimal exchangeCurrencyRate) {
		this.exchangeCurrencyRate = exchangeCurrencyRate;
	}

	public BigDecimal getAfterExchangeGrandTotal() {
		return afterExchangeGrandTotal;
	}

	public void setAfterExchangeGrandTotal(BigDecimal afterExchangeGrandTotal) {
		this.afterExchangeGrandTotal = afterExchangeGrandTotal;
	}

	public PaymentAccount getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(PaymentAccount paymentAccount) {
		this.paymentAccount = paymentAccount;
	}


	

	}