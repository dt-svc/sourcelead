package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.ContractsTemplate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ContractController {
	
	private static final Logger logger = LoggerFactory.getLogger(ContractController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;

	@Autowired
	private ContractsTemplateService contractsTemplateService;
	
	/**
	 * display contract template list page
	 * @return model
	 */
	@RequestMapping(value = "/orgContractTemplate")
	public ModelAndView orgContractTemplate() {
		logger.debug("display contract template page");
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/contractsTemplate");
		return model;
	}
	
	/**
	 * 	Display add template page
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_CONTRACT_TEMPLATE')")
	@RequestMapping(value = "/addContractTemplate")
	public ModelAndView addContractTemplate(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addContractTemplate");
		return model;
	}
	/**
	 * display contract template list page
	 * @return model
	 */
	@RequestMapping(value = "/contractListbyStatus")
	public ModelAndView contractListbyStatus(HttpServletRequest request) {
		logger.debug("display contract template page");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Contract);
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/contractList");
		return model;
	}
	
	
	/**
	 * display add new contract  page
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_NEW_CONTRACT')")
	@RequestMapping(value = "/addNewContract")
	public ModelAndView addNewContract(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		commonUtil.highLightMenu(Constants.List_by_status, request);
		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_CLIENT));
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrg().getOrgName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
			}
			orgRelations.setOrgName(orgName);
		}
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrg().getOrgName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
			}
			orgRelations.setOrgName(orgName);
		}
		model.addObject("userList", userList);
		model.addObject("clientList", clientList);
		model.addObject("orgRelationsList", orgRelationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addNewContract");
		return model;
	}
	
	/**
	 * display add new contract  page 
	 * @return model
	 * @throws Exception 
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADD_EXISTING_CONTRACT')")
	@RequestMapping(value = "/addExistingContract")
	public ModelAndView addExistingContract(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
//		commonUtil.highLightMenu(Constants.List_by_status, request);
		List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocation.getId(),Long.valueOf(Constants.TAB_CLIENT));
		List<OrgRelations> orgRelationsList = orgRelationsService.getAllVendorsListByOrgLocationId(orgLocation.getId());
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgLocation.getId());
		for (OrgRelations orgRelations : orgRelationsList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrg().getOrgName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
			}
			orgRelations.setOrgName(orgName);
		}
		for (OrgRelations orgRelations : clientList) {
			String orgName = "";
			if(orgRelations.getResponderLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getLocationId().getOrg().getOrgName();
			}if(orgRelations.getLocationId().getId().longValue() == orgLocation.getId().longValue()){
				orgName = orgRelations.getResponderLocationId().getOrg().getOrgName();
			}
			orgRelations.setOrgName(orgName);
		}
		model.addObject("userList", userList);
		model.addObject("clientList", clientList);
		model.addObject("orgRelationsList", orgRelationsList);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("contracts/addExistedContract");
		return model;
	}
	
	@PreAuthorize("hasAnyRole('ROLE_VIEW_EDIT_CONTRACT_TEMPLATE')")
	@RequestMapping(value = "/editTemplate")
    public ModelAndView editTemplate(@RequestParam(value="templateId", required=false) String templateId ,HttpServletResponse response) throws Exception {
		
		ContractsTemplate contractsTemplate =	contractsTemplateService.getContractTemplateById(Long.parseLong(templateId));
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("contractsTemplate", contractsTemplate);
		model.addObject("templateContent", contractsTemplate.getTemplateContent());
		model.addObject("templateId", templateId);
		model.setViewName("contracts/addContractTemplate");
		return model;
	}
	
		
}
