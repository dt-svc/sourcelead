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
import com.bis.sourcelead.dto.CandidateOnBoardingDTO;
import com.bis.sourcelead.dto.PayrollBonusDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.PayrollBonusService;
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
					}
					if(StringUtils.isNotBlank(payrollBonusDTO.getPaymentFrequency())) {
						payrollBonus.setPaymentFrequency(payrollBonusDTO.getPaymentFrequency());
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getDescription())) {
						payrollBonus.setDescription(payrollBonusDTO.getDescription());
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getFromMonth())) {
						payrollBonus.setFromMonth(payrollBonusDTO.getFromMonth());
					}
					
					if(StringUtils.isNotBlank(payrollBonusDTO.getToMonth())) {
						payrollBonus.setToMonth(payrollBonusDTO.getToMonth());
					}
					
					payrollBonus.setOrgLocationId(orgLocations);
					payrollBonus.setCreatedDate(new Date());
					payrollBonus.setUpdatedDate(new Date());
					payrollBonus.setCreatedBy(user);
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

}
