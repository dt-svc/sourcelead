package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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

import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.UserRoleService;
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
	OrgLocationService orgLocationService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	ReferContactsService referContactsService;

	@RequestMapping(value = "/addNewConnection")
	public String addNewConnection(@RequestParam(value = "userCode", required = false) String userCode,
			HttpServletRequest request) {
		String result = null;
		try {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		result = connectionsService.saveConnection(user, userCode, loginOrgLocation, sourceleadUrl, sourceleadLoginUrl);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/getConnectionsList")
	public @ResponseBody List<ConnectionsDTO> getConnectionsList(
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ConnectionsDTO> userRoleDTOList = new ArrayList<ConnectionsDTO>();
		userRoleDTOList = connectionsService.getConnectionsListByStatus(searchValue, offset, user.getId(), status);
		return userRoleDTOList;
	}

	@RequestMapping(value = { "/loadFindConnections", "/loadSentConnections", "/loadReceivedConnections",
			"/loadConnectedList" })
	public ModelAndView loadFindConnections(HttpServletRequest request,
			@RequestParam(value = "status", required = false) String status) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Long receivedConnectionCount = connectionsService.getConnectionReceivedCount(loginUser.getId());
		SourceLeadSessionManager.removeSessionAttribute(request, "connectionReceivedCount");
		SourceLeadSessionManager.setSessionAttribute(request, "connectionReceivedCount", receivedConnectionCount);
		if (status.equalsIgnoreCase("find")) {
			model.setViewName("/connections/connections");
		} else if (status.equalsIgnoreCase("sent")) {
			model.setViewName("/connections/sentConnections");
		} else if (status.equalsIgnoreCase("received")) {
			model.setViewName("/connections/receivedConnections");
		} else if (status.equalsIgnoreCase("connected")) {
			model.setViewName("/connections/connectedConnections");
		}
		return model;
	}

	@RequestMapping(value = "/withdrawConnection")
	public String withdrawConnection(@RequestParam(value = "connectionUUID", required = false) String connectionUUID,
			HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String result = "invalid";
		try {
			int resultValue = 0;
			resultValue = connectionsService.deleteConnection(connectionUUID);
			if (resultValue != 0) {
				result = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/ws/acceptConnection")
	public ModelAndView addAcceptConnection(
			@RequestParam(value = "connectionUUID", required = false) String connectionUUID, HttpServletRequest request)
			throws Exception {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() != null) {
			Connections connections = null;
			String result = "invalid";
			try {
				if (StringUtils.isNotBlank(connectionUUID)) {
					connections = connectionsService.getConnectionByUUID(connectionUUID);
				}
				if (connections != null) {
					connections.setConnectionStatus(ConnectionStatus.CONNECTED);
					connectionsService.updateConnection(connections);
					ReferContacts refercontacts1 = referContactsService.getReferContactsByFromUserIdAndToUserId(
							connections.getFromUser().getId(), connections.getToUser().getId());
					if (refercontacts1 == null) {
						referContactsService.saveuserContactsFromConnections(connections.getFromUser(),
								connections.getToUser(),null,sourceleadUrl,sourceleadLoginUrl);
					}

					ReferContacts refercontacts2 = referContactsService.getReferContactsByFromUserIdAndToUserId(
							connections.getToUser().getId(), connections.getFromUser().getId());
					if (refercontacts2 == null) {
						referContactsService.saveuserContactsFromConnections(connections.getToUser(),
								connections.getFromUser(),null,sourceleadUrl,sourceleadLoginUrl);
					}
					result = "success";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result == "success") {
				return new ModelAndView("redirect:/app/loadConnectedList?status=connected");
			} else {
				model.setViewName("login");
				return model;
			}
		} else {
			model.setViewName("login");
			return model;
		}
	}

	@RequestMapping(value = "/getMututalConnections")
	public @ResponseBody List<ConnectionsDTO> getMututalConnections(
			@RequestParam(value = "userId", required = false) Long userId, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ConnectionsDTO> connectionsDTOs = connectionsService.getMututalConnections(userId, user.getId());
		return connectionsDTOs;
	}

	@RequestMapping(value = "/inviteAllMutualAndSearch")
	public String inviteAllMutualAndSearch(
			@RequestParam(value = "connectionsUerIdList[]", required = false) String[] connectionsUerIdList,
			HttpServletRequest request) throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		String result = connectionsService.inviteAllMutualAndSearch(user, connectionsUerIdList, loginOrgLocation, sourceleadUrl, sourceleadLoginUrl );
		return result;
	}

	@RequestMapping(value = "/withdrawAllValues")
	public String withdrawAllValues(
			@RequestParam(value = "connectionsUerIdList[]", required = false) String[] connectionsUerIdList,
			HttpServletRequest request) throws Exception {
		String result = connectionsService.withdrawAllValues(connectionsUerIdList);
		return result;
	}
	
	
	@RequestMapping(value = "/acceptAllValues")
	public String acceptAllValues(
			@RequestParam(value = "connectionsUerIdList[]", required = false) String[] connectionsUerIdList, HttpServletRequest request)
			throws Exception {
			String result = "invalid";
			Connections connections = null;
			try {
				if(connectionsUerIdList!= null) {
				for(String connectionUUID : connectionsUerIdList) {
					if (StringUtils.isNotBlank(connectionUUID)) {
						connections = connectionsService.getConnectionByUUID(connectionUUID);
					}
					if (connections != null) {
						connections.setConnectionStatus(ConnectionStatus.CONNECTED);
						connectionsService.updateConnection(connections);
						ReferContacts refercontacts1 = referContactsService.getReferContactsByFromUserIdAndToUserId(
								connections.getFromUser().getId(), connections.getToUser().getId());
						if (refercontacts1 == null) {
							referContactsService.saveuserContactsFromConnections(connections.getFromUser(),
									connections.getToUser(),null,sourceleadUrl,sourceleadLoginUrl);
						}
	
						ReferContacts refercontacts2 = referContactsService.getReferContactsByFromUserIdAndToUserId(
								connections.getToUser().getId(), connections.getFromUser().getId());
						if (refercontacts2 == null) {
							referContactsService.saveuserContactsFromConnections(connections.getToUser(),
									connections.getFromUser(),null,sourceleadUrl,sourceleadLoginUrl);
						}
					}
				}
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
