package com.bis.sourcelead.ticketManagement.dao;

import java.util.List;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketAssignTo;
import com.bis.sourcelead.entity.TicketAttachment;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketCategoryAssignTo;
import com.bis.sourcelead.entity.TicketComment;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketStatusHistory;
import com.bis.sourcelead.entity.User;

public interface TicketManagementDao {

	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations(long orgLocationId);

	public List<TicketType> getTicketTypeListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketTypeDTO ticketTypeDTO);

	public TicketType getTicketTypeById(long id);
	
	public long getTicketTypeIdByNameAndOrgLocationId(String name, long orgLocationsId);
	
	public long getTicketCategoryIdByNameAndOrgLocationId(String name, long orgLocationsId);

	public long getTicketPriorityIdByNameAndOrgLocationId(String name, long orgLocationsId);

	public long getTicketStatusIdByNameAndOrgLocationId(String name, long orgLocationsId);
	
	public TicketPriority getTicketPriorityById(long id);
	
	public TicketCategory getTicketCategoryById(long id);

	public long saveTicketType(TicketType ticketType);

	public boolean deleteTicketTypeDataById(long id);

	public List<TicketCategory> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status, String searchValue);

	public long saveTicketCategory(TicketCategory ticketCategory);

	public Boolean deleteTicketCategoryDataById(long id);

	public List<TicketPriority> getTicketPriorityListByOrgLocationId(Long orgLocationId, int status, String searchValue);

	public long saveTicketPriority(TicketPriority ticketpriority);

	public Boolean deleteTicketPriorityDataById(long id);

	public List<TicketStatus> getTicketStatusListByOrgLocationId(Long orgLocationId, int status, String searchValue);

	public TicketStatus getTicketStatusById(long id);

	public long saveTicketStatus(TicketStatus ticketStatus);

	public Boolean deleteTicketStatusDataById(long id);

	public List<Ticket> getTicketListByOrgLocationId(Long orgLocationId);

	public Ticket getTicketById(long id);

	public Boolean saveTicket(Ticket ticketManagement);

	public Boolean changeStatusTicketTypeDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation);

	public List<Ticket> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId, int status, String userRole, String searchValue);

	public Ticket getTicketSummaryByUUID(String uuid, Long orgLocationId);

//	public TicketComment getTicketSummaryByTicketId(Long ticketId, long userId, Long orgLocationId);

	public List<TicketComment> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId);

	//public Boolean saveTicketCommentListByTicketId(TicketComment ticketComment);

	public TicketComment getTicketCommentByTicketId(long ticketId);
	
	public TicketComment getTicketComment(long ticketCommentId);

	public Boolean saveTicketComment(TicketComment ticketComment);
	
	public Boolean deleteTicketComment(long ticketCommentId);

	public Ticket getTicketDataById(long ticketId);

	public Boolean changeTicketManagementStatusDataById(long id, int status, OrgLocations orgLocation);

	public boolean saveTicketStatusHistory(TicketStatusHistory ticketStatusHistory);

	public List<Long> getTicketAssignToIdList(long ticketId);

	public TicketAssignTo getTicketAssignToByUserIdAndTicket(long userId, long ticketId);
	
	public TicketCategoryAssignTo getTicketCategoryAssignToByUserIdAndTicket(long userId, long ticketCategoryId);

	public void deleteTicketAssignTo(TicketAssignTo ticketAssignTo);

	public Ticket getTicketSnoAndTicketId(String generatedId, Long ticketId, Long orgLocationsId);

	public Boolean validateTicketId(String generatedId, Long orgLocationsId);

	public List<User> getCategoryAssignToListByTicketCategoryId(long ticketCategoryId,Long orgLocationId, long userId);

	public TicketAttachment getTicketAttachmentByTicketIdAndUUID(long ticketId, String uuid);

	public List<String> getSavedTicketAttachmentUUIDList(long ticketId);

	public void deleteTicketAttachment(TicketAttachment ticketAttachment);

	public boolean changeTicketManagementStatus(long ticketId, int status);
	
	public List<Long> getTicketCategoryAssignToIdList(long ticketCategoryId);
	
	public void deleteTicketCategoryAssignTo(TicketCategoryAssignTo ticketCategoryAssignTo);

	

}