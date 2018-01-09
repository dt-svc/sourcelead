package com.bis.sourcelead.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ConnectionsRestController {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionsRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	ConnectionsService connectionsService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	EmailScenarioService emailScenarioService;
	
	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@RequestMapping(value="/addNewConnection")
	public String addNewConnection(@RequestParam(value = "userCode", required = false) String userCode, HttpServletRequest request){
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String result = "invalid";
		try {
			User toUser = userService.getUserByUserCode(userCode);
			Connections connections = new Connections();
			connections.setFromUser(user);
			connections.setToUser(toUser);
			connections.setConnectionStatus(ConnectionStatus.REQUEST_SENT);
			connections.setStatus(Constants.RECORD_ACTIVE);
			connections.setCreatedDate(new Timestamp(new Date().getTime()));
			connectionsService.saveConnection(connections);
			connectionInvitationMailToUser(user,toUser,loginOrgLocation.getId());
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value = "/getConnectionsList")
	public @ResponseBody List<UserRoleDTO> getConnectionsList(
			@RequestParam(value = "searchValue", required = false) String searchValue, 
			@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "status", required = false) String status, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<UserRoleDTO> userRoleDTOList = new ArrayList<UserRoleDTO>();
		userRoleDTOList = connectionsService.getConnectionsListByStatus(searchValue, offset, user.getId(), ConnectionStatus.REQUEST_SENT.getValue(), status);
		return userRoleDTOList;
	}
	
	@RequestMapping(value="/loadFindConnections")
	public ModelAndView loadFindConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(loginUser.getId());
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);
		model.setViewName("/connections/connections");
		return model;
	}
	
	@RequestMapping(value="/loadSentConnections")
	public ModelAndView loadSentConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(loginUser.getId());
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/sentConnections");
		return model;
	}
	
	@RequestMapping(value="/loadReceivedConnections")
	public ModelAndView loadReceivedConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(loginUser.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/receivedConnections");
		return model;
	}
	
	@RequestMapping(value="/withdrawConnection")
	public String withdrawConnection(@RequestParam(value = "userCode", required = false) String userCode,@RequestParam(value = "statusCode", required = false) String statusCode, HttpServletRequest request){
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		String result = "invalid";
		try {
			User toUser = userService.getUserByUserCode(userCode);
			int resultValue = 0;
			if(statusCode.equals("withdraw")) {
				resultValue = connectionsService.deleteConnection(user, toUser, ConnectionStatus.REQUEST_SENT.getValue());
			}else if(statusCode.equals("ignore")) {
				resultValue = connectionsService.deleteConnection(toUser, user, ConnectionStatus.REQUEST_SENT.getValue());
			}else {
				resultValue = connectionsService.deleteConnection(user, toUser, ConnectionStatus.CONNECTED.getValue());
			}
			if(resultValue != 0) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/ws/acceptConnection")
	public ModelAndView addAcceptConnection(@RequestParam(value = "userCode", required = false) String userCode,@RequestParam(value = "fromUser", required = false) String fromUser, HttpServletRequest request){
		User user=null;
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			
			if(fromUser ==null){
			 user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
			}
			
			String result = "invalid";
			try {
				User toUser = userService.getUserByUserCode(userCode);
				if(fromUser !=null){
				user = userService.getUserByUserCode(fromUser);
				}
				int resultValue = 0;
					resultValue = connectionsService.acceptConnection(user, toUser, ConnectionStatus.CONNECTED.getValue());
				if(resultValue != 0) {
					result = "success";
				}
			} catch (Exception e) {
			e.printStackTrace();
			}
			if(fromUser !=null && result == "success"){
				return new ModelAndView("redirect:/app/loadConnectedList");
			}else{
				model.setViewName("login");
				return model;
			}
		} else {
			model.setViewName("login");
			return model;
		}
	}
	

	@RequestMapping(value="/loadConnectedList")
	public ModelAndView loadConnectedList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(loginUser.getId());
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/connectedConnections");
		return model;
	}
	
	@RequestMapping(value = "/getConnectedList")
	public @ResponseBody List<UserRoleDTO> getConnectedList(
			@RequestParam(value = "searchValue", required = false) String searchValue, 
			@RequestParam(value = "offset", required = false) Integer offset, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<UserRoleDTO> userRoleDTOList = new ArrayList<UserRoleDTO>();
		userRoleDTOList = connectionsService.getConnectedList(searchValue, offset, user.getId(), ConnectionStatus.CONNECTED.getValue());
		return userRoleDTOList;
	}
	
	private void connectionInvitationMailToUser(User loginUser, User toUser, Long locationId) throws Exception
	{
		OrgLocations locations = orgLocationService.getOrgLocations(locationId);
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(toUser.getId(), locationId);
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toUser.getFirstName());
		emailMessage.setLastName(toUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setFromName(sb.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		// emailMessage.setOrgLocationId(locationId);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("receiverUserName", toUser.getFullName());
		String loginUserFullName = loginUser.getFullName().substring(0, 1).toUpperCase()
				+ loginUser.getFullName().substring(1);
		globalVarsMap.put("loginUserName", loginUserFullName);
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginLink", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
		globalVarsMap.put("receiverEmail", toUser.getEmail());
		StringBuilder connectionsBuilder = sendEmailToInvitationUserTable(loginUser, toUser, locations);
		globalVarsMap.put("profilePicUrl", connectionsBuilder.toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER, Constants.ENTITY_TYPE_EMAIL);
		EmailScenario noificationEmailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER, Constants.ENTITY_TYPE_NOTIFICATION);
		// covert subject,body and signature
		String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(), globalVarsMap);
		String body = emailScenerioUtil.buildTempBody(emailScenario.getBody(), globalVarsMap);
		String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(), globalVarsMap);
		String url = sourceleadUrl;
		 StringBuilder acceptBuilder = new StringBuilder();
		String notificationbody = emailScenerioUtil.buildTempBody(noificationEmailScenario.getBody(), globalVarsMap);
		noificationEmailScenario.setBody(notificationbody);
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body, signature, url, userRole.getOrgLocations().getOrg(), userRole.getOrgLocations());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, locationId, iAdressArray, new ArrayList<EmailAttachment>());
//		String actionLink = "";
		
		 StringBuilder actionUrlBuilder = new StringBuilder();
		 String actionLink = actionUrlBuilder.append("").append(sourceleadUrl).append("/").append("loadReceivedConnections").toString();
		 
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, locations, noificationEmailScenario);
	}
	
	
	public StringBuilder sendEmailToInvitationUserTable(User loginUser, User toUser, OrgLocations locations) throws Exception
	{
		StringBuilder tempHTML = new StringBuilder();
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(toUser.getId(), locations.getId());
		String imageUrl = StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
		StringBuilder loginBuilder12 = new StringBuilder();
		if(toUser.getFileUrl() != null){
			loginBuilder12.append("<img src='" + imageUrl + "" + toUser.getFileUrl() + "' style='width: 60px; height: 60px; border-radius: 50%;'>").toString();
		}else{
			loginBuilder12.append("<img src='"+imageUrl+"/resources/images/userprofileImg.jpg'  style='width: 60px; height: 60px; border-radius: 50%;'>").toString();
		}
		tempHTML.append("<a style='text-decoration:none; display:block;' href='javascript:void(0)'>");
		tempHTML.append("<div style='background-color: #fff; padding: 10px; border-bottom: 1px solid #f1f1f1;'>");
		tempHTML.append("		<div class='userImg' style='width: 60px; padding: 0px 20px 0px 5px; float: left;'>");
		tempHTML.append(loginBuilder12.toString());
		tempHTML.append("		</div>");
		tempHTML.append("		<div style='width:350px; float: left; padding: 0px 0px;'>");
		tempHTML.append("			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 14px; font-weight: 600; color: #454545;'>").append(toUser.getFullName()).append("</p>");
		tempHTML.append("			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 13px; font-weight: 500; color: #666;'>").append(userRole.getUserType()).append(" at ").append(locations.getOrgLocationName()
				+ "</span></p>");
		// tempHTML.append(" <p style='padding: 0px 0px 3px; margin: 0px 0px;
		// font-size: 13px; font-weight: 500; color: #666;'>16 mutual
		// connections</p>");
		tempHTML.append("		</div>");
		tempHTML.append("   	<div style='float: right; padding: 5px;'>");
		tempHTML.append("<a href='").append(sourceleadUrl).append("/ws/acceptConnection?userCode=").append(loginUser.getUserCode()).append("&fromUser=").append(toUser.getUserCode()).append(" ' style='display: block; background-color: #1176d4; color: #fff; text-decoration: none; padding: 4px 15px 6px; border-radius: 2px; font-size: 14px; line-height: 20px;' href='javascript:void(0)'>Accept </a>");
		tempHTML.append("   	</div>");
		tempHTML.append("   	<div style='clear:both'></div>");
		tempHTML.append("	</div>");
		tempHTML.append("   	<div style='text-align: center; padding: 15px 0px 5px'>");
		tempHTML.append("<a href='").append(sourceleadLoginUrl).append(" ' style='text-decoration: none; font-size: 14px; font-weight: 600; color: #214b90;' href='javascript:void(0)'>Manage Invitations </a>");
		tempHTML.append("   	</div>");
		tempHTML.append(" </a>");
		return tempHTML;
	}
	
	
	
}
