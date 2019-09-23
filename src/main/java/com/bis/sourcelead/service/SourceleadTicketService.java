package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTicketDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsAttachments;
import com.bis.sourcelead.entity.SourceleadTicketsComments;
import com.bis.sourcelead.entity.User;

public interface SourceleadTicketService {
	
	//ticket related
	SourceleadTickets saveSourceleadTickets(SourceleadTickets sourceleadTickets) throws Exception;

	public String saveSourceleadTickets(SourceleadTicketDTO sourceleadTicketDTO,User user, OrgLocations orgLocation) throws Exception;

	ResponseList<SourceleadTicketDTO> getSourceleadTicketListByOrgLocationId(SourceleadTicketDTO sourceleadTicketDTO,Long userId,OrgLocations orgLocation,Date[] searchDates)throws Exception;
	
	SourceleadTickets getSourceleadTicketsByTicketUUID(String ticketUUID);
	
	List<OrgLocations> getSourceleadTicketRequestedOrgLocatons()throws Exception;
	
//	void sendEmailSourceleadTicketToSourceleadAdmin(SourceleadTickets sourceleadTickets,OrgLocations orgLocation);
	
	//attachments related
	
	List<SourceleadTicketsAttachments> getSourceleadTicketsAttachmentsListByTicketId(Long ticketId) throws Exception;
	
	boolean deleteSourceleadTicketAttachmentByTicketId(Long ticketAttachmentId)throws Exception;
	
	SourceleadTicketsAttachments getSourceleadTicketAttachmentByTicketId(Long ticketId)throws Exception;
	
	
	//comments related 
	List<SourceleadTicketsComments> getSourceleadTicketsCommentsListByTicketId(Long ticketId)throws Exception;

	void saveSourceleadTicketComment(SourceleadTicketDTO sourceleadTicketDTO, User user)throws Exception;
	
	public void pmsTicketStatusUpdatedEmail(User user,Org org,OrgLocations orgloc, SourceleadTickets sourceleadTicket, String existingticketStatus, String ticketStatus)throws Exception;
}
