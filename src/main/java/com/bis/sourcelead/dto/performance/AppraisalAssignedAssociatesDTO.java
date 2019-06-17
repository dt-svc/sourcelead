package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class AppraisalAssignedAssociatesDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long orgLocationId;
	
	private Long orgId;
	
	private Long userId;
	
	private String appraisalTemplateUUId;
	
	private String associateUserIds;

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppraisalTemplateUUId() {
		return appraisalTemplateUUId;
	}

	public void setAppraisalTemplateUUId(String appraisalTemplateUUId) {
		this.appraisalTemplateUUId = appraisalTemplateUUId;
	}

	public String getAssociateUserIds() {
		return associateUserIds;
	}

	public void setAssociateUserIds(String associateUserIds) {
		this.associateUserIds = associateUserIds;
	}

	
	
}
