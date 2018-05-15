package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.JobShareDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.JobShareCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.JobShareService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.util.EmailScenerioUtil;

@Service
public class JobShareServiceImpl implements JobShareService{
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	JobShareDao jobShareDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	public RequirementService requirementService;
	
	
	/*@Override
	public JobShare saveJobShare(JobShare jobShare) throws Exception {
		return jobShareDao.saveJobShare(jobShare);
	}
*/
	@Override
	public JobShareCandidate saveJobShareCandidate(JobShareCandidate jobShareCandidate) throws Exception {
		return jobShareDao.saveJobShareCandidate(jobShareCandidate);
	}

	@Override
	public void jobShareEmail(User loginUser, OrgLocations orgLocation, String requirement, Candidate candidate)
			throws Exception {
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(candidate.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		
		
		/*if (requirement.getJobTitle() != null && !"".equalsIgnoreCase(requirement.getJobTitle())) {
			StringBuilder sb = new StringBuilder();
			if (requirement.getJobId() != null && !"".equalsIgnoreCase(requirement.getJobId())) {
				globalVarsMap.put("jobName", sb.append(requirement.getJobTitle()).append("-").append(requirement.getJobId()).toString());
			}
			else {
				globalVarsMap.put("jobName", requirement.getJobTitle());
			}
		}
		 
		StringBuilder sb = new StringBuilder();
		if ((requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate()))
				&& (requirement.getMaxRate() != null
						&& !"".equalsIgnoreCase(requirement.getMaxRate()))) {
			globalVarsMap.put("jobRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" - ").append(requirement.getMaxRate()).append(" / ").append(requirement.getRateType()).toString());
		}
		else if (requirement.getRate() != null && !"".equalsIgnoreCase(requirement.getRate())) {
			globalVarsMap.put("jobRate", sb.append(requirement.getCurrencyCode()).append(" ").append(requirement.getRate()).append(" / ").append(requirement.getRateType()).toString());
		}
		else {
			globalVarsMap.put("jobRate", "--");
		}
		if (requirement.getDescription() != null && !"".equalsIgnoreCase(requirement.getDescription())){
			globalVarsMap.put("jobDescription", requirement.getDescription());
		}else {
			globalVarsMap.put("jobDescription", requirement.getDescription());
		}
				
		if (requirement.getMinExp() != null && requirement .getMaxExp() != null){
			globalVarsMap.put("jobExperience", "Min Exp  "+requirement.getMinExp() +" - "+"Max Exp  "+ requirement.getMaxExp());
		}else if(requirement.getMinExp() != null){
			globalVarsMap.put("jobExperience", "Min Exp  "+requirement.getMinExp());
		}else if(requirement .getMaxExp() != null) {
			globalVarsMap.put("jobExperience", "Max Exp  "+ requirement.getMaxExp());
		}*/
		if(candidate.getLastName() != null) {
			StringBuilder candidateName = new StringBuilder();
			globalVarsMap.put("candidateName", candidateName.append(candidate.getFirstName()).append(" ").append(candidate.getLastName()).toString());
		}else {
			globalVarsMap.put("candidateName", candidate.getFirstName());
		}
		globalVarsMap.put("loginOrgName", orgLocation.getOrgLocationName());
		globalVarsMap.put("loginUserName", loginUser.getFullName());

//		List<Requirement> jobsList = requirementService.getRequirementsByIdsList(requirement);
		
//		String table =  getJobListTable(jobsList); 
//		globalVarsMap.put("jobListTable", table);
		
		StringBuilder linkBuild = new StringBuilder();
		globalVarsMap.put("viewJobLink", linkBuild.append("<a href='").append(sourceleadUrl).append("/").append("jobCampaignSummary?requirementUUID=").append("").append("' style='text-decoration:none; '><div style=' width:220px; border-radius:30px; background-color:#214b90; color:#FFF; font-size:18px; font-weight:500; margin:10px auto 5px auto; padding:10px 0px; text-align:center; letter-spacing:2px;'>View Job</div></a>").toString());
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		EmailScenario emailScenario =null;
		 if(orgLocation != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.ACCEPT_BULK_NEW_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL, orgLocation.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.ACCEPT_BULK_NEW_RESOURCE_REQUEST,Constants.ENTITY_TYPE_EMAIL);
			   }
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
 
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, orgLocation.getOrg(), orgLocation);
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		emailScenerioUtil.sendEmail(emailMessage, orgLocation.getId(), iAdressArray, new ArrayList<EmailAttachment>());
	}

	private String getJobListTable(List<Requirement> requirement) {
		String tempHTML = "";
		tempHTML = tempHTML + "<table style='width:100%; border-collapse:collapse; border:1px solid #e5e5e5;' cellpadding='10' cellspacing='0'>";
		tempHTML = tempHTML + "	  <tr>";
		tempHTML = tempHTML + "	    <th style='border:1px solid #e5e5e5; background-color: #f5f5f5'>Job Name</th>";
		tempHTML = tempHTML + "	    <th style='border:1px solid #e5e5e5; background-color: #f5f5f5'>Job Location</th> ";
		tempHTML = tempHTML + "	    <th style='border:1px solid #e5e5e5; background-color: #f5f5f5'>Experience</th>";
		tempHTML = tempHTML + "	    <th style='border:1px solid #e5e5e5; background-color: #f5f5f5'>Action</th>";
		tempHTML = tempHTML + "	  </tr>";
		tempHTML = tempHTML + "	  <tr>";
		tempHTML = tempHTML + "	    <td style='border:1px solid #e5e5e5;'>Java Developer</td>";
		tempHTML = tempHTML + "	    <td style='border:1px solid #e5e5e5;'>Hyderabad</td>";
		tempHTML = tempHTML + "	    <td style='border:1px solid #e5e5e5;'>3 to 5 Years</td>";
		tempHTML = tempHTML + "	    <td style='border:1px solid #e5e5e5;'>View Job</td>";
		tempHTML = tempHTML + "	  </tr>";
		tempHTML = tempHTML + "	</table>";
		tempHTML = tempHTML + "	<table style='width:100%; border-collapse:collapse;' cellpadding='10' cellspacing='0'>";
		tempHTML = tempHTML + "	  <tr>";
		tempHTML = tempHTML + "		<td><a href='javascript:void(0)' style='text-align: center; padding:5px; background-color: #337ab7; width: 90px; float: right; color: #fff; text-decoration: none;border-radius: 2px;'>View More</a></td>";
		tempHTML = tempHTML + "	  </tr>";
		tempHTML = tempHTML + "		</table>";
		return tempHTML;
	}

}
