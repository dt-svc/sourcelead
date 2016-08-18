package com.bis.sourcelead.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class RegistrationController {
	
	 
	 @Value("${sourceLeadBlockedSocialDomains}")
	 private String sourceLeadBlockedSocialDomains;
	 
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @RequestMapping(value = "/enrolledOrgsList")
		public ModelAndView enrolledOrgsList(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
		    
			ModelAndView model = new ModelAndView();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Collection<GrantedAuthority> authorities =  (Collection<GrantedAuthority>) auth.getAuthorities();
			GrantedAuthority authority= new SimpleGrantedAuthority("SOURCELEAD_ADMIN");
			if(authorities.contains(authority)){
				
				String msg = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCreateMsg");
				if(!isNullOrEmpty(msg)){
					model.addObject("msg", msg);
					SourceLeadSessionManager.removeSessionAttribute(request, "orgCreateMsg");
				}
				model.setViewName("registration/enrolledOrgsList");
				model.addObject("sourceleadUrl", sourceleadUrl);
			}else{
				model.setViewName("registration/persmissionDenied");
			}
			
			
			return model;

	 }
	 
	 
	 @RequestMapping(value = "/enrollOrg")
		public ModelAndView registration() {
			ModelAndView model = new ModelAndView();
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Collection<GrantedAuthority> authorities =  (Collection<GrantedAuthority>) auth.getAuthorities();
			GrantedAuthority authority= new SimpleGrantedAuthority("SOURCELEAD_ADMIN");
			if(authorities.contains(authority)){
				model.setViewName("registration/enrollOrg");

				model.addObject("sourceleadUrl", sourceleadUrl);
				model.addObject("sourceLeadBlockedSocialDomains", sourceLeadBlockedSocialDomains);
			}else{
				model.setViewName("registration/persmissionDenied");
			}
			
			return model;

		}
	 /**	 
	  * checks if string is null or empty
	  * @param str
	  * @return boolean
	  */
	   protected boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
	
	
	}