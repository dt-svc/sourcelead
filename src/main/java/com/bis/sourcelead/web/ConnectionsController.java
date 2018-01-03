package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.ConnectionBean;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping(value="/app")
public class ConnectionsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private ConnectionsService connectionsService;
	
	@RequestMapping(value="/findConnections")
	public ModelAndView getFindConnections(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		List<ConnectionBean> findConnectionsList =  connectionsService.getFindConnections(user.getId());
		model.addObject("findConnList", findConnectionsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/connections");
		return model;
	}
	
	@RequestMapping(value="/clickConnect/{userId}")
	public String clickConnectedList(@PathVariable String userId, HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		connectionsService.changeClickConnect(userId, user.getId());
		return "redirect:/app/findConnections";
	}
	
	@RequestMapping(value="/connectedList")
	public ModelAndView getConnectedList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<ConnectionBean> connectedList = connectionsService.getConnectedList(user.getId());
		model.addObject("findConnList", connectedList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/connectedConnections");
		return model;
	}
	
	@RequestMapping(value="/connectionSent")
	public ModelAndView getSentList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		List<ConnectionBean> sentConnections = connectionsService.getSentList(user.getId());
		model.addObject("sentConnections", sentConnections);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/sentConnections");
		return model;
	}
	
	@RequestMapping(value="/connectionReceived")
	public ModelAndView getReceivedList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<ConnectionBean> receivedConnections = connectionsService.getReceivedList(user.getId());
		model.addObject("sentConnections", receivedConnections);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/connections/receivedConnections");
		return model;
	}
	
	
	@RequestMapping(value="withdrawRequest/{userId}")
	public String withdrawRequest(@PathVariable String userId, HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		connectionsService.withdrawUser(userId, user.getId());
		return "redirect:/app/connectionSent";
	}
	
	@RequestMapping(value="acceptRequest/{userId}")
	public String acceptRequest(@PathVariable String userId, HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		connectionsService.acceptRequest(userId, user.getId());
		return "redirect:/app/connectionReceived";
	}
	
	@RequestMapping(value="ignoreUser/{userId}")
	public String ignoreUser(@PathVariable String userId, HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		connectionsService.ignoreUser(userId, user.getId());
		return "redirect:/app/connectionReceived";
	}
	
	
	@RequestMapping(value="disconnectConnect/{userId}")
	public String disconnectConnect(@PathVariable String userId, HttpServletRequest request) {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		connectionsService.disconnectConnect(userId, user.getId());
		return "redirect:/app/connectedList";
	}
	
}
