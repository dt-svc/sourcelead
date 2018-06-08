package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TotalLeaveDTO;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.LeaveRole;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;
import com.bis.sourcelead.service.LeaveTypeService;
import com.bis.sourcelead.service.UserRoleService;

@Service
public class AssociateBalanceLeaveServiceImpl implements AssociateBalanceLeaveService {

	private static final Logger logger = LoggerFactory.getLogger(AssociateBalanceLeaveServiceImpl.class);
	
	@Autowired
	AssociateBalanceLeaveDao associateBalanceLeaveDao;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	LeaveTypeService leaveTypeService;
	
	@Override
	public Response<AssociateBalanceLeaveDTO> addAssociateLeave(AssociateBalanceLeaveDTO associateBalanceLeaveDTO, User loginUser, OrgLocations orgLocations)
	{
		Response<AssociateBalanceLeaveDTO>  resObj = new Response<>();
		try {
			if(associateBalanceLeaveDTO.getTotalLeavesDto() != null && associateBalanceLeaveDTO.getTotalLeavesDto().size() > 0) {
				int count = associateBalanceLeaveDao.addAssociateLeave(associateBalanceLeaveDTO,loginUser,orgLocations);
					
					if (count == 1) {
						resObj.setResponse(Result.SUCCESS.name());
						resObj.setResponseMessage(Result.SUCCESS.getValue());
						resObj.setObject(associateBalanceLeaveDTO);
					}
			}else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error("Add Association Balance Leave Failed Due To : "+e.getMessage());
		}
		
		
		return resObj;
	}

	@Override
	public List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId,OrgLocations orgLocations,User loginUser)
	{
		
		ResponseList<AssociateBalanceLeaveView> finalResObj = associateBalanceLeaveDao.getAssociateBalanceLeavesByUserId(userId,orgLocations.getId());
		ArrayList<AssociateBalanceLeaveDTO> list = new ArrayList<>();
		 
		 if(finalResObj.getData() != null && finalResObj.getData().size() >0) {
			 for(AssociateBalanceLeaveView l : finalResObj.getData()) {
				 AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
				 associateBalanceLeaveDTO.setId(String.valueOf(l.getId()));
				 associateBalanceLeaveDTO.setAssoBalLeaveUUID(l.getAssoBalLeaveUUID());
				 associateBalanceLeaveDTO.setLeaveTypeName(l.getLeaveTypeName());
				 associateBalanceLeaveDTO.setNoofLeaves(l.getTotalLeaves());
				 associateBalanceLeaveDTO.setLeaveTypeId(l.getLeaveTypeId());
				 if(l.getCreatedByFirstName() != null && !"".equalsIgnoreCase(l.getCreatedByFirstName())) {
					 associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName()+" "+l.getCreatedByLastName());
				 }else {
					 associateBalanceLeaveDTO.setCreatedBy(l.getCreatedByFirstName());
				 }
				 list.add(associateBalanceLeaveDTO); 
			 }
		 }
		
		 
		return list;
	}

	@Override
	public boolean getAssociateBalanceByUUID(String assoBalLeaveUUID,String totalLeaves)
	{ 
		boolean isUpdated = false;
		int result = associateBalanceLeaveDao.updateAssociateBalLeave(assoBalLeaveUUID,totalLeaves);
		if(result > 0) {
			isUpdated = true;	
		}
		return isUpdated;
	}

	@Override
	public int getTotalLeavesByUserIdAndLeaveTypeId(Long leaveTypeId, Long userId,Long orgLocationId)
	{
		int totalLeaves = 0;
		try {
			AssociateBalanceLeave associateBalanceLeave = associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveTypeId,userId,orgLocationId);
			if(associateBalanceLeave != null) {
				totalLeaves = Integer.parseInt(associateBalanceLeave.getTotalLeaves());
			}else {
				LeaveType leaveType = leaveTypeService.getLeaveTypeById(leaveTypeId);
				totalLeaves = leaveType.getNoOfDays();
			}
		}catch (Exception e) {
			logger.error("Get Total Leaves By User Id And LeaveType Id "+e.getMessage());
		}
		
		return totalLeaves;
	}

	@Override
	public int addLeaveTypeToAssociate(List<User> userList, OrgLocations orgLocation,LeaveType leaveType,User loginUser)
	{
		int isAdded = 0;
		if(userList != null && userList.size() >0) {
			AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
			List<TotalLeaveDTO> list = new ArrayList<>();
			for(User user : userList) {
				TotalLeaveDTO totalLeaveDTO = new TotalLeaveDTO();
				totalLeaveDTO.setUserId(String.valueOf(user.getId()));
				totalLeaveDTO.setLeaveTypeId(String.valueOf(leaveType.getId()));
				totalLeaveDTO.setNoOfLeaves(String.valueOf(leaveType.getNoOfDays()));
				list.add(totalLeaveDTO);
			}
			associateBalanceLeaveDTO.setTotalLeavesDto(list);
			isAdded = associateBalanceLeaveDao.addAssociateLeave(associateBalanceLeaveDTO, loginUser, orgLocation);
		}
		return isAdded;
	}

	@Override
	public int updateAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType,int status)
	{
		return associateBalanceLeaveDao.updateAssociateBalanceLeaveType(userList,orgLocationId,leaveType,status,0,"NONE");
		
	}

	@Override
	public int addOrSubAssociateBalanceLeaveType(List<User> userList, Long orgLocationId, LeaveType leaveType, int status, int diffLeaves, String actionType)
	{
		
		return associateBalanceLeaveDao.updateAssociateBalanceLeaveType(userList,orgLocationId,leaveType,status,diffLeaves,actionType);
//		for(User user : userList) {
//			associateBalanceLeaveDao.getTotalLeavesByUserIdAndLeaveTypeId(leaveType.getId(), user.getId(), orgLocationId);
//		}
//		return 0;
	}
	
}
