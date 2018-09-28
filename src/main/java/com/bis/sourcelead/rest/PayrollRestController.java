package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.payroll.service.IndPayrollService;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	IndPayrollService indPayrollService;
	
	@Autowired
	AssociatePayrollPayheadHistoryService associatePayrollPayheadHistoryService;
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get payment details by id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	
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
	
	@RequestMapping(value = { "/reviewPayrollInd","/rest/reviewPayrollInd" })
	public ModelAndView reviewPayrollInd(HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		model.setViewName("payroll_india/payroll/reviewPayrollInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	

	@RequestMapping(value = { "/runPayrollInd","/rest/runPayrollInd" })
	public ModelAndView runPayrollInd(HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		model.setViewName("payroll_india/payroll/runPayrollInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@RequestMapping(value = { "/calculateAssociateSalaryInd","/rest/calculateAssociateSalaryInd" })
	public ModelAndView calculateAssociateSalaryInd(AssociatePayrollPayheadHistoryDTO associatePayrollPayheadHistoryDTO, HttpServletRequest request) throws Exception
	{
	 	ModelAndView model = new ModelAndView();
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		ResponseList<AssociatePayrollPayheadHistoryDTO> associatePayrollPayhead = associatePayrollPayheadHistoryService.getUpdatedAssociatePayrollPayheadByEmpPayrollId(associatePayrollPayheadHistoryDTO.getIndEmployeePayrollId());
		model.addObject("associatePayrollPayhead", associatePayrollPayhead.getData());
		
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

}
