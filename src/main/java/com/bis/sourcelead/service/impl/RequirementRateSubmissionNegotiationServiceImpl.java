package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementRateSubmissionNegotiationDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmissionTemplateFields;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementRateSubmissionNegotiationService;
import com.bis.sourcelead.service.RequirementSubmissionTemplateFieldsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class RequirementRateSubmissionNegotiationServiceImpl implements RequirementRateSubmissionNegotiationService{

	@Autowired
	RequirementRateSubmissionNegotiationDao requirementRateSubmissionNegotiationDao;
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${atsUrl}")
    private String atsUrl;
	
	@Autowired
	RequirementSubmissionTemplateFieldsService requirementSubmissionTemplateFieldsService;
	
	@Autowired
	UserService userService;
	
	/*@Override
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		requirementRateSubmissionNegotiationDao.saveRateNegotiation(rateNegotiation);		
	}*/

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionById(Long ratesubmissionId) {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionById(ratesubmissionId);
	}

	@Override
	public List<RequirementRateSubmissionNegotiation> getRequirementRateSubmissionByOrgLocationsId(Long orgLocationsId)
			throws Exception {
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionByOrgLocationsId(orgLocationsId);
	}
	
	
	@Override
	public void saveRateNegotiation(RequirementRateSubmissionNegotiation rateNegotiation) {
		rateNegotiation.setUpdatedDate(commonUtil.currentDate());
		requirementRateSubmissionNegotiationDao.saveRateNegotiation(rateNegotiation);		
	}
	
	@Override
	public RequirementRateSubmissionNegotiation getLatestRequirementRateSubmissionNegotiationBySubmissionId(Long submissionId) {
		return requirementRateSubmissionNegotiationDao.getLatestRequirementRateSubmissionNegotiationBySubmissionId(submissionId);
	}
	
	@Override
	public List<RequirementRateSubmissionNegotiation> getAllRateHistoryBySubmissionIdAndCandidateId(Long submissionId, Long candidateId) throws Exception {
		return requirementRateSubmissionNegotiationDao.getAllRateHistoryBySubmissionIdAndCandidateId(submissionId, candidateId) ;
	}

	@Override
	public StringBuilder buildNegotiationHistory(List<RequirementRateSubmissionNegotiation> negotiationList,
			Requirement requirement,  Long submissionId, String type) throws Exception {
		
		RequirementSubmissionTemplateFields requirementSubmissionTemplateFields = requirementSubmissionTemplateFieldsService.getSubmissionsTemplateFieldsByRequirementSubmissionsId(submissionId);
		
		StringBuilder tempHTML = new StringBuilder();
		 if(!negotiationList.isEmpty()){
			 
	         tempHTML.append("<table width='100%' bgcolor='#F9F9F9' cellpadding='0' border='2' cellspacing='0' style='background-color:#F9F9F9;'>");
	         tempHTML.append("<tr>");
	         tempHTML.append("<th> Job Title </th>");
//	         tempHTML.append("<th> Requirement Rate </th>");
	         tempHTML.append("<th> Rate </th>");
	         tempHTML.append("<th> By </th>");
	         //tempHTML.append("<th> Action </th>");
	         tempHTML.append("</tr>");
	         
	        	 for (RequirementRateSubmissionNegotiation negotiations : negotiationList) {
			         tempHTML.append("<tr>");
			         tempHTML.append("<td>").append(requirement.getJobTitle()).append("</td>");
//			         tempHTML.append("<td>").append(requirement.getRate()).append("</td>");
			         if(null != negotiations.getExpectedRate() && "" != negotiations.getExpectedRate() && null != negotiations.getExpectedRateType() && "" != negotiations.getExpectedRateType()){
			        	 tempHTML.append("<td>").append(negotiations.getExpectedRate()).append("/").append(negotiations.getExpectedRateType()).append("</td>");
			         }else{
			        	 tempHTML.append("<td>---</td>");
			         }
			        
			         if(negotiations.getCreatedBy() != null && "" != negotiations.getCreatedBy()){
			        	 User proposedUser = userService.getUserByUserCode(negotiations.getCreatedBy());
			        	 tempHTML.append("<td>").append(proposedUser.getFirstName()).append(" ").append(proposedUser.getLastName()).append("</td>");
			         }else{
			        	 if(requirementSubmissionTemplateFields.getLastName() != null && !"".equalsIgnoreCase(requirementSubmissionTemplateFields.getLastName())){
			        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getFirstName()).append(" ").append(requirementSubmissionTemplateFields.getLastName()).append("</td>");
			        	 }else{
			        		 tempHTML.append("<td>").append(requirementSubmissionTemplateFields.getFirstName()).append("</td>");
			        	 }
			         }
			         tempHTML.append("</tr>");
	            }
		        tempHTML.append("</table> ");
		        if("user".equalsIgnoreCase(type)){
		        	//these links are provided in user to applicant Negotiate email
		        	tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Accepted&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left; margin-left:25px;'><div style=' width:160px; border-radius:20px; background-color:#5cb85c; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Accept</div></a>");
			        tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Rejected&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:35px;'><div style=' width:160px; border-radius:20px; background-color:#e74c51; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Reject</div></a>");
			        tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateApplicantRates?rateStatus=Negotiate&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:35px;'><div style='width:160px; border-radius:20px; background-color:#f0ad4e;; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Negotiate Rate</div></a>");
		        }else if("Vendor".equalsIgnoreCase(type) || "Client".equalsIgnoreCase(type)){
		        	tempHTML.append("<a href='").append(sourceleadUrl).append("/").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>Login</div></a>");
		        }else{
		        	//these links are provided in applicant to user Negotiate email
		        	tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateUserRates?rateStatus=Accepted&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left; margin-left:25px;'><div style=' width:160px; border-radius:20px; background-color:#5cb85c; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Accept</div></a>");
			        tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateUserRates?rateStatus=Rejected&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:35px;'><div style=' width:160px; border-radius:20px; background-color:#e74c51; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Reject</div></a>");
			        tempHTML.append("<a href='").append(atsUrl).append("/ws/acceptOrRejectOrRateNegotiateUserRates?rateStatus=Negotiate&submissionId=").append(submissionId).append("'style='text-decoration:none;float:left;margin-left:35px;'><div style='width:160px; border-radius:20px; background-color:#f0ad4e;; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:0px;'>Negotiate Rate</div></a>");
		        }
		 	}
		return tempHTML;
	}

	@Override
	public RequirementRateSubmissionNegotiation getRequirementRateSubmissionByMaxId(Long submissionId) {
		
		return requirementRateSubmissionNegotiationDao.getRequirementRateSubmissionByMaxId(submissionId);
	}

}
