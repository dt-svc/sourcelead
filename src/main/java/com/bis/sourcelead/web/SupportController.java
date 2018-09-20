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
public class SupportController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	
	 @PreAuthorize("hasAnyRole('ROLE_ADD_SUPPORT_TICKET','ROLE_VIEW_EDIT_SUPPORT_TICKET','ROLE_DELETE_SUPPORT_TICKET')")
	 @RequestMapping(value = {"/supportManagement", "/rest/supportManagement"}, method = RequestMethod.GET)
		public ModelAndView supportManagement(HttpServletRequest request) {
		 
		    commonUtil.highLightMenu(Constants.Support, request);
		    
			ModelAndView model = new ModelAndView();
			model.setViewName("support/supportManagement");
			model.addObject("sourceleadUrl", sourceleadUrl);
			
			return model;

	 }

}
