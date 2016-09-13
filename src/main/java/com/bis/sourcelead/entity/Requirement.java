package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

	@Entity
	@Table(name = "REQUIREMENT", catalog = "sourcelead")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirement")
	public class Requirement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;
	
	@Column(name = "JOB_TITLE")
	private String jobTitile;
	
	@Column(name = "JOB_TYPE")
	private String jobType;
	
	@Column(name = "JOB_ID")
	private String jobId;
	
	@Column(name = "POSITIONS")
	private String positions;
	
	@Column(name = "EDUCATION")
	private String education;
	
	@Column(name = "MIN_EXP")
	private String min_exp;
	
	@Column(name = "MAX_EXP")
	private String max_exp;
	
	@Column(name = "CURRENCY_CODE")
	private String currency_code;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "SKILLS")
	private String skiills;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "DOC_FILE",columnDefinition="mediumblob")
	@Lob
	private  byte[] docFile;
		
	@Column(name = "FILE_CONTENT_TYPE")
	private String fileContentType;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "IS_CLIENT_JOB")
	private String isClientJob;
	
	@ManyToOne
    @JoinColumn(name="ORG_RELATION_ID", nullable=false )        
    private OrgRelations clientId;
	
	@ManyToOne
    @JoinColumn(name="SOPC_ID", nullable=false )        
    private OrgRelations sopc_Id;
	
	@Column(name = "CLIENT_CURRENCY_CODE")
	private String client_currency_code;
	
	@Column(name = "CLIENT_RATE")
	private String clientRate;
	
	@Column(name = "CLIENT_RATE_TYPE")
	private String clientRateType;
	
	
	@ManyToOne
    @JoinColumn(name="SUBMISSION_TEMPLATE_ID", nullable=false )        
    private SubmissionTemplate submissionTemplateId;
	
	
	@ManyToOne
    @JoinColumn(name="CLIENT_SUBMISSION_TEMPLATE_ID", nullable=false )        
    private SubmissionTemplate clientSubmissionTemplateId;
	
	@ManyToOne
    @JoinColumn(name="MANAGER_USER_ID", nullable=false )        
    private User managerUserId;
	
	@Column(name="IS_Template")
	private String isTemplate;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="PARENT_REQUIREMENT_ID")
	private String parent_requirement_id;
	
	@Column(name="PRIORITY")
	private String priority;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
}
