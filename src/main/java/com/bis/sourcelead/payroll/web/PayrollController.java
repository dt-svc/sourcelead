package com.bis.sourcelead.payroll.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

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

	
	
	@RequestMapping(value = "/payrollList")
	public ModelAndView getPayrollList(HttpServletRequest request,HttpServletResponse response) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("payroll/payrollList");
	       return model;
	}
}
