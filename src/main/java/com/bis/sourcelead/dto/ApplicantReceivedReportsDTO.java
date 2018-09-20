package com.bis.sourcelead.dto;

	import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

	public class ApplicantReceivedReportsDTO extends CommonDTO {

		private String name;
		
		private String jobTitle;
		
		private BigInteger orglocationId;
		
		private String submissionStatus;
		
		private String userAndOrgName;
		
		private Date updatedDate;
		
		private BigDecimal shortlistCount;
		
		private BigInteger applicantCount;
		
		private BigDecimal rejectedCount;
		
		private BigDecimal submitToClientCount;
		
		private BigDecimal interviewCount;
		
		private BigDecimal offermadeCount;
		
		
		

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getJobTitle() {
			return jobTitle;
		}

		public void setJobTitle(String jobTitle) {
			this.jobTitle = jobTitle;
		}

		public BigInteger getOrglocationId() {
			return orglocationId;
		}

		public void setOrglocationId(BigInteger orglocationId) {
			this.orglocationId = orglocationId;
		}

		public String getSubmissionStatus() {
			return submissionStatus;
		}

		public void setSubmissionStatus(String submissionStatus) {
			this.submissionStatus = submissionStatus;
		}

		public String getUserAndOrgName() {
			return userAndOrgName;
		}

		public void setUserAndOrgName(String userAndOrgName) {
			this.userAndOrgName = userAndOrgName;
		}

		public Date getUpdatedDate() {
			return updatedDate;
		}

		public void setUpdatedDate(Date updatedDate) {
			this.updatedDate = updatedDate;
		}

		public BigDecimal getShortlistCount() {
			return shortlistCount;
		}

		public void setShortlistCount(BigDecimal shortlistCount) {
			this.shortlistCount = shortlistCount;
		}

		public BigDecimal getRejectedCount() {
			return rejectedCount;
		}

		public void setRejectedCount(BigDecimal rejectedCount) {
			this.rejectedCount = rejectedCount;
		}

		public BigDecimal getSubmitToClientCount() {
			return submitToClientCount;
		}

		public void setSubmitToClientCount(BigDecimal submitToClientCount) {
			this.submitToClientCount = submitToClientCount;
		}

		public BigDecimal getInterviewCount() {
			return interviewCount;
		}

		public void setInterviewCount(BigDecimal interviewCount) {
			this.interviewCount = interviewCount;
		}

		public BigDecimal getOffermadeCount() {
			return offermadeCount;
		}

		public void setOffermadeCount(BigDecimal offermadeCount) {
			this.offermadeCount = offermadeCount;
		}

		public BigInteger getApplicantCount() {
			return applicantCount;
		}

		public void setApplicantCount(BigInteger applicantCount) {
			this.applicantCount = applicantCount;
		}

					
	}