package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.service.UserService;

import com.bis.sourcelead.ticketManagement.service.TicketManagementService;

@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class TicketManagementController {
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${static.path}")
	private String staticPath;

	@Autowired
	private UserService userService;

	@Autowired
	private TicketManagementService ticketManagementService;

	@RequestMapping(value = "/ticketManagementTypeList")
	public ModelAndView ticketManagementTypeList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("ticketManagement/ticketManagementTypeList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/ticketManagementCategoryList")
	public ModelAndView ticketManagementCategoryList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		model.addObject("userList", userList);
		model.setViewName("ticketManagement/ticketManagementCategoryList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/ticketManagementPriorityList")
	public ModelAndView ticketManagementPriorityList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("ticketManagement/ticketManagementPriorityList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/ticketManagementStatusList")
	public ModelAndView ticketManagementStatusList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.setViewName("ticketManagement/ticketManagementStatusList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}

	@RequestMapping(value = "/ticketManagementList")
	public ModelAndView ticketManagementList(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
//			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//			model.addObject("statusList",ticketManagementService.getTicketStatusListByOrgLocationId(orgLocations.getId(), 1));
			model.addObject("sourceleadUrl", sourceleadUrl);
		}
		model.setViewName("ticketManagement/ticketManagementList");
		return model;
	}

	@RequestMapping(value = {"/addTicket", "/editTicket"})
	public ModelAndView addTicketManagement(HttpServletRequest request, @RequestParam(defaultValue= "") String uuid) throws Exception {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			model.addObject("ticketTypeList", ticketManagementService.getTicketTypeListByOrgLocationId(orgLocations.getId(), 1, null,null));
			model.addObject("ticketCategoryList", ticketManagementService.getTicketCategoryListByOrgLocationId(orgLocations.getId(),1, null));
			model.addObject("ticketPriorityList", ticketManagementService.getTicketPriorityListByOrgLocationId(orgLocations.getId(),1, null));
			model.addObject("ticketStatusList", ticketManagementService.getTicketStatusListByOrgLocationId(orgLocations.getId(),1, null));
			if(uuid != null && !uuid.isEmpty()) {
				model.addObject("ticketSummary", ticketManagementService.getTicketSummaryByUUID(uuid, orgLocations.getId(), orgLocations.getDateFormat(), true));
			}
			model.setViewName("ticketManagement/addTicketManagement");
			List<User> userList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
			model.addObject("userList", userList);
			model.addObject("sourceleadUrl", sourceleadUrl);
		}
		return model;
	}

	@RequestMapping(value = "/ticketSummary")
	public ModelAndView ticketManagementSummary(HttpServletRequest request, @RequestParam(defaultValue="") String uuid) throws Exception {
		ModelAndView model = new ModelAndView();
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			model.addObject("statusList", ticketManagementService.getTicketStatusListByOrgLocationId(orgLocations.getId(), -1, null));
			model.addObject("ticketSummary", ticketManagementService.getTicketSummaryByUUID(uuid, orgLocations.getId(), orgLocations.getDateFormat(), true));
			//model.addObject("ticketComment", ticketManagementService.getTicketCommentByTicketId(ticketCommentDTO,ticketId,user.getId(),orgLocations.getId()));
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.addObject("loggedInUserId", user.getId());
			model.setViewName("ticketManagement/ticketManagementSummary");
		}
		return model;
	}
}
