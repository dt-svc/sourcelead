package com.bis.sourcelead.ticketManagement.service;

import java.util.List;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketCommentDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketStatusHistoryDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.dto.Response;

public interface TicketManagementService {

	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations(long orgLocationId);

	public List<TicketTypeDTO> getTicketTypeListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketTypeDTO ticketTypeSorting);

	public List<TicketType> getTicketTypeListByOrgLocationIdAndStatus(Long orgLocationId, int status) throws Exception;
	
	public List<TicketCategory> getTicketCategoryListByOrgLocationIdAndStatus(Long orgLocationId, int status) throws Exception;
	
	public List<TicketPriority> getTicketPriorityListByOrgLocationIdAndStatus(Long orgLocationId, int status) throws Exception;

	public List<TicketStatus> getTicketStatusListByOrgLocationIdAndStatus(Long orgLocationId, int status)throws Exception; 
	
	public long saveTicketType(TicketTypeDTO ticketTypeDTO, long userId, OrgLocations loginOrgLocation);

	public TicketTypeDTO getTicketTypeDataById(long id, long userId, OrgLocations loginOrgLocation);

	public boolean deleteTicketTypeDataById(long id);

	public List<TicketCategoryDTO> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketCategoryDTO ticketCategorySorting);

	public long saveTicketCategory(TicketCategoryDTO ticketCategoryDTO, User user, long userId, OrgLocations loginOrgLocation);

	public TicketCategoryDTO getTicketCategoryDataById(long ticketCategoryId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketCategoryDataById(long ticketCategoryId);

	public List<TicketPriorityDTO> getTicketPriorityListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketPriorityDTO ticketPrioritySorting);

	public long saveTicketPriority(TicketPriorityDTO ticketPriorityDTO, long userId, OrgLocations loginOrgLocation);

	public TicketPriorityDTO getTicketPriorityDataById(long ticketPriorityId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketPriorityDataById(long ticketPriorityId);

	public List<TicketStatusDTO> getTicketStatusListByOrgLocationId(Long orgLocationId, int status, String searchValue,TicketStatusDTO ticketStatusSorting);

	public long saveTicketStatus(TicketStatusDTO ticketStatusDTO, long userId, OrgLocations loginOrgLocation);

	public TicketStatusDTO getTicketStatusDataById(long ticketPriorityId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketStatusDataById(long ticketStatusId);

	// public List<TicketDTO> getTicketManagementList(long ticketTypeId);

	public Boolean saveTicket(TicketDTO ticketDTO, User user, OrgLocations orgLocations);

	public Boolean changeStatusTicketTypeDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation);

	public List<TicketDTO> getTicketManagementListByOrgLocationId(Long orgLocationId, long userId, int status, String dateFormat, String userRole, String searchValue);

	public TicketDTO getTicketSummaryByUUID(String uuid, Long orgLocationId, String dateFormat, boolean includeAttachments);

	public List<TicketCommentDTO> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId, String dateFormat);

	public Boolean saveTicketComment(TicketCommentDTO ticketCommentDTO, User user, OrgLocations orgLocations);

	public TicketCommentDTO getTicketComment(long ticketCommentId, String dateFormat);

	public Boolean deleteTicketComment(long ticketCommentId);

	public TicketDTO getTicketDataById(long ticketId, String dateFormat);

	public Boolean changeTicketManagementStatusDataById(long id, int status, OrgLocations orgLocation);

	public Response<Boolean> changeTicketStatus(TicketStatusHistoryDTO ticketStatusHistoryDTO, User user, OrgLocations orgLocation);

	public Ticket getTicketSnoAndTicketId(String ticketSno, Long ticketId, Long orgLocationsId);

	public Boolean validateTicketId(String ticketSno, Long orgLocationsId);

	public List<UserDTO> getCategoryAssignToListByTicketCategoryId(long ticketCategoryId, long userId, Long orgLocationId);

	public boolean changeTicketManagementStatus(long ticketId, int status);

}
