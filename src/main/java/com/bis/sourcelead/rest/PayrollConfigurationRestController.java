package com.bis.sourcelead.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.PayrollBonusService;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class PayrollConfigurationRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollConfigurationRestController.class);
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	PayrollBonusService payrollBonusService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	PayrollLoanAndAdvancesService payrollLoanAndAdvancesService; 
	
	
	@RequestMapping(value = "/saveBonus" , method = RequestMethod.POST)
	public Response<String> saveBonus(PayrollBonusDTO payrollBonusDTO, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if (Validator.validate(payrollBonusDTO)) {
					PayrollBonus payrollBonus = null;
					if(StringUtils.isNotBlank(payrollBonusDTO.getUuid())) {
						payrollBonus = payrollBonusService.getPayrollBonusByUUID(payrollBonusDTO.getUuid());
					}else {
						payrollBonus = new PayrollBonus();
						payrollBonus.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						payrollBonus.setCreatedDate(new Date());
						payrollBonus.setCreatedBy(user);
					}
					
					Date paymentDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollBonusDTO.getPaymentDate());
					String month = CalendarUtil.getMonthFromGivenDate(paymentDate);
					String year = CalendarUtil.getYearFromGivenDate(paymentDate);
					payrollBonus.setPaymentDate(paymentDate);
					payrollBonus.setPaymentMonth(month);
					payrollBonus.setPaymentYear(year);
					payrollBonus.setMaxPercentage(Double.valueOf(payrollBonusDTO.getMaxPercentage()));
					payrollBonus.setMinPercentage(Double.valueOf(payrollBonusDTO.getMinPercentage()));
					if(StringUtils.isNotBlank(payrollBonusDTO.getMaxSalary())) {
						payrollBonus.setMaxSalary(Double.valueOf(payrollBonusDTO.getMaxSalary()));
					}else {
						payrollBonus.setMaxSalary(null);
					}
					if(StringUtils.isNotBlank(payrollBonusDTO.getPaymentFrequency())) {
						payrollBonus.setPaymentFrequency(payrollBonusDTO.getPaymentFrequency());
					}else {
						payrollBonus.setPaymentFrequency(null);
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getDescription())) {
						payrollBonus.setDescription(payrollBonusDTO.getDescription());
					}else {
						payrollBonus.setDescription(null);
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getFromMonth())) {
						payrollBonus.setFromMonth(payrollBonusDTO.getFromMonth());
					}else {
						payrollBonus.setFromMonth(null);
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getToMonth())) {
						payrollBonus.setToMonth(payrollBonusDTO.getToMonth());
					}else {
						payrollBonus.setFromMonth(null);
					}
					
					payrollBonus.setOrgLocationId(orgLocations);
					payrollBonus.setUpdatedDate(new Date());
					payrollBonus.setUpdatedBy(user);
					payrollBonus.setStatus(Constants.RECORD_ACTIVE);
					payrollBonusService.savePayrollBonus(payrollBonus);
					
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
				else {
					response.setObject("DataMissing");
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	
	
	
	
	
	@RequestMapping(value = "/deletePayrollBonus" , method = RequestMethod.POST)
	public Response<String> deletePayrollBonus(@RequestParam("uuid") String uuid, HttpServletRequest request)
			throws Exception {
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Response<String> response = new Response<>();
		try {
			if (user != null) {
				if(StringUtils.isNotBlank(uuid)) {
					PayrollBonus payrollBonus = payrollBonusService.getPayrollBonusByUUID(uuid);
					if(payrollBonus != null) {
						payrollBonus.setStatus(Constants.RECORD_INACTIVE);
						payrollBonusService.savePayrollBonus(payrollBonus);
					}
					
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}
			}
			else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}
	 
	 
	 @RequestMapping(value = { "/getPayRollBonusList", "rest/getPayRollBonusList" })
		public ResponseList<PayrollBonusDTO> getPayRollBonusList(PayrollBonusDTO payrollBonusDTO,
				@RequestParam("locationCode") String locationCode) throws Exception {
			OrgLocations loginOrgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			String[] tableColumns = { "paymentMonth", "paymentDate", "fromMonth", "minPercentage", "maxPercentage","actions" };
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (payrollBonusDTO.getOrder() != null && !payrollBonusDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(payrollBonusDTO.getOrder().get(0).get("column"));
				sortColumnOrder = payrollBonusDTO.getOrder().get(0).get("dir");
			}
			payrollBonusDTO.setSortColumn(tableColumns[sortColumnIndex]);
			payrollBonusDTO.setSortOrder(sortColumnOrder);
			ResponseList<PayrollBonusDTO> payrollBonusList = payrollBonusService.getPayrollBonusList(payrollBonusDTO, loginOrgLocation);
			return payrollBonusList;
		}
	 
	 
	 
	 @RequestMapping(value = "/savePayrollLoanAndAdvance" , method = RequestMethod.POST)
		public Response<String> savePayrollLoanAndAdvance(PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Response<String> response = new Response<>();
			try {
				if (user != null) {
					if (Validator.validate(payrollLoanAndAdvancesDTO)) {
						PayrollLoanAndAdvances payrollLoanAndAdvances = null;
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getUuid())) {
							payrollLoanAndAdvances = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByUUID(payrollLoanAndAdvancesDTO.getUuid());
						}else {
							payrollLoanAndAdvances = new PayrollLoanAndAdvances();
							payrollLoanAndAdvances.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							payrollLoanAndAdvances.setCreatedDate(new Date());
							payrollLoanAndAdvances.setCreatedBy(user);
						}
						
						payrollLoanAndAdvances.setType(payrollLoanAndAdvancesDTO.getType());
						payrollLoanAndAdvances.setName(payrollLoanAndAdvancesDTO.getName());
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getMinAmount())) {
							payrollLoanAndAdvances.setMinAmount(Double.valueOf(payrollLoanAndAdvancesDTO.getMinAmount()));
						}else {
							payrollLoanAndAdvances.setMinAmount(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getMaxAmount())) {
							payrollLoanAndAdvances.setMaxAmount(Double.valueOf(payrollLoanAndAdvancesDTO.getMaxAmount()));
						}else {
							payrollLoanAndAdvances.setMaxAmount(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getInterestRate())) {
							payrollLoanAndAdvances.setInterestRate(Double.valueOf(payrollLoanAndAdvancesDTO.getInterestRate()));
						}else {
							payrollLoanAndAdvances.setInterestRate(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getPayHeadId())) {
							PayrollPayhead payhead = new PayrollPayhead();
							payhead.setId(Long.valueOf(payrollLoanAndAdvancesDTO.getPayHeadId()));
							payrollLoanAndAdvances.setPayHeadId(payhead);
						}else {
							payrollLoanAndAdvances.setPayHeadId(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getApplicableFor())) {
							payrollLoanAndAdvances.setApplicableFor(payrollLoanAndAdvancesDTO.getApplicableFor());
						}else {
							payrollLoanAndAdvances.setApplicableFor(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getExperienceYears())) {
							payrollLoanAndAdvances.setExperienceYears(Integer.parseInt(payrollLoanAndAdvancesDTO.getExperienceYears()));
						}else {
							payrollLoanAndAdvances.setExperienceYears(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getExperienceMonths())) {
							payrollLoanAndAdvances.setExperienceMonths(Integer.parseInt(payrollLoanAndAdvancesDTO.getExperienceMonths()));
						}else {
							payrollLoanAndAdvances.setExperienceMonths(null);
						}
						
						if(StringUtils.isNotBlank(payrollLoanAndAdvancesDTO.getDescription())) {
							payrollLoanAndAdvances.setDescription(payrollLoanAndAdvancesDTO.getDescription());
						}else {
							payrollLoanAndAdvances.setDescription(null);
						}
						
						payrollLoanAndAdvances.setOrgLocationId(orgLocations);
						payrollLoanAndAdvances.setUpdatedDate(new Date());
						payrollLoanAndAdvances.setUpdatedBy(user);
						payrollLoanAndAdvances.setStatus(Constants.RECORD_ACTIVE);
						payrollLoanAndAdvancesService.savePayrollLoanAndAdvances(payrollLoanAndAdvances);
						
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
					else {
						response.setObject("DataMissing");
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				}
				else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
		}
	 
	 
	   @RequestMapping(value = { "/getPayrollLoanAndAdvancesList", "rest/getPayrollLoanAndAdvancesList" })
		public ResponseList<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvancesList(PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO,
				@RequestParam("locationCode") String locationCode) throws Exception {
			OrgLocations loginOrgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
			String[] tableColumns = { "type", "name", "minAmount", "interestRate", "applicableFor","actions" };
			int sortColumnIndex = 1; // by default sorting using updated date
			String sortColumnOrder = "desc"; // by default descending order
			if (payrollLoanAndAdvancesDTO.getOrder() != null && !payrollLoanAndAdvancesDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(payrollLoanAndAdvancesDTO.getOrder().get(0).get("column"));
				sortColumnOrder = payrollLoanAndAdvancesDTO.getOrder().get(0).get("dir");
			}
			payrollLoanAndAdvancesDTO.setSortColumn(tableColumns[sortColumnIndex]);
			payrollLoanAndAdvancesDTO.setSortOrder(sortColumnOrder);
			ResponseList<PayrollLoanAndAdvancesDTO> payrollLoanAndAdvancesList = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesList(payrollLoanAndAdvancesDTO, loginOrgLocation);
			return payrollLoanAndAdvancesList;
		}
	 

		@RequestMapping(value = "/deletePayrollLoanAndAdvances" , method = RequestMethod.POST)
		public Response<String> deletePayrollLoanAndAdvances(@RequestParam("uuid") String uuid, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Response<String> response = new Response<>();
			try {
				if (user != null) {
					if(StringUtils.isNotBlank(uuid)) {
						PayrollLoanAndAdvances payrollLoanAndAdvances = payrollLoanAndAdvancesService.getPayrollLoanAndAdvancesByUUID(uuid);
						if(payrollLoanAndAdvances != null) {
							payrollLoanAndAdvances.setStatus(Constants.RECORD_INACTIVE);
							payrollLoanAndAdvancesService.savePayrollLoanAndAdvances(payrollLoanAndAdvances);
						}
						
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}
				else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
			} catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
			return response;
		}
}
