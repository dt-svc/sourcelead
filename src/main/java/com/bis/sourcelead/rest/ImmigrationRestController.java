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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ConnectionsDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ReferContactsDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.Connections;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.ConnectionStatus;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.ConnectionsService;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app/immigration")
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
	
	@Autowired
	LookUpDataService lookUpDataService;
	
	
	@Autowired
	ImmigrationTemplateService immigrationTemplateService;

	/*@RequestMapping(value={"/viewImmigrationList"})
	public ModelAndView loadImmigrationList(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/immigrationList");
		return model;
	}*/

	/*@RequestMapping(value={"/loadNewPetition"})
	public ModelAndView loadNewPetition(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/newPetition");
		return model;
	}*/
	
	
	/*@RequestMapping(value={"/loadNewPetitionPage"})
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
	}*/

	@RequestMapping(value={"/addNewPetitionTemplate"})
	public ModelAndView loadAddPetitionTemplate(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/addPetitionTemplate");
		return model;
	}
	
	/*@RequestMapping(value={"/viewImmigrationTemplateList"})
	public ModelAndView loadViewPetitionTemplate(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/immigrationTemplateList");
		return model;
	}*/
	
	@RequestMapping(value={"/addPetitionTemplate"})
	public ModelAndView addPetitionTemplatePage(HttpServletRequest request,
			@RequestParam(value = "forwardTo", required = false) String forwardTo) {
		ModelAndView model = new ModelAndView();
		try {

			model.addObject("sourceleadUrl", sourceleadUrl);
			if (forwardTo.equalsIgnoreCase("tempDetails")) {
				model.setViewName("/immigration/petitionTemplateDetails");
			} else if (forwardTo.equalsIgnoreCase("docDetails")) {
				List<LookUpData> educationsList = null;
				educationsList = lookUpDataService.getLookUpDataByCategory(Constants.EDUCATIONS); // for populating drop
				model.addObject("educationsList", educationsList);
				model.setViewName("/immigration/petitionTemplateDocDetails");
			} else if (forwardTo.equalsIgnoreCase("formDetails")) {
				System.out.println(" inside formDetails controller --->");
				model.setViewName("/immigration/petitionTemplateFormDetails");
			} else if (forwardTo.equalsIgnoreCase("taskList")) {
				model.setViewName("/immigration/immigrationTemplateTaskList");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception occured in method addPetitionTemplatePage");
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value={"/savePetitionTemplateDetails"},method = RequestMethod.POST, produces = "application/json")
	public String savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		Long immigrationId = immigrationTemplateService.savePetitionTemplateDetails(templateDTO, user);
		return "{status:'success', id:"+immigrationId+ "}";
	}
	@RequestMapping(value={"/savePetitionTemplateDocDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		//To do service
		return "success";
	}
	@RequestMapping(value={"/savePetitionTemplateFormDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		//To do service
		return "success";
	}
	/*@RequestMapping(value={"/viewImmigration"})
	public ModelAndView viewImmigration(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/immigrationView");
		return model;
	}*/
}
