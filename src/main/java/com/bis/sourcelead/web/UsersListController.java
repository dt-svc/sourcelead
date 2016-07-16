package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller
@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class UsersListController {
	
private static final Logger logger = LoggerFactory.getLogger(UsersListController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserRoleService userRoleService;
	
	
	
	@RequestMapping(value = "/usersList")
	public ModelAndView usersList(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("users/usersList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	
	@RequestMapping(value = "/getUsersList")
	@ResponseBody
	public String getUsersList(HttpServletRequest request,
			@RequestParam(value="userRole", required=false) String userRole,
			@RequestParam(value="searchByName", required=false) String searchByName,
			@RequestParam(value="userState", required=false) String userState) {

	 	String resultJson = null;
		Collection<UserRole> rolesList = null;
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		ArrayList<UserRole> userRolesList = new ArrayList<UserRole>();
		try {
			if(!isNullOrEmpty(userState) && isNullOrEmpty(userRole) && isNullOrEmpty(userRole)){
				rolesList = userRoleService.getUsersListByUserStateAndLocationId(userState, orgLocations.getId(),user.getId());
			}
			else if((isNullOrEmpty(userRole) && isNullOrEmpty(searchByName)) || (userRole.equalsIgnoreCase("All") && isNullOrEmpty(searchByName))){
				rolesList = userRoleService.getUsersListByLocationId(orgLocations.getId(),userState,user.getId());
			}
			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByRoleAndLocationId(userRole, orgLocations.getId(),userState,user.getId());
			}
			else if((!isNullOrEmpty(searchByName) && isNullOrEmpty(userRole)) || ("All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName))){
				rolesList = userRoleService.getUsersListByUserNameAndLocationId(searchByName, orgLocations.getId(),userState,user.getId());
			}
			else if(!isNullOrEmpty(userRole) && !"All".equalsIgnoreCase(userRole) && !isNullOrEmpty(searchByName)){
				rolesList = userRoleService.getUsersListByRoleAndNameAndLocationId(userRole, searchByName, orgLocations.getId(),userState,user.getId());
			}
			 
			
			for(UserRole role : rolesList){
				if(role.getRoles().getRoleName().equalsIgnoreCase("ROLE_ADMIN")){
					role.setUserRoleName("Administrator");
				}else if(role.getRoles().getRoleName().equalsIgnoreCase("ROLE_USER")){
					role.setUserRoleName("User");
				}else if(role.getRoles().getRoleName().equalsIgnoreCase("ROLE_APPROVER")){
					role.setUserRoleName("Approver");
				}
				userRolesList.add(role);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 resultJson = toJSON(userRolesList);
		 return resultJson;

	}
	
	
	private String toJSON(Object obj){
        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });
        return xstream.toXML(obj);
    }
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}

}
