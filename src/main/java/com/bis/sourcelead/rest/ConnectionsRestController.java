package com.bis.sourcelead.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.UserService;
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
	
	@RequestMapping(value="/addNewConnection")
	public String addNewConnection(@RequestParam(value = "userCode", required = false) String userCode, HttpServletRequest request){
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
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
		model.setViewName("/connections/connections");
		return model;
	}
	
	@RequestMapping(value="/loadSentConnections")
	public ModelAndView loadSentConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/sentConnections");
		return model;
	}
	
	@RequestMapping(value="/loadReceivedConnections")
	public ModelAndView loadReceivedConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
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
	
	
	@RequestMapping(value="/acceptConnection")
	public String addAcceptConnection(@RequestParam(value = "userCode", required = false) String userCode, HttpServletRequest request){
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		
		String result = "invalid";
		try {
			User toUser = userService.getUserByUserCode(userCode);
			int resultValue = 0;
				resultValue = connectionsService.acceptConnection(user, toUser, ConnectionStatus.CONNECTED.getValue());
			if(resultValue != 0) {
				result = "success";
			}
		} catch (Exception e) {
		e.printStackTrace();
		}
		return result;
	}
	

	@RequestMapping(value="/loadConnectedList")
	public ModelAndView loadConnectedList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
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
	
	
}
