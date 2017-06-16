package com.bis.sourcelead.payroll.rest;

import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class EmployeePayrollRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeePayrollRestController.class);
	
	@Autowired
	EmployerPayrollCostService employerPayrollCostService;
	
	@RequestMapping(value = "/uploadPayrollDocument", method = RequestMethod.POST)
	@ResponseBody 
	public String fileUploaded(@RequestParam(value = "json", required = false) String json,MultipartHttpServletRequest request,HttpServletResponse response) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);
		MultipartFile multipartFile = null;
		Iterator<String> itrator = request.getFileNames();
		if(itrator.hasNext()){
			multipartFile = request.getFile(itrator.next());
		}		
		String isInserted = employerPayrollCostService.saveEmployerPayrollCost(multipartFile,orgLocation,user,jsonObj);
			
		return "success";
	}
	
}
