package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.SourceleadTicketDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SourceleadTickets;
import com.bis.sourcelead.entity.SourceleadTicketsAttachments;
import com.bis.sourcelead.entity.SourceleadTicketsComments;

public interface SourceleadTicketsDao {
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	
	//ticket related
	SourceleadTickets saveSourceleadTickets(SourceleadTickets sourceleadTickets) throws Exception;
	
	ResponseList<SourceleadTickets> getSourceleadTicketListByOrgLocationId(SourceleadTicketDTO sourceleadTicketDTO,Long userId,OrgLocations orgLocation,Date[] searchDates)throws Exception;

	SourceleadTickets getSourceleadTicketsByTicketUUID(String ticketUUID);
	
	Long getTicketGeneratedIdByGeneratedIdcount(Integer generatedIdCount);
	
	List<OrgLocations> getSourceleadTicketRequestedOrgLocatons()throws Exception;

	//attchment related
	List<SourceleadTicketsAttachments> getSourceleadTicketsAttachmentsListByTicketId(Long ticketId) throws Exception;

	boolean deleteSourceleadTicketAttachmentByTicketId(Long ticketAttachmentId)throws Exception;
	
	SourceleadTicketsAttachments getSourceleadTicketAttachmentByTicketId(Long ticketId)throws Exception;
	
	List<SourceleadTicketsComments> getSourceleadTicketsCommentsListByTicketId(Long ticketId)throws Exception;

	//comment related
	SourceleadTicketsComments saveSourceleadTicketsComments(SourceleadTicketsComments sourceleadTicketsComments) throws Exception;


	
}
