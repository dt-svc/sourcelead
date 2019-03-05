package com.bis.sourcelead.ticketManagement.dao;

import java.util.List;

import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.dto.TicketAttachmentDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketComment;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.TicketStatus;

public interface TicketManagementDao {

	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations();

	public List<TicketType> getTicketTypeListByOrgLocationId(Long orgLocationId, int status);

	public TicketType getTicketTypeById(long id);
	
	public TicketPriority getTicketPriorityById(long id);
	
	public TicketCategory getTicketCategoryById(long id);

	public boolean saveTicketType(TicketType ticketType);

	public boolean deleteTicketTypeDataById(long id);

	public List<TicketCategory> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status);

	public Boolean saveTicketCategory(TicketCategory ticketCategory);

	public Boolean deleteTicketCategoryDataById(long id);

	public List<TicketPriority> getTicketPriorityListByOrgLocationId(Long orgLocationId, int status);

	public Boolean saveTicketPriority(TicketPriority ticketpriority);

	public Boolean deleteTicketPriorityDataById(long id);

	public List<TicketStatus> getTicketStatusListByOrgLocationId(Long orgLocationId, int status);

	public TicketStatus getTicketStatusById(long id);

	public Boolean saveTicketStatus(TicketStatus ticketStatus);

	public Boolean deleteTicketStatusDataById(long id);

	public List<Ticket> getTicketListByOrgLocationId(Long orgLocationId);

	public Ticket getTicketById(long id);

	public Boolean saveTicket(Ticket ticketManagement);

	

	public Boolean changeStatusTicketTypeDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation);

	public List<Ticket> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId, int status);

	public Ticket getTicketSummaryByUUID(String uuid, long userId, Long orgLocationId);

//	public TicketComment getTicketSummaryByTicketId(Long ticketId, long userId, Long orgLocationId);

	public List<TicketComment> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId);

	//public Boolean saveTicketCommentListByTicketId(TicketComment ticketComment);

	public TicketComment getTicketCommentByTicketId(long ticketId);

	public Boolean saveTicketComment(TicketComment ticketComment);

	public List<TicketAttachmentDTO> getExpenseAttachmentStatusByCategoryId(Long ticketId);

	public Ticket getTicketDataById(long ticketId);

	public Boolean changeTicketManagementStatusDataById(long id, int status, OrgLocations orgLocation);

	
	
	

	

	

	

	

	
}
