package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PROJECT_APPROVER_HIERARCHY")
public class ProjectApproverHierarchy implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PROJECT_ID", nullable=false )	
	private Projects projectId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APPROVER_USER_ID", nullable=false )	
	private User approverUserId;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "PROJECT_CLIENTS_ID")
    private ProjectClients projectClientsId;
 	
    @Column(name = "LEVEL")
    private Integer level;
    
    @Column(name = "APPROVAL_MECHANISM_TYPE")
    private String approvalMechanismType;
 
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    
    @OneToMany(mappedBy="projects",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<ProjectMembers> projectMembers;
    
    @Transient
    @JsonProperty
    private Long  projectApproverUserId;
    
    
    @Transient
    private String fullName;

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Projects getProjectId() {
		return projectId;
	}

	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}

	public User getApproverUserId() {
		return approverUserId;
	}

	public void setApproverUserId(User approverUserId) {
		this.approverUserId = approverUserId;
	}


	public ProjectClients getProjectClientsId() {
		return projectClientsId;
	}

	public void setProjectClientsId(ProjectClients projectClientsId) {
		this.projectClientsId = projectClientsId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Set<ProjectMembers> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Set<ProjectMembers> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getProjectApproverUserId() {
		if(approverUserId != null){
			projectApproverUserId = approverUserId.getId();
		}
		return projectApproverUserId;
	}

	public void setProjectApproverUserId(Long projectApproverUserId) {
		this.projectApproverUserId = projectApproverUserId;
	}

	public String getApprovalMechanismType() {
		return approvalMechanismType;
	}

	public void setApprovalMechanismType(String approvalMechanismType) {
		this.approvalMechanismType = approvalMechanismType;
	}
	
}
