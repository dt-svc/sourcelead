package com.bis.sourcelead.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.UserLoginLogoutSession;
import com.bis.sourcelead.service.ActionHistoryService;

@Controller
public class ActionHistoryController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	
	@RequestMapping(value = "/getActionHistoryList")
	public ModelAndView getActionHistoryList(UserLoginLogoutSession userLoginLogoutSession) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("userLoginLogoutSessionId", userLoginLogoutSession.getId());
		model.setViewName("/actionHistory");
		return model;
	}
	

}
