package com.bis.sourcelead.payroll.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/app")
public class USPayrollReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollReportRestController.class);
	
	/**
	 * Returning Us Payroll Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/usPayrollReport")
	public ModelAndView usPayrollReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/usPayrollReport");
		return model;
	}
	
	/**
	 * Returning US Contribution Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/contributionReport")
	public ModelAndView contributionReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/contributionReport");
		return model;
	}
	
	/**
	 * Returning US Deduction Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deductionReport")
	public ModelAndView deductionReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/deductionReport");
		return model;
	}
	
	/**
	 * Returning US Payroll Register Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollRegisterReport")
	public ModelAndView payrollRegisterReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollRegisterReport");
		return model;
	}
	
	/**
	 * Returning US Payroll Comparison Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollComparison")
	public ModelAndView payrollComparisonJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollRegisterReport");
		return model;
	}
	
	/**
	 * Returning US Payroll Direct Deposit Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/directDepositReport")
	public ModelAndView directDepositReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/directDepositReport");
		return model;
	}
	/**
	 * Returning US Payroll List Summary Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/payrollListSummaryReport")
	public ModelAndView payrollListSummaryReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/payrollListSummaryReport");
		return model;
	}
	/**
	 * Returning US Employee Payroll Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/employeePayrollReport")
	public ModelAndView employeePayrollReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/employeePayrollReport");
		return model;
	}
	
	/**
	 * Returning US Check Register Report Jsp
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkRegisterReport")
	public ModelAndView checkRegisterReportJsp(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("payroll/checkRegisterReport");
		return model;
	}
}
