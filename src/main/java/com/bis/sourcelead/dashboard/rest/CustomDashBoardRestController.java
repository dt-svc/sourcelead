package com.bis.sourcelead.dashboard.rest;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dashboard.dao.CustomDashBoardConfDao;
import com.bis.sourcelead.dashboard.dto.CustomDashBoardConfDTO;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardAssociatesConf;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardConfiguration;
import com.bis.sourcelead.dashboard.entity.CustomDashBoardRoleConfiguration;
import com.bis.sourcelead.dashboard.service.CustomDashboardConfService;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LetterHead;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.enums.Result;


@RestController
@RequestMapping("/app")
public class CustomDashBoardRestController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	CustomDashboardConfService customDashboardConfService;
	
	@Autowired
	CustomDashBoardConfDao customDashBoardConfDao;
	
	@Autowired
	OrgService orgService;
	
	
	/**
	 * Return Dash board configuration jsp
	 * 
	 * @return ModelAndView
	 * 
	 */
	@RequestMapping(value = "/configureCustomDashBoard")
	public ModelAndView configureCustomDashBoard(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("customdashboard/customDashBoardConfig");
				
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return model;

	}
	
	/**
	 * Return Dash board configuration jsp
	 * 
	 * @return ModelAndView
	 * 
	 */
	@RequestMapping(value = "/addCustomDashBoardConf")
	public ModelAndView addConfigureCustomDashBoard(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			//Getting roles
			List<Roles> roles = rolesService.getRoles(orgLocation.getId());
			model.addObject("roles", roles);
			//Gettingh associates
			List<UserRole> users = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocation.getId());
			model.addObject("users", users);
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("customdashboard/addCustomDashBoardConfig");
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return model;

	}
	
	
	@RequestMapping(value = "/saveCustomDashBoardConfiguration", method = RequestMethod.POST)
	public @ResponseBody Response<CustomDashBoardConfiguration> saveCustomDashBoardConfiguration(CustomDashBoardConfDTO customDashBoardConfDTO ,HttpServletRequest request) throws Exception
	{
		Response<CustomDashBoardConfiguration> response = new Response<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			if (Validator.validate(customDashBoardConfDTO)) {
				CustomDashBoardConfiguration customDashBoardConf =  customDashboardConfService.saveCustomDashBoardConf(customDashBoardConfDTO,orgLocation,user.getId());
				response.setObject(customDashBoardConf);
				response.setResponse(Result.SUCCESS.name());
			}
			else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
		}
		return response;
	}
	
	
	/**
	 * View configured dashboard list
	 * @param req
	 * @param res
	 * @return ResponseList<CustomDashBoardConfDTO>
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewCustomDashBoardConfiguredList", method = RequestMethod.GET)
	public @ResponseBody ResponseList<CustomDashBoardConfDTO> viewCustomDashBoardConfiguredList(HttpServletRequest req,HttpServletResponse res) throws Exception
	{
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");
			return customDashboardConfService.getCustomConfDashBoardByLocationId(orgLocation.getId());
		
	}
	
	
	/**
	 * Edit  
	 * 
	 * @param customDashBoardUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editCustomDashBoardConf")
	public ModelAndView editCustomDashBoardConf(@RequestParam(value = "customDashBoardUUID", required = false) String customDashBoardUUID,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		ModelAndView model = new ModelAndView();
		try {
			model.addObject("sourceleadUrl", sourceleadUrl);
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");
			CustomDashBoardConfiguration customDashBoardConf = customDashboardConfService.getCustomDashboardConfByUUID(customDashBoardUUID,orgLocation.getId());
			model.addObject("customDashBoardConf", customDashBoardConf);
			
			// existed Roles
			List<Roles> roles = rolesService.getRoles(orgLocation.getId());
			model.addObject("roles", roles);
			//existed associates
			List<UserRole> users = userRoleService.getAllActiveUsersListByOrgLocationId(orgLocation.getId());
			model.addObject("users", users);
			
			//selected roles
			Set<CustomDashBoardRoleConfiguration> selectedRoles = customDashBoardConf.getDashBoardRoleConf();
			model.addObject("selectedRoles", selectedRoles);
			
			
			// selected associates
			Set<CustomDashBoardAssociatesConf> selectedAsso  = customDashBoardConf.getDashBoardAssociatesConf();
			model.addObject("selectedAssociates", selectedAsso);
			
			model.setViewName("customdashboard/addCustomDashBoardConfig");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return model;
		
	}
	
	
	/**
	 * delete custom dashboard configuration by changing status
	 * 
	 * @param customDashBoardUUID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/deleteCustomDashBoardConf", method = RequestMethod.POST)
	public @ResponseBody  void  deleteCustomDashBoardConf(@RequestParam("customDashBoardUUID") String customDashBoardUUID,HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(req, "orgLocations");
			CustomDashBoardConfiguration customDashBoardConf = customDashboardConfService.getCustomDashboardConfByUUID(customDashBoardUUID, orgLocation.getId());
			customDashBoardConf.setStatus(0);
			customDashBoardConfDao.saveCustomDashBoardConf(customDashBoardConf);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
