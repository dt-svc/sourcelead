package com.bis.sourcelead.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Raja Gopal Reddy P
 */
@Entity
@Table(name = "PAYMENT_ACCOUNT")
public class PaymentAccount extends Record implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    private String email;
    
    @Column(name = "FIRST_NAME")    
    private String firstName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Column(name = "IS_DEFAULT")
    private boolean defaultAccount;
    
    @Column(name = "ACCESS_TOKEN")
    private String accessToken;
    
    @Column(name = "USER_ID")
    private String userId;
    
    @Column(name = "ACCOUNT_ID")
    private String accountId;
    
    @Column(name = "GRANT_ACCESS")
    private boolean access;
    
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    
    @Column(name = "ROUTING_NUMBER")
    private String routingNumber;
    
    @Column(name = "BSR_NUMBER")
    private String bsrNumber;
    
    @Column(name = "MICR_CODE")
    private String micrCode;
    
    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name="DISABLED")
    private boolean disabled;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
    private OrgLocations orgLocation;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANDIDATE_ID", nullable = false)
    private Candidate candidateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID")
    private Org org;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSOCIATE_USER_ID")
    private User associateUser;
    
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the defaultAccount
     */
    public boolean isDefaultAccount() {
        return defaultAccount;
    }

    /**
     * @param defaultAccount the defaultAccount to set
     */
    public void setDefaultAccount(boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the access
     */
    public boolean isAccess() {
        return access;
    }

    /**
     * @param access the access to set
     */
    public void setAccess(boolean access) {
        this.access = access;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the orgLocation
     */
    public OrgLocations getOrgLocation() {
        return orgLocation;
    }

    /**
     * @param orgLocation the orgLocation to set
     */
    public void setOrgLocation(OrgLocations orgLocation) {
        this.orgLocation = orgLocation;
    }

    /**
     * @return the org
     */
    public Org getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(Org org) {
        this.org = org;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the routingNumber
     */
    public String getRoutingNumber() {
        return routingNumber;
    }

    /**
     * @param routingNumber the routingNumber to set
     */
    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getBsrNumber() {
		return bsrNumber;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setBsrNumber(String bsrNumber) {
		this.bsrNumber = bsrNumber;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public User getAssociateUser() {
		return associateUser;
	}

	public void setAssociateUser(User associateUser) {
		this.associateUser = associateUser;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}
}
