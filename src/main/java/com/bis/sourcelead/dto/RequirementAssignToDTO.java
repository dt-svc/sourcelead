package com.bis.sourcelead.dto;

import java.io.Serializable;
	
	public class RequirementAssignToDTO extends SearchDTO implements Serializable{
	
		private long requirementId;
		private long orgLocationId;
		private long userId;
		private String submissionType;
		private String loginUserRole;
		private String userCode;
		private String name;
		private String email;
		private String role;
		private String team;
		private String noOfApplicants;
		
		public long getRequirementId()
		{
			return requirementId;
		}
		public void setRequirementId(long requirementId)
		{
			this.requirementId = requirementId;
		}
		public long getOrgLocationId()
		{
			return orgLocationId;
		}
		public void setOrgLocationId(long orgLocationId)
		{
			this.orgLocationId = orgLocationId;
		}
		public long getUserId()
		{
			return userId;
		}
		public void setUserId(long userId)
		{
			this.userId = userId;
		}
		public String getSubmissionType()
		{
			return submissionType;
		}
		public void setSubmissionType(String submissionType)
		{
			this.submissionType = submissionType;
		}
		public String getLoginUserRole()
		{
			return loginUserRole;
		}
		public void setLoginUserRole(String loginUserRole)
		{
			this.loginUserRole = loginUserRole;
		}
		public String getUserCode()
		{
			return userCode;
		}
		public void setUserCode(String userCode)
		{
			this.userCode = userCode;
		}
		public String getName()
		{
			return name;
		}
		public void setName(String name)
		{
			this.name = name;
		}
		public String getEmail()
		{
			return email;
		}
		public void setEmail(String email)
		{
			this.email = email;
		}
		public String getRole()
		{
			return role;
		}
		public void setRole(String role)
		{
			this.role = role;
		}
		public String getTeam()
		{
			return team;
		}
		public void setTeam(String team)
		{
			this.team = team;
		}
		public String getNoOfApplicants()
		{
			return noOfApplicants;
		}
		public void setNoOfApplicants(String noOfApplicants)
		{
			this.noOfApplicants = noOfApplicants;
		}
}
