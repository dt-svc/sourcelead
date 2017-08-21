package com.bis.sourcelead.payroll.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.BisUserMapping;
import com.bis.sourcelead.payroll.entity.File;
import com.bis.sourcelead.payroll.entity.PayrollFileValidator;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.service.BisUserMappingService;
import com.bis.sourcelead.payroll.service.WageTaxSummaryService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SessionMaintainanceData;
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
	private UserService userService;
	
	@Autowired
	private BisUserMappingService bisUserMappingService;
	
	@Autowired
	WageTaxSummaryService wageTaxSummaryService;

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
		   
		   SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PAYROLL);
			request.getSession().setAttribute("sessionData",sessionData);
			
	       return model;
	}
	
	/**
	 * returns user mapping jsp for payroll
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception 
	 */
	@RequestMapping(value = "/userMapping")
	public ModelAndView userMapping(HttpServletRequest request,HttpServletResponse response) throws Exception {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		   List<User> usersList = userService.getAllUsersByOrgLocationCode(orgLocations.getId());
		   usersList.sort((User report1, User report2)-> report1.getFirstName().compareToIgnoreCase(report2.getFirstName()));
		   List<BisUserMapping> masterUserList = bisUserMappingService.getBisUserListByOrgLocationId(orgLocations.getId());
		 //lambda here!
		   masterUserList.sort((BisUserMapping report1, BisUserMapping report2)-> report1.getCandidateName().compareToIgnoreCase(report2.getCandidateName()));
			
		   model.addObject("usersList", usersList);
		   model.addObject("masterUsersList", masterUserList);
		   model.setViewName("payroll/userMapping");
	       return model;
	}
	
	/**
	 * @author Pavan Jampana
	 * used to redirect to user pay screen
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/userPay")
	public ModelAndView getUserPayroll(HttpServletRequest request) {
		   ModelAndView model = new ModelAndView();
		   model.addObject("sourceleadUrl", sourceleadUrl);
		   model.setViewName("payroll/userPayroll");
		   
		   SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
			if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PAY);
			request.getSession().setAttribute("sessionData",sessionData);
			
	       return model;
	}
	
	 @RequestMapping(value = "/payrollReport")
		public ModelAndView payrollReport(HttpServletRequest request) {
		 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		 ModelAndView model = new ModelAndView();	
		 if(sessionData == null)
			{
				sessionData = new SessionMaintainanceData();
			}
			sessionData.setNav(Constants.PAYROLL);
			
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			List<WageTaxSummary> wageTaxSummaryList =  wageTaxSummaryService.getWagesAndTaxListByOrgLocationId(orgLocation);
			if(wageTaxSummaryList !=null){
				model.addObject("wageTaxSummaryList", wageTaxSummaryList);
			}
			request.getSession().setAttribute("sessionData",sessionData);
			
			model.setViewName("payroll/payrollReport");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;

		}

}
