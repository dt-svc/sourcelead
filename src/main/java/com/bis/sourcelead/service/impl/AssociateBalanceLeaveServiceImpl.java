package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateBalanceLeaveDao;
import com.bis.sourcelead.dto.AssociateBalanceLeaveDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociateBalanceLeave;
import com.bis.sourcelead.entity.AssociateBalanceLeaveView;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.service.AssociateBalanceLeaveService;

@Service
public class AssociateBalanceLeaveServiceImpl implements AssociateBalanceLeaveService {

	private static final Logger logger = LoggerFactory.getLogger(AssociateBalanceLeaveServiceImpl.class);
	
	@Autowired
	AssociateBalanceLeaveDao associateBalanceLeaveDao;
	
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
	public List<AssociateBalanceLeaveDTO> getAssociateBalanceLeavesByUserId(Long userId)
	{
		ResponseList<AssociateBalanceLeaveView> resObj = associateBalanceLeaveDao.getAssociateBalanceLeavesByUserId(userId);
		
		ArrayList<AssociateBalanceLeaveDTO> list = new ArrayList<>();
		 
		 if(resObj.getData() != null && resObj.getData().size() >0) {
			 for(AssociateBalanceLeaveView l : resObj.getData()) {
				 AssociateBalanceLeaveDTO associateBalanceLeaveDTO = new AssociateBalanceLeaveDTO();
				 associateBalanceLeaveDTO.setId(String.valueOf(l.getId()));
				 associateBalanceLeaveDTO.setAssoBalLeaveUUID(l.getAssoBalLeaveUUID());
				 associateBalanceLeaveDTO.setLeaveTypeName(l.getLeaveTypeName());
				 associateBalanceLeaveDTO.setNoofLeaves(l.getTotalLeaves());
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
	
}
