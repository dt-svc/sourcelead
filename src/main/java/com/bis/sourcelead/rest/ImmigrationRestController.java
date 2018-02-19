package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.immigration.service.ImmigrationTemplateService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
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
	private UserService userService;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private LookUpDataService lookUpDataService;
	
	@Autowired
	private ImmigrationTemplateService immigrationTemplateService;

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
	
	
	@RequestMapping(value={"/gotoTemplatePreviousPage"})
	public ModelAndView loagNewPetitionPageOne(HttpServletRequest request, @RequestParam(value = "status", required = false) String status ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		if(status.equalsIgnoreCase("templateDetails")) {
			model.setViewName("/immigration/petitionTemplateDetails");
		}
		return model;
	}

	@RequestMapping(value={"/addNewPetitionTemplate"})
	public ModelAndView loadAddPetitionTemplate(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/addPetitionTemplate");
		return model;
	}
	
	@RequestMapping(value={"/viewImmigrationTemplateList"})
	public ModelAndView loadViewPetitionTemplate(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/immigrationTemplateList");
		try {
		List<ImmigrationTemplateView> templateList= immigrationTemplateService.getAllTemplateDetails();
		System.out.println("template list size is "+templateList.size());
		model.addObject("templateList", templateList);
		}
		catch (Exception e) {
			logger.error("Exception occured while getting template list-->"+e.getMessage());
			e.printStackTrace();
		}
		return model;
	}
	
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
				educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating dropdown values in UI.
				model.addObject("educationsList", educationsList);
				model.setViewName("/immigration/petitionTemplateDocDetails");
			} else if (forwardTo.equalsIgnoreCase("formDetails")) {
				System.out.println(" inside formDetails controller --->");
				model.setViewName("/immigration/petitionTemplateFormDetails");
			} else if (forwardTo.equalsIgnoreCase("taskList")) {
				model.setViewName("/immigration/immigrationTemplateTaskList");
			}

		} catch (Exception e) {
			logger.error("Exception occured in controller while navigating-->");
			e.printStackTrace();
		}

		return model;
	}
	
	
	@RequestMapping(value={"/savePetitionTemplateDetails"},method = RequestMethod.POST, produces = "application/json")
	public String savePetitionTemplateDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,@RequestParam(value = "templateId", required = false) String templateId ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		Long immigrationId = null;
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
			immigrationId = immigrationTemplateService.savePetitionTemplateDetails(templateDTO, user, templateId);
		}
		catch (Exception e) {
			logger.error("Exception occured while saving Petition Template Deatils"+e.getMessage());
			e.printStackTrace();
		}
		return "{status:'success', id:"+immigrationId+ "}";
	}
	@RequestMapping(value={"/savePetitionTemplateDocDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,@RequestParam(value = "templateId", required = false) String templateId) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		try {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		immigrationTemplateService.savePetitionTemplateDocDetails(templateDTO, user);
		}
		catch (Exception e) {
			logger.error("Exception occured while saving Petition Template Doc Details"+e.getMessage());
			e.printStackTrace();
		}
		return "success";
	}
	@RequestMapping(value={"/savePetitionTemplateFormDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		templateDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		immigrationTemplateService.savePetitionTemplateFormDetails(templateDTO, user);
		return "success";
	}
	
	@RequestMapping(value={"/savePetitionTemplateTaskDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateTaskDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			immigrationTemplateService.savePetitionTemplateTaskDetails(templateDTO, user);
			System.out.println("Template Task Details saved succesfully --> ");
		} catch (Exception e) {
			logger.error("Exception occured while saving Template Task Details" + e.getMessage());
			e.printStackTrace();
		}
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
