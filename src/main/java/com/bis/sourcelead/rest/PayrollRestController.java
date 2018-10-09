package com.bis.sourcelead.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.payroll.service.IndPayrollService;
import com.bis.sourcelead.payroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.INDPayrollCommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.IndianPayrollController;

/**
 * 
 * Handles requests for the Requirement service.
 * 
 * @author sarath kumar punnana
 * @date 14th Dec 2016
 *
 */

@RestController
@RequestMapping("/app")
public class PayrollRestController {

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	PayrollService payrollService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	INDPayrollCommonUtil iNDPayrollCommonUtil;
	
	@Autowired
	IndPayrollService indPayrollService;
	
	@Autowired
	AssociatePayrollPayheadHistoryService associatePayrollPayheadHistoryService;
	
	@Autowired
	IndPaysheetFrequencyService indPaysheetFrequencyService;
	
	@Autowired
	ConfigurePayrollApproverService configurePayrollApproverService;
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get payment details by id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	private static final Logger logger = LoggerFactory.getLogger(IndianPayrollController.class);
	
	@RequestMapping(value = "/getPayrollById", method = RequestMethod.POST)
	public @ResponseBody String getPayrollById(Long payrollId) throws Exception {
		String resultJson;

			Payroll payroll = payrollService.getPayrollById(payrollId);
			resultJson = commonUtil.toJSON(payroll);
		    return resultJson;
		    
	}
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method returns payments list
	 * 
	 * @return JSON  : this method returns json objects.
	 *  
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllPayrolls", method = RequestMethod.POST)
	public @ResponseBody String getAllPayrolls() throws Exception {
			String resultJson;
			List<Payroll> payrolls  = payrollService.getAllPayroll();
			resultJson = commonUtil.toJSON(payrolls);
			 return resultJson;
	    
	}
	
	@RequestMapping(value = { "/employeePayrollList","/rest/employeePayrollList" })
	public ModelAndView reviewPayrollInd(IndPaysheetFrequencyDTO indPaysheetFrequencyDTO, HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
	 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	
	    Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID());
	    List<INDPayrollDTO> employeePayrollDTOList = null;
	    if(sessionObj != null) {
	    	indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
	    	employeePayrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO();
	    }else {
	    	Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indPaysheetFrequencyDTO.getPaySheetFrequencyUUID(), orgLocation);
	    	if(freqResponse != null) {
	    		indPaysheetFrequencyDTO = freqResponse.getObject();
	    		employeePayrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO();
	    	}
	    }
	    model.addObject("freqResponse", indPaysheetFrequencyDTO);
		model.addObject("employeePayrollList", employeePayrollDTOList);
		Boolean isPayrollApprover = configurePayrollApproverService.isLoginUserIsPayRollApprover(loginUser.getId(), orgLocation.getId());
		model.addObject("isPayrollApprover", isPayrollApprover);
		model.setViewName("payroll_india/payroll/employeePayrollList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	

	@RequestMapping(value = { "/runPayrollInd","/rest/runPayrollInd" })
	public ModelAndView runPayrollInd(HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
		model.setViewName("payroll_india/payroll/runPayrollInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = { "/calculateAssociateSalaryInd","/rest/calculateAssociateSalaryInd" })
	public ModelAndView calculateAssociateSalaryInd(AssociatePayrollPayheadHistoryDTO associatePayrollPayheadHistoryDTO, HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
		ResponseList<AssociatePayrollPayheadHistoryDTO> associatePayrollPayhead = associatePayrollPayheadHistoryService.getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID());
		
		List<AssociatePayrollPayheadHistoryDTO> earningList = associatePayrollPayhead.getData().stream().filter(app -> app.getPayrollPayheadType().equals("Earnings")).collect(Collectors.toList());
		model.addObject("earningPayhead", earningList);
		List<AssociatePayrollPayheadHistoryDTO> decuctionList =  associatePayrollPayhead.getData().stream().filter(app -> app.getPayrollPayheadType().equals("Deduction")).collect(Collectors.toList());
		model.addObject("decuctionPayhead", decuctionList);
		
		Response<IndEmployeePayroll> indEmployeePayroll = indPaysheetFrequencyService.getIndEmployeePayrollByEmpPayrollUUID(associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID());
		model.addObject("indEmployeePayroll", indEmployeePayroll.getObject());
		
		ResponseList<IndEmployeePayroll> indEmployeePayrollResponse = indPaysheetFrequencyService.getIndEmployeePayrollListByPaysheetFrequencyUuid(associatePayrollPayheadHistoryDTO.getPaySheetFrequencyUUID());
		model.addObject("indEmployeePayrollList", indEmployeePayrollResponse.getData());
		
		model.addObject("empPayrollUUID", associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID());
		
		model.setViewName("payroll_india/payroll/associatePayrollSummaryInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	@RequestMapping(value = { "/getPaySheetFrequencyDataInd", "/rest/getPaySheetFrequencyDataInd" })
	@ResponseBody
	public ResponseList<IndPaysheetFrequencyDTO> getPaySheetFrequencyList(IndPaysheetFrequencyDTO indpaysheetFrequencyDTO, @RequestParam(value = "paySheetFrequencyUUID", required = false) String searchValue, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		return indPayrollService.getPayPeriodListByOrgLocationId(orgLocation, indpaysheetFrequencyDTO);
	}
	
	
	
	@RequestMapping(value = { "/getMyPays", "/rest/getMyPays" })
	@ResponseBody
	public ModelAndView getMyPays(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.setViewName("payroll_india/payroll/myPaysInd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/getMyPaysListInd", "/rest/getMyPaysListInd" })
	@ResponseBody
	public ResponseList<IndEmployeePayrollDTO> getMyPaysListInd(IndEmployeePayrollDTO indEmployeePayrollDTO, @RequestParam(value = "searchValue", required = false) String searchValue, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ResponseList<IndEmployeePayrollDTO> responseList = new ResponseList<IndEmployeePayrollDTO>();
		try {
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			responseList =  indPaysheetFrequencyService.getEmployeePaysByOrgLocationIdAndUserId(orgLocation, loginUser, indEmployeePayrollDTO);
			
			responseList.setResponse(Result.SUCCESS.name());
			responseList.setResponseMessage(Result.SUCCESS.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	
	@RequestMapping(value = { "/getMyForms", "/rest/getMyForms" })
	@ResponseBody
	public ModelAndView getMyForms(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.setViewName("payroll_india/payroll/myFormsInd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/getMyLoansAdv", "/rest/getMyLoansAdv" })
	@ResponseBody
	public ModelAndView getMyLoansAdv(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.setViewName("payroll_india/payroll/myLoansAndAdvances");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}

}
