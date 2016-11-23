package com.bis.sourcelead.web;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.AmexUploadFile;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UploadedFile;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.UploadFileService;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.validation.FileValidator;

@Controller
public class BackOfficeController {
	
private static final Logger logger = LoggerFactory.getLogger(BackOfficeController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	FileValidator fileValidator;

	@Autowired
	UploadFileService uploadFileService;
	
	
	
	@RequestMapping(value = "/finance")
	public ModelAndView finance(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/finance");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping(value = "/transactionsImports")
	public ModelAndView transactionsImports(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/transactionsImports");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Finance);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}
	
	
	@RequestMapping("/saveImports")
	public String saveImports(@ModelAttribute("amexUploadFile") AmexUploadFile amexUploadFile, BindingResult result,
			Model model, HttpServletRequest request) {
		
		
		if(amexUploadFile.getFile() != null){
			fileValidator.validateTransFile(amexUploadFile, result);
		}else{
			return "backOffice/transactionsImports";
		}
		if (result.hasErrors()) {
			return "backOffice/transactionsImports";
		}
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		
		try {
			String isFileCreated = uploadFileService.uploadAmexFile(amexUploadFile.getFile(),user,amexUploadFile);
			if ("FailureTransactionsCheck.xlsx".equalsIgnoreCase(isFileCreated)) {
				model.addAttribute("errorMessage", "Please download Error file here");
                model.addAttribute("fileName", "xlsx");
                 return "backOffice/transactionsImports";
			}else if("FailureTransactionsCheck.xls".equalsIgnoreCase(isFileCreated)){
				model.addAttribute("errorMessage", "Please download Error file here");
                model.addAttribute("fileName", "xls");
                return "backOffice/transactionsImports";
			}
		} catch (Exception e) {
			logger.error("Exception cause is : "+ExceptionUtils.getFullStackTrace(e));	
		}
		model.addAttribute("msg", "successfully uploaded");
		model.addAttribute("fileName", "");
		return "redirect:/backOffice/finance";
	}
	
	
	@RequestMapping(value = "/assetManagement")
	public ModelAndView assetManagement(HttpServletRequest request) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName("backOffice/assetManagement");
		model.addObject("sourceleadUrl", sourceleadUrl);
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.AssetManagement);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		return model;
		
	}

}
