package com.bis.sourcelead;

import java.util.ArrayList;
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
import org.springframework.web.filter.GenericFilterBean;

import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
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
  
    /*@param
     * 
     * Overriding AuthenticationProvider authenticate(-) method to handle NullPointer Exception for unregistered users
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
          String username = authentication.getName();
          String password = (String) authentication.getCredentials();
     
          User  user = userService.findByUserName(username);
          if(user == null){
        	  throw new BadCredentialsException("login_unRegistered_error");
          }
          else{
        	  if(sourceleadPasswordEncoder.matches(password, user.getPassword())){
        		  List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole()); 
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



	


}
