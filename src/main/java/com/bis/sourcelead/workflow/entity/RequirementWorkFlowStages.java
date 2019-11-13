package com.bis.sourcelead.workflow.entity;



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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

	
	@Entity
	@Table(name = "REQUIREMENT_WORKFLOW_STAGES")
	public class RequirementWorkFlowStages implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_WORKFLOW_ID", nullable = false)
	private RequirementWorkFlow requirementWorkFlowId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUIREMENT_QUESTIONNAIRE_TEMPLATE_ID")
	private RequirementQuestionnaireTemplate requirementQuestionnaireTemplateId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_LOCATION_ID", nullable = false)
	private OrgLocations orgLocationId;
	
	@Column(name = "STAGE_NAME")
	private String stageName;
	
	@Column(name = "DESCRIPTION",  columnDefinition = "LONGTEXT default NULL")
	private String  description;
	
	@Column(name = "SEQ_ORDER")
	private Integer seqOrder;
	
	@Column(name = "UUID ")
	private String uuid;
	
	@Column(name = "CONFIGURE_TYPE")
	private String configureType;
	
	@Column(name = "IS_EDITABLE")
	private String editable;
	
	@Column(name = "STATUS")
	private Integer status;
	
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
	
	@Column(name = "COMMENT_REQUIRED")
	private String commentRequired;
	
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "requirementWorkFlowStagesId")
	private Set<RequirementWorkFlowTasks> workflowTasks;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public RequirementWorkFlow getRequirementWorkFlowId() {
		return requirementWorkFlowId;
	}

	public void setRequirementWorkFlowId(RequirementWorkFlow requirementWorkFlowId) {
		this.requirementWorkFlowId = requirementWorkFlowId;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	public Integer getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(Integer seqOrder) {
		this.seqOrder = seqOrder;
	}

	public String getConfigureType() {
		return configureType;
	}

	public void setConfigureType(String configureType) {
		this.configureType = configureType;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequirementQuestionnaireTemplate getRequirementQuestionnaireTemplateId() {
		return requirementQuestionnaireTemplateId;
	}

	public void setRequirementQuestionnaireTemplateId(RequirementQuestionnaireTemplate requirementQuestionnaireTemplateId) {
		this.requirementQuestionnaireTemplateId = requirementQuestionnaireTemplateId;
	}

	public String getCommentRequired() {
		return commentRequired;
	}

	public void setCommentRequired(String commentRequired) {
		this.commentRequired = commentRequired;
	}

	public Set<RequirementWorkFlowTasks> getWorkflowTasks() {
		return workflowTasks;
	}

	public void setWorkflowTasks(Set<RequirementWorkFlowTasks> workflowTasks) {
		this.workflowTasks = workflowTasks;
	}

	
}
