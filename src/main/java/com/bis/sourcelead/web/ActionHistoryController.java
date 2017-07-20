package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.ActionHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ActionHistoryService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
public class ActionHistoryController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	ActionHistoryService actionHistoryService;
	
	
	@RequestMapping(value = "/getActionHistoryList")
	public ModelAndView getActionHistoryList(@RequestParam(value = "userId", required = true) Long userId, HttpServletRequest request) throws Exception
	{
		
		ModelAndView model = new ModelAndView();
		model.addObject("userId", userId);
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/actionHistory");
		return model;
	}
	

}
