package com.bis.sourcelead.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ConnectionsDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.ConnectionsDTO;
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

@Service
public class ConnectionsServiceImpl implements ConnectionsService {

	@Autowired
	private ConnectionsDao connectionsDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	EmailScenarioService emailScenarioService;

	@Autowired
	EmailScenerioUtil emailScenerioUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Value("${static.path}")
    private String staticPath;

	@Override
	public String saveConnection(User user,String userCode, OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl) throws Exception {
		String result = null;
		try {
			User toUser = userService.getUserByUserCode(userCode);
			Connections connections = new Connections();
			connections.setFromUser(user);
			connections.setToUser(toUser);
			connections.setConnectionStatus(ConnectionStatus.REQUEST_SENT);
			connections.setStatus(Constants.RECORD_ACTIVE);
			String connectionUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
			connections.setConnectionUID(connectionUUID);
			connections.setCreatedDate(new Timestamp(new Date().getTime()));
			connectionsDao.saveConnection(connections);
			connectionInvitationMailToUser(user, toUser, loginOrgLocation.getId(), connections.getConnectionUID(), sourceleadUrl, sourceleadLoginUrl);
			result = connectionUUID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ConnectionsDTO> getConnectionsListByStatus(String searchValue, Integer offset, Long userId,
			String status) throws Exception {
		return connectionsDao.getConnectionsListByStatus(searchValue, offset, userId, status);
	}

	@Override
	public int deleteConnection(String connectionUUID) {
		return connectionsDao.deleteConnection(connectionUUID);

	}

	@Override
	public Long getConnectionReceivedCount(Long id) {
		return connectionsDao.getConnectionReceivedCount(id);
	}

	@Override
	public Connections getConnectionByUUID(String connectionUUID) throws Exception {
		return connectionsDao.getConnectionByUUID(connectionUUID);
	}

	@Override
	public Connections updateConnection(Connections connections) throws Exception {
		return connectionsDao.updateConnection(connections);
	}

	@Override
	public Long checkUserInContacts(Long fromUid, Long toUid) {
		return connectionsDao.checkUserInContacts(fromUid, toUid);
	}

	@Override
	public List<ConnectionsDTO> getMututalConnections(Long userId, Long sessionUid) {
		return connectionsDao.getMututalConnections(userId, sessionUid);
	}

	@Override
	public String inviteAllMutualAndSearch(User user, String[] connectionsUerIdList, OrgLocations loginOrgLocation, String sourceleadUrl, String sourceleadLoginUrl) {
		List<Connections> connections = new ArrayList<>();
		try {
			if (connectionsUerIdList != null) {
				for (String userCode : connectionsUerIdList) {
					User toUser;
					toUser = userDao.getUserByUserCode(userCode);
					Connections connection = new Connections();
					connection.setFromUser(user);
					connection.setToUser(toUser);
					connection.setConnectionStatus(ConnectionStatus.REQUEST_SENT);
					connection.setStatus(Constants.RECORD_ACTIVE);
					String connectionUUID = SourceLeadRandomCodeHelper.generateRandomUUID();
					connection.setConnectionUID(connectionUUID);
					connection.setCreatedDate(new Timestamp(new Date().getTime()));
					connections.add(connection);
					connectionInvitationMailToUser(user, toUser, loginOrgLocation.getId(), connectionUUID, sourceleadUrl, sourceleadLoginUrl);
				}
				int count = connectionsDao.saveConnectionsList(connections);
				if (count != 0) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String withdrawAllValues(String[] connectionsUerIdList) {
		return connectionsDao.withdrawAllValues(connectionsUerIdList);
	}
	
	private void connectionInvitationMailToUser(User loginUser, User toUser, Long locationId, String connectionUUID, String sourceleadUrl, String sourceleadLoginUrl)
			throws Exception {
		OrgLocations locations = orgLocationService.getOrgLocations(locationId);
		UserRole userRole = userRoleService.getUserRoleByUserId(toUser.getId());
		StringBuilder sb = new StringBuilder();
		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(toUser.getFirstName());
		emailMessage.setLastName(toUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail("donotreply@sourcelead.com");
		emailMessage.setFromName("donotreply@sourcelead.com");
		emailMessage.setCreatedBy(loginUser.getUserCode());
		// emailMessage.setOrgLocationId(locationId);
		// setting the class filed values
		Map<String, String> globalVarsMap = new HashMap<String, String>();
		globalVarsMap.put("receiverUserName", toUser.getFullName());
		String loginUserFullName = loginUser.getFullName().substring(0, 1).toUpperCase()
				+ loginUser.getFullName().substring(1);
		globalVarsMap.put("loginUserName", loginUserFullName);
		StringBuilder loginBuilder = new StringBuilder();
		globalVarsMap.put("loginLink", loginBuilder.append("<a href='").append(sourceleadLoginUrl).append(
				"' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>")
				.toString());
		globalVarsMap.put("receiverEmail", toUser.getEmail());
		StringBuilder connectionsBuilder = sendEmailToInvitationUserTable(loginUser, toUser, locations, connectionUUID,sourceleadUrl, sourceleadLoginUrl);
		globalVarsMap.put("profilePicUrl", connectionsBuilder.toString());
		// setting the Recipient information
		InternetAddress[] iAdressArray = InternetAddress.parse(emailMessage.getToEmail());
		// getting the current EmailScenario by EmailScenario unique name and
		// entity type
		EmailScenario emailScenario = null;
		 if(locations != null)
			 emailScenario =  emailScenarioService.getEmailScenarioByUniqueNameAndOrgLocationId(Constants.INVITE_CONNECTION_USER,Constants.ENTITY_TYPE_EMAIL, locations.getId());
			  if(emailScenario == null){
					emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER,Constants.ENTITY_TYPE_EMAIL);
			   }
		EmailScenario noificationEmailScenario = emailScenarioService
				.getEmailScenarioByUniqueName(Constants.INVITE_CONNECTION_USER, Constants.ENTITY_TYPE_NOTIFICATION);
		
		 //covert subject,body and signature
		 String subject = emailScenerioUtil.bulidTemplateSubject(emailScenario.getSubject(),globalVarsMap,emailScenario);
		 String body = emailScenerioUtil.buildTemplateBody(emailScenario.getBody(),globalVarsMap,emailScenario);
		 String signature = "";
		 String url = sourceleadUrl;
		StringBuilder acceptBuilder = new StringBuilder();
		String notificationbody = emailScenerioUtil.buildTemplateBody(noificationEmailScenario.getBody(), globalVarsMap,noificationEmailScenario);
		noificationEmailScenario.setBody(notificationbody);
		// construct HTML template using required data
		String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario, emailMessage, subject, body,
				signature, url, userRole.getOrgLocations().getOrg(), userRole.getOrgLocations());
		emailMessage.setSubject(subject);
		emailMessage.setMessageBody(emailTemplate);
		// calling the sendEmail method of emailScenerioUtil
		emailScenerioUtil.sendEmail(emailMessage, locationId, iAdressArray, new ArrayList<EmailAttachment>());
		 StringBuilder notificationLink = new StringBuilder();
		 String actionLink="";
	     actionLink = notificationLink.append(sourceleadUrl).append("/").append("loadReceivedConnections?status=received").toString();
		UserRole role = userRoleService.getUserRoleByUserId(toUser.getId());
		emailScenerioUtil.sendNotification(actionLink, loginUser, toUser, role.getOrgLocations(),
				noificationEmailScenario, null);
	}

	public StringBuilder sendEmailToInvitationUserTable(User loginUser, User toUser, OrgLocations locations,
			String connectionUUID, String sourceleadUrl, String sourceleadLoginUrl) throws Exception {
		StringBuilder tempHTML = new StringBuilder();
		UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(loginUser.getId(), locations.getId());
		String imageUrl = StringUtils.removeEnd(sourceleadUrl, sourceleadUrl.substring(sourceleadUrl.lastIndexOf("/")));
		StringBuilder loginBuilder12 = new StringBuilder();
		if (loginUser.getFileUrl() != null) {
			loginBuilder12.append("<img src='" + imageUrl + "" + loginUser.getFileUrl()
					+ "' style='width: 60px; height: 60px; border-radius: 50%;'>").toString();
		} else {
			loginBuilder12.append("<img src='" + imageUrl
					+ "/resources/images/userprofileImg.jpg'  style='width: 60px; height: 60px; border-radius: 50%;'>")
					.toString();
		}
		tempHTML.append("<a style='text-decoration:none; display:block;' href='javascript:void(0)'>");
		tempHTML.append("<div style='background-color: #fff; padding: 10px; border-bottom: 1px solid #f1f1f1;'>");
		tempHTML.append("		<div class='userImg' style='width: 60px; padding: 0px 20px 0px 5px; float: left;'>");
		tempHTML.append(loginBuilder12.toString());
		tempHTML.append("		</div>");
		tempHTML.append("		<div style='width:350px; float: left; padding: 0px 0px;'>");
		tempHTML.append(
				"			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 14px; font-weight: 600; color: #454545;'>")
				.append(loginUser.getFullName()).append("</p>");
		tempHTML.append(
				"			<p style='padding: 0px 0px 3px; margin: 0px 0px; font-size: 13px; font-weight: 500; color: #666;'>")
				.append(userRole.getUserType()).append(" at ").append(locations.getOrgLocationName() + "</span></p>");
		// tempHTML.append(" <p style='padding: 0px 0px 3px; margin: 0px 0px;
		// font-size: 13px; font-weight: 500; color: #666;'>16 mutual
		// connections</p>");
		tempHTML.append("		</div>");
		tempHTML.append("   	<div style='float: right; padding: 5px;'>");
		tempHTML.append("<a href='").append(sourceleadUrl).append("/ws/acceptConnection?connectionUUID=")
				.append(connectionUUID)
				.append(" ' style='display: block; background-color: #1176d4; color: #fff; text-decoration: none; padding: 4px 15px 6px; border-radius: 2px; font-size: 14px; line-height: 20px;' href='javascript:void(0)'>Accept </a>");
		tempHTML.append("   	</div>");
		tempHTML.append("   	<div style='clear:both'></div>");
		tempHTML.append("	</div>");
		tempHTML.append("   	<div style='text-align: center; padding: 15px 0px 5px'>");
		tempHTML.append("<a href='").append(sourceleadLoginUrl).append(
				" ' style='text-decoration: none; font-size: 14px; font-weight: 600; color: #214b90;' href='javascript:void(0)'>Manage Invitations </a>");
		tempHTML.append("   	</div>");
		tempHTML.append(" </a>");
		return tempHTML;
	}

}
