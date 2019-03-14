package com.bis.sourcelead.rest;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.EmailReminderDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.EmailReminder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.EmailReminderService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
@RequestMapping("/app")
public class EmailReminderConfigurationRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailReminderConfigurationRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	EmailReminderService emailReminderService;
	
	
	 @RequestMapping(value = "/storeJobConfigureEmail" , method = RequestMethod.POST)
		public Response<String> storeJobConfigureEmail(EmailReminderDTO emailreminderDTO, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Response<String> response = new Response<>();
			if (user != null) {
				if (Validator.validate(emailreminderDTO)) {
					try {
						EmailReminder emailReminder = emailReminderService.saveEmailReminderDetails(emailreminderDTO, user, orgLocations);
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
	 
	 @RequestMapping(value = "/storeLeaveConfigureEmail" , method = RequestMethod.POST)
		public Response<String> storeLeaveConfigureEmail(EmailReminderDTO emailreminderDTO, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Response<String> response = new Response<>();
			if (user != null) {
				if (Validator.validate(emailreminderDTO)) {
					try {
						EmailReminder emailReminder = emailReminderService.saveEmailReminderDetails(emailreminderDTO, user, orgLocations);
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
	 
	 @RequestMapping(value = "/storeContractReminderConfiguration" , method = RequestMethod.POST)
		public Response<String> storeContractConfigureEmail(EmailReminderDTO emailreminderDTO, HttpServletRequest request)
				throws Exception {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			Response<String> response = new Response<>();
			if (user != null) {
				if (Validator.validate(emailreminderDTO)) {
					try {
						EmailReminder emailReminder = emailReminderService.saveEmailReminderDetails(emailreminderDTO, user, orgLocations);
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

}
