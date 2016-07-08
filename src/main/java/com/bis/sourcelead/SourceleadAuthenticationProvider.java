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

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserLoginAttempts;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.UserLoginAttemptsService;
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
  
    /*@param
     * 
     * Overriding AuthenticationProvider authenticate(-) method to handle NullPointer Exception for unregistered users
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          String username = authentication.getName();
          String password = (String) authentication.getCredentials();
     
          UserLoginAttempts userLoginAttempts = null;
          User  user = userService.findByUserName(username);
          if(user == null){
        	  throw new BadCredentialsException("login_unRegistered_error");
          }
          else if(!user.getUserState().equals(Status.BLOCKED)){
        	  if(sourceleadPasswordEncoder.matches(password, user.getPassword())){
        		  List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole()); 
        		  try {
					userLoginAttempts = userLoginAttemptsService.getUserLoginAttemptsByUserId(user.getId());
					userLoginAttemptsService.deleteUserLoginAttempts(userLoginAttempts);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		  
        		  return new UsernamePasswordAuthenticationToken(username, password, authorities);
        	  }
        	  else{
				try {
					userLoginAttempts = userLoginAttemptsService.getUserLoginAttemptsByUserId(user.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(userLoginAttempts == null){
					userLoginAttempts = getUserLoginAttemptsDetails(user);
				}else{
					Integer attemptsCount = userLoginAttempts.getAttemptsCount()+1;
					userLoginAttempts.setAttemptsCount(attemptsCount);
				}
				try {
					userLoginAttemptsService.addUserLoginAttempts(userLoginAttempts);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(userLoginAttempts.getAttemptsCount() == 3){
					 try {
						user = userLoginAttempts.getUser();
						user.setUserState(Status.BLOCKED);
						userService.addUser(user);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(!user.getUserState().equals(Status.BLOCKED)){
					throw new BadCredentialsException("login_password_error");
				}else{
					throw new BadCredentialsException("login_blocked_user_error");
				}
        		  
        	  }
          }else{
        	  throw new BadCredentialsException("login_blocked_user_error");
          }
    }
 
    public boolean supports(Class<?> arg0) {
        return true;
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
       
    	if(userRoles != null && !userRoles.isEmpty()){
    		// Build user's authorities
    		for (UserRole userRole : userRoles) {
    			setAuths.add(new SimpleGrantedAuthority(userRole.getRoles().getRoleName()));
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
