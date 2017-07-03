package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InviteCompanyDao;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.InviteCompany;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProposeTime;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class InviteCompanyServiceImpl implements InviteCompanyService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	InviteCompanyDao inviteCompanyDao;
	
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 private EmailScenarioService emailScenarioService; 
	 
	 @Autowired
	 private EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 private UserRoleService userRoleService;
	 

	@Override
	public InviteCompany saveInviteCompany(InviteCompany inviteCompany) throws Exception {
		return inviteCompanyDao.saveInviteCompany(inviteCompany);
	}

	@Override
	public String saveInviteCompany(JSONObject jsonObj) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		InviteCompany inviteCompany = new InviteCompany();
		
		if(jsonObj.has("companyName") && !"".equalsIgnoreCase(jsonObj.getString("companyName"))){
			inviteCompany.setCompanyName(jsonObj.getString("companyName"));
		}
		
		if(jsonObj.has("location") && !"".equalsIgnoreCase(jsonObj.getString("location"))){
			inviteCompany.setLocation(jsonObj.getString("location"));
		}
		
		if(jsonObj.has("inviteCountry") && !"".equalsIgnoreCase(jsonObj.getString("inviteCountry"))){
			inviteCompany.setCountry(jsonObj.getString("inviteCountry"));
		}
		
		
		if(jsonObj.has("inviteSate") && !"".equalsIgnoreCase(jsonObj.getString("inviteSate"))){
			inviteCompany.setState(jsonObj.getString("inviteSate"));
		}
		
		if(jsonObj.has("inviteCity") && !"".equalsIgnoreCase(jsonObj.getString("inviteCity"))){
			inviteCompany.setCity(jsonObj.getString("inviteCity"));
		}
		
		if(jsonObj.has("name") && !"".equalsIgnoreCase(jsonObj.getString("name"))){
			inviteCompany.setName(jsonObj.getString("name"));
		}
		
		if(jsonObj.has("email") && !"".equalsIgnoreCase(jsonObj.getString("email"))){
			inviteCompany.setEmail(jsonObj.getString("email"));
			inviteCompanyEmail(user,jsonObj,orgLocations);
		}
		
		if(jsonObj.has("phoneNumber") && !"".equalsIgnoreCase(jsonObj.getString("phoneNumber"))){
			inviteCompany.setPhone_number(jsonObj.getString("phoneNumber"));
		}
		inviteCompany.setCreatedBy(user.getUserCode());
		inviteCompany.setCreatedDate(commonUtil.currentDate());
		inviteCompanyDao.saveInviteCompany(inviteCompany);
		
		
		return "success";
	}

	private void inviteCompanyEmail(User user, JSONObject jsonObj, OrgLocations orgLocations) throws Exception {
//		UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 StringBuilder builder=new StringBuilder();
		 if(null != user.getLastName()){
			 emailMessage.setFirstName(builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
		 }else{
			 emailMessage.setFirstName(builder.append(user.getFirstName()).toString());
		 }
		 emailMessage.setToEmail(jsonObj.getString("email"));
		 emailMessage.setFromEmail(user.getEmail());
		 if(null != user.getLastName()){
			 emailMessage.setFromName(sb.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
		 }else{
			 emailMessage.setFromName(sb.append(user.getFirstName()).toString());
		 }
		 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		 emailMessage.setCreatedBy(user.getUserCode());
		 
		//setting the class filed values
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		 globalVarsMap.put("firstName", emailMessage.getFirstName());
		 globalVarsMap.put("LoginUser Email", user.getEmail());
		 globalVarsMap.put("Name",jsonObj.getString("name"));
		 globalVarsMap.put("LoginUser",emailMessage.getFromName());
		 globalVarsMap.put("LoginOrg",orgLocations.getOrg().getOrgName());
		 StringBuilder  inviteComapnyServiceDiv  = buildInviteCompanyServices();
		 globalVarsMap.put("ServicesList", inviteComapnyServiceDiv.toString());
		
		 //setting the Recipient information
		 InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		 
		 //getting the current EmailScenario by EmailScenario unique name and entity type
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_COMPANY,Constants.ENTITY_TYPE_EMAIL);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
		 String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(),globalVarsMap);
		 String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
		 String url = null;
		 
		 //construct HTML template using required data
		 String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url,orgLocations.getOrg(),orgLocations);
		 emailMessage.setSubject(subject);
		 emailMessage.setMessageBody(emailTemplate);
		 
		 //calling the sendEmail method of emailScenerioUtil
		 emailScenerioUtil.sendEmail(emailMessage,orgLocations.getId(),iAdressArray,new ArrayList<EmailAttachment>());
		
	}
 

	private StringBuilder buildInviteCompanyServices() throws Exception {
		StringBuilder tempHTML = new StringBuilder();
		tempHTML.append("<html>");
				tempHTML.append("<body style='font-family:arial;'>");
				tempHTML.append("<h3 style='color:#f60; margin:10px 0 5px 0; padding:0; font-size:18px;'>Services:</h3>");
					tempHTML.append("<ul style='list-style-type:none; margin:0; padding-left:25px; line-height: 22px; font-size:15px;'>");
						tempHTML.append("<li><span style='font-size:18px; padding-right:10px;'>&#187;</span>Crowdsourcing</li>");
						tempHTML.append("<li><span style='font-size:18px; padding-right:10px;'>&#187;</span>ATS</li>");
						tempHTML.append("<li><span style='font-size:18px; padding-right:10px;'>&#187;</span>VMS</li>");
						tempHTML.append("<li><span style='font-size:18px; padding-right:10px;'>&#187;</span>Timesheets</li>");
						tempHTML.append("<li>");
						tempHTML.append("<span style='font-size:20px; padding-right:10px;'>&#187;</span><a href='http://www.sourcelead.com/about-us.html' title='Know More' target='_blank' style='outline:none; border:none;'>More...</a>");
						tempHTML.append("</li>");
					tempHTML.append("</ul> ");
				tempHTML.append("</body>");
				tempHTML.append("</html>");
		return tempHTML;
	}
	
}
