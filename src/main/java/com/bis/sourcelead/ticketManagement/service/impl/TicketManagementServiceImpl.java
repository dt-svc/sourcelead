package com.bis.sourcelead.ticketManagement.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketAssignTo;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketCategoryAssignTo;
import com.bis.sourcelead.ticketManagement.dao.TicketManagementDao;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.ticketManagement.service.TicketManagementService;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class TicketManagementServiceImpl implements TicketManagementService {

	@Autowired
	private TicketManagementDao ticketManagementDao;
	
	@Autowired
	private UserDao userDao;

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

	@Override
	public Boolean saveTicketCategory(TicketCategoryDTO ticketCategoryDTO, long userId,OrgLocations loginOrgLocation) {
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
	public Boolean saveTicket(TicketDTO ticketDTO,long userId, OrgLocations orgLocations) {
		try {
			Ticket ticketManagement = null;
			if (ticketDTO.getId() > 0) {
				ticketManagement = ticketManagementDao.getTicketById(ticketDTO.getId());
				ticketManagement.setUpdatedBy(userId);
				ticketManagement.setUpdatedDate(new Date());
			} else {
				ticketManagement = new Ticket();
				ticketManagement.setCreatedBy(userId);
				ticketManagement.setCreatedDate(new Date());
				ticketManagement.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketManagement.setStatus(1);
				ticketManagement.setOrgLocations(orgLocations);
			}
			ticketManagement.setTicketType(ticketManagementDao.getTicketTypeById(ticketDTO.getTicketTypeId()));
			ticketManagement.setTicketCategory(ticketManagementDao.getTicketCategoryById(ticketDTO.getTicketCategoryId()));
			ticketManagement.setTicketPriority(ticketManagementDao.getTicketPriorityById(ticketDTO.getTicketPriorityId()));
			ticketManagement.setTicketStatus(ticketManagementDao.getTicketStatusById(ticketDTO.getTicketStatusId()));
			ticketManagement.setDescription(ticketDTO.getDescription());
			ticketManagement.setGeneratedId(ticketDTO.getGeneratedId());
			ticketManagement.setNotifyTo(ticketDTO.getNotifyTo());
			List<TicketAssignTo> assignToList = new LinkedList<>();
			if(ticketDTO.getAssignedTo() != null) {
				for(String assignUserId : ticketDTO.getAssignedTo()) {
					TicketAssignTo assignTo = new TicketAssignTo();
					assignTo.setUser(userDao.getUserById(Long.parseLong(assignUserId)));
					assignTo.setTicket(ticketManagement);
					assignTo.setCreatedBy(userId);
					assignTo.setCreatedDate(new Date());
					assignToList.add(assignTo);
				}
			}
			
			ticketManagement.setTicketAssignToList(assignToList);
			return ticketManagementDao.saveTicket(ticketManagement);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	public List<TicketDTO> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId) {
		List<TicketDTO> ticketDTOList = new LinkedList<>();
		try {
			List<Ticket> ticketManagementList = ticketManagementDao.getTicketManagementListByOrgLocationId(orgLocationId,userId);
			if (ticketManagementList != null && !ticketManagementList.isEmpty()) {
				for (Ticket ticketManagement : ticketManagementList) {
					TicketDTO ticketDTO = new TicketDTO();
					ticketDTO.setId(ticketManagement.getId());
					ticketDTO.setGeneratedId(ticketManagement.getGeneratedId());
					ticketDTO.setUuid(ticketManagement.getUuid());
					ticketDTO.setTicketType(ticketManagement.getTicketType().getName());
					ticketDTO.setTicketCategory(ticketManagement.getTicketCategory().getName());
					ticketDTO.setTicketStatus(ticketManagement.getTicketStatus().getName());
					ticketDTO.setTicketPriority(ticketManagement.getTicketPriority().getName());
					ticketDTO.setCreatedById(ticketManagement.getCreatedBy());
					ticketDTO.setCreatedDate(ticketManagement.getCreatedDate());
					if(ticketManagement.getUpdatedBy() != null) {
						ticketDTO.setUpdatedById(ticketManagement.getUpdatedBy());
						ticketDTO.setUpdatedDate(ticketManagement.getUpdatedDate());
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

}

	

	


	

	
	

	
	

	

	
	


	

	

