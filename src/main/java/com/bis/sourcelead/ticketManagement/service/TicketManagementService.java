package com.bis.sourcelead.ticketManagement.service;

import java.util.List;

import com.bis.sourcelead.dto.TicketAttachmentDTO;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketCommentDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface TicketManagementService {

	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations();

	public List<TicketTypeDTO> getTicketTypeListByOrgLocationId(Long orgLocationId, int status);

	public boolean saveTicketType(TicketTypeDTO ticketTypeDTO, long userId, OrgLocations loginOrgLocation);

	public TicketTypeDTO getTicketTypeDataById(long id, long userId, OrgLocations loginOrgLocation);

	public boolean deleteTicketTypeDataById(long id);

	public List<TicketCategoryDTO> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status);

	public Boolean saveTicketCategory(TicketCategoryDTO ticketCategoryDTO, long userId, OrgLocations loginOrgLocation);

	public TicketCategoryDTO getTicketCategoryDataById(long ticketCategoryId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketCategoryDataById(long ticketCategoryId);

	public List<TicketPriorityDTO> getTicketPriorityListByOrgLocationId(Long orgLocationId, int status);

	public Boolean saveTicketPriority(TicketPriorityDTO ticketPriorityDTO, long userId, OrgLocations loginOrgLocation);

	public TicketPriorityDTO getTicketPriorityDataById(long ticketPriorityId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketPriorityDataById(long ticketPriorityId);

	public List<TicketStatusDTO> getTicketStatusListByOrgLocationId(Long orgLocationId, int status);

	public Boolean saveTicketStatus(TicketStatusDTO ticketStatusDTO, long userId, OrgLocations loginOrgLocation);

	public TicketStatusDTO getTicketStatusDataById(long ticketPriorityId, OrgLocations loginOrgLocation);

	public Boolean deleteTicketStatusDataById(long ticketStatusId);

	// public List<TicketDTO> getTicketManagementList(long ticketTypeId);

	public Boolean saveTicket(TicketDTO ticketDTO, User user, OrgLocations orgLocations);

	public Boolean changeStatusTicketTypeDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation);

	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation);

	public List<TicketDTO> getTicketManagementListByOrgLocationId(Long orgLocationId, long userId, String dateFormat);

	public TicketDTO getTicketSummaryByUUID(String uuid, long userId, Long orgLocationId, String dateFormat);

	public List<TicketCommentDTO> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId, String dateFormat);
	
	public Boolean saveTicketComment(TicketCommentDTO ticketCommentDTO, User user);

	public List<TicketAttachmentDTO> getTicketAttachmentStatusByTicketId(Long ticketId);

	public TicketDTO getTicketDataById(long ticketId, String dateFormat);

	

}
