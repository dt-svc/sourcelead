package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformancePeersDTO implements Serializable{

	 private static final long serialVersionUID = 1L;
	 
	 private Long appraisalAssignedUserId;
	 
	 private String peerUserIds;
	 
	 private Long orgLocationId;
	 
	 private Long userId;
	 
	 

	public Long getAppraisalAssignedUserId() {
		return appraisalAssignedUserId;
	}

	public void setAppraisalAssignedUserId(Long appraisalAssignedUserId) {
		this.appraisalAssignedUserId = appraisalAssignedUserId;
	}

	public String getPeerUserIds() {
		return peerUserIds;
	}

	public void setPeerUserIds(String peerUserIds) {
		this.peerUserIds = peerUserIds;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	 
	 
}
