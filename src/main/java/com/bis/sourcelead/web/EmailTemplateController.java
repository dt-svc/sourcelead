package com.bis.sourcelead.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.ClassField;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.EntityType;
import com.bis.sourcelead.service.ClassFieldService;
import com.bis.sourcelead.service.EmailScenarioService;

@Controller
@RequestMapping("/app")
public class EmailTemplateController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private ClassFieldService classFieldService;
	
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//@PreAuthorize("hasAnyRole('ROLE_CREATE_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_VIEW_INTEVIEW_FEEDBACK_TEMPLATE','ROLE_EDIT_INTEVIEW_FEEDBACK_TEMPLATE')")
	@RequestMapping(value = "/emailTemplates")
	public ModelAndView emailTemplates(HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/emailTemplates");
		return model;

	}
	
	@RequestMapping(value = "/editEmailTemplate")
	public ModelAndView editEmailTemplate(@RequestParam(value="templateUUID",required = false) String templateUUID,HttpServletRequest request) throws Exception {
		
		EmailScenario emailScenario=emailScenarioService.getEmailTemplateByUUID(templateUUID);
		List<String> classFieldList=null;
		ModelAndView model = new ModelAndView();
		if(emailScenario != null){
			if(emailScenario.getParentId() != null){
				classFieldList=classFieldService.getClassFieldsByEmailScenerioId(emailScenario.getParentId().getId());
			}else{
				classFieldList=classFieldService.getClassFieldsByEmailScenerioId(emailScenario.getId());
			}
			 
			
		}
		
		model.addObject("emailScenario",emailScenario);
		model.setViewName("/editEmailTemplates");
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.addObject("classFieldList", classFieldList);
		if(emailScenario == null){
			model.setViewName("/errorPage");
		}
		return model;

	}

	

}
