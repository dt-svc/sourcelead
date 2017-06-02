package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.service.ExpensesCategoryService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@PropertySource("${propertyLocation:app.properties}")
public class ExpensesCategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExpensesCategoryController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	private ExpensesCategoryService ExpensesCategoryService;
	
	
	
	protected boolean isNullOrEmpty(String str) {
		return str == null || "".equals(str.trim());
	}
	
	
	@RequestMapping(value = "/expenseCategory")
 	public ModelAndView expenseCategory(HttpServletRequest request) {
		
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    
		    ModelAndView model = new ModelAndView();
		    commonUtil.highLightMenu(Constants.OrgSettings, request);
 			//model.setViewName("expenses/expenseCategory");
 			model.setViewName("expenses/expenseCategory");
 			model.addObject("sourceleadUrl", sourceleadUrl);
 			return model;
 	}
	
	
	
}
