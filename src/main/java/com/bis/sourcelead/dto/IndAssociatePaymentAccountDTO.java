package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;


public class IndAssociatePaymentAccountDTO {
    private long id;
    
    private String firstName;
    
    private String lastName;
    
    private boolean defaultAccount;
    private String accessToken;
    private String userId;
    private String accountId;
    private boolean access;
    private String accountType;
    private long orgLocationId;
    private String orgLocationName;
    private long orgId;
    private long associateUserId;
    private long candidateId;
    
    private String bankName;
    
    private String accountNumber;
    
    private String routingNumber;
    
    private String bsrNumber;
    private String micrCode;
    private boolean disabled;
    private String paymentAccountId;
    private String email;
    private String ifscCode;
    private String reason;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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
     * @return the orgLocationId
     */
    public long getOrgLocationId() {
        return orgLocationId;
    }

    /**
     * @param orgLocationId the orgLocationId to set
     */
    public void setOrgLocationId(long orgLocationId) {
        this.orgLocationId = orgLocationId;
    }

    /**
     * @return the orgId
     */
    public long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(long orgId) {
        this.orgId = orgId;
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
     * @return the orgLocationName
     */
    public String getOrgLocationName() {
        return orgLocationName;
    }

    /**
     * @param orgLocationName the orgLocationName to set
     */
    public void setOrgLocationName(String orgLocationName) {
        this.orgLocationName = orgLocationName;
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

	public long getAssociateUserId() {
		return associateUserId;
	}

	public void setAssociateUserId(long associateUserId) {
		this.associateUserId = associateUserId;
	}

	public long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	public String getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(String paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
     
}
