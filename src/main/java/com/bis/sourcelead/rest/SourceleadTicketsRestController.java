package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTicketDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsComments;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.ModulesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadTicketService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;


@RestController
@RequestMapping("/app")
public class SourceleadTicketsRestController {

	private static final Logger logger = LoggerFactory.getLogger(SourceleadTicketsRestController.class);
	
	@Autowired
	public SourceleadTicketService sourceleadTicketService;
	
	@Value("${static.path}")
    private String staticPath;
	
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
	@Autowired
	ReportsUtil reportsUtil;
	
	@Autowired
	UserRoleService userRoleService;

	@Autowired
	ModulesService modulesService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	TabsService tabsService;
	

	
	 @RequestMapping(value = "storeSourceleadTicket" , method = RequestMethod.POST)
	 public  Response<String> storeDepartment(SourceleadTicketDTO sourceleadTicketDTO,HttpServletRequest request) throws Exception {
		 
		 Response<String> response = new Response<>();
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null && Validator.validate(sourceleadTicketDTO)){
				 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				 sourceleadTicketDTO.setStaticPath(staticPath);
				String value= sourceleadTicketService.saveSourceleadTickets(sourceleadTicketDTO,user,orgLocation);
				if(value.equalsIgnoreCase("success")){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				 
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			
		 } catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		 return response;
	   }
	 
	 @RequestMapping(value = "getSourceleadTicketList" , method = RequestMethod.POST)
	 public  ResponseList<SourceleadTicketDTO> getSourceleadTicketList(SourceleadTicketDTO sourceleadTicketDTO,HttpServletRequest request) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 sourceleadTicketDTO.setLoginUserRole(userRole);
		                                          
		 String[] tableColumns = {"ticketGeneratedId", "ticketTitle", "ticketType", "tabId","createdDate","createdBy","ticketStatus",""};
		    int sortColumnIndex = 5; //by default sorting using updated date
			String sortColumnOrder = "asc"; //by default descending order
			if(sourceleadTicketDTO.getOrder() != null && !sourceleadTicketDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(sourceleadTicketDTO.getOrder().get(0).get("column"));
				sortColumnOrder = sourceleadTicketDTO.getOrder().get(0).get("dir");
			}
			sourceleadTicketDTO.setSortColumn(tableColumns[sortColumnIndex]);
			sourceleadTicketDTO.setSortOrder(sortColumnOrder);
			
			
		 Date[] searchDates = null;
			if(StringUtils.isNotBlank(sourceleadTicketDTO.getDateSelection())) {
				if(!sourceleadTicketDTO.getDateSelection().equalsIgnoreCase("custom")) {
					searchDates = reportsUtil.getJobFilterDate(sourceleadTicketDTO.getDateSelection(),orgLocation);
				}else {
					Timezone timezone = timeZoneService.getTimezone(orgLocation.getTimezone().getId());
					DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
					DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
					Date fromDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), sourceleadTicketDTO.getFromDate());
		    	    Date toDate =  commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(), sourceleadTicketDTO.getToDate());
					String customStartTimeStr = TZ_DF_CSD.format(fromDate);
					String customEndTimeStr = TZ_DF_CED.format(toDate);
					String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
					fromDate = TZ_DF_TS.parse(startDateString);
					toDate = TZ_DF_TS.parse(endDateString);
					
		    	    searchDates = new Date[2];
		    	    searchDates[0] = fromDate;
		    	    searchDates[1] = toDate;
				}
			}
			
		 ResponseList<SourceleadTicketDTO> sourceleadTicketList = sourceleadTicketService.getSourceleadTicketListByOrgLocationId(sourceleadTicketDTO, user.getId(),orgLocation,searchDates);
		 return sourceleadTicketList;
	   }
	 
	 
	 @RequestMapping(value = "terminateTicketAtteachment" , method = RequestMethod.POST)
	 public  Response<String> terminateTicketAtteachment(SourceleadTicketDTO sourceleadTicketDTO,HttpServletRequest request) throws Exception {

		 Response<String> response = new Response<>();
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 if(user != null){
				 boolean value= sourceleadTicketService.deleteSourceleadTicketAttachmentByTicketId(Long.valueOf(sourceleadTicketDTO.getAttachmentId()));
				if(value){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				 
			 }
			 else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			
		 } catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		 return response;
		 
	   }
	 
	 @RequestMapping(value = "saveSourceleadTicketComment" , method = RequestMethod.POST)
	 public  String saveSourceleadTicketComment(SourceleadTicketDTO sourceleadTicketDTO,HttpServletRequest request) throws Exception {
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 sourceleadTicketService.saveSourceleadTicketComment(sourceleadTicketDTO,user);
		 return "success";
	   }
	 
	 @RequestMapping(value = "changeTicketStatus" , method = RequestMethod.POST)
	 public   Response<String> changeTicketStatus(@RequestParam(value = "ticketStatus", required = false) String ticketStatus,
			 @RequestParam(value = "ticketUUID", required = false) String ticketUUID,HttpServletRequest request) throws Exception {
		 Response<String> response = new Response<>();
		 try{
			 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
//			 Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 OrgLocations orgloc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 
			 if(user != null){
				 SourceleadTickets sourceleadTicket = sourceleadTicketService.getSourceleadTicketsByTicketUUID(ticketUUID);
				 String existingticketStatus = sourceleadTicket.getTicketStatus();
				 if(ticketStatus != null && ticketStatus != "" && ticketStatus != "undefined") {
					 sourceleadTicket.setTicketStatus(ticketStatus);
					 sourceleadTicket.setUpdatedBy(user);
					 sourceleadTicket.setUpdatedDate(new Date());
				 }
				 SourceleadTickets tickets= sourceleadTicketService.saveSourceleadTickets(sourceleadTicket);
				 
				 if(!existingticketStatus.equals(ticketStatus))
					 sourceleadTicketService.pmsTicketStatusUpdatedEmail(user, orgloc.getOrg(), orgloc, sourceleadTicket, existingticketStatus, ticketStatus);
				 
				if(tickets != null){
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				} else {
					response.setResponse(Result.FAILED.name());
					response.setResponseMessage(Result.FAILED.getValue());
				}
				 
			 }else if (user == null) {
					response.setResponse(Result.SESSION_TIMEDOUT.name());
					response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
				} else {
					response.setResponse(Result.INVALID_DATA.name());
					response.setResponseMessage(Result.INVALID_DATA.getValue());
				}
			
		 } catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		 return response;
	   }
	 
	 
	 @RequestMapping(value = "getSourceleadTicketComments" , method = RequestMethod.POST)
	 public  List<SourceleadTicketsComments> getSourceleadTicketComments(SourceleadTicketDTO sourceleadTicketDTO,HttpServletRequest request) throws Exception {
			List<SourceleadTicketsComments> sourceleadTicketsCommentsList =sourceleadTicketService.getSourceleadTicketsCommentsListByTicketId(Long.valueOf(sourceleadTicketDTO.getId()));
			 for(SourceleadTicketsComments stc:sourceleadTicketsCommentsList) {
				 stc.setDescription(stc.getDescription());
				 stc.setUserImage(stc.getCreatedBy().getFileUrl());
				 stc.setUserName(stc.getCreatedBy().getFullName());
				 long diff = commonUtil.currentDate().getTime() - stc.getCreatedDate().getTime();
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					StringBuilder timeBuilder = new StringBuilder();
					if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
						stc.setCreatedDateStr(timeBuilder.append(diffSeconds).append("seconds ago").toString());
					}
					else if (diffDays == 0 && diffHours == 0) {
						stc.setCreatedDateStr(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
					}
					else if (diffDays == 0) {
						stc.setCreatedDateStr(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
					} else {
						stc.setCreatedDateStr(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
					} 
					
					DateFormat standardFormat = new SimpleDateFormat("dd-MM-yyy hh:mm:ss aa");
					String commonDateFormat = standardFormat.format(stc.getCreatedDate());
					stc.setCreatedDateTime(commonDateFormat.toString());
			 }
			
			return sourceleadTicketsCommentsList;
	   }
	 
	 
	 @RequestMapping(value = "getAllUserListByOrgLocationId" , method = RequestMethod.POST)
	 public  List<UserRole> getAllUserListByOrgLocationId(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId) throws Exception {
		 List<UserRole> userRoleList = userRoleService.getAllUsersBasedOrgLocationId(orgLocationId);
		 for(UserRole ur:userRoleList) {
			 ur.setUserName(ur.getUser().getFullName());
			 ur.setUserId(ur.getUser().getId());
			 ur.setUserRoleName(ur.getRoles().getDisplayName());
		 }
		 return userRoleList;
	   }
	 
	
	 @RequestMapping(value = "getSourceleadTicketModules" , method = RequestMethod.POST)
	 public  Collection<Tabs> getSourceleadTicketModules(@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			 @RequestParam(value = "userId", required = false) Long userId,HttpServletRequest request) throws Exception {
		
		 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		 Collection<Tabs> tabsList = new ArrayList<>();
		 Collection<Tabs> modulesList = new ArrayList<>();
		 Tabs dashobardTab = tabsService.getTabListByTabId(1L);
		 if(!userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")) {
			 tabsList = (Collection<Tabs>) SourceLeadSessionManager.getSessionAttribute(request, "tabsList");
		 }else{
			 if(orgLocationId != null && userId != null) {
				 OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
				 UserRole orgUserRole = userRoleService.getUserDetailsByUserIdAndLocationId(userId, orgLocationId);
				 tabsList = tabsService.getTabsListByOrgLocationId(orgUserRole.getRoles().getId(), orgLocations.getId());
				
			 }
		 }
		 
		 modulesList.add(dashobardTab);
		 for(Tabs tabs : tabsList) {
			 modulesList.add(tabs);
		 }
		 
		 return modulesList;
		
	   } 
	 
	 @RequestMapping(value = "getTicketDescription" , method = RequestMethod.POST)
	 public  SourceleadTickets getTicketDescription(@RequestParam(value = "ticketUUID", required = false) String ticketUUID) throws Exception {
		 SourceleadTickets ticket = sourceleadTicketService.getSourceleadTicketsByTicketUUID(ticketUUID);
		 return ticket;
	   }
	 
}
