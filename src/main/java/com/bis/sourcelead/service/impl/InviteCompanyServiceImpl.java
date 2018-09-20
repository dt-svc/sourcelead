package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InviteCompanyDao;
import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.InviteCompany;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ScheduleMaintenance;
import com.bis.sourcelead.entity.User;
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
	public String saveInviteCompany(InviteCompanyDTO inviteCompanyDTO) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		InviteCompany inviteCompany = new InviteCompany();
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getCompanyName())){
			inviteCompany.setCompanyName(inviteCompanyDTO.getCompanyName());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getLocation())){
			inviteCompany.setLocation(inviteCompanyDTO.getLocation());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getInviteCountry())){
			inviteCompany.setCountry(inviteCompanyDTO.getInviteCountry());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getInviteSate())){
			inviteCompany.setState(inviteCompanyDTO.getInviteSate());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getInviteCity())){
			inviteCompany.setCity(inviteCompanyDTO.getInviteCity());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getName())){
			inviteCompany.setName(inviteCompanyDTO.getName());
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getEmail())){
			inviteCompany.setEmail(inviteCompanyDTO.getEmail());
			inviteCompanyEmail(user,inviteCompanyDTO,orgLocations);
		}
		
		if(StringUtils.isNoneBlank(inviteCompanyDTO.getPhoneNumber())){
			inviteCompany.setPhone_number(inviteCompanyDTO.getPhoneNumber());
		}
		inviteCompany.setCreatedBy(user.getUserCode());
		inviteCompany.setCreatedDate(commonUtil.currentDate());
		inviteCompanyDao.saveInviteCompany(inviteCompany);
		
		
		return "success";
	}

	private void inviteCompanyEmail(User user, InviteCompanyDTO inviteCompanyDTO, OrgLocations orgLocations) throws Exception {
//		UserRole userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
		 StringBuilder sb = new StringBuilder();
		 EmailMessage emailMessage = new EmailMessage();
		 StringBuilder builder=new StringBuilder();
		 if(null != user.getLastName()){
			 emailMessage.setFirstName(builder.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());	 
		 }else{
			 emailMessage.setFirstName(builder.append(user.getFirstName()).toString());
		 }
		 emailMessage.setToEmail(inviteCompanyDTO.getEmail());
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
		 globalVarsMap.put("Name",inviteCompanyDTO.getName());
		 globalVarsMap.put("LoginUser",emailMessage.getFromName());
		 globalVarsMap.put("LoginOrg",orgLocations.getOrg().getOrgName());
		 StringBuilder  inviteComapnyServiceDiv  = buildInviteCompanyServices();
		 globalVarsMap.put("ServicesList", inviteComapnyServiceDiv.toString());
		
		 //calling the sendEmail method of emailScenerioUtil
		 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocations.getOrg(),orgLocations,Constants.INVITE_COMPANY,new ArrayList<EmailAttachment>());
		
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

	@Override
	public void storeScheduleMaintenance(ScheduleMaintenanceDTO maintenanceDTO) throws ParseException {
		ScheduleMaintenance maintenance = new ScheduleMaintenance();
		if(maintenanceDTO.getId() != null) {
			maintenance.setId(maintenanceDTO.getId());
		}
		maintenance.setScheduleMessage(maintenanceDTO.getScheduleMessage());
		DateFormat dateFormat = new SimpleDateFormat ("MM/dd/yyyy hh:mm a");
		maintenance.setStartDate(dateFormat.parse(maintenanceDTO.getStartDate()));
		maintenance.setEndDate(dateFormat.parse(maintenanceDTO.getEndDate()));
		maintenance.setCreatedDate(new Date());
		inviteCompanyDao.storeScheduleMaintenance(maintenance);
	}

	@Override
	public ScheduleMaintenanceDTO getScheduleStatus() throws ParseException {
		ScheduleMaintenance scheduleMaintenance = inviteCompanyDao.getScheduleStatus(new Date());
		ScheduleMaintenanceDTO maintenanceDTO = null;
		if(scheduleMaintenance != null) {
			maintenanceDTO = new ScheduleMaintenanceDTO();
			maintenanceDTO.setStartDate(scheduleMaintenance.getStartDate().toString());
			maintenanceDTO.setScheduleMessage(scheduleMaintenance.getScheduleMessage());
			maintenanceDTO.setEndDate(scheduleMaintenance.getEndDate().toString());
		}
		return maintenanceDTO;
	}

	@Override
	public ResponseList<ScheduleMaintenanceDTO> getScheduleMaintenanceList(ScheduleMaintenanceDTO scheduleMaintenanceDTO, String searchValue) throws ParseException{
		return inviteCompanyDao.getScheduleMaintenanceList(scheduleMaintenanceDTO, searchValue);
	}

	@Override
	public void deleteScheduleMaintenance(String id) {
		inviteCompanyDao.deleteScheduleMaintenance(id);
	}

}
