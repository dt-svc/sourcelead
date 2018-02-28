package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ImmigrationTemplateDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateDocDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateFormDTO;
import com.bis.sourcelead.dto.ImmigrationTemplateListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.dto.UserRoleDTO;
import com.bis.sourcelead.entity.ImmigrationTemplateView;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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

	//====================immigration view startviewImmigration if you don't required comment below code start here==============================
		@RequestMapping(value={"/viewImmigrationList"})
		public ModelAndView loadImmigrationList(HttpServletRequest request ) {
			ModelAndView model = new ModelAndView();
			model.addObject("sourceleadUrl", sourceleadUrl);
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
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
		public ModelAndView loagNewPetitionPageView(HttpServletRequest request, @RequestParam(value = "status", required = false) String status ) {
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
			model.setViewName("/immigration/immigrationView");
			return model;
		}
		
		//============= immigration view End. if you don't required comment above code end here============================
	
	
	@RequestMapping(value={"/gotoTemplatePreviousPage"})
	public ModelAndView loagNewPetitionPageOne(HttpServletRequest request, @RequestParam(value = "status", required = false) String status ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		try {
			if(status.equalsIgnoreCase("templateDetails")) {
				ImmigrationTemplateDTO dto = (ImmigrationTemplateDTO) request.getSession().getAttribute("templateDTO");
				model.addObject("templateDTO",dto);
				model.setViewName("/immigration/petitionTemplateDetails");
			}if (status.equalsIgnoreCase("tempDetails")) {
				model.setViewName("/immigration/petitionTemplateDetails");
			} else if (status.equalsIgnoreCase("docDetails")) {
				List<LookUpData> educationsList = null;
				educationsList = lookUpDataService.getLookUpDataByCategory(Constants.DOCUMENT_TYPE); // for populating dropdown values in UI.
				/*List<ImmigrationTemplateDTO> templateDTO =  (List<ImmigrationTemplateDTO>)request.getSession().getAttribute("templateDTO");
				model.addObject("templateDTO", );*/
				model.addObject("educationsList", educationsList);
				model.setViewName("/immigration/petitionTemplateDocDetails");
			} else if (status.equalsIgnoreCase("formDetails")) {
				System.out.println(" inside formDetails controller --->");
				model.setViewName("/immigration/petitionTemplateFormDetails");
			} else if (status.equalsIgnoreCase("taskList")) {
				model.setViewName("/immigration/immigrationTemplateTaskList");
			}
		} catch (Exception e) {
			logger.error("Exception occured in controller while navigating-->");
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value={"/addNewPetitionTemplate"})
	public ModelAndView loadAddPetitionTemplate(HttpServletRequest request ) throws ServletRequestBindingException {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		request.getSession().removeAttribute("templateDTO");
		request.getSession().removeAttribute("templateDocList");
		request.getSession().removeAttribute("templateFormsList");
		model.setViewName("/immigration/addPetitionTemplate");
		Long templateId= ServletRequestUtils.getLongParameter(request, "templateId");
		if(templateId != null) {
			try {
				System.out.println(" Edit mode templateId templateId -->" + templateId);
				ImmigrationTemplateDTO templateDTO = immigrationTemplateService.getPetitionTemplateDetails(templateId);
				request.getSession().setAttribute("templateDTO", templateDTO);
			} catch (Exception e) {
				logger.error(" Exception caught in edit mode in controller -->" + templateId);
				e.printStackTrace();
			}
		}
		return model;
	}
	
	@RequestMapping(value={"/viewImmigrationTemplateList"})
	public ModelAndView loadViewPetitionTemplate(HttpServletRequest request ) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		model.setViewName("/immigration/immigrationTemplateList");
	/*	try {
	//	List<ImmigrationTemplateView> templateList= immigrationTemplateService.getAllTemplateDetails();
		System.out.println("template list size is "+templateList.size());
		model.addObject("templateList", templateList);
		}
		catch (Exception e) {
			logger.error("Exception occured while getting template list-->"+e.getMessage());
			e.printStackTrace();
		}*/
		return model;
	}
	

@RequestMapping(value="/getImmigrationTemplateList")
public @ResponseBody ResponseList<ImmigrationTemplateListDTO> getAlltemplateList(ImmigrationTemplateListDTO templateListDTO,@RequestParam(value = "searchValue", required = false) String searchValue,HttpServletRequest request) {
		ResponseList<ImmigrationTemplateListDTO> templateListDTOList = null;
		try {
			String[] tableColumns = { "name", "description", "docsCount", "formsCount", "tasksCount" };//should be entity names 
			int sortColumnIndex = 0; // by default sorting using updated date
			String sortColumnOrder = "asc"; // by default ascending order
			if (templateListDTO.getOrder() != null && !templateListDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(templateListDTO.getOrder().get(0).get("column"));
				sortColumnOrder = templateListDTO.getOrder().get(0).get("dir"); // sorting order 'dir' means direction of the sorting.
			}
			templateListDTO.setSortColumn(tableColumns[sortColumnIndex]);
			templateListDTO.setSortOrder(sortColumnOrder);
			System.out.println("searchValue in controller ---> "+searchValue);
			templateListDTOList = immigrationTemplateService.getAllTemplateDetails(templateListDTO, searchValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(templateListDTOList.getRecordsTotal()>0)
		System.out.println("After getAlltemplateList in controller ---> "+templateListDTOList.getData().get(0).getDocsCount());
		return templateListDTOList;
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
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			immigrationId = immigrationTemplateService.savePetitionTemplateDetails(templateDTO, user,orgLocations, templateId);
		}
		catch (Exception e) {
			logger.error("Exception occured while saving Petition Template Deatils"+e.getMessage());
			e.printStackTrace();
		}
		request.getSession().setAttribute("templateDTO", templateDTO);
		return "{status:'success', id:"+immigrationId+ "}";
	}
	
	@RequestMapping(value={"/savePetitionTemplateDocDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateDocDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request,@RequestParam(value = "templateId", required = false) String templateId) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		List<ImmigrationTemplateDocDTO> templateDocList = null;
		try {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		templateDocList = immigrationTemplateService.savePetitionTemplateDocDetails(templateDTO, user);
		}
		catch (Exception e) {
			logger.error("Exception occured while saving Petition Template Doc Details"+e.getMessage());
			e.printStackTrace();
		}
		request.getSession().setAttribute("templateDocList", templateDocList);
		return "success";
	}
	
	@RequestMapping(value={"/savePetitionTemplateFormDetails"},method = RequestMethod.POST)
	public String savePetitionTemplateFormDetails(ImmigrationTemplateDTO templateDTO, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		List<ImmigrationTemplateFormDTO> templateFormsList = null;
		model.addObject("sourceleadUrl", sourceleadUrl);
		templateDTO.setStaticPath(staticPath);
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request,"user");
		templateFormsList =immigrationTemplateService.savePetitionTemplateFormDetails(templateDTO, user);
		request.getSession().setAttribute("templateFormsList", templateFormsList);
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
	
	
	@RequestMapping(value={"/getTemplateTaskList"},method = RequestMethod.POST)
	public @ResponseBody ResponseList<ImmigrationTemplateDTO> getTemplateTaskList(ImmigrationTemplateDTO templateDTO,HttpServletRequest request,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		ResponseList<ImmigrationTemplateDTO> immiTemplateList = new ResponseList<>();
    	String[] tableColumns = {"taskName", "assignedTo", "priority","taskRepeat"};
		int sortColumnIndex = 3; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default descending order
		if(templateDTO.getOrder() != null && !templateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(templateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = templateDTO.getOrder().get(0).get("dir");
		}
		templateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		templateDTO.setSortOrder(sortColumnOrder);
		immiTemplateList = immigrationTemplateService.getTemplateTaskList(templateDTO, searchValue);
		return immiTemplateList;
	}
	
	@RequestMapping(value={"/editImmigrationTaskList"})
	public ImmigrationTemplateDTO editImmigrationTaskList(HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id) throws NumberFormatException, Exception {
		ImmigrationTemplateDTO templateDTO = immigrationTemplateService.editImmigrationTaskList(id);
		/*List<UserRole> userRoleList = userRoleService.getAllActiveUsersListByOrgLocationId(Long.parseLong(templateDTO.getPetitionAssignedTo()));
		for(UserRole ur :userRoleList){
			
			UserRoleDTO userRoleDTO = new UserRoleDTO();
			StringBuilder userName = new StringBuilder();
			userRoleDTO.setUserId(ur.getUser().getId());
			
			if(null != ur.getUser().getLastName()){
				userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).append("").append(ur.getUser().getLastName()).toString());
			}else{
				userRoleDTO.setUserName(userName.append(ur.getUser().getFirstName()).toString());
			}
			
		}*/
		return templateDTO;
	}
	
}
