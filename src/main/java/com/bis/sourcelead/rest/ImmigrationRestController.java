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
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class ImmigrationRestController {
	private static final Logger logger = LoggerFactory.getLogger(ImmigrationRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	UserRoleService userRoleService;
	

	@RequestMapping(value={"/loadImmigrationList"})
	public ModelAndView loadImmigrationList(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		System.out.println("inside loadImmigrationList...");
		model.setViewName("/immigration/immigrationList");
		return model;
	}

	@RequestMapping(value={"/loadNewPetition"})
	public ModelAndView loadNewPetition(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/newPetition");
		return model;
	}
	
	
	@RequestMapping(value={"/loadNewPetitionPage"})
	public ModelAndView loagNewPetitionPageOne(HttpServletRequest request, @RequestParam(value = "status", required = false) String status ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(status.equalsIgnoreCase("one")) {
			model.setViewName("/immigration/newPetitionPage1");
		}else if(status.equalsIgnoreCase("two")) {
			model.setViewName("/immigration/newPetitionPage2");
		}else if(status.equalsIgnoreCase("three")) {
			model.setViewName("/immigration/newPetitionPage3");
		}else if(status.equalsIgnoreCase("four")) {
			model.setViewName("/immigration/newPetitionPage4");
		}else if(status.equalsIgnoreCase("five")) {
			model.setViewName("/immigration/newPetitionPage5");
		}else if(status.equalsIgnoreCase("six")) {
			model.setViewName("/immigration/newPetitionPage6");
		}else if(status.equalsIgnoreCase("seven")) {
			model.setViewName("/immigration/newPetitionPage7");
		}
		return model;
	}

	@RequestMapping(value={"/viewImmigration"})
	public ModelAndView viewImmigration(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		System.out.println("inside viewImmigration URL....");
		model.setViewName("/immigration/immigrationView");
		return model;
	}
}
