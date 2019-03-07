package com.bis.sourcelead.ticketManagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.TicketAssignToDTO;
import com.bis.sourcelead.dto.TicketAttachmentDTO;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketCommentDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketStatusHistoryDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketAssignTo;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketCategoryAssignTo;
import com.bis.sourcelead.entity.TicketComment;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketStatusHistory;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.ticketManagement.dao.TicketManagementDao;
import com.bis.sourcelead.ticketManagement.service.TicketManagementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;


@Service
public class TicketManagementServiceImpl implements TicketManagementService {

	@Autowired
	private TicketManagementDao ticketManagementDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private EmailScenarioService emailScenarioService;
	
	@Autowired
	private EmailScenerioUtil emailScenerioUtil;

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;

	@Override
	public int dumpTicketManagementPreDefinedValuesToAllOrgLocations() {
		try {
			return ticketManagementDao.dumpTicketManagementPreDefinedValuesToAllOrgLocations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<TicketTypeDTO> getTicketTypeListByOrgLocationId(Long orgLocationId, int status) {
		List<TicketTypeDTO> ticketTypeDTOList = new LinkedList<>();
		try {
			List<TicketType> ticketTypeList = ticketManagementDao.getTicketTypeListByOrgLocationId(orgLocationId, status);
			if (ticketTypeList != null && !ticketTypeList.isEmpty()) {
				for (TicketType ticketType : ticketTypeList) {
					TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
					ticketTypeDTO.setId(ticketType.getId());
					ticketTypeDTO.setUuid(ticketType.getUuid());
					ticketTypeDTO.setName(ticketType.getName());
					ticketTypeDTO.setDescription(ticketType.getDescription());
					ticketTypeDTO.setStatus(ticketType.getStatus());
					ticketTypeDTO.setOrgLocationsId(ticketType.getOrgLocations().getId());
					ticketTypeDTO.setCreatedBy(ticketType.getCreatedBy());
					ticketTypeDTO.setCreatedDate(ticketType.getCreatedDate());
					ticketTypeDTO.setUpdatedBy(ticketType.getUpdatedBy());
					ticketTypeDTO.setUpdatedDate(ticketType.getUpdatedDate());
					ticketTypeDTOList.add(ticketTypeDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketTypeDTOList;
	}

	@Override
	public boolean saveTicketType(TicketTypeDTO ticketTypeDTO, long userId, OrgLocations loginOrgLocation) {
		try {
			TicketType ticketType = null;
			if (ticketTypeDTO.getId() > 0) {
				ticketType = ticketManagementDao.getTicketTypeById(ticketTypeDTO.getId());
				ticketType.setUpdatedBy(userId);
				ticketType.setUpdatedDate(new Date());
			} else {
				ticketType = new TicketType();
				ticketType.setCreatedBy(userId);
				ticketType.setCreatedDate(new Date());
				ticketType.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketType.setStatus(1);
				ticketType.setOrgLocations(loginOrgLocation);
			}
			ticketType.setName(ticketTypeDTO.getName());
			ticketType.setDescription(ticketTypeDTO.getDescription());
			return ticketManagementDao.saveTicketType(ticketType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TicketTypeDTO getTicketTypeDataById(long id, long userId, OrgLocations loginOrgLocation) {
		TicketTypeDTO ticketTypeDTO = new TicketTypeDTO();
		try {
			TicketType ticketType = ticketManagementDao.getTicketTypeById(id);
			if (ticketType != null) {
				ticketTypeDTO.setId(ticketType.getId());
				ticketTypeDTO.setUuid(ticketType.getUuid());
				ticketTypeDTO.setName(ticketType.getName());
				ticketTypeDTO.setDescription(ticketType.getDescription());
				ticketTypeDTO.setStatus(ticketType.getStatus());
				ticketTypeDTO.setOrgLocationsId(ticketType.getOrgLocations().getId());
				ticketTypeDTO.setCreatedBy(ticketType.getCreatedBy());
				ticketTypeDTO.setCreatedDate(ticketType.getCreatedDate());
				ticketTypeDTO.setUpdatedBy(ticketType.getUpdatedBy());
				ticketTypeDTO.setUpdatedDate(ticketType.getUpdatedDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketTypeDTO;
	}

	@Override
	public boolean deleteTicketTypeDataById(long id) {
		return ticketManagementDao.deleteTicketTypeDataById(id);
	}

	@Override
	public List<TicketCategoryDTO> getTicketCategoryListByOrgLocationId(Long orgLocationId, int status) {
		List<TicketCategoryDTO> ticketCategoryDTOList = new LinkedList<>();
		try {
			List<TicketCategory> ticketCategoryList = ticketManagementDao.getTicketCategoryListByOrgLocationId(orgLocationId,status);
		if (ticketCategoryList != null && !ticketCategoryList.isEmpty()) {
				for (TicketCategory ticketCategory : ticketCategoryList) {
					TicketCategoryDTO ticketCategoryDTO = new TicketCategoryDTO();
					ticketCategoryDTO.setId(ticketCategory.getId());
					ticketCategoryDTO.setUuid(ticketCategory.getUuid());
					ticketCategoryDTO.setName(ticketCategory.getName());
					ticketCategoryDTO.setDescription(ticketCategory.getDescription());
					ticketCategoryDTO.setStatus(ticketCategory.getStatus());
					ticketCategoryDTO.setOrgLocationsId(ticketCategory.getOrgLocations().getId());
					List<String> assignedToList = new LinkedList<>();
					for(TicketCategoryAssignTo ticketCategoryAssignTo : ticketCategory.getTicketCategoryAssignToList()) {
						assignedToList.add(ticketCategoryAssignTo.getUser().getFullName());
					}
					ticketCategoryDTO.setAssignedTo(assignedToList);
					ticketCategoryDTO.setNotifyTo(ticketCategory.getNotifyTo());
					ticketCategoryDTO.setCreatedById(ticketCategory.getCreatedBy());
					ticketCategoryDTO.setCreatedDate(ticketCategory.getCreatedDate());
					ticketCategoryDTO.setUpdatedById(ticketCategory.getUpdatedBy());
					ticketCategoryDTO.setUpdatedDate(ticketCategory.getUpdatedDate());
					ticketCategoryDTOList.add(ticketCategoryDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketCategoryDTOList;
	}

	public Boolean saveTicketCategory(TicketCategoryDTO ticketCategoryDTO, User user, long userId, OrgLocations loginOrgLocation) {
		try {
			TicketCategory ticketCategory = null;
			if (ticketCategoryDTO.getId() > 0) {
				ticketCategory = ticketManagementDao.getTicketCategoryById(ticketCategoryDTO.getId());
				ticketCategory.setUpdatedBy(userId);
				ticketCategory.setUpdatedDate(new Date());
			} else {
				ticketCategory = new TicketCategory();
				ticketCategory.setCreatedBy(userId);
				ticketCategory.setCreatedDate(new Date());
				ticketCategory.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketCategory.setStatus(1);
				ticketCategory.setOrgLocations(loginOrgLocation);
			}
			ticketCategory.setName(ticketCategoryDTO.getName());
			ticketCategory.setDescription(ticketCategoryDTO.getDescription());
			ticketCategory.setNotifyTo(ticketCategoryDTO.getNotifyTo());
			List<TicketCategoryAssignTo> assignToList = new LinkedList<>();
			if(ticketCategoryDTO.getAssignedTo() != null) {
				for(String assignUserId : ticketCategoryDTO.getAssignedTo()) {
					User assignUser = userDao.getUserById(Long.parseLong(assignUserId));
					TicketCategoryAssignTo assignTo = new TicketCategoryAssignTo();
					assignTo.setUser(assignUser);
					assignTo.setTicketCategory(ticketCategory);
					assignTo.setCreatedBy(userId);
					assignTo.setCreatedDate(new Date());
					assignToList.add(assignTo);
				}
			}
			List<Long> deleteAssignToIdList = ticketManagementDao.getTicketAssignToIdList(ticketCategory.getId());
			List<TicketCategoryAssignTo> categoryAssignToList = new LinkedList<>();
			if(ticketCategoryDTO.getAssignedTo() != null) {
				for(String assignUserId : ticketCategoryDTO.getAssignedTo()) {
					User assignedUser = userDao.getUserById(Long.parseLong(assignUserId));
					TicketCategoryAssignTo assignTo = ticketManagementDao.getTicketCategoryAssignToByUserIdAndTicket(assignedUser.getId(), ticketCategory.getId());
					if (assignTo == null) {
						assignTo = new TicketCategoryAssignTo();
						assignTo.setCreatedBy(user.getId());
						assignTo.setCreatedDate(new Date());
					} else {
						assignTo.setUpdatedBy(user.getId());
						assignTo.setUpdatedDate(new Date());
					}
					assignTo.setUser(assignedUser);
					assignTo.setTicketCategory(ticketCategory);
					categoryAssignToList.add(assignTo);
					//send Assign email & notification
					deleteAssignToIdList.remove(assignTo.getUser().getId());
				}
			}
			
			for(Long assignUserId : deleteAssignToIdList) {
				TicketAssignTo assignTo = ticketManagementDao.getTicketAssignToByUserIdAndTicket(assignUserId, ticketCategory.getId());
				ticketManagementDao.deleteTicketAssignTo(assignTo);
			}
			
			ticketCategory.setTicketCategoryAssignToList(assignToList);
			return ticketManagementDao.saveTicketCategory(ticketCategory);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TicketCategoryDTO getTicketCategoryDataById(long ticketCategoryId, OrgLocations loginOrgLocation) {
		TicketCategoryDTO ticketCategoryDTO = new TicketCategoryDTO();
		try {
			TicketCategory ticketCategory = ticketManagementDao.getTicketCategoryById(ticketCategoryId);
			if (ticketCategory != null) {
				ticketCategoryDTO.setId(ticketCategory.getId());
				ticketCategoryDTO.setUuid(ticketCategory.getUuid());
				ticketCategoryDTO.setName(ticketCategory.getName());
				ticketCategoryDTO.setDescription(ticketCategory.getDescription());
				ticketCategoryDTO.setStatus(ticketCategory.getStatus());
				List<String> assignedToList = new LinkedList<>();
				for(TicketCategoryAssignTo ticketCategoryAssignTo : ticketCategory.getTicketCategoryAssignToList()) {
					assignedToList.add(ticketCategoryAssignTo.getUser().getId() + "");
				}
				ticketCategoryDTO.setAssignedTo(assignedToList);
				ticketCategoryDTO.setNotifyTo(ticketCategory.getNotifyTo());
				ticketCategoryDTO.setOrgLocationsId(ticketCategory.getOrgLocations().getId());
				ticketCategoryDTO.setCreatedById(ticketCategory.getCreatedBy());
				ticketCategoryDTO.setCreatedDate(ticketCategory.getCreatedDate());
				ticketCategoryDTO.setUpdatedById(ticketCategory.getUpdatedBy());
				ticketCategoryDTO.setUpdatedDate(ticketCategory.getUpdatedDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketCategoryDTO;
	}

	@Override
	public Boolean deleteTicketCategoryDataById(long id) {
		return ticketManagementDao.deleteTicketCategoryDataById(id);
		
	}

	@Override
	public List<TicketPriorityDTO> getTicketPriorityListByOrgLocationId(Long orgLocationId,int status) {
		List<TicketPriorityDTO> ticketPriorityDTOList = new LinkedList<>();
		try {
			List<TicketPriority> ticketPriorityList = ticketManagementDao.getTicketPriorityListByOrgLocationId(orgLocationId,status);
			if (ticketPriorityList != null && !ticketPriorityList.isEmpty()) {
				for (TicketPriority ticketpriority : ticketPriorityList) {
					TicketPriorityDTO ticketPriorityDTO = new TicketPriorityDTO();
					ticketPriorityDTO.setId(ticketpriority.getId());
					ticketPriorityDTO.setUuid(ticketpriority.getUuid());
					ticketPriorityDTO.setName(ticketpriority.getName());
					ticketPriorityDTO.setDescription(ticketpriority.getDescription());
					ticketPriorityDTO.setColorCode(ticketpriority.getColorCode());
					ticketPriorityDTO.setFileName(ticketpriority.getFileName());
					ticketPriorityDTO.setFileURL(ticketpriority.getFileURL());
					ticketPriorityDTO.setFileContentType(ticketpriority.getFileContentType());
					ticketPriorityDTO.setStatus(ticketpriority.getStatus());
					ticketPriorityDTO.setOrgLocationsId(ticketpriority.getOrgLocations().getId());
					ticketPriorityDTO.setCreatedBy(ticketpriority.getCreatedBy());
					ticketPriorityDTO.setCreatedDate(ticketpriority.getCreatedDate());
					ticketPriorityDTO.setUpdatedBy(ticketpriority.getUpdatedBy());
					ticketPriorityDTO.setUpdatedDate(ticketpriority.getUpdatedDate());
					ticketPriorityDTOList.add(ticketPriorityDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketPriorityDTOList;
	}

	@Override
	public Boolean saveTicketPriority(TicketPriorityDTO ticketPriorityDTO, long userId, OrgLocations loginOrgLocation) {
		try {
			TicketPriority ticketpriority = null;
			if (ticketPriorityDTO.getId() > 0) {
				ticketpriority = ticketManagementDao.getTicketPriorityById(ticketPriorityDTO.getId());
				ticketpriority.setUpdatedBy(userId);
				ticketpriority.setUpdatedDate(new Date());
			} else {
				ticketpriority = new TicketPriority();
				ticketpriority.setCreatedBy(userId);
				ticketpriority.setCreatedDate(new Date());
				ticketpriority.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketpriority.setStatus(1);
				ticketpriority.setOrgLocations(loginOrgLocation);
			}
			ticketpriority.setName(ticketPriorityDTO.getName());
			ticketpriority.setDescription(ticketPriorityDTO.getDescription());
			ticketpriority.setColorCode(ticketPriorityDTO.getColorCode());
			if (ticketPriorityDTO.getFileInfo() != null && !ticketPriorityDTO.getFileInfo().isEmpty()) {
				FileInfo fileInfo = ticketPriorityDTO.getFileInfo().get(0);
				ticketpriority.setFileName(fileInfo.getFileName());
				ticketpriority.setFileContentType(fileInfo.getContentType());
				ticketpriority.setFileURL(fileInfo.getFileURL());
				FileUploadUtil.moveFile(ticketPriorityDTO.getStaticPath(), fileInfo, "ticketPriority");
			}
			return ticketManagementDao.saveTicketPriority(ticketpriority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TicketPriorityDTO getTicketPriorityDataById(long ticketPriorityId, OrgLocations loginOrgLocation) {
		TicketPriorityDTO ticketPriorityDTO = new TicketPriorityDTO();
		try {
			TicketPriority ticketpriority = ticketManagementDao.getTicketPriorityById(ticketPriorityId);
			if (ticketpriority != null) {
				ticketPriorityDTO.setId(ticketpriority.getId());
				ticketPriorityDTO.setUuid(ticketpriority.getUuid());
				ticketPriorityDTO.setName(ticketpriority.getName());
				ticketPriorityDTO.setDescription(ticketpriority.getDescription());
				ticketPriorityDTO.setColorCode(ticketpriority.getColorCode());
				ticketPriorityDTO.setFileName(ticketpriority.getFileName());
				ticketPriorityDTO.setFileURL(ticketpriority.getFileURL());
				ticketPriorityDTO.setFileContentType(ticketpriority.getFileContentType());
				ticketPriorityDTO.setStatus(ticketpriority.getStatus());
				ticketPriorityDTO.setOrgLocationsId(ticketpriority.getOrgLocations().getId());
				ticketPriorityDTO.setCreatedBy(ticketpriority.getCreatedBy());
				ticketPriorityDTO.setCreatedDate(ticketpriority.getCreatedDate());
				ticketPriorityDTO.setUpdatedBy(ticketpriority.getUpdatedBy());
				ticketPriorityDTO.setUpdatedDate(ticketpriority.getUpdatedDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketPriorityDTO;
	}

	@Override
	public Boolean deleteTicketPriorityDataById(long id) {
		return ticketManagementDao.deleteTicketPriorityDataById(id);
	}

	@Override
	public List<TicketStatusDTO> getTicketStatusListByOrgLocationId(Long orgLocationId,int status) {
		List<TicketStatusDTO> ticketStatusDTOList = new LinkedList<>();
		try {
			List<TicketStatus> ticketStatusList = ticketManagementDao.getTicketStatusListByOrgLocationId(orgLocationId,status);
			if (ticketStatusList != null && !ticketStatusList.isEmpty()) {
				for (TicketStatus ticketStatus : ticketStatusList) {
					TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
					ticketStatusDTO.setId(ticketStatus.getId());
					ticketStatusDTO.setUuid(ticketStatus.getUuid());
					ticketStatusDTO.setName(ticketStatus.getName());
					ticketStatusDTO.setDescription(ticketStatus.getDescription());
					ticketStatusDTO.setStatus(ticketStatus.getStatus());
					ticketStatusDTO.setCommentRequired(ticketStatus.isCommentRequired());
					ticketStatusDTO.setOrgLocationsId(ticketStatus.getOrgLocations().getId());
					ticketStatusDTO.setCreatedBy(ticketStatus.getCreatedBy());
					ticketStatusDTO.setCreatedDate(ticketStatus.getCreatedDate());
					ticketStatusDTO.setUpdatedBy(ticketStatus.getUpdatedBy());
					ticketStatusDTO.setUpdatedDate(ticketStatus.getUpdatedDate());
					ticketStatusDTOList.add(ticketStatusDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketStatusDTOList;
}

	@Override
	public Boolean saveTicketStatus(TicketStatusDTO ticketStatusDTO,long userId,  OrgLocations orgLocations) {
		try {
			TicketStatus ticketStatus = null;
			if (ticketStatusDTO.getId() > 0) {
				ticketStatus = ticketManagementDao.getTicketStatusById(ticketStatusDTO.getId());
				ticketStatus.setUpdatedBy(userId);
				ticketStatus.setUpdatedDate(new Date());
			} else {
				ticketStatus = new TicketStatus();
				ticketStatus.setCreatedBy(userId);
				ticketStatus.setCreatedDate(new Date());
				ticketStatus.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketStatus.setStatus(1);
				ticketStatus.setOrgLocations(orgLocations);
			}
			ticketStatus.setName(ticketStatusDTO.getName());
			ticketStatus.setDescription(ticketStatusDTO.getDescription());
			ticketStatus.setCommentRequired(ticketStatusDTO.isCommentRequired());
			return ticketManagementDao.saveTicketStatus(ticketStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		}

	@Override
	public TicketStatusDTO getTicketStatusDataById(long ticketStatusId, OrgLocations loginOrgLocation) {
		TicketStatusDTO ticketStatusDTO = new TicketStatusDTO();
		try {
			TicketStatus ticketStatus = ticketManagementDao.getTicketStatusById(ticketStatusId);
			if (ticketStatus != null) {
				ticketStatusDTO.setId(ticketStatus.getId());
				ticketStatusDTO.setUuid(ticketStatus.getUuid());
				ticketStatusDTO.setName(ticketStatus.getName());
				ticketStatusDTO.setDescription(ticketStatus.getDescription());
				ticketStatusDTO.setStatus(ticketStatus.getStatus());
				ticketStatusDTO.setCommentRequired(ticketStatus.isCommentRequired());
				ticketStatusDTO.setOrgLocationsId(ticketStatus.getOrgLocations().getId());
				ticketStatusDTO.setCreatedBy(ticketStatus.getCreatedBy());
				ticketStatusDTO.setCreatedDate(ticketStatus.getCreatedDate());
				ticketStatusDTO.setUpdatedBy(ticketStatus.getUpdatedBy());
				ticketStatusDTO.setUpdatedDate(ticketStatus.getUpdatedDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketStatusDTO;
	}

	@Override
	public Boolean deleteTicketStatusDataById(long id) {
		return  ticketManagementDao.deleteTicketStatusDataById(id);
	}


	@Override
	@SuppressWarnings("unused")
	public Boolean saveTicket(TicketDTO ticketDTO, User user, OrgLocations orgLocations) {
		try {
			Ticket ticketManagement = null;
			if (ticketDTO.getId() > 0) {
				ticketManagement = ticketManagementDao.getTicketById(ticketDTO.getId());
				ticketManagement.setUpdatedBy(user);
				ticketManagement.setUpdatedDate(new Date());
			} else {
				ticketManagement = new Ticket();
				ticketManagement.setCreatedBy(user);
				ticketManagement.setCreatedDate(new Date());
				ticketManagement.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketManagement.setOrgLocations(orgLocations);
				ticketManagement.setStatus(1);
			}
			ticketManagement.setTicketType(ticketManagementDao.getTicketTypeById(ticketDTO.getTicketTypeId()));
			ticketManagement.setTicketCategory(ticketManagementDao.getTicketCategoryById(ticketDTO.getTicketCategoryId()));
			ticketManagement.setTicketPriority(ticketManagementDao.getTicketPriorityById(ticketDTO.getTicketPriorityId()));
			ticketManagement.setTicketStatus(ticketManagementDao.getTicketStatusById(ticketDTO.getTicketStatusId()));
			ticketManagement.setDescription(ticketDTO.getDescription());
			ticketManagement.setTitle(ticketDTO.getTitle());
			ticketManagement.setGeneratedId(ticketDTO.getGeneratedId());
			ticketManagement.setNotifyTo(ticketDTO.getNotifyTo());
			
			List<Long> deleteAssignToIdList = ticketManagementDao.getTicketAssignToIdList(ticketManagement.getId());
			List<TicketAssignTo> assignToList = new LinkedList<>();
			if(ticketDTO.getAssignedTo() != null) {
				for(String assignUserId : ticketDTO.getAssignedTo()) {
					User assignedUser = userDao.getUserById(Long.parseLong(assignUserId));
					TicketAssignTo assignTo = ticketManagementDao.getTicketAssignToByUserIdAndTicket(assignedUser.getId(), ticketManagement.getId());
					if (assignTo == null) {
						assignTo = new TicketAssignTo();
						assignTo.setCreatedBy(user.getId());
						assignTo.setCreatedDate(new Date());
					} else {
						assignTo.setUpdatedBy(user.getId());
						assignTo.setUpdatedDate(new Date());
					}
					assignTo.setUser(assignedUser);
					assignTo.setTicket(ticketManagement);
					assignToList.add(assignTo);
					deleteAssignToIdList.remove(assignTo.getUser().getId());
				}
			}
			
			for(Long assignUserId : deleteAssignToIdList) {
				TicketAssignTo assignTo = ticketManagementDao.getTicketAssignToByUserIdAndTicket(assignUserId, ticketManagement.getId());
				ticketManagementDao.deleteTicketAssignTo(assignTo);
			}
			ticketManagement.setTicketAssignToList(assignToList);
			Boolean issaved = ticketManagementDao.saveTicket(ticketManagement);
			
			
			if(issaved && ticketDTO.getId() == 0) {
				//send Assign email & notification
				if(ticketDTO.getAssignedTo() != null) {
					for(String assignUserId : ticketDTO.getAssignedTo()) {
						User toUser = userDao.getUserById(Long.parseLong(assignUserId));
						sendTicketManagementEmailToAssignedUsers(user,orgLocations,ticketManagement,toUser);
					}
				}
				
				//send notifyTo email & Notification
				if(ticketDTO.getNotifyTo() != null && !ticketDTO.getNotifyTo().isEmpty()) {
					for(String notifyToEmail : ticketDTO.getNotifyTo().split(",")) {
//						sendTicketManagementEmailToNotifyUsers(user,orgLocations,ticketManagement,notifyToEmail);
					}
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/*
	 * @Override public TicketDTO getTicketDataById(long ticketId, OrgLocations
	 * loginOrgLocation, String dateFormat) { TicketDTO ticketDTO = new TicketDTO();
	 * try { SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); Ticket ticket
	 * = ticketManagementDao.getTicketById(ticketId); if (ticket != null) {
	 * ticketDTO.setId(ticket.getId()); ticketDTO.setUuid(ticket.getUuid());
	 * ticketDTO.setTitle(ticket.getTitle());
	 * ticketDTO.setTicketTypeId(ticket.getTicketType().getId());
	 * ticketDTO.setTicketStatusId(ticket.getTicketStatus().getId());
	 * ticketDTO.setTicketPriorityId(ticket.getTicketPriority().getId());
	 * ticketDTO.setTicketCategoryId(ticket.getTicketCategory().getId());
	 * ticketDTO.setDescription(ticket.getDescription());
	 * ticketDTO.setGeneratedId(ticket.getGeneratedId()); List<String>
	 * assignedToList = new LinkedList<>(); for (TicketAssignTo ticketAssignTo :
	 * ticket.getTicketAssignToList()) {
	 * assignedToList.add(ticketAssignTo.getUser().getFullName()); }
	 * ticketDTO.setAssignedTo(assignedToList);
	 * 
	 * ticketDTO.setOrgLocationsId(ticket.getOrgLocations().getId());
	 * ticketDTO.setCreatedById(ticket.getCreatedBy().getId());
	 * ticketDTO.setCreatedDate(sdf.format(ticket.getCreatedDate())); if
	 * (ticket.getUpdatedBy() != null) {
	 * ticketDTO.setUpdatedById(ticket.getUpdatedBy().getId());
	 * ticketDTO.setUpdatedDate(sdf.format(ticket.getUpdatedDate())); }
	 * 
	 * } } catch (Exception e) { e.printStackTrace(); } return ticketDTO; }
	 */

	private void sendTicketManagementEmailToAssignedUsers(User loginUser, OrgLocations orgLocations, Ticket ticketManagement, User toUser) throws Exception {

		EmailMessage emailMessage = new EmailMessage();
		emailMessage.setFirstName(loginUser.getFirstName());
		emailMessage.setLastName(loginUser.getLastName());
		emailMessage.setToEmail(toUser.getEmail());
		emailMessage.setFromEmail(loginUser.getEmail());
		emailMessage.setCreatedBy(loginUser.getUserCode());
		StringBuilder msgBuild = new StringBuilder();
		emailMessage.setFromName(msgBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
		emailMessage.setEmailType(Constants.CUSTOM_USER_EMAIL);
		
		
			// setting the class filed values
			Map<String, String> globalVarsMap = new HashMap<String, String>();
		
			if(toUser.getLastName() != null && toUser.getLastName() !=""){
			StringBuilder fNameBulid= new StringBuilder();
				globalVarsMap.put("AssignTo", fNameBulid.append(toUser.getFirstName()).append(" ").append(toUser.getLastName()).toString());
			}else{
				globalVarsMap.put("AssignTo", toUser.getFirstName());
			}
			
			
		
			globalVarsMap.put("TicketTitle", ticketManagement.getTitle());
			
			
			globalVarsMap.put("TicketId", ticketManagement.getGeneratedId());
			
			globalVarsMap.put("CategoryName", ticketManagement.getTicketCategory().getName());
			
			globalVarsMap.put("Type", ticketManagement.getTicketType().getName());
			
			globalVarsMap.put("Priority", ticketManagement.getTicketPriority().getName());
			
			StringBuilder loginBuilder = new StringBuilder();
			globalVarsMap.put("Login",loginBuilder.append("<a href='").append(sourceleadUrl).append("' style='text-decoration:none; '><div style=' width:100px; border-radius:30px; background-color:#a21e23; color:#FFF; font-size:15px; font-weight:500; margin:10px auto 5px auto; padding:5px 0px 10px; text-align:center;'>Login</div></a>").toString());
			
			
			if(StringUtils.isNotBlank(loginUser.getFirstName())){
				StringBuilder loginUserBuild= new StringBuilder();
					globalVarsMap.put("LoginUser", loginUserBuild.append(loginUser.getFirstName()).append(" ").append(loginUser.getLastName()).toString());
				}else{
					globalVarsMap.put("LoginUser", loginUser.getFirstName());
				}
			
			if(StringUtils.isNotBlank(orgLocations.getOrgLocationName())){
					globalVarsMap.put("LoginOrg", orgLocations.getOrgLocationName());
				}
			
			commonUtil.sendTemplateEmail(emailMessage, globalVarsMap, orgLocations.getOrg(), orgLocations, Constants.TICKET_MANAGEMENT_EMAIL,new ArrayList<EmailAttachment>());
		
			/*send notification*/
			
			//find required notification scenarios
			EmailScenario notificationScenario = emailScenarioService.getEmailScenarioByUniqueName(
					Constants.TICKET_MANAGEMENT_NOTIFICATION, Constants.ENTITY_TYPE_NOTIFICATION);
			
			//set notification body
			String notificationbody = emailScenerioUtil.buildTemplateBody(notificationScenario.getBody(), globalVarsMap,
					notificationScenario);
			notificationScenario.setBody(notificationbody);
						
			// calling the sendEmail method of emailScenerioUtil
			StringBuilder notificationLink = new StringBuilder();
			String actionLink = "";
			
		/*	actionLink = notificationLink.append(sourceleadUrl).append("/")
					.append("requirementSummary?requirementUUID=" + requirement.getRequirementUUID()).toString();*/

			emailScenerioUtil.sendNotification(actionLink, loginUser, toUser,orgLocations,
					notificationScenario, new HashMap<String, String>());
			
	}

	@Override
	public Boolean changeStatusTicketTypeDataById(long id, int status, OrgLocations orgLocation) {
		return  ticketManagementDao.changeStatusTicketTypeDataById(id, status, orgLocation);
	}

	@Override
	public Boolean changeTicketCategoryStatusDataById(long id, int status, OrgLocations orgLocation) {
		return  ticketManagementDao.changeTicketCategoryStatusDataById(id, status, orgLocation);
	}

	@Override
	public Boolean changeTicketPriorityStatusDataById(long id, int status, OrgLocations orgLocation) {
		return  ticketManagementDao.changeTicketPriorityStatusDataById(id, status, orgLocation);
	}

	@Override
	public Boolean changeTicketStatusDataById(long id, int status, OrgLocations orgLocation) {
		return  ticketManagementDao.changeTicketStatusDataById(id, status, orgLocation);
	}

	@Override
	public List<TicketDTO> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId,int status, String dateFormat) {
		List<TicketDTO> ticketDTOList = new LinkedList<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			List<Ticket> ticketManagementList = ticketManagementDao.getTicketManagementListByOrgLocationId(orgLocationId,userId,status);
			if (ticketManagementList != null && !ticketManagementList.isEmpty()) {
				for (Ticket ticketManagement : ticketManagementList) {
					TicketDTO ticketDTO = new TicketDTO();
					ticketDTO.setId(ticketManagement.getId());
					ticketDTO.setGeneratedId(ticketManagement.getGeneratedId());
					ticketDTO.setTitle(ticketManagement.getTitle());
					ticketDTO.setUuid(ticketManagement.getUuid());
					ticketDTO.setTicketType(ticketManagement.getTicketType().getName());
					ticketDTO.setTicketCategory(ticketManagement.getTicketCategory().getName());
					ticketDTO.setTicketStatus(ticketManagement.getTicketStatus().getName());
					ticketDTO.setTicketPriority(ticketManagement.getTicketPriority().getName());
					ticketDTO.setTicketPriorityTextColor(ticketManagement.getTicketPriority().getColorCode());
					ticketDTO.setTicketPriorityImage(ticketManagement.getTicketPriority().getFileURL());
					ticketDTO.setCreatedById(ticketManagement.getCreatedBy().getId());
					ticketDTO.setCreatedBy(ticketManagement.getCreatedBy().getFullName());
					ticketDTO.setCreatedDate(sdf.format(ticketManagement.getCreatedDate()));
					if(ticketManagement.getUpdatedBy() != null) {
						ticketDTO.setUpdatedById(ticketManagement.getUpdatedBy().getId());
						ticketDTO.setUpdatedDate(sdf.format(ticketManagement.getUpdatedDate()));
					}
					
					List<String> assignedToList = new LinkedList<>();
					for(TicketAssignTo ticketCategoryAssignTo : ticketManagement.getTicketAssignToList()) {
						assignedToList.add(ticketCategoryAssignTo.getUser().getFullName());
					}
					ticketDTO.setAssignedTo(assignedToList);
					ticketDTOList.add(ticketDTO); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketDTOList;
	}

	@Override
	public TicketDTO getTicketSummaryByUUID(String uuid, long userId, Long orgLocationId, String dateFormat) {
		TicketDTO ticketDTO =  new TicketDTO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat();
			Ticket ticketManagement = ticketManagementDao.getTicketSummaryByUUID(uuid,userId,orgLocationId);
			if (ticketManagement != null) {
				ticketDTO.setId(ticketManagement.getId());
				ticketDTO.setUuid(ticketManagement.getUuid());
				ticketDTO.setGeneratedId(ticketManagement.getGeneratedId());
				ticketDTO.setTitle(ticketManagement.getTitle());
				ticketDTO.setDescription(ticketManagement.getDescription());
				if(ticketManagement.getTicketType() != null) {
					ticketDTO.setTicketTypeId(ticketManagement.getTicketType().getId());
					ticketDTO.setTicketType(ticketManagement.getTicketType().getName());
				}
				if(ticketManagement.getTicketCategory() != null) {
					ticketDTO.setTicketCategoryId(ticketManagement.getTicketCategory().getId());
					ticketDTO.setTicketCategory(ticketManagement.getTicketCategory().getName());
				}
				if(ticketManagement.getTicketPriority() != null) {
					ticketDTO.setTicketPriorityId(ticketManagement.getTicketPriority().getId());
					ticketDTO.setTicketPriority(ticketManagement.getTicketPriority().getName());
					ticketDTO.setTicketPriorityImage(ticketManagement.getTicketPriority().getFileURL());
				}
				if(ticketManagement.getTicketStatus() != null) {
					ticketDTO.setTicketStatusId(ticketManagement.getTicketStatus().getId());
					ticketDTO.setTicketStatus(ticketManagement.getTicketStatus().getName());
				}
				ticketDTO.setCreatedById(ticketManagement.getCreatedBy().getId());
				ticketDTO.setCreatedBy(ticketManagement.getCreatedBy().getFullName());
				ticketDTO.setCreatedDate(sdf.format(ticketManagement.getCreatedDate()));
				if(ticketManagement.getUpdatedBy() != null) {
					ticketDTO.setUpdatedById(ticketManagement.getUpdatedBy().getId());
					ticketDTO.setUpdatedDate(sdf.format(ticketManagement.getUpdatedDate()));
				}
				List<TicketAssignToDTO> assignedToList = new LinkedList<>();
				for(TicketAssignTo ticketAssignTo : ticketManagement.getTicketAssignToList()) {
					TicketAssignToDTO ticketAssignToDTO = new TicketAssignToDTO();
					ticketAssignToDTO.setId(ticketAssignTo.getId());
					ticketAssignToDTO.setUserId(ticketAssignTo.getUser().getId());
					ticketAssignToDTO.setUserName(ticketAssignTo.getUser().getFullName());
					ticketAssignToDTO.setUserImageURL(ticketAssignTo.getUser().getFileUrl());
					assignedToList.add(ticketAssignToDTO);
				}
				ticketDTO.setTicketAssignToList(assignedToList);
				ticketDTO.setNotifyTo(ticketManagement.getNotifyTo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketDTO;
	}

	@Override
	public List<TicketCommentDTO> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId, String dateFormat) {
		List<TicketCommentDTO> ticketCommentDTOList = new LinkedList<>();
		try {
			List<TicketComment> ticketCommentList = ticketManagementDao.getTicketCommentListByTicketId(ticketId, userId, orgLocationId);
			if (ticketCommentList != null && !ticketCommentList.isEmpty()) {
				for (TicketComment ticketComment : ticketCommentList) {
					TicketCommentDTO ticketCommentDTO = new TicketCommentDTO();
					ticketCommentDTO.setId(ticketComment.getId());
					ticketCommentDTO.setTicketId(ticketComment.getTicket().getId());
					ticketCommentDTO.setComment(ticketComment.getComment());
					ticketCommentDTO.setStatus(ticketComment.getStatus());
					
					long diff = CommonUtil.currentDate().getTime() - ticketComment.getCreatedDate().getTime();
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					StringBuilder timeBuilder = new StringBuilder();
					if (diffDays == 0 && diffHours == 0 && diffMinutes == 0) {
						ticketCommentDTO.setCreatedDateString(timeBuilder.append(diffSeconds).append("seconds ago").toString());
					} else if (diffDays == 0 && diffHours == 0) {
						ticketCommentDTO.setCreatedDateString(timeBuilder.append(diffMinutes).append(" minutes ago").toString());
					} else if (diffDays == 0) {
						ticketCommentDTO.setCreatedDateString(timeBuilder.append(diffHours).append(" hours ").append(diffMinutes).append(" minutes ago").toString());
					} else {
						ticketCommentDTO.setCreatedDateString(timeBuilder.append(diffDays).append(" days ").append(diffHours).append(" hours ago").toString());
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormat + " hh:mm:ss aa");
					ticketCommentDTO.setCreatedDate(sdf.format(ticketComment.getCreatedDate()));
					ticketCommentDTO.setCreatedById(ticketComment.getCreatedBy().getId());
					ticketCommentDTO.setCreatedByName(ticketComment.getCreatedBy().getFullName());
					if(ticketComment.getUpdatedBy() != null) {
						ticketCommentDTO.setUpdatedById(ticketComment.getUpdatedBy().getId());
						ticketCommentDTO.setUpdatedByName(ticketComment.getUpdatedBy().getFullName());
						ticketCommentDTO.setUpdatedDate(sdf.format(ticketComment.getUpdatedDate()));
					}
					ticketCommentDTOList.add(ticketCommentDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketCommentDTOList;
	}

	@Override
	public Boolean saveTicketComment(TicketCommentDTO ticketCommentDTO, User user) {
		try {
			TicketComment ticketComment = null;
			if (ticketCommentDTO.getId() > 0) {
				ticketComment = ticketManagementDao.getTicketCommentByTicketId(ticketCommentDTO.getTicketId());
				ticketComment.setUpdatedBy(user);
				ticketComment.setUpdatedDate(new Date());
			} else {
				ticketComment = new TicketComment();
				ticketComment.setStatus(1);
				ticketComment.setCreatedBy(user);
				ticketComment.setCreatedDate(new Date());
			}
			ticketComment.setTicket(ticketManagementDao.getTicketById(ticketCommentDTO.getTicketId()));
			ticketComment.setComment(ticketCommentDTO.getComment());
			return ticketManagementDao.saveTicketComment(ticketComment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TicketAttachmentDTO> getTicketAttachmentStatusByTicketId(Long ticketId) {
		
		 return ticketManagementDao.getExpenseAttachmentStatusByCategoryId(ticketId);
	}

	@Override
	public TicketDTO getTicketDataById(long ticketId, String dateFormat) {
		TicketDTO ticketDTO = new TicketDTO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			Ticket ticket = ticketManagementDao.getTicketDataById(ticketId);
			if (ticket != null) {
				ticketDTO.setId(ticket.getId());
				ticketDTO.setUuid(ticket.getUuid());
				ticketDTO.setTitle(ticket.getTitle());
				ticketDTO.setTicketTypeId(ticket.getTicketType().getId());
				ticketDTO.setTicketStatusId(ticket.getTicketStatus().getId());
				ticketDTO.setTicketPriorityId(ticket.getTicketPriority().getId());
				ticketDTO.setTicketCategoryId(ticket.getTicketCategory().getId());
				ticketDTO.setDescription(ticket.getDescription());
				ticketDTO.setGeneratedId(ticket.getGeneratedId());
				ticketDTO.setCreatedById(ticket.getCreatedBy().getUserId());
				ticketDTO.setNotifyTo(ticket.getNotifyTo());
				List<String> assignedToList = new LinkedList<>();
				for (TicketAssignTo ticketAssignTo : ticket.getTicketAssignToList()) {
					assignedToList.add(ticketAssignTo.getUser().getFullName());
				}
				ticketDTO.setAssignedTo(assignedToList);

				ticketDTO.setOrgLocationsId(ticket.getOrgLocations().getId());
				ticketDTO.setCreatedById(ticket.getCreatedBy().getId());
				ticketDTO.setCreatedDate(sdf.format(ticket.getCreatedDate()));
				if (ticket.getUpdatedBy() != null) {
					ticketDTO.setUpdatedById(ticket.getUpdatedBy().getId());
					ticketDTO.setUpdatedDate(sdf.format(ticket.getUpdatedDate()));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketDTO;
	}

	@Override
	public Boolean changeTicketManagementStatusDataById(long id, int status, OrgLocations orgLocation) {
		return  ticketManagementDao.changeTicketManagementStatusDataById(id, status, orgLocation);
	}

	@Override
	public Response<Boolean> changeTicketStatus(TicketStatusHistoryDTO ticketStatusHistoryDTO, User user, OrgLocations orgLocation) {
		Response<Boolean> response = new Response<>();
		try {
			Ticket ticket = ticketManagementDao.getTicketById(ticketStatusHistoryDTO.getTicketId());
			TicketStatus ticketStatus = ticketManagementDao.getTicketStatusById(ticketStatusHistoryDTO.getStatusId());
			if(ticket != null && ticketStatus != null) {
				String previousStatus = ticket.getTicketStatus().getName();
				ticket.setTicketStatus(ticketStatus);
				ticket.setUpdatedBy(user);
				ticket.setUpdatedDate(new Date());
				boolean result = ticketManagementDao.saveTicket(ticket);
				response.setObject(result);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
				if(result) {
					TicketStatusHistory ticketStatusHistory = new TicketStatusHistory();
					ticketStatusHistory.setTicket(ticket);
					ticketStatusHistory.setComment(ticketStatusHistoryDTO.getComment());
					ticketStatusHistory.setPreviousStatus(previousStatus);
					ticketStatusHistory.setNewStatus(ticket.getTicketStatus().getName());
					ticketStatusHistory.setCreatedBy(user.getId());
					ticketStatusHistory.setCreatedDate(new Date());
					ticketManagementDao.saveTicketStatusHistory(ticketStatusHistory);
					
					//Send Ticket Status got changed Email & Notification
				}
			} else {
				response.setResponse(Result.INVALID_DATA.name());
				response.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}

	@Override
	public Ticket getTicketSnoAndTicketId(String generatedId, Long ticketId, Long orgLocationsId) {
		return ticketManagementDao.getTicketSnoAndTicketId(generatedId,ticketId,orgLocationsId);
	}
	

	@Override
	public Boolean validateTicketId(String generatedId, Long orgLocationsId) {
		
		return ticketManagementDao.validateTicketId(generatedId,orgLocationsId);
	}
	
	

	

	
}

