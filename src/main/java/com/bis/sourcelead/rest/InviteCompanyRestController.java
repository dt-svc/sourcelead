package com.bis.sourcelead.rest;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dto.InviteCompanyDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.ScheduleMaintenanceDTO;
import com.bis.sourcelead.entity.SourceleadBlogs;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.InviteCompanyService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@RestController
@RequestMapping("/app")
public class InviteCompanyRestController {

	private static final Logger logger = LoggerFactory.getLogger(InviteCompanyRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Value("${sourceleadLoginUrl}")
	private String sourceleadLoginUrl;
	
	@Autowired
	public InviteCompanyService inviteCompanyService;
	
	@Autowired
	 TimeZoneService timeZoneService;
	
	
	 @Autowired
	 CommonUtil commonUtil;
	

    @RequestMapping(value = "/storeInviteCompany", method = RequestMethod.POST)
	public @ResponseBody  Response<Void> storeInviteCompany(InviteCompanyDTO inviteCompanyDTO,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	
    	Response<Void> inviteCompanyResponse = new Response<>();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (loginUser != null) {

				if (Validator.validate(inviteCompanyDTO)) {
					String inviteCompany =  inviteCompanyService.saveInviteCompany(inviteCompanyDTO);
					inviteCompanyResponse.setResponse(Result.SUCCESS.name());
					inviteCompanyResponse.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					inviteCompanyResponse.setResponse(Result.INVALID_DATA.name());
					inviteCompanyResponse.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				inviteCompanyResponse.setResponse(Result.SESSION_TIMEDOUT.name());
				inviteCompanyResponse.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			inviteCompanyResponse.setResponse(Result.AWKWARD.name());
			inviteCompanyResponse.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return inviteCompanyResponse;
	}
   
    @RequestMapping(value = "/storeScheduleMaintenance", method =RequestMethod.POST)
	public @ResponseBody Response<Void> storeScheduleMaintenance(ScheduleMaintenanceDTO maintenanceDTO,HttpServletRequest request, HttpServletResponse response) throws Exception{
    	Response<Void> responseScheduleMaintenance = new Response<>();
    	User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		try {
			if (loginUser != null) {

				if (Validator.validate(maintenanceDTO)) {
					inviteCompanyService.storeScheduleMaintenance(maintenanceDTO);
					responseScheduleMaintenance.setResponse(Result.SUCCESS.name());
					responseScheduleMaintenance.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					responseScheduleMaintenance.setResponse(Result.INVALID_DATA.name());
					responseScheduleMaintenance.setResponseMessage(Result.INVALID_DATA.getValue());
				}

			} else {
				responseScheduleMaintenance.setResponse(Result.SESSION_TIMEDOUT.name());
				responseScheduleMaintenance.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			responseScheduleMaintenance.setResponse(Result.AWKWARD.name());
			responseScheduleMaintenance.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		return responseScheduleMaintenance;
	}
    
    @RequestMapping(value="/loadScheduleMaintenance")
	public ModelAndView loadScheduleMaintenance(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.addObject("sourceleadUrl", sourceleadUrl);
		model.setViewName("/scheduleMaintenance");
		return model;
	}
    @RequestMapping(value="/getScheduleMaintenanceList")
	public @ResponseBody   ResponseList<ScheduleMaintenanceDTO> getScheduleMaintenanceList(ScheduleMaintenanceDTO scheduleMaintenanceDTO,@RequestParam(value = "searchValue", required = false) String searchValue,HttpServletRequest request) {
    	ResponseList<ScheduleMaintenanceDTO> maintenanceDTOs = null;
    	try {
    		String[] tableColumns = {"startDate", "endDate", "scheduleMessage",""};
		    int sortColumnIndex = 3; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default descending order
			if(scheduleMaintenanceDTO.getOrder() != null && !scheduleMaintenanceDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(scheduleMaintenanceDTO.getOrder().get(0).get("column"));
				sortColumnOrder = scheduleMaintenanceDTO.getOrder().get(0).get("dir");
			}
			scheduleMaintenanceDTO.setSortColumn(tableColumns[sortColumnIndex]);
			scheduleMaintenanceDTO.setSortOrder(sortColumnOrder);
			maintenanceDTOs = inviteCompanyService.getScheduleMaintenanceList(scheduleMaintenanceDTO, searchValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return maintenanceDTOs;
	}
    
    @RequestMapping(value="/deleteScheduleMaintenance")
   	public Response<Void> deleteScheduleMaintenance(@RequestParam(value = "id", required = false) String id,HttpServletRequest request) {
    	
    	Response<Void> response = new Response<>();
        User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		
		try {
			if (loginUser != null) {
			if(id != null) {
			inviteCompanyService.deleteScheduleMaintenance(id);
			response.setResponse(Result.SUCCESS.name());
			response.setResponseMessage(Result.SUCCESS.getValue());
			}else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());	
			}
			}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());	
			}
		}catch (Exception e) {
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		
		return response;
    }
       
}
