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
import com.bis.sourcelead.dto.PayrollPayheadDTO;
import com.bis.sourcelead.dto.PayrollPaytypeDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollBonus;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;
import com.bis.sourcelead.entity.PayrollPayhead;
import com.bis.sourcelead.entity.PayrollPaytype;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.PayrollBonusService;
import com.bis.sourcelead.service.PayrollLoanAndAdvancesService;
import com.bis.sourcelead.service.PayrollPayheadService;
import com.bis.sourcelead.service.PayrollPaytypeService;
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
	
	@Autowired
	PayrollPayheadService payrollPayheadService;
	
	@Autowired
	PayrollPaytypeService payrollPaytypeService;
	
	
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
		
		
		  
		   @RequestMapping(value = "/storePayhead" , method = RequestMethod.POST)
			public Response<String> storePayhead(PayrollPayheadDTO payrollPayheadDTO, HttpServletRequest request)
					throws Exception {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Response<String> response = new Response<>();
				if (user != null) {
					if (Validator.validate(payrollPayheadDTO)) {
						try {
							PayrollPayhead payrollPayhead = null;
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getUuid())) {
								payrollPayhead = payrollPayheadService.getPayrollPayheadByUUID(payrollPayheadDTO.getUuid());
								
								if(payrollPayhead.getOrgLocationId().getId() == 1l) {
									PayrollPayhead parentPayrollPayhead = new PayrollPayhead();
									parentPayrollPayhead.setId(payrollPayhead.getId());
									payrollPayhead = new PayrollPayhead();
									payrollPayhead.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
									payrollPayhead.setParentPayhead(parentPayrollPayhead);
									payrollPayhead.setCreatedDate(new Date());
									payrollPayhead.setCreatedBy(user);
								}else {
									payrollPayhead.setUpdatedDate(new Date());
									payrollPayhead.setUpdatedBy(user);
								}
							}else {
								payrollPayhead = new PayrollPayhead();
								payrollPayhead.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
								payrollPayhead.setCreatedDate(new Date());
								payrollPayhead.setCreatedBy(user);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getName())) {
								payrollPayhead.setName(payrollPayheadDTO.getName());
							}else {
								payrollPayhead.setName(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getEffectiveDate())) {
								Date paymentDate = commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(), payrollPayheadDTO.getEffectiveDate());
								payrollPayhead.setEffectiveDate(paymentDate);
							}else {
								payrollPayhead.setEffectiveDate(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getShortCode())) {
								payrollPayhead.setShortCode(payrollPayheadDTO.getShortCode());
							}else {
								payrollPayhead.setShortCode(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getType())) {
								payrollPayhead.setType(payrollPayheadDTO.getType());
							}else {
								payrollPayhead.setType(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getPayType())) {
								PayrollPaytype payrollPaytype = new PayrollPaytype();
								payrollPaytype.setId(Long.parseLong(payrollPayheadDTO.getPayType()));
								payrollPayhead.setPayType(payrollPaytype);
							}else {
								payrollPayhead.setPayType(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getForm16())) {
								payrollPayhead.setForm16(payrollPayheadDTO.getForm16());
							}else {
								payrollPayhead.setForm16(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getTax())) {
								payrollPayhead.setTax(payrollPayheadDTO.getTax());
							}else {
								payrollPayhead.setTax(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getApplicableFor())) {
								payrollPayhead.setApplicableFor(payrollPayheadDTO.getApplicableFor());
								if(payrollPayheadDTO.getApplicableFor().equalsIgnoreCase("Experienced")) {
									if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceYears())) {
										payrollPayhead.setExperienceYears(payrollPayheadDTO.getExperienceYears());
									}else {
										payrollPayhead.setExperienceYears(null);
									}
									if(StringUtils.isNotBlank(payrollPayheadDTO.getExperienceMonths())) {
										payrollPayhead.setExperienceMonths(payrollPayheadDTO.getExperienceMonths());
									}else {
										payrollPayhead.setExperienceMonths(null);
									}
								}
							}else {
								payrollPayhead.setApplicableFor(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getComponent())) {
								payrollPayhead.setComponent(payrollPayheadDTO.getComponent());
							}else {
								payrollPayhead.setComponent(null);
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getPaidComponent())) {
								payrollPayhead.setPaidComponent(payrollPayheadDTO.getPaidComponent());
							}else {
								payrollPayhead.setPaidComponent("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getPartOfGratitutiy())) {
								payrollPayhead.setPartOfGratitutiy(payrollPayheadDTO.getPartOfGratitutiy());
							}else {
								payrollPayhead.setPartOfGratitutiy("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getFbpComponent())) {
								payrollPayhead.setFbpComponent(payrollPayheadDTO.getFbpComponent());
							}else {
								payrollPayhead.setFbpComponent("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getLeaveDependent())) {
								payrollPayhead.setLeaveDependent(payrollPayheadDTO.getLeaveDependent());
							}else {
								payrollPayhead.setLeaveDependent("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getShowInPayslip())) {
								payrollPayhead.setShowInPayslip(payrollPayheadDTO.getShowInPayslip());
							}else {
								payrollPayhead.setShowInPayslip("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getPfApplicable())) {
								payrollPayhead.setPfApplicable(payrollPayheadDTO.getPfApplicable());
							}else {
								payrollPayhead.setPfApplicable("N");
							}
							
							if(StringUtils.isNotBlank(payrollPayheadDTO.getEsiApplicable())) {
								payrollPayhead.setEsiApplicable(payrollPayheadDTO.getEsiApplicable());
							}else {
								payrollPayhead.setEsiApplicable("N");
							}
							
							payrollPayhead.setOrgLocationId(orgLocations);
							//payrollPayhead.setCreatedDate(new Date());
							//payrollPayhead.setCreatedBy(user);
							payrollPayhead.setStatus(Constants.RECORD_ACTIVE);
							payrollPayhead = payrollPayheadService.savePayrollPayhead(payrollPayhead);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} catch (Exception e) {
							response.setResponse(Result.AWKWARD.name());
							response.setResponseMessage(Result.AWKWARD.getValue());
							logger.error(ExceptionUtils.getFullStackTrace(e));
						}
					}else {
						response.setObject("DataMissing");
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				return response;
			}
		   
		   
		   @RequestMapping(value = "/storePaytype" , method = RequestMethod.POST)
			public Response<String> storePaytype(PayrollPaytypeDTO payrollPaytypeDTO, HttpServletRequest request)
					throws Exception {
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Response<String> response = new Response<>();
				if (user != null) {
					if (Validator.validate(payrollPaytypeDTO)) {
						try {
							PayrollPaytype payrollPaytype = new PayrollPaytype();
							
							if(StringUtils.isNotBlank(payrollPaytypeDTO.getName())) {
								payrollPaytype.setName(payrollPaytypeDTO.getName());
							}else {
								payrollPaytype.setName(null);
							}
							
							if(StringUtils.isNotBlank(payrollPaytypeDTO.getDescription())) {
								payrollPaytype.setDescription(payrollPaytypeDTO.getDescription());
							}else {
								payrollPaytype.setDescription(null);
							}
							
							payrollPaytype.setOrgLocationId(orgLocations);
							payrollPaytype.setCreatedDate(new Date());
							payrollPaytype.setCreatedBy(user);
							payrollPaytype.setStatus(Constants.RECORD_ACTIVE);
							payrollPaytype = payrollPaytypeService.savePayrollPaytype(payrollPaytype);
							response.setResponse(Result.SUCCESS.name());
							response.setResponseMessage(Result.SUCCESS.getValue());
						} catch (Exception e) {
							response.setResponse(Result.AWKWARD.name());
							response.setResponseMessage(Result.AWKWARD.getValue());
							logger.error(ExceptionUtils.getFullStackTrace(e));
						}
					}else {
						response.setObject("DataMissing");
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
					}
				}else {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				return response;
			}
		   
		   
		   @RequestMapping(value = "/getPayheadList")
			public ResponseList<PayrollPayheadDTO> getPayheadList(PayrollPayheadDTO payrollPayheadDTO, HttpServletRequest request) throws Exception {
			    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				ResponseList<PayrollPayheadDTO> responseList = new ResponseList<>();
				if (user != null) {
					try {
						ResponseList<PayrollPayheadDTO> responsePayheadList = payrollPayheadService.getPayrollPayheadList(orgLocations);
						responseList.setData(responsePayheadList.getData());
						responseList.setResponse(Result.SUCCESS.name());
						responseList.setResponseMessage(Result.SUCCESS.getValue());
					} catch (Exception e) {
						responseList.setResponse(Result.AWKWARD.name());
						responseList.setResponseMessage(Result.AWKWARD.getValue());
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}else {
					responseList.setResponse(Result.SESSION_TIMEDOUT.name());
					responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				return responseList;
			}
		   
		   
		   @RequestMapping(value = "/getPaytypeList")
			public ResponseList<PayrollPaytypeDTO> getPaytypeList(PayrollPaytypeDTO payrollPaytypeDTO, HttpServletRequest request) throws Exception {
			    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				ResponseList<PayrollPaytypeDTO> responseList = new ResponseList<>();
				if (user != null) {
					try {
						ResponseList<PayrollPaytypeDTO> responsePaytypeList = payrollPaytypeService.getPayrollPaytypeList(orgLocations);
						responseList.setData(responsePaytypeList.getData());
						responseList.setResponse(Result.SUCCESS.name());
						responseList.setResponseMessage(Result.SUCCESS.getValue());
					} catch (Exception e) {
						responseList.setResponse(Result.AWKWARD.name());
						responseList.setResponseMessage(Result.AWKWARD.getValue());
						logger.error(ExceptionUtils.getFullStackTrace(e));
					}
				}else {
					responseList.setResponse(Result.SESSION_TIMEDOUT.name());
					responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				}
				return responseList;
			}
		   
		   
			 @RequestMapping(value = { "/getPayRollPayheadListWithPagination", "rest/getPayRollPayheadListWithPagination" })
				public ResponseList<PayrollPayheadDTO> getPayRollPayheadListWithPagination(PayrollPayheadDTO payrollPayheadDTO,
						@RequestParam("locationCode") String locationCode) throws Exception {
					OrgLocations loginOrgLocation = orgLocationService.getOrgLocationsByLocationCode(locationCode);
					String[] tableColumns = { "name", "shortCode", "type", "tax","component", "actions" };
					int sortColumnIndex = 1; // by default sorting using payhead
					String sortColumnOrder = "desc"; // by default descending order
					if (payrollPayheadDTO.getOrder() != null && !payrollPayheadDTO.getOrder().isEmpty()) {
						sortColumnIndex = Integer.parseInt(payrollPayheadDTO.getOrder().get(0).get("column"));
						sortColumnOrder = payrollPayheadDTO.getOrder().get(0).get("dir");
					}
					payrollPayheadDTO.setSortColumn(tableColumns[sortColumnIndex]);
					payrollPayheadDTO.setSortOrder(sortColumnOrder);
					ResponseList<PayrollPayheadDTO> payrollPayheadList = payrollPayheadService.getPayrollPayheadListWithPagination(payrollPayheadDTO, loginOrgLocation);
					return payrollPayheadList;
				}
}