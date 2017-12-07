package com.bis.sourcelead.web;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.util.CommonUtil;
 

@Controller 
@RequestMapping("/app")
public class SocialInviterController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public CommonUtil commonUtil;

	
	/**
	 * @author naresh bolisetty 
	 * @since  march 28 2017
	 * @version 0.0.1
	 * 
	 * this  controller method is for timesheet screen redirection.
	 * 
	 * @param request
	 *  
	 * @return TimesheetEntries 
	 * 
	 * @throws NullPointer
	 */
	
	@RequestMapping(value = "/socialinviter")
	public ModelAndView socialinviter(HttpServletRequest request) {
			ModelAndView model = new ModelAndView();
			commonUtil.highLightMenu(Constants.SocialInviter, request);
			
			model.addObject("sourceleadUrl", sourceleadUrl);
			model.setViewName("si/socialinviter");
			return model;

	}
	
	
	
	
	public static boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
 
}