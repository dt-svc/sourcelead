package com.bis.sourcelead.ticketManagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.TicketAssignToDTO;
import com.bis.sourcelead.dto.TicketCategoryDTO;
import com.bis.sourcelead.dto.TicketCommentDTO;
import com.bis.sourcelead.dto.TicketDTO;
import com.bis.sourcelead.dto.TicketPriorityDTO;
import com.bis.sourcelead.dto.TicketStatusDTO;
import com.bis.sourcelead.dto.TicketTypeDTO;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.entity.TicketType;
import com.bis.sourcelead.entity.TicketStatus;
import com.bis.sourcelead.entity.TicketPriority;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Ticket;
import com.bis.sourcelead.entity.TicketAssignTo;
import com.bis.sourcelead.entity.TicketCategory;
import com.bis.sourcelead.entity.TicketCategoryAssignTo;
import com.bis.sourcelead.entity.TicketComment;
import com.bis.sourcelead.ticketManagement.dao.TicketManagementDao;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.ticketManagement.service.TicketManagementService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
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
	public Boolean saveTicket(TicketDTO ticketDTO, User loginUser, OrgLocations orgLocations) {
		try {
			Ticket ticketManagement = null;
			if (ticketDTO.getId() > 0) {
				ticketManagement = ticketManagementDao.getTicketById(ticketDTO.getId());
				ticketManagement.setUpdatedBy(loginUser);
				ticketManagement.setUpdatedDate(new Date());
			} else {
				ticketManagement = new Ticket();
				ticketManagement.setCreatedBy(loginUser);
				ticketManagement.setCreatedDate(new Date());
				ticketManagement.setUuid(SourceLeadRandomCodeHelper.generateRandomUserCode());
				ticketManagement.setOrgLocations(orgLocations);
			}
			ticketManagement.setTicketType(ticketManagementDao.getTicketTypeById(ticketDTO.getTicketTypeId()));
			ticketManagement.setTicketCategory(ticketManagementDao.getTicketCategoryById(ticketDTO.getTicketCategoryId()));
			ticketManagement.setTicketPriority(ticketManagementDao.getTicketPriorityById(ticketDTO.getTicketPriorityId()));
			ticketManagement.setTicketStatus(ticketManagementDao.getTicketStatusById(ticketDTO.getTicketStatusId()));
			ticketManagement.setDescription(ticketDTO.getDescription());
			ticketManagement.setGeneratedId(ticketDTO.getGeneratedId());
			ticketManagement.setNotifyTo(ticketDTO.getNotifyTo());
			if(ticketDTO.getNotifyTo() != null && !ticketDTO.getNotifyTo().isEmpty() && ticketDTO.getId() == 0) {
				// send Nofitify email & notification
			}
			
			List<TicketAssignTo> assignToList = new LinkedList<>();
			if(ticketDTO.getAssignedTo() != null) {
				for(String assignUserId : ticketDTO.getAssignedTo()) {
					TicketAssignTo assignTo = new TicketAssignTo();
					User assignedUser = userDao.getUserById(Long.parseLong(assignUserId));
					assignTo.setUser(assignedUser);
					assignTo.setTicket(ticketManagement);
					assignTo.setCreatedBy(loginUser.getId());
					assignTo.setCreatedDate(new Date());
					assignToList.add(assignTo);
				}
			}
			
			ticketManagement.setTicketAssignToList(assignToList);
			Boolean issaved = ticketManagementDao.saveTicket(ticketManagement);
			
			//send Assign email & notification
			
			if(issaved && ticketDTO.getId() == 0) {
				if(ticketDTO.getAssignedTo() != null) {
					for(String assignUserId : ticketDTO.getAssignedTo()) {
						User toUser = userDao.getUserById(Long.parseLong(assignUserId));
						sendTicketManagementEmailToAssignedUsers(loginUser,orgLocations,ticketManagement,toUser);
					}
				}
				
				if(ticketDTO.getNotifyTo() != null && !ticketDTO.getNotifyTo().isEmpty()) {
					for(String notifyToEmail : ticketDTO.getNotifyTo().split(",")) {
						//send notifyTo email & Notification
						sendTicketManagementEmailToNotifyUsers(loginUser,orgLocations,ticketManagement,notifyToEmail);
					}
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

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
			
			
		
			globalVarsMap.put("TicketTitle", "--");
			
			
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
	public List<TicketDTO> getTicketManagementListByOrgLocationId(Long orgLocationId,long userId, String dateFormat) {
		List<TicketDTO> ticketDTOList = new LinkedList<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
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
					ticketDTO.setCreatedById(ticketManagement.getCreatedBy().getId());
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
	public List<TicketCommentDTO> getTicketCommentListByTicketId(Long ticketId, long userId, Long orgLocationId) {
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
					ticketCommentDTO.setCreatedById(ticketComment.getCreatedBy().getId());
					ticketCommentDTO.setCreatedByName(ticketComment.getCreatedBy().getFullName());
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					ticketCommentDTO.setCreatedDate(sdf.format(ticketComment.getCreatedDate()));
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

	
	 
	 
}

	