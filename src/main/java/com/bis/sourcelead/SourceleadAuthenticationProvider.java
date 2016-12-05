package com.bis.sourcelead;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginAttempts;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.UserLoginAttemptsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;

/**
 * Custom AuthenticationProvider 
 * 
 * @author: Naresh Bolisetty
 * @date: 26-May-2016
 */
@Component
public class SourceleadAuthenticationProvider  implements AuthenticationProvider {
 
    @Autowired
    private UserService userService;
    
    @Autowired
    private SourceleadPasswordEncoder sourceleadPasswordEncoder;
    
    @Autowired
    private UserLoginAttemptsService userLoginAttemptsService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired 
	private ModulesService modulesService;
	
	@Autowired 
	private SourceleadActionsService sourceleadActionsService;
  
    /*@param
     * 
     * Overriding AuthenticationProvider authenticate(-) method to handle NullPointer Exception for unregistered users
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          String username = authentication.getName();
          String password = (String) authentication.getCredentials();
     
          User  user = userService.findByUserName(username);
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
          else{
        	  if(sourceleadPasswordEncoder.matches(password, user.getPassword())){
        		  OrgLocations orgLocations = userRole.getOrgLocations();
      			  Org org = orgLocations.getOrg();
				try {
					modulesId = modulesService.getOrgRegisteredModules(org);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    			List<SourceleadActions> actionsList = null;
    			try {
					actionsList = sourceleadActionsService.getLoginUserRoleActionsByOrgLocationId(modulesId, userRole.getRoles().getId(), orgLocations.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
        		  List<GrantedAuthority> authorities = buildUserAuthority(actionsList); 
				  try {
					  if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
	        			  userRole.setUserState(Status.ACTIVE);
	        			  userRoleService.addUserRole(userRole);
	        		  }
				   } catch (Exception e) {
					  e.printStackTrace();
				   }
        		  
        		  return new UsernamePasswordAuthenticationToken(username, password, authorities);
        	  }
        	  else{
        		  throw new BadCredentialsException("login_password_error");
        	  }
          }
          
    }
 
    public boolean supports(Class<?> arg0) {
        return true;
    }

  /*  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
       
    	if(userRoles != null && !userRoles.isEmpty()){
    		// Build user's authorities
    		for (UserRole userRole : userRoles) {
    			setAuths.add(new SimpleGrantedAuthority(userRole.getRoles().getRoleName()));
    		}
    	}
    	List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
    	return Result;
    }*/
    
    
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

    private UserLoginAttempts getUserLoginAttemptsDetails(User user){
    	
    	UserLoginAttempts userLoginAttempts = new UserLoginAttempts();
    	userLoginAttempts.setUser(user);
    	userLoginAttempts.setAttemptsCount(1);
    	userLoginAttempts.setCreatedDate(new Date());
    	return userLoginAttempts;
    	
    }

	


}
