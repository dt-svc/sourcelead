package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTaxSubmissionMasterDTO;
import com.bis.sourcelead.dto.helper.Paginator;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.TaxService;
import com.bis.sourcelead.service.UserGroupService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Controller
@RequestMapping("/app")
public class IndianPayrollController {
	private static final Logger logger = LoggerFactory.getLogger(IndianPayrollController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Autowired
	private TaxService taxService;
	
	@Autowired
	UserGroupService userGroupService;
	
	@Autowired
	private CommonUtil commonUtil;

	@RequestMapping(value = "/myPayrollInd")
	public ModelAndView paySlips(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.setViewName("payroll_india/payroll/myPayrollInd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	/*@RequestMapping(value = "/forms")
	public ModelAndView forms(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			TaxAdminController.setYears(modelAndView, null);
			modelAndView.addObject("activeTaxTab", "forms");
			modelAndView.setViewName("payroll_india/payroll/forms");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping(value = "/loansAndAdvances")
	public ModelAndView loansAndAdvances(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("activeTaxTab", "loansAndAdvances");
			modelAndView.setViewName("payroll_india/payroll/loansAndAdvances");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}*/

	@RequestMapping(value = "/forms/all", method = RequestMethod.POST)
	@ResponseBody
	public ResponseList<SourceleadTaxSubmissionMasterDTO> getCentralTaxes(HttpServletRequest request,
			@RequestParam(defaultValue = "") String financialYear, Paginator paginator) {
		ResponseList<SourceleadTaxSubmissionMasterDTO> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				List<SourceleadTaxSubmissionMasterDTO> taxSubmissionMasterList = taxService.getTaxSubmissionMaster(user.getId(), orgLocations.getDateFormat(), financialYear, paginator);
				response.setData(taxSubmissionMasterList);
				response.setPaginator(paginator);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			e.printStackTrace();
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	@RequestMapping(value = "/payrollListSummary")
	public ModelAndView payrollListSummary(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
			modelAndView.setViewName("payroll_india/org/payrollListSummary");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/runPayroll")
	public ModelAndView runPayroll(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<UserGroup> userGroupList = userGroupService.getAllUserGroupsByLocationId(orgLocations);
			modelAndView.addObject("userGroupList",userGroupList);
			modelAndView.setViewName("payroll_india/org/runPayroll");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

	@RequestMapping (value = "/runIndianPayRoll")
	public ModelAndView indianRunPayRoll(@RequestParam(defaultValue = "") String stab,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
		modelAndView.addObject("stab",stab);
		try {
			modelAndView.setViewName("payroll_india/payroll/runPayrollInd");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
}
