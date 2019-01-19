package com.bis.sourcelead.payroll.entity;

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

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;


/**
 * 
 * @author pavan jampana
 * 
 * this entity is used to track history of Us Paroll Associate Tax.
 *
 */
@Entity
@Table(name = "US_PAYROLL_ASSOCIATE_TAX_HISTORY")
public class USPayrollAssociateTaxHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	                                                                                                  
	@Column(name = "ASSOCIATE_TAX_ID")                                                                
	private Long assTaxId;                                                                            
	                                                                                                  
	@Column(name = "CANDIDATE_ID")                                                                    
	private Long candidate;                                                                           
	                                                                                                  
	@Column(name = "ORG_LOCATION_ID")                                                                 
	private Long orgLocationId;                                                                       
	                                                                                                  
	@Column(name = "PREVIOUS_FEDERAL_TAX_FILLIING_STATUS")                                                     
	private String previousFederalTaxFilliingStatus; 
	
	@Column(name = "FEDERAL_TAX_FILLIING_STATUS")                                                     
	private String federalTaxFilliingStatus; 
	                                                                                                  
	@Column(name = "FEDERAL_TAX_ALLOWANCES")                                                          
	private String federalTaxAllowances;   
	
	@Column(name = "PREVIOUS_FEDERAL_TAX_ALLOWANCES")                                                          
	private String previousFederalTaxAllowances;   
	                                                                                                  
	@Column(name = "FEDERAL_TAX_ADDITIONNAL_WITH_HOLDING")                                            
	private String federalTaxAdditionnalWithHolding; 
	
	@Column(name = "PREVIOUS_FEDERAL_TAX_ADDITIONNAL_WITH_HOLDING")                                            
	private String previousFederalTaxAdditionnalWithHolding; 
	                                                                                                  
	@Column(name = "STATE_TAX_FILLIING_STATUS")                                                       
	private String stateTaxFilliingStatus; 
	
	@Column(name = "PREVIOUS_STATE_TAX_FILLIING_STATUS")                                                       
	private String previousStateTaxFilliingStatus;
	                                                                                                  
	@Column(name = "STATE_TAX_ALLOWANCES")                                                            
	private String stateTaxAllowances;
	
	@Column(name = "PREVIOUS_STATE_TAX_ALLOWANCES")                                                            
	private String previousStateTaxAllowances;  
	                                                                                                  
	@Column(name = "STATE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String stateTaxAdditionnalWithHolding;
	
	@Column(name = "PREVIOUS_STATE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String previousStateTaxAdditionnalWithHolding;
	
	@Column(name = "RESIDENCE_TAX_FILLIING_STATUS")
	private String residenceTaxFilliingStatus;
	
	@Column(name = "PREVIOUS_RESIDENCE_TAX_FILLIING_STATUS")
	private String previousResidenceTaxFilliingStatus;

	@Column(name = "RESIDENCE_TAX_ALLOWANCES")
	private String residenceTaxAllowances;
	
	@Column(name = "PREVIOUS_RESIDENCE_TAX_ALLOWANCES")
	private String previousResidenceTaxAllowances;
	
	@Column(name = "RESIDENCE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String residenceTaxAdditionnalWithHolding;
	
	@Column(name = "PREVIOUS_RESIDENCE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String previousResidenceTaxAdditionnalWithHolding;
	
	@Column(name = "SUPPLEMENT_TYPE")
	private String supplementType;
	
	@Column(name = "PREVIOUS_SUPPLEMENT_TYPE")
	private String previousSupplementType;
	
	@Column(name = "FEDERAL_EXEMPT")
	private Boolean federalExempt;
	
	@Column(name = "PREVIOUS_FEDERAL_EXEMPT")
	private Boolean previousFederalExempt;
	
	@Column(name = "MEDIACARE_EXEMPT")
	private Boolean mediacareExempt;
	
	@Column(name = "PREVIOUS_MEDIACARE_EXEMPT")
	private Boolean previousMediacareExempt;
	
	@Column(name = "SOCIAL_SECURITY_EXEMPT")
	private Boolean socialSecurityExempt;
	
	@Column(name = "PREVIOUS_SOCIAL_SECURITY_EXEMPT")
	private Boolean previousSocialSecurityExempt;
	
	@Column(name = "FUTA_EXEMPT")
	private Boolean futaExempt;
	
	@Column(name = "PREVIOUS_FUTA_EXEMPT")
	private Boolean previousFutaExempt;
	
	@Column(name = "SUTA_EXEMPT")
	private Boolean sutaExempt;
	
	@Column(name = "PREVIOUS_SUTA_EXEMPT")
	private Boolean previousSutaExempt;
	
	@Column(name = "LOCAL_TAX")
	private String localTax;
	
	@Column(name = "PREVIOUS_LOCAL_TAX")
	private String previousLocalTax;
	
	@Column(name = "LOCAL_TAX_STATE")
	private String localTaxState;
	
	@Column(name = "PREVIOUS_LOCAL_TAX_STATE")
	private String previousLocalTaxState;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "ASSOCIATE_TAX_HIS_UUID")
	private String associateTaxUuid;

	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getAssTaxId()
	{
		return assTaxId;
	}

	public void setAssTaxId(Long assTaxId)
	{
		this.assTaxId = assTaxId;
	}

	public Long getCandidate()
	{
		return candidate;
	}

	public void setCandidate(Long candidate)
	{
		this.candidate = candidate;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getFederalTaxFilliingStatus()
	{
		return federalTaxFilliingStatus;
	}

	public void setFederalTaxFilliingStatus(String federalTaxFilliingStatus)
	{
		this.federalTaxFilliingStatus = federalTaxFilliingStatus;
	}

	public String getFederalTaxAllowances()
	{
		return federalTaxAllowances;
	}

	public void setFederalTaxAllowances(String federalTaxAllowances)
	{
		this.federalTaxAllowances = federalTaxAllowances;
	}

	public String getFederalTaxAdditionnalWithHolding()
	{
		return federalTaxAdditionnalWithHolding;
	}

	public void setFederalTaxAdditionnalWithHolding(String federalTaxAdditionnalWithHolding)
	{
		this.federalTaxAdditionnalWithHolding = federalTaxAdditionnalWithHolding;
	}

	public String getStateTaxFilliingStatus()
	{
		return stateTaxFilliingStatus;
	}

	public void setStateTaxFilliingStatus(String stateTaxFilliingStatus)
	{
		this.stateTaxFilliingStatus = stateTaxFilliingStatus;
	}

	public String getStateTaxAllowances()
	{
		return stateTaxAllowances;
	}

	public void setStateTaxAllowances(String stateTaxAllowances)
	{
		this.stateTaxAllowances = stateTaxAllowances;
	}

	public String getStateTaxAdditionnalWithHolding()
	{
		return stateTaxAdditionnalWithHolding;
	}

	public void setStateTaxAdditionnalWithHolding(String stateTaxAdditionnalWithHolding)
	{
		this.stateTaxAdditionnalWithHolding = stateTaxAdditionnalWithHolding;
	}

	public String getResidenceTaxFilliingStatus()
	{
		return residenceTaxFilliingStatus;
	}

	public void setResidenceTaxFilliingStatus(String residenceTaxFilliingStatus)
	{
		this.residenceTaxFilliingStatus = residenceTaxFilliingStatus;
	}

	public String getResidenceTaxAllowances()
	{
		return residenceTaxAllowances;
	}

	public void setResidenceTaxAllowances(String residenceTaxAllowances)
	{
		this.residenceTaxAllowances = residenceTaxAllowances;
	}

	public String getResidenceTaxAdditionnalWithHolding()
	{
		return residenceTaxAdditionnalWithHolding;
	}

	public void setResidenceTaxAdditionnalWithHolding(String residenceTaxAdditionnalWithHolding)
	{
		this.residenceTaxAdditionnalWithHolding = residenceTaxAdditionnalWithHolding;
	}

	public String getSupplementType()
	{
		return supplementType;
	}

	public void setSupplementType(String supplementType)
	{
		this.supplementType = supplementType;
	}

	public Boolean getFederalExempt()
	{
		return federalExempt;
	}

	public void setFederalExempt(Boolean federalExempt)
	{
		this.federalExempt = federalExempt;
	}

	public Boolean getMediacareExempt()
	{
		return mediacareExempt;
	}

	public void setMediacareExempt(Boolean mediacareExempt)
	{
		this.mediacareExempt = mediacareExempt;
	}

	public Boolean getSocialSecurityExempt()
	{
		return socialSecurityExempt;
	}

	public void setSocialSecurityExempt(Boolean socialSecurityExempt)
	{
		this.socialSecurityExempt = socialSecurityExempt;
	}

	public Boolean getFutaExempt()
	{
		return futaExempt;
	}

	public void setFutaExempt(Boolean futaExempt)
	{
		this.futaExempt = futaExempt;
	}

	public Boolean getSutaExempt()
	{
		return sutaExempt;
	}

	public void setSutaExempt(Boolean sutaExempt)
	{
		this.sutaExempt = sutaExempt;
	}

	public String getLocalTax()
	{
		return localTax;
	}

	public void setLocalTax(String localTax)
	{
		this.localTax = localTax;
	}

	public String getLocalTaxState()
	{
		return localTaxState;
	}

	public void setLocalTaxState(String localTaxState)
	{
		this.localTaxState = localTaxState;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getAssociateTaxUuid()
	{
		return associateTaxUuid;
	}

	public void setAssociateTaxUuid(String associateTaxUuid)
	{
		this.associateTaxUuid = associateTaxUuid;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy()
	{
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public String getPreviousFederalTaxFilliingStatus()
	{
		return previousFederalTaxFilliingStatus;
	}

	public void setPreviousFederalTaxFilliingStatus(String previousFederalTaxFilliingStatus)
	{
		this.previousFederalTaxFilliingStatus = previousFederalTaxFilliingStatus;
	}

	public String getPreviousFederalTaxAllowances()
	{
		return previousFederalTaxAllowances;
	}

	public void setPreviousFederalTaxAllowances(String previousFederalTaxAllowances)
	{
		this.previousFederalTaxAllowances = previousFederalTaxAllowances;
	}

	public String getPreviousFederalTaxAdditionnalWithHolding()
	{
		return previousFederalTaxAdditionnalWithHolding;
	}

	public void setPreviousFederalTaxAdditionnalWithHolding(String previousFederalTaxAdditionnalWithHolding)
	{
		this.previousFederalTaxAdditionnalWithHolding = previousFederalTaxAdditionnalWithHolding;
	}

	public String getPreviousStateTaxFilliingStatus()
	{
		return previousStateTaxFilliingStatus;
	}

	public void setPreviousStateTaxFilliingStatus(String previousStateTaxFilliingStatus)
	{
		this.previousStateTaxFilliingStatus = previousStateTaxFilliingStatus;
	}

	public String getPreviousStateTaxAllowances()
	{
		return previousStateTaxAllowances;
	}

	public void setPreviousStateTaxAllowances(String previousStateTaxAllowances)
	{
		this.previousStateTaxAllowances = previousStateTaxAllowances;
	}

	public String getPreviousStateTaxAdditionnalWithHolding()
	{
		return previousStateTaxAdditionnalWithHolding;
	}

	public void setPreviousStateTaxAdditionnalWithHolding(String previousStateTaxAdditionnalWithHolding)
	{
		this.previousStateTaxAdditionnalWithHolding = previousStateTaxAdditionnalWithHolding;
	}

	public String getPreviousResidenceTaxFilliingStatus()
	{
		return previousResidenceTaxFilliingStatus;
	}

	public void setPreviousResidenceTaxFilliingStatus(String previousResidenceTaxFilliingStatus)
	{
		this.previousResidenceTaxFilliingStatus = previousResidenceTaxFilliingStatus;
	}

	public String getPreviousResidenceTaxAllowances()
	{
		return previousResidenceTaxAllowances;
	}

	public void setPreviousResidenceTaxAllowances(String previousResidenceTaxAllowances)
	{
		this.previousResidenceTaxAllowances = previousResidenceTaxAllowances;
	}

	public String getPreviousResidenceTaxAdditionnalWithHolding()
	{
		return previousResidenceTaxAdditionnalWithHolding;
	}

	public void setPreviousResidenceTaxAdditionnalWithHolding(String previousResidenceTaxAdditionnalWithHolding)
	{
		this.previousResidenceTaxAdditionnalWithHolding = previousResidenceTaxAdditionnalWithHolding;
	}

	public String getPreviousSupplementType()
	{
		return previousSupplementType;
	}

	public void setPreviousSupplementType(String previousSupplementType)
	{
		this.previousSupplementType = previousSupplementType;
	}

	public Boolean getPreviousFederalExempt()
	{
		return previousFederalExempt;
	}

	public void setPreviousFederalExempt(Boolean previousFederalExempt)
	{
		this.previousFederalExempt = previousFederalExempt;
	}

	public Boolean getPreviousMediacareExempt()
	{
		return previousMediacareExempt;
	}

	public void setPreviousMediacareExempt(Boolean previousMediacareExempt)
	{
		this.previousMediacareExempt = previousMediacareExempt;
	}

	public Boolean getPreviousSocialSecurityExempt()
	{
		return previousSocialSecurityExempt;
	}

	public void setPreviousSocialSecurityExempt(Boolean previousSocialSecurityExempt)
	{
		this.previousSocialSecurityExempt = previousSocialSecurityExempt;
	}

	public Boolean getPreviousFutaExempt()
	{
		return previousFutaExempt;
	}

	public void setPreviousFutaExempt(Boolean previousFutaExempt)
	{
		this.previousFutaExempt = previousFutaExempt;
	}

	public Boolean getPreviousSutaExempt()
	{
		return previousSutaExempt;
	}

	public void setPreviousSutaExempt(Boolean previousSutaExempt)
	{
		this.previousSutaExempt = previousSutaExempt;
	}

	public String getPreviousLocalTax()
	{
		return previousLocalTax;
	}

	public void setPreviousLocalTax(String previousLocalTax)
	{
		this.previousLocalTax = previousLocalTax;
	}

	public String getPreviousLocalTaxState()
	{
		return previousLocalTaxState;
	}

	public void setPreviousLocalTaxState(String previousLocalTaxState)
	{
		this.previousLocalTaxState = previousLocalTaxState;
	}
}
