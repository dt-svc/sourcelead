package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.CommonUtil;

@Controller
@RequestMapping("/app")
public class ResourcesController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	 @PreAuthorize("hasAnyRole('ROLE_ADD_BLOG','ROLE_PUBLISH_BLOG','ROLE_VIEW_EDIT_BLOG','ROLE_DELETE_BLOG')")
	 @RequestMapping(value = {"/blogs", "/rest/blogs"}, method = RequestMethod.GET)
		public ModelAndView blogs(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.Blogs, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/blogs");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
	 
	 @PreAuthorize("hasAnyRole('ROLE_ADD_USER_MANUAL','ROLE_PUBLISH_USER_MANUAL','ROLE_VIEW_EDIT_USER_MANUAL','ROLE_DELETE_USER_MANUAL')")
	 @RequestMapping(value = {"/userManuals", "/rest/userManuals"}, method = RequestMethod.GET)
		public ModelAndView userManuals(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.UserManuals, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("resources/userManuals");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }
}
