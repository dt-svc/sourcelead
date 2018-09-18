package com.bis.sourcelead.payroll.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RestrictDay;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class USPayrollReportRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(USPayrollReportRestController.class);
	
	@Autowired
	UserGroupService userGroupService;
	
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
		model.setViewName("payroll/payrollComparison");
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
	
	/**
	 * Pay Groups by org location Id
	 * @param request
	 * @param response
	 * @return List<UserGroup> 
	 * @throws Exception
	 */
	@RequestMapping(value = { "/getPayGroupList","/rest/getPayGroupList" }, method = RequestMethod.GET)
	@ResponseBody
	public List<UserGroup> findAllDeduction(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return userGroupService.getAllUserGroupsByLocationId(orgLocation);
	}
	
	@RequestMapping(value = { "/getPayPeriodByGroupId","/rest/getPayPeriodByGroupId" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseList<PayPeriodDTO> getPayPeriodByGroupId(@RequestParam(value = "groupId",required = false) Long groupId,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResponseList<PayPeriodDTO> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) { 
				List<PayPeriodDTO> PayPeriodDTOList = userGroupService.getPayPeriodsByGroupId(groupId);
				responseList.setData(PayPeriodDTOList);
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			}else{
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return responseList;
	}
}
