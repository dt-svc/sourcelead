package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.PayrollPaySlipIndDTO;
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.PayrollPaySlipInd;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.indPayroll.dto.INDPayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.service.AssociatePayrollPayheadHistoryService;
import com.bis.sourcelead.indPayroll.service.IndPayrollService;
import com.bis.sourcelead.indPayroll.service.IndPaysheetFrequencyService;
import com.bis.sourcelead.indPayroll.utill.INDPayrollCommonUtil;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.service.ConfigurePayrollApproverService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesHistoryService;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesService;
import com.bis.sourcelead.service.PayrollPaySlipIndService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.dynamicReport.IndPaySlipPdf;

/**
 * 
 * Handles requests for the Requirement service.
 * 
 * @author sarath kumar punnana
 * @date 14th Dec 2016
 *
 */

@RestController
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
	
	@Autowired
	AssociateLoanAndAdvancesService associateLoanAndAdvancesService;
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Autowired
	AssociateLoanAndAdvancesHistoryService associateLoanAndAdvancesHistoryService;
	
	@Autowired
	PayrollPaySlipIndService payrollPaySlipIndService;
	
	@Autowired
	IndPaySlipPdf indPaySlipPdf;
	
	/**
	 * @author sarath kumar punnana and Ajith Matta
	 * 
	 * @description this method is for get payment details by id
	 * 
	 * @return JSON  : this method returns json objects
	 *  
	 * @throws Exception
	 */
	private static final Logger logger = LoggerFactory.getLogger(PayrollRestController.class);
	
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
	 	try {
	 		Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID());
		    List<INDPayrollDTO> employeePayrollDTOList = null;
		    if(sessionObj != null) {
		    	indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
		    	employeePayrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO();
		    }else {
		    	Response<IndPaysheetFrequencyDTO> freqResponse = indPaysheetFrequencyService.getIndPaysheetFrequencyByEmpPayrollUUID(indPaysheetFrequencyDTO.getPaySheetFrequencyUUID(), orgLocation);
		    	if(freqResponse != null && freqResponse.getObject() != null) {
		    		indPaysheetFrequencyDTO = freqResponse.getObject();
		    		employeePayrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO();
		    	}else {
		    		model.setViewName("payroll_india/payroll/runPayrollInd");
		    		model.addObject("sourceleadUrl", sourceleadUrl);
		    		return model;
		    	}
		    }
		    model.addObject("freqResponse", indPaysheetFrequencyDTO);
			model.addObject("employeePayrollList", employeePayrollDTOList);
			Boolean isPayrollApprover = configurePayrollApproverService.isLoginUserIsPayRollApprover(loginUser.getId(), orgLocation.getId());
			model.addObject("isPayrollApprover", isPayrollApprover);
			model.setViewName("payroll_india/payroll/employeePayrollList");
			model.addObject("sourceleadUrl", sourceleadUrl);
	 	}catch (Exception e) {
	 		model.setViewName("payroll_india/payroll/runPayrollInd");
    		model.addObject("sourceleadUrl", sourceleadUrl);
    		e.printStackTrace();
			logger.error(e.getMessage());
	 	}
	    
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
	 	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = null;
	 	Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, associatePayrollPayheadHistoryDTO.getPaySheetFrequencyUUID());
	 	INDPayrollDTO indPayrollDTO = null;
	    if(sessionObj != null) {
	    	IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
	    	List<INDPayrollDTO> payrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO().stream().filter(employeePayrollDTO ->employeePayrollDTO.getIndEmpPayrollUUID().equalsIgnoreCase(associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID())).collect(Collectors.toList());
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	if(payrollDTOList != null && !payrollDTOList.isEmpty()) {
	    	    indPayrollDTO = payrollDTOList.iterator().next();
	    	    List<AssociatePayrollPayheadHistoryDTO> dupAssociatePayrollPayheadDTOList = indPayrollDTO.getPayHeadHistoryDTOList();
	    		associatePayrollPayheadDTOList = indPayrollDTO.getPayHeadHistoryDTOList().stream().filter(ml -> (dupAssociatePayrollPayheadDTOList.stream().filter(sl -> {
					try {
						return sl.getPayrollPayhead().equals(ml.getPayrollPayhead()) && df.parse(sl.getUpdatedDate()).after(df.parse(ml.getUpdatedDate()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return false;
				}).count())<1).collect(Collectors.toList());
	    		
	    		
	    	}
	   	}else {
	   		Response<IndEmployeePayroll> indEmployeePayrollResponse = indPaysheetFrequencyService.getIndEmployeePayrollByEmpPayrollUUID(associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID());
	   		IndEmployeePayroll	indEmployeePayroll = indEmployeePayrollResponse.getObject();
	   		HashSet<IndEmployeePayroll> payrollList = new HashSet<>();
	   		if(indEmployeePayroll != null) {
	   			payrollList.add(indEmployeePayroll);
	   		}
	   		indPayrollDTO = iNDPayrollCommonUtil.getINDPayrollDTOFromIndEmployeePayroll(payrollList, orgLocation).iterator().next();
	   	    associatePayrollPayheadDTOList = indPayrollDTO.getPayHeadHistoryDTOList();
	   	}
		 
		if(associatePayrollPayheadDTOList != null && !associatePayrollPayheadDTOList.isEmpty()) {
			List<AssociatePayrollPayheadHistoryDTO> earningList = associatePayrollPayheadDTOList.stream().filter(app -> (app.getPayrollPayheadType().equals("Earnings") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0))).collect(Collectors.toList());
			model.addObject("earningPayhead", earningList);
			List<AssociatePayrollPayheadHistoryDTO> decuctionList =  associatePayrollPayheadDTOList.stream().filter(app -> app.getPayrollPayheadType().equals("Deduction") && (app.getMonthly()!=null && app.getMonthly().compareTo(BigDecimal.ZERO) > 0)).collect(Collectors.toList());
			model.addObject("decuctionPayhead", decuctionList);
		}
		
		
		model.addObject("indPayrollDTO", indPayrollDTO);
		
		/*ResponseList<IndEmployeePayroll> indEmployeePayrollResponse = indPaysheetFrequencyService.getIndEmployeePayrollListByPaysheetFrequencyUuid(associatePayrollPayheadHistoryDTO.getPaySheetFrequencyUUID());
		model.addObject("indEmployeePayrollList", indEmployeePayrollResponse.getData());*/
		
		model.addObject("empPayrollUUID", associatePayrollPayheadHistoryDTO.getIndEmpPayrollUUID());
		
		model.setViewName("payroll_india/payroll/associatePayrollSummaryInd");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
	}
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/saveAssociatePayheadAdjustInd","/rest/saveAssociatePayheadAdjustInd" })
	public Response<String> saveAssociatePayheadAdjustInd(IndEmployeePayrollDTO indEmployeePayrollDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if(loginUser != null){
			 	List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = null;
			 	Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indEmployeePayrollDTO.getPaySheetFrequencyUUID());
			 	INDPayrollDTO indPayrollDTO = null;
			 	if(sessionObj != null) {
				    	IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
				    	List<INDPayrollDTO> payrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO().stream().filter(employeePayrollDTO ->employeePayrollDTO.getIndEmpPayrollUUID().equalsIgnoreCase(indEmployeePayrollDTO.getIndEmpPayrollUUID())).collect(Collectors.toList());
				    	if(payrollDTOList != null && !payrollDTOList.isEmpty()) {
				    	    indPayrollDTO = payrollDTOList.iterator().next();
				    		associatePayrollPayheadDTOList = iNDPayrollCommonUtil.associatePayrollPayheadAdjustIndSessionList(indPayrollDTO.getPayHeadHistoryDTOList(), indPayrollDTO, indEmployeePayrollDTO, loginUser, orgLocation);
				    		indPayrollDTO.setPayHeadHistoryDTOList(associatePayrollPayheadDTOList);
				    		indPayrollDTO.setGrossPay(indEmployeePayrollDTO.getGrossPay());
				    		indPayrollDTO.setNetPay(indEmployeePayrollDTO.getNetPay());
				    		indPayrollDTO.setUpdatedBy(loginUser.getId());
				    		indPayrollDTO.setUpdatedDate(new Date());
				    	}
			   	}else {
				   		IndEmployeePayroll indEmployeePayroll = indPaysheetFrequencyService.getIndEmployeePayrollByEmpPayrollUUID(indEmployeePayrollDTO.getIndEmpPayrollUUID()).getObject();
				   		Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory = iNDPayrollCommonUtil.associatePayrollPayheadAdjustIndList(indEmployeePayroll, indEmployeePayrollDTO, loginUser);
				   		indEmployeePayroll.setAssociatePayrollPayheadHistory(associatePayrollPayheadHistory);
				   		indEmployeePayroll.setGrossPay(indEmployeePayrollDTO.getGrossPay());
				   		indEmployeePayroll.setNetPay(indEmployeePayrollDTO.getNetPay());
				   		indEmployeePayroll.setUpdatedBy(loginUser.getId());
				   		indEmployeePayroll.setUpdatedDate(new Date());
				   		associatePayrollPayheadHistoryService.saveIndEmployeePayroll(indEmployeePayroll);
			   	}
			 	response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}else if (loginUser == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "/saveBulkPayheadAdjustInd","/rest/saveBulkPayheadAdjustInd" })
	public Response<String> saveBulkPayheadAdjustInd(IndEmployeePayrollDTO indEmployeePayrollDTO, HttpServletRequest request) throws Exception
	{
		Response<String> response = new Response<>();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if(loginUser != null){
			 	List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = null;
			 	Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indEmployeePayrollDTO.getPaySheetFrequencyUUID());
			 	if(sessionObj != null) {
				    	IndPaysheetFrequencyDTO indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
				    	List<INDPayrollDTO> payrollDTOList = null ;
				    	if(StringUtils.isEmpty(indEmployeePayrollDTO.getAllEmployees())) {
				    		payrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO().stream().filter(employeePayrollDTO -> indEmployeePayrollDTO.getIndEmpPayrollUUIDList().contains(employeePayrollDTO.getIndEmpPayrollUUID())).collect(Collectors.toList());
				    	}else {
				    		payrollDTOList = indPaysheetFrequencyDTO.getIndPayrollDTO();
				    	}
				    	
				    	if(payrollDTOList != null && !payrollDTOList.isEmpty()) {
				    		for(INDPayrollDTO indPayrollDTO : payrollDTOList) {
				    			if(StringUtils.isEmpty(indEmployeePayrollDTO.getAllEmployees()) && indEmployeePayrollDTO.getIndEmpPayrollUUIDList() != null && !indEmployeePayrollDTO.getIndEmpPayrollUUIDList().contains(indPayrollDTO.getIndEmpPayrollUUID()) ) {
			   						continue;
			   					}
					    		associatePayrollPayheadDTOList = iNDPayrollCommonUtil.associatePayrollPayheadAdjustIndSessionList(indPayrollDTO.getPayHeadHistoryDTOList(), indPayrollDTO, indEmployeePayrollDTO, loginUser, orgLocation);
					    		indPayrollDTO.setPayHeadHistoryDTOList(associatePayrollPayheadDTOList);
					    		
					    		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					    		List<AssociatePayrollPayheadHistoryDTO> dupAssociatePayrollPayheadDTOList = associatePayrollPayheadDTOList;
					    		associatePayrollPayheadDTOList = associatePayrollPayheadDTOList.stream().filter(ml -> (dupAssociatePayrollPayheadDTOList.stream().filter(sl -> {
									try {
										return sl.getPayrollPayhead().equals(ml.getPayrollPayhead()) && df.parse(sl.getUpdatedDate()).after(df.parse(ml.getUpdatedDate()));
									} catch (ParseException e) {
										e.printStackTrace();
									}
									return false;
								}).count())<1).collect(Collectors.toList());
					    		
					    		BigDecimal ePay = new BigDecimal(0);
					    		BigDecimal dPay = new BigDecimal(0);
					    		for(AssociatePayrollPayheadHistoryDTO apphDTO : associatePayrollPayheadDTOList) {
					    			if(apphDTO.getPayrollPayheadType().equalsIgnoreCase("Earnings"))
					    				ePay = ePay.add(apphDTO.getMonthly());
					    			
					    			if(apphDTO.getPayrollPayheadType().equalsIgnoreCase("Deduction"))
					    				dPay = dPay.add(apphDTO.getMonthly());
					    		}
					    		indPayrollDTO.setGrossPay(ePay);
					    		indPayrollDTO.setNetPay(ePay.subtract(dPay));
					    		indPayrollDTO.setUpdatedBy(loginUser.getId());
					    		indPayrollDTO.setUpdatedDate(new Date());
				    		}
				    	}
			   	}else {
			   			List<IndEmployeePayroll> indEmployeePayrollList = indPaysheetFrequencyService.getIndEmployeePayrollListByPaysheetFrequencyUuid(indEmployeePayrollDTO.getPaySheetFrequencyUUID()).getData();
			   				for(IndEmployeePayroll indEmployeePayroll : indEmployeePayrollList) {
			   					if(StringUtils.isEmpty(indEmployeePayrollDTO.getAllEmployees()) && indEmployeePayrollDTO.getIndEmpPayrollUUIDList() != null && !indEmployeePayrollDTO.getIndEmpPayrollUUIDList().contains(indEmployeePayroll.getIndEmpPayrollUUID()) ) {
			   						continue;
			   					}
			   					
			   					//the phList is to collect all payheads and calculate gross and net pay
			   					Set<AssociatePayrollPayheadHistory> phList = new HashSet<>();
			   					phList.addAll(indEmployeePayroll.getAssociatePayrollPayheadHistory());
			   					
		   						Set<AssociatePayrollPayheadHistory> associatePayrollPayheadHistory = iNDPayrollCommonUtil.associatePayrollPayheadAdjustIndList(indEmployeePayroll, indEmployeePayrollDTO, loginUser);
						   		indEmployeePayroll.setAssociatePayrollPayheadHistory(associatePayrollPayheadHistory);
						   		
						   		
						   		associatePayrollPayheadHistory.addAll(phList);
					    		Set<AssociatePayrollPayheadHistory> dupAssociatePayrollPayheadHistoryList = associatePayrollPayheadHistory;
					    		associatePayrollPayheadHistory = associatePayrollPayheadHistory.stream().filter(ml -> (dupAssociatePayrollPayheadHistoryList.stream().filter(sl -> {
										return sl.getPayrollPayhead().getId().equals(ml.getPayrollPayhead().getId()) && sl.getUpdatedDate().after(ml.getUpdatedDate());
								}).count())<1).collect(Collectors.toSet());
					    		
					    		BigDecimal ePay = new BigDecimal(0);
					    		BigDecimal dPay = new BigDecimal(0);
					    		for(AssociatePayrollPayheadHistory apph : associatePayrollPayheadHistory) {
					    			if(apph.getPayrollPayhead().getType().equalsIgnoreCase("Earnings")) {
					    				ePay = ePay.add(apph.getMonthly());
					    			}else if(apph.getPayrollPayhead().getType().equalsIgnoreCase("Deduction")) {
					    				dPay = dPay.add(apph.getMonthly());
					    			}
					    		}
					    		indEmployeePayroll.setGrossPay(ePay);
					    		indEmployeePayroll.setNetPay(ePay.subtract(dPay));
						   		indEmployeePayroll.setUpdatedBy(loginUser.getId());
						   		indEmployeePayroll.setUpdatedDate(new Date());
						   		associatePayrollPayheadHistoryService.saveIndEmployeePayroll(indEmployeePayroll);
			   				}
			   		
			   	}
			 	response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}else if (loginUser == null) {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = { "/bulkPayAdjustmentsForFrequencyInd","/rest/bulkPayAdjustmentsForFrequencyInd" })
	public ModelAndView bulkPayAdjustmentsForFrequencyInd(IndPaysheetFrequencyDTO indPaysheetFrequencyDTO, HttpServletRequest request) throws Exception
	{
		ModelAndView modelAndView = new ModelAndView();
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	 	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	 	try {
	 		if(loginUser != null){
			 	List<AssociatePayrollPayheadHistoryDTO> associatePayrollPayheadDTOList = null;
			 	Object sessionObj = SourceLeadSessionManager.getSessionAttribute(request, indPaysheetFrequencyDTO.getPaySheetFrequencyUUID());
			 	if(sessionObj != null) {
				    	indPaysheetFrequencyDTO = (IndPaysheetFrequencyDTO) sessionObj;
				    	modelAndView.addObject("payFrequencyEmployeeList", indPaysheetFrequencyDTO.getIndPayrollDTO());
			   	}else {
			   			List<INDPayrollDTO> indPayrollDTOList = new ArrayList<>();
			   			ResponseList<IndEmployeePayroll> indEmployeePayrollResponse = indPaysheetFrequencyService.getIndEmployeePayrollListByPaysheetFrequencyUuid(indPaysheetFrequencyDTO.getPaySheetFrequencyUUID());
			   			for(INDPayrollDTO indPayrollDTO : iNDPayrollCommonUtil.getINDPayrollDTOFromIndEmployeePayroll(new HashSet<IndEmployeePayroll> (indEmployeePayrollResponse.getData()), orgLocation)) {
			   				indPayrollDTOList.add(indPayrollDTO);
			   			}
			   			modelAndView.addObject("payFrequencyEmployeeList", indPayrollDTOList);
			   	}
			 	
			 	ResponseList<PayrollPayheadDTO> payrollPayheadResponseList = payrollPayheadService.getPayrollPayheadListByPayheadComponentType(orgLocation, Constants.IND_PAYROLL_PAYHEAD_COMPONENT_VARIABLE);
		 		modelAndView.addObject("variablePayheadsList", payrollPayheadResponseList.getData());
		 		
		 		modelAndView.addObject("indPaysheetFrequency", indPaysheetFrequencyDTO);
		 		
				commonUtil.highLightMenu(Constants.RUNPAYROLL, request);
				modelAndView.addObject("activeTaxTab", "runPayrollInd");
				modelAndView.setViewName("payroll_india/payroll/bulkPayAdjustmentsInd");
			}else {
				modelAndView.setViewName("errorPage");
			}
	 		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/getPaySheetFrequencyDataInd", "/rest/getPaySheetFrequencyDataInd" })
	@ResponseBody
	public ResponseList<IndPaysheetFrequencyDTO> getPaySheetFrequencyList(IndPaysheetFrequencyDTO indpaysheetFrequencyDTO, @RequestParam(value = "paySheetFrequencyUUID", required = false) String searchValue, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		String[] tableColumns = {"payRunId", "groupName", "frequencyStartDate", "payCheckDate", "type", "total", "approverStatus"};
	    int sortColumnIndex = 3; //by default sorting using Pay date
		String sortColumnOrder = "desc"; //by default descending order
		if(indpaysheetFrequencyDTO.getOrder() != null && !indpaysheetFrequencyDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(indpaysheetFrequencyDTO.getOrder().get(0).get("column"));
			sortColumnOrder = indpaysheetFrequencyDTO.getOrder().get(0).get("dir");
		}
		indpaysheetFrequencyDTO.setSortColumn(tableColumns[sortColumnIndex]);
		indpaysheetFrequencyDTO.setSortOrder(sortColumnOrder);
		
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
	public ModelAndView getMyLoansAdv(@RequestParam(value = "loanStatus", required = false) String loanStatus, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			commonUtil.highLightMenu(Constants.MYPAYROLL, request);
			modelAndView.addObject("activeTaxTab", "myPayrollInd");
			modelAndView.setViewName("payroll_india/payroll/myLoansAndAdvancesInd");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return modelAndView;
	}
	
	
	
	@RequestMapping(value = "/getMyLoansAdvList", method = RequestMethod.POST)
	public List<AssociateLoanAndAdvanceHistory> getMyLoansAdvList(@RequestParam(value = "loanStatus", required = false) String loanStatus, HttpServletRequest request) {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		List<AssociateLoanAndAdvanceHistory> loanAdvHistoryList = null;
		try {
			loanAdvHistoryList = associateLoanAndAdvancesHistoryService.getAssociateLoanAndAdvanceHistoryListByUserIdAndLocationId(loginUser, orgLocation, loanStatus);
			for(AssociateLoanAndAdvanceHistory alah:loanAdvHistoryList) {
				alah.setName(alah.getAssociateLoanAndAdvances().getPayrollLoanAndAdvanceId().getName());
				alah.setType(alah.getAssociateLoanAndAdvances().getLoanAdvanceType());
				alah.setTotalAmount(alah.getAssociateLoanAndAdvances().getAmount());
				alah.setNoOfShedules(alah.getAssociateLoanAndAdvances().getNoOfSchedules());
				alah.setRequestedOn(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), alah.getAssociateLoanAndAdvances().getCreatedDate()));
				alah.setRequestedTo(commonUtil.dateStrFromOrgDateFormatter(orgLocation.getDateFormat(), alah.getAssociateLoanAndAdvances().getEffectiveDate()));
				alah.setLoanStatus(alah.getAssociateLoanAndAdvances().getLoanAndAdvanceStatus());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return loanAdvHistoryList;
	}

	
	

    @RequestMapping(value = "/savePayrollPaySlipInd", method = RequestMethod.POST)
    @ResponseBody
	public   Response<String> savePayrollPaySlipInd(PayrollPaySlipIndDTO  payrollPaySlipIndDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    	OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    	payrollPaySlipIndDTO.setOrgLocation(orgLocation);
    	payrollPaySlipIndDTO.setUser(user);
    	Response<String> respons=new Response<>();
    	try{
    		if(user !=null){
    			if(Validator.validate(payrollPaySlipIndDTO)){
    				payrollPaySlipIndService.savePayrollPaySlipIndDTO(payrollPaySlipIndDTO);
    				respons.setResponse(Result.SUCCESS.name());
    				respons.setResponseMessage(Result.SUCCESS.getValue());
    			}else{
    				respons.setObject("DataMissing");
					respons.setResponse(Result.INVALID_DATA.name());
					respons.setResponseMessage(Result.INVALID_DATA.getValue());
    			}
    		}else{
    			respons.setResponse(Result.SESSION_TIMEDOUT.name());
				respons.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
    		}
    		
    	}catch (Exception e) {
    		respons.setResponse(Result.AWKWARD.name());
			respons.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return respons;
		
	}
    
    
    
    
    @RequestMapping(value = "/getPayrollPaySlipViewList", method = RequestMethod.POST)
	public List<PayrollPaySlipInd> getPayrollPaySlipViewList(HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<PayrollPaySlipInd> indPaySlipList = payrollPaySlipIndService.getIndPayrollPaySlipListByOrglocationInd(orgLocation.getId());
		return indPaySlipList;
	}

    
    @RequestMapping(value = "/deletePayrollPaySlip", method = RequestMethod.POST)
   	public PayrollPaySlipInd deletePayrollPaySlip(PayrollPaySlipInd payrollPaySlipInd,@RequestParam(value = "defaultStatus", required = false) String defaultStatus,
   			HttpServletRequest request) throws Exception {
  	   OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 

   		 PayrollPaySlipInd indPaySlip = payrollPaySlipIndService.getPayrollPayslipByUUID(payrollPaySlipInd.getUuid());
   		 if(defaultStatus.equalsIgnoreCase("N")) {
   			if(indPaySlip != null) {
   	   			indPaySlip.setStatus(Constants.RECORD_INACTIVE);
   	   			payrollPaySlipIndService.savePayrollPaySlipInd(indPaySlip);
   	   		 }
   		 }else if(defaultStatus.equalsIgnoreCase("Y")) {
   			 List<PayrollPaySlipInd> indPayslipList = payrollPaySlipIndService.getIndPayrollPaySlipListByOrglocationInd(orgLocation.getId());
   	   		 for(PayrollPaySlipInd pps : indPayslipList) {
   	   			pps.setIsActive(Constants.NO);
   	   			payrollPaySlipIndService.savePayrollPaySlipInd(pps);
   	   		 }
   	    	if(indPaySlip != null) {
	   			indPaySlip.setStatus(Constants.RECORD_INACTIVE);
	   			indPaySlip.setIsActive(Constants.NO);
	   			payrollPaySlipIndService.savePayrollPaySlipInd(indPaySlip);
	   		 }
   	   	   PayrollPaySlipInd indPaySlips =  payrollPaySlipIndService.deletePermissionStatsuByDeletePermission("N", orgLocation.getId());
   	   	   if(indPaySlips != null) {
   	   		indPaySlips.setIsActive(Constants.YES);
	   			payrollPaySlipIndService.savePayrollPaySlipInd(indPaySlips);
   	   	   }
   	   	
   		 }
   		 
   		return indPaySlip;
   	}
    
    @RequestMapping(value = "/validatePayslipName", method = RequestMethod.POST)
   	public @ResponseBody Boolean validatePayslipName(@RequestParam(value = "payslipName", required = false) String payslipName,
   			@RequestParam(value="payslipUUID", required=false) String payslipUUID,HttpServletRequest request) throws Exception {
 	   OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations"); 
 	   PayrollPaySlipInd indPaySlip = null;
    	if(payslipUUID != null) {
    		indPaySlip = payrollPaySlipIndService.validatePayslipNameByUUIDAndPayslipName(payslipUUID, payslipName);
    	}
    	Boolean flag = false;
    	if(indPaySlip == null) {
    		flag = payrollPaySlipIndService.validatePayslipNameByOrglocationIdAndPayslipName(orgLocation.getId(), payslipName);
    	}
   		return flag;
   	}
    
    
    @RequestMapping(value = { "/getDownloadPayslipInd", "/rest/getDownloadPayslipInd" })
	@ResponseBody
	public void getDownloadPayslipInd(IndEmployeePayrollDTO indEmployeePayrollDTO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<IndEmployeePayroll> indEmployeePayrollResponse  = indPaysheetFrequencyService.getIndEmployeePayrollByEmpPayrollUUID(indEmployeePayrollDTO.getIndEmpPayrollUUID());
		IndEmployeePayroll indEmployeePayroll = indEmployeePayrollResponse.getObject();
		File  file=  indPaySlipPdf.getIndiaPaySlipPdf(indEmployeePayroll,orgLocations);
	    
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
		
	}
    
    @RequestMapping(value = "/changeDefaultPayslip", method = RequestMethod.POST)
   	public PayrollPaySlipInd changeDefaultPayslip(PayrollPaySlipInd payrollPaySlipInd,HttpServletRequest request) throws Exception {
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
   		 List<PayrollPaySlipInd> indPayslipList = payrollPaySlipIndService.getIndPayrollPaySlipListByOrglocationInd(orgLocation.getId());
   		 for(PayrollPaySlipInd pps : indPayslipList) {
   			pps.setIsActive(Constants.NO);
   			payrollPaySlipIndService.savePayrollPaySlipInd(pps);
   		 }
   		 PayrollPaySlipInd indPaySlip = payrollPaySlipIndService.getPayrollPayslipByUUID(payrollPaySlipInd.getUuid());
   		 if(indPaySlip != null) {
   			indPaySlip.setIsActive(Constants.YES);
   			payrollPaySlipIndService.savePayrollPaySlipInd(indPaySlip);
   		 }
   		return indPaySlip;
   	}
    
     
    
}
