package com.bis.sourcelead.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsAttachments;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.SourceleadTicketService;
import com.bis.sourcelead.util.CommonUtil;


@Controller
@PropertySource("${propertyLocation:app.properties}")
@RequestMapping("/app")
public class SourceleadTicketsController {
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	SourceleadTicketService sourceleadTicketService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	 protected boolean isNullOrEmpty(String str)
	 {
		return (str == null || "".equals(str.trim()));
	 }

//	 @PreAuthorize("hasRole('ROLE_SUPPORT')")
	 @RequestMapping(value = "/sourceleadTicketList")
   	 public ModelAndView sourceleadTicketList( HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    List<OrgLocations> orgLocationsList = sourceleadTicketService.getSourceleadTicketRequestedOrgLocatons();
   	    if(orgLocationsList != null) {
   	        model.addObject("orgLocationsList", orgLocationsList);
   	    }
   	    commonUtil.highLightMenu(Constants.Support, request);
   		model.setViewName("sourceleadTicketList");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
	 
//	 @PreAuthorize("hasRole('ROLE_SUPPORT')")
	 @RequestMapping(value = "/addSourceleadTicket")
   	 public ModelAndView addSourceleadTicket(@RequestParam(value = "ticketUUID", required = false) String ticketUUID, HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    commonUtil.highLightMenu(Constants.Support, request);
   	     if(ticketUUID != null) {
   	   	    SourceleadTickets sourceleadTicket = sourceleadTicketService.getSourceleadTicketsByTicketUUID(ticketUUID);
   	        model.addObject("sourceleadTicket", sourceleadTicket);
   	        
   	        List<SourceleadTicketsAttachments> sourceleadTicketsAttachmentsList = sourceleadTicketService.getSourceleadTicketsAttachmentsListByTicketId(sourceleadTicket.getId());
   	        model.addObject("sourceleadTicketsAttachmentsList", sourceleadTicketsAttachmentsList);
   	        model.addObject("ticketUUID",ticketUUID);
   	     }
   	     
   	    List<OrgLocations>  orgLocationList = orgLocationService.getAllOrgLocationsByOrgTypeCompany();
   	    model.addObject("orgLocationList", orgLocationList);
   	    
   		model.setViewName("addSourceleadTicket");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
    
//	 @PreAuthorize("hasRole('ROLE_SUPPORT')")
	 @RequestMapping(value = "/sourceleadTicketView")
   	 public ModelAndView sourceleadTicketView(@RequestParam(value = "ticketUUID", required = false) String ticketUUID,HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    commonUtil.highLightMenu(Constants.Support, request);
   	    
   	    SourceleadTickets sourceleadTicket = sourceleadTicketService.getSourceleadTicketsByTicketUUID(ticketUUID);
        String createddateStr = commonUtil.dateStrFromOrgDateFormatter(sourceleadTicket.getOrgLocationId().getDateFormat(), sourceleadTicket.getCreatedDate());
        model.addObject("createddateStr", createddateStr);
        model.addObject("sourceleadTicket", sourceleadTicket);
        model.addObject("userId", sourceleadTicket.getUserId().getId());
        
        List<SourceleadTicketsAttachments> sourceleadTicketsAttachmentsList = sourceleadTicketService.getSourceleadTicketsAttachmentsListByTicketId(sourceleadTicket.getId());
        model.addObject("sourceleadTicketsAttachmentsList", sourceleadTicketsAttachmentsList);
        
   		model.setViewName("sourceleadTicketView");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }

}
