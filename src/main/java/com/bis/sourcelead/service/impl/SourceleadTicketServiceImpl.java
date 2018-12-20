package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.SourceleadTicketsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTicketDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsAttachments;
import com.bis.sourcelead.entity.SourceleadTicketsComments;
import com.bis.sourcelead.entity.Tabs;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadTicketService;
import com.bis.sourcelead.service.TabsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@Service
public class SourceleadTicketServiceImpl implements SourceleadTicketService{
	
	@Value("${sourceleadUrl}")
    private String sourceleadUrl;
	
	@Value("${defaultFromEmailAddressForProd}")
	private String defaultFromEmailAddressForProd;

	@Autowired
	HttpServletRequest request;
	
	@Autowired
	SourceleadTicketsDao sourceleadTicketDao;
	
	 @Autowired
	 private CommonUtil commonUtil;
	 
	 @Autowired
	 TabsService tabsService;
	 
	 @Autowired
	 OrgLocationService orgLocationService;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Value("${env}")
	 private String environment;
	 
	 
	@Override
	public String saveSourceleadTickets(SourceleadTicketDTO sourceleadTicketDTO,User user, OrgLocations orgLocation) throws Exception {
		SourceleadTickets sourceleadTickets = null;
		if(!sourceleadTicketDTO.getTicketUUID().isEmpty()) {
			sourceleadTickets = sourceleadTicketDao.getSourceleadTicketsByTicketUUID(sourceleadTicketDTO.getTicketUUID());
			sourceleadTickets.setUpdatedDate(new Date());
			sourceleadTickets.setUpdatedBy(user);
		}else {
			sourceleadTickets = new SourceleadTickets();
			sourceleadTickets.setTicketGeneratedId(getTicketGeneratedId(orgLocation));
			sourceleadTickets.setTicketUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
			sourceleadTickets.setCreatedBy(user);
			sourceleadTickets.setCreatedDate(new Date());
		}
		sourceleadTickets.setTicketTitle(sourceleadTicketDTO.getTicketTitle());
		sourceleadTickets.setTicketType(sourceleadTicketDTO.getTitcketType());
		sourceleadTickets.setTicketURL(sourceleadTicketDTO.getTicketUrl());
		sourceleadTickets.setDescription(sourceleadTicketDTO.getDescription());
		  if(!sourceleadTicketDTO.getTicketTabs().isEmpty()) {
			 Tabs tab =  tabsService.getTabListByTabId(Long.valueOf(sourceleadTicketDTO.getTicketTabs()));
			 sourceleadTickets.setTabId(tab);
		  }
		  sourceleadTickets.setTicketStatus("Open");
		  
		  if(sourceleadTicketDTO.getOrgLocationsId() != null && !sourceleadTicketDTO.getOrgLocationsId().isEmpty()) {
			  OrgLocations orgl = orgLocationService.getOrgLocations(Long.valueOf(sourceleadTicketDTO.getOrgLocationsId()));
			  sourceleadTickets.setOrgLocationId(orgl);
		  }else {
			  sourceleadTickets.setOrgLocationId(orgLocation);
		  }
		  if(sourceleadTicketDTO.getUserId() !=null && !sourceleadTicketDTO.getUserId().isEmpty()) {
			  	User exitUserId = userService.getUserById(Long.valueOf(sourceleadTicketDTO.getUserId()));
				sourceleadTickets.setUserId(exitUserId);

		  }else {
			  sourceleadTickets.setUserId(user);

		  }
		  sourceleadTickets.setStatus(Constants.RECORD_ACTIVE);
		  if(sourceleadTicketDTO.getFileInfo() !=null && !sourceleadTicketDTO.getFileInfo().isEmpty()){
				Set<SourceleadTicketsAttachments> ticketDocumentsSet = getTicketDocuments(sourceleadTicketDTO,user,sourceleadTickets);	
				sourceleadTickets.setSourceleadTicketsAttachments(ticketDocumentsSet);
		}
		  sourceleadTicketDao.saveSourceleadTickets(sourceleadTickets);
		  sendEmailSourceleadTicketToSourceleadAdmin(sourceleadTickets, user,orgLocation);
		return "Success";
	}

	private void sendEmailSourceleadTicketToSourceleadAdmin(SourceleadTickets sourceleadTickets, User user,OrgLocations orgLocation)throws Exception {
		if (environment.equalsIgnoreCase("prod")) {
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setFromEmail(user.getEmail());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

			 String[] toEmails = {"info@sourcelead.com","support@sourcelead.com"};
			 
			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("companyName",orgLocation.getOrgLocationName());
			 globalVarsMap.put("tikcetTitle", sourceleadTickets.getTicketTitle());
			 globalVarsMap.put("ticketType",sourceleadTickets.getTicketType());
			 globalVarsMap.put("ticketModule",sourceleadTickets.getTabId().getTabName());
			  
			 StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("ticketLoginUrl",loginBuilder.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
			 //calling the sendEmail method of emailScenerioUtil
			 for(String toEmail : toEmails) {
				 emailMessage.setToEmail(toEmail);
				 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.SOURCLEAD_TICKET_EMAIL,new ArrayList<EmailAttachment>());
			 }
		}else {
			 EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(user.getFirstName());
			 emailMessage.setLastName(user.getLastName());
			 emailMessage.setToEmail("qa@sourcelead.com");
			 emailMessage.setFromEmail(user.getEmail());
			 emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);

			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("companyName",orgLocation.getOrgLocationName());
			 globalVarsMap.put("tikcetTitle", sourceleadTickets.getTicketTitle());
			 globalVarsMap.put("ticketType",sourceleadTickets.getTicketType());
			 globalVarsMap.put("ticketModule",sourceleadTickets.getTabId().getTabName());
			  
			 StringBuilder loginBuilder = new StringBuilder();
			 globalVarsMap.put("ticketLoginUrl",loginBuilder.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
			
			 //calling the sendEmail method of emailScenerioUtil
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.SOURCLEAD_TICKET_EMAIL,new ArrayList<EmailAttachment>());
		}
	}

	private String getTicketGeneratedId(OrgLocations orgLocations) {
    	Long number = 1L;
		String requirementId = null;
		Integer generatedIdcount =0;
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append(orgLocations.getGeneratedUserId()).append(Constants.TICKET_ENTITY);
		String generatedUsreId = sbuilder.toString();
		for(int i=0;i< generatedUsreId.length();i++){
			char ch = generatedUsreId.charAt(i);
			if(Character.isAlphabetic(ch)){
				generatedIdcount++;
			}
		}
		generatedIdcount = generatedIdcount+1;
		Long checkNumber = sourceleadTicketDao.getTicketGeneratedIdByGeneratedIdcount(generatedIdcount);
		if(checkNumber == null){
			requirementId = number.toString();
		}else{
		Long reqId = checkNumber+1;
		requirementId = reqId.toString();
		}
		final DecimalFormat decimalFormat = new DecimalFormat("0");                
        String convetredRequirementsId = decimalFormat.format(Long.parseLong(requirementId));
        StringBuffer sb = new StringBuffer();
		return sb.append(Constants.TICKET_ENTITY).append(convetredRequirementsId).toString();
	}

	
	private Set<SourceleadTicketsAttachments> getTicketDocuments(SourceleadTicketDTO sourceleadTicketDTO, User user,SourceleadTickets sourceleadTickets)throws Exception {
		Set<SourceleadTicketsAttachments> ticketDocumentSet = new HashSet<>();
		if (sourceleadTicketDTO.getFileInfo() != null && sourceleadTicketDTO.getFileInfo().size() > 0) {
			List<FileInfo> fileInfoList = sourceleadTicketDTO.getFileInfo();
			for (FileInfo fileInfo : fileInfoList) {
				SourceleadTicketsAttachments sta = new SourceleadTicketsAttachments();
				sta.setTicketId(sourceleadTickets);
				sta.setAttachmentUUID(SourceLeadRandomCodeHelper.generateRandomUserCode());
				sta.setFileType(fileInfo.getContentType());
				sta.setFileName(fileInfo.getFileName());
				sta.setFileURL(fileInfo.getFileURL());
				sta.setStatus(Constants.RECORD_ACTIVE);
				sta.setCreatedBy(user);
				sta.setCreatedDate(new Date());
				if(fileInfo != null){
					FileUploadUtil.moveFile(sourceleadTicketDTO.getStaticPath(), fileInfo, "sourceleadTickets");
				}
				ticketDocumentSet.add(sta);
			}
		}
		return ticketDocumentSet;
	}

	@Override
	public ResponseList<SourceleadTicketDTO> getSourceleadTicketListByOrgLocationId(SourceleadTicketDTO sourceleadTicketDTO, Long userId,OrgLocations orgLocation,Date[] searchDates) throws Exception {
		 ResponseList<SourceleadTicketDTO> responseList = new ResponseList<>();
		 ResponseList<SourceleadTickets> sourceleadTicketsList = sourceleadTicketDao.getSourceleadTicketListByOrgLocationId(sourceleadTicketDTO, userId,orgLocation,searchDates);
		 List<SourceleadTicketDTO> sourceleadTicketDTOList = new ArrayList<>();
		 if(sourceleadTicketsList != null) {
			 for(SourceleadTickets sourceleadTickets : sourceleadTicketsList.getData()){
				 SourceleadTicketDTO std = new SourceleadTicketDTO();
				 std.setTicketUUID(sourceleadTickets.getTicketUUID());
				 std.setId(String.valueOf(sourceleadTickets.getId()));
				 std.setTicketTitle(sourceleadTickets.getTicketTitle());
				 std.setTicketGeneratedId(sourceleadTickets.getTicketGeneratedId());
				 std.setTitcketType(sourceleadTickets.getTicketType());
				 std.setTicketTabs(sourceleadTickets.getTabId().getTabName());
				 std.setTicketStatus(sourceleadTickets.getTicketStatus());
				 std.setUserId(sourceleadTickets.getUserId().getId().toString());
				 std.setCreatedByUserId(sourceleadTickets.getCreatedBy().getId().toString());
				 std.setCompanyName(sourceleadTickets.getOrgLocationId().getOrgLocationName());
				 std.setRequestedBy(sourceleadTickets.getUserId().getFullName());
				 std.setRequestedOn(commonUtil.dateStrFromOrgDateFormatter(sourceleadTickets.getOrgLocationId().getDateFormat(), sourceleadTickets.getCreatedDate()));
				 sourceleadTicketDTOList.add(std);
			 }
		 }
		 responseList.setRecordsFiltered(sourceleadTicketsList.getRecordsFiltered());
			responseList.setRecordsTotal(sourceleadTicketsList.getRecordsTotal());
			responseList.setData(sourceleadTicketDTOList);
		return responseList;
	}

	
	@Override
	public void saveSourceleadTicketComment(SourceleadTicketDTO sourceleadTicketDTO, User user) throws Exception {
		
		SourceleadTicketsComments stc = new SourceleadTicketsComments();
		SourceleadTickets sourceleadTickets= sourceleadTicketDao.getSourceleadTicketsByTicketUUID(sourceleadTicketDTO.getTicketUUID());
		stc.setDescription(sourceleadTicketDTO.getTicketComment());
		stc.setTicketId(sourceleadTickets);
		stc.setStatus(Constants.RECORD_ACTIVE);
		stc.setCreatedBy(user);
		stc.setCreatedDate(new Date());
		sourceleadTicketDao.saveSourceleadTicketsComments(stc);
		mailCommentedOnRaisedTicket(sourceleadTickets, user, sourceleadTicketDTO.getTicketComment());
	}
	
	
	
	@Override
	public SourceleadTickets getSourceleadTicketsByTicketUUID(String ticketUUID) {
		return sourceleadTicketDao.getSourceleadTicketsByTicketUUID(ticketUUID);
	}

	@Override
	public SourceleadTickets saveSourceleadTickets(SourceleadTickets sourceleadTickets) throws Exception {
		return sourceleadTicketDao.saveSourceleadTickets(sourceleadTickets);
	}
	
	@Override
	public List<SourceleadTicketsAttachments> getSourceleadTicketsAttachmentsListByTicketId(Long ticketId) throws Exception {
		return sourceleadTicketDao.getSourceleadTicketsAttachmentsListByTicketId(ticketId);
	}

	@Override
	public boolean deleteSourceleadTicketAttachmentByTicketId(Long ticketAttachmentId) throws Exception {
		return sourceleadTicketDao.deleteSourceleadTicketAttachmentByTicketId(ticketAttachmentId);
	}

	@Override
	public SourceleadTicketsAttachments getSourceleadTicketAttachmentByTicketId(Long ticketId) throws Exception {
		return sourceleadTicketDao.getSourceleadTicketAttachmentByTicketId(ticketId);
	}

	@Override
	public List<SourceleadTicketsComments> getSourceleadTicketsCommentsListByTicketId(Long ticketId) throws Exception {
		return sourceleadTicketDao.getSourceleadTicketsCommentsListByTicketId(ticketId);
	}

	@Override
	public List<OrgLocations> getSourceleadTicketRequestedOrgLocatons() throws Exception {
		return sourceleadTicketDao.getSourceleadTicketRequestedOrgLocatons();
	}
	
	public void pmsTicketStatusUpdatedEmail(User user,Org org,OrgLocations orgloc, SourceleadTickets sourceleadTicket, String existingticketStatus, String ticketStatus)throws Exception{
		if (environment.equalsIgnoreCase("prod")) {
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(sourceleadTicket.getUserId().getFirstName());
			 emailMessage.setLastName(sourceleadTicket.getUserId().getLastName());
			 emailMessage.setToEmail(sourceleadTicket.getUserId().getEmail());
			 emailMessage.setFromEmail("info@sourcelead.com");
			 emailMessage.setFromName("Sourcelead");
			 emailMessage.setEmailType(Constants.PMS_Ticket_Status_Updated_Email);
			 emailMessage.setCreatedBy(user.getUserCode());

			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("ticketId",sourceleadTicket.getTicketGeneratedId());
			 globalVarsMap.put("ticketOwner", sourceleadTicket.getUserId().getFullName());	 
			 globalVarsMap.put("previousStatus", existingticketStatus);
			 globalVarsMap.put("currentStatus",ticketStatus);
			 globalVarsMap.put("ticketTitle",sourceleadTicket.getTicketTitle());
			 globalVarsMap.put("module",sourceleadTicket.getTabId().getTabName());
			 List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,org,orgloc,Constants.PMS_Ticket_Status_Updated_Email,attachmentList);
			
		}else {
			EmailMessage emailMessage = new EmailMessage();
			 emailMessage.setFirstName(sourceleadTicket.getUserId().getFirstName());
			 emailMessage.setLastName(sourceleadTicket.getUserId().getLastName());
			 emailMessage.setToEmail(sourceleadTicket.getUserId().getEmail());
			 emailMessage.setFromEmail("qa@sourcelead.com");
			 emailMessage.setFromName("Sourcelead");
			 emailMessage.setEmailType(Constants.PMS_Ticket_Status_Updated_Email);
			 emailMessage.setCreatedBy(user.getUserCode());

			//setting the class filed values
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("ticketId",sourceleadTicket.getTicketGeneratedId());
			 globalVarsMap.put("ticketOwner", sourceleadTicket.getUserId().getFullName());	 
			 globalVarsMap.put("previousStatus", existingticketStatus);
			 globalVarsMap.put("currentStatus",ticketStatus);
			 globalVarsMap.put("ticketTitle",sourceleadTicket.getTicketTitle());
			 globalVarsMap.put("module",sourceleadTicket.getTabId().getTabName());
			 List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
			 commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,org,orgloc,Constants.PMS_Ticket_Status_Updated_Email,attachmentList);
		}
	}
	
	private void mailCommentedOnRaisedTicket(SourceleadTickets sourceleadTicket, User user, String latestComment) throws Exception {
		if(sourceleadTicket != null) {
			
			User toUser = sourceleadTicket.getCreatedBy();
			OrgLocations orgLocation = sourceleadTicket.getOrgLocationId();
			
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setFirstName(sourceleadTicket.getUserId().getFirstName());
			emailMessage.setLastName(sourceleadTicket.getUserId().getLastName());
			emailMessage.setToEmail(sourceleadTicket.getUserId().getEmail());
			emailMessage.setFromEmail("info@sourcelead.com");
			emailMessage.setFromName("Sourcelead");
			emailMessage.setEmailType(Constants.COMMENTED_RAISED_TICKET_EMAIL);
			emailMessage.setCreatedBy(user.getUserCode());
			 
			//setting the class filed values
			Map<String, String> globalVarsMap = new HashMap<String, String>();
			globalVarsMap.put("issueId",sourceleadTicket.getTicketGeneratedId());
			globalVarsMap.put("ticketId",sourceleadTicket.getTicketGeneratedId());
			globalVarsMap.put("ticketOwner", sourceleadTicket.getUserId().getFullName());
			globalVarsMap.put("latestComment", latestComment);
			List<EmailAttachment> attachmentList=new ArrayList<EmailAttachment>();
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap,orgLocation.getOrg(),orgLocation,Constants.COMMENTED_RAISED_TICKET_EMAIL,attachmentList);			 

			EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.COMMENTED_RAISED_TICKET_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
			String notificationBody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap, notificationScenario);
			notificationScenario.setBody(notificationBody);
			
			StringBuilder notificationLink = new StringBuilder();
			String actionLink = "";
			
			actionLink = notificationLink.append(sourceleadUrl).append("/").append("sourceleadTicketView?"
					+ "ticketUUID=" + sourceleadTicket.getTicketUUID()).toString();
			
			emailScenerioUtil.sendNotification(actionLink, user, toUser, orgLocation, notificationScenario, new HashMap<String, String>());
		}
	}
}

