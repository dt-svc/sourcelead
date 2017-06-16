package com.bis.sourcelead.payroll.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.File;
import com.bis.sourcelead.payroll.entity.PayrollFileValidator;
import com.bis.sourcelead.payroll.service.EmployerPayrollCostService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
/**
 * 
 * @author Rajashekar
 *
 */
@Controller
public class PayrollController {

	@Autowired
	PayrollFileValidator validator;

	@Autowired
	private EmployerPayrollCostService employerPayrollCostService;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = "/payrollForm")
	public String getUploadForm(Model model) {
		File fileModel = new File();
		model.addAttribute("file", fileModel);
		return "payroll/uploadPayroll";
	}

	@RequestMapping(value = "/uploadPayrollForm", method = RequestMethod.POST)
	public String fileUploaded(HttpServletRequest request,HttpServletResponse response,Model model, @Validated File file, BindingResult result) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String returnVal = "payroll/uploadPayroll";
		if (result.hasErrors()) {
			returnVal = "payroll/uploadPayroll";
		} else {
			MultipartFile multipartFile = file.getFile();
			String isInserted = employerPayrollCostService.saveEmployerPayrollCost(multipartFile,orgLocation,user);
			
		}
		return returnVal;
	}
	
	@RequestMapping(value = "/payrollList")
	public ModelAndView getPayrollList(Model model) {
		   ModelAndView m = new ModelAndView();
	  	   m.setViewName("payroll/payrollList");
	       return m;
	}
}
