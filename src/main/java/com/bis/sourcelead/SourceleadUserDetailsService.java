package com.bis.sourcelead;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

@Configuration
@EnableTransactionManagement
public class SourceleadUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	 @Autowired 
	private ModulesService modulesService;
    
    @Autowired
	EmployeesService employeesService;
	
	@Autowired 
	private SourceleadActionsService sourceleadActionsService;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	
		com.bis.sourcelead.entity.User user = userService.findByUserName(username);
		UserRole userRole = null;
		 List<Long> modulesId = null;
		 try {
     	    if(user != null){
     		     userRole = userRoleService.getLoginUserPrimaryRole(user.getId());
     	     }
				
			  } catch (Exception e1) {
				e1.printStackTrace();
			 }
       
       if(user == null){
     	  throw new BadCredentialsException("login_unRegistered_error");
       }
       else if((userRole == null) || (userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.INACTIVE))){
     	  throw new BadCredentialsException("login_inActive_error");
       }
       

 	  OrgLocations orgLocations = null;
	  Org org = null;
	  List<SourceleadActions> actionsList = null;
 		  if(null == user.getUserType() || (!user.getUserType().equalsIgnoreCase(Constants.PROFESSIONAL) && !user.getUserType().equalsIgnoreCase(Constants.INDIVIDUAL_RECRUITER))){
 			  orgLocations = userRole.getOrgLocations();
   			  org = orgLocations.getOrg();
				try {
					modulesId = modulesService.getOrgRegisteredModulesByOrgLocationId(orgLocations);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				try {
					actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
 		  }else{
 			  try {
					actionsList = sourceleadActionsService.getLoginCrowdSourcingUserRoleActionsByOrgLocationId(userRole.getRoles().getId());
				} catch (Exception e) {
					e.printStackTrace();
				} 
 		  }
			
 		  List<GrantedAuthority> authorities = buildUserAuthority(actionsList); 
			  try {
				  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
     			  userRole.setUserState(Status.ACTIVE);
     			  userRole.setNewlyLoginUser(Constants.YES);
     			  userRoleService.addUserRole(userRole);

     			  /*
                    * Update USER DB data as ElasticSearch Documents
                    */
                   Employees employee = employeesService.getEmployeeByUserIdAndOrgLocationsId(userRole.getUser().getId(), orgLocations.getId());
                   if(null != employee){
                       userService.saveOrUpdateUserElkDocument(userRole,employee,userRole.getCreatedBy());
                   }
     		  }
			   } catch (Exception e) {
				  e.printStackTrace();
			   }
 		
		return buildUserForAuthentication(user, authorities);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.bis.sourcelead.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUserName(), user.getPassword(), true, true, true, true, authorities);
	}

	  private List<GrantedAuthority> buildUserAuthority(List<SourceleadActions> actionsList) {

	    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
	       
	    	if(actionsList != null && !actionsList.isEmpty()){
	    		// Build user's authorities
	    		for (SourceleadActions sourceleadActions : actionsList) {
	    			setAuths.add(new SimpleGrantedAuthority(sourceleadActions.getActionRole()));
	    		}
	    	}
	    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
	    	return Result;
	   }
}
