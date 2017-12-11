package com.bis.sourcelead.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.service.ActionHistoryService;

@Controller
@RequestMapping("/app")
public class ActionHistoryController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	
	@RequestMapping(value = "/getActionHistoryList/{actionUUID}" , method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getActionHistoryList(@PathVariable String actionUUID,HttpServletRequest request) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.addObject("actionUUID", actionUUID);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/actionHistory");
		return model;
	}
	

}
