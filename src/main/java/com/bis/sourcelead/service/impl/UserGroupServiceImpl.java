package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.payroll.dto.AssociateDeductionDTO;
import com.bis.sourcelead.service.UserGroupService;

@Service
public class UserGroupServiceImpl implements UserGroupService{

	private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);
	
	@Autowired
	UserGroupDao userGroupDao;
	
	@Override
	public Response<UserGroup> createUserGroup(OrgLocations orgLocations, UserGroupDTO userGroupDTO, User user)
	{
		Response<UserGroup> resObj = new Response<>();
		try {
			if (Validator.validate(userGroupDTO)) {
				UserGroup userGroup = new UserGroup();
				userGroup.setGroupId(userGroupDTO.getGroupId());
				userGroup.setGroupName(userGroupDTO.getGroupName());
				userGroup.setDepartmentId(userGroupDTO.getDepartment());
				userGroup.setUserId(userGroupDTO.getAssociateIds());
				userGroup.setDescription(userGroupDTO.getDescription());
				userGroup.setPayFrequency(userGroupDTO.getPayFrequency());
				userGroup.setCreatedDate(new Date());
				userGroup.setUpdatedDate(new Date());
				userGroup.setCreatedBy(user.getUserCode());
				userGroup.setUpdatedBy(user.getUserCode());
				userGroup.setOrgLocationId(orgLocations);
				userGroup.setStatus(1);
				UserGroup savedUserGroup = userGroupDao.saveUserGroup(userGroup);
				if(savedUserGroup != null) {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
					resObj.setObject(userGroup);
				}
			}else {
				resObj.setResponse("DataMissing");
				resObj.setResponseMessage(Result.INVALID_DATA.getValue());
			}
		}catch (Exception e) {
			resObj.setResponse(Result.AWKWARD.name());
			resObj.setResponseMessage(Result.AWKWARD.getValue());
			logger.error("create user group failed due to : "+e.getMessage());
		}
		return resObj;
	}

	@Override
	public ResponseList<UserGroupDTO> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation)
	{
		ResponseList<UserGroupDTO> res = new ResponseList<>(); 
		ResponseList<UserGroup> userGroupList = userGroupDao.findAllUserGroup(userGroupDTO,orgLocation);
		if(userGroupList.getData() != null) {
			List<UserGroupDTO> list = new ArrayList<>();
			userGroupList.getData().forEach(ug->{
				int numOfAssociates = 0;
				UserGroupDTO ugDTO = new UserGroupDTO();
				ugDTO.setId(ug.getId());
				ugDTO.setGroupId(ug.getGroupId());
				ugDTO.setGroupName(ug.getGroupName());
				ugDTO.setDescription(ug.getDescription());
				ugDTO.setPayFrequency(ug.getPayFrequency());
				if(ug.getUserId() !=null && !"".equalsIgnoreCase(ug.getUserId())) {
					String numOfAss = ug.getUserId().replaceAll("\\s+","");
					numOfAssociates = numOfAss.split(",").length;
				}
				ugDTO.setNumOfAssociates(String.valueOf(numOfAssociates));
				list.add(ugDTO);
			});
			res.setData(list);
			res.setRecordsFiltered(userGroupList.getRecordsFiltered());
			res.setRecordsTotal(userGroupList.getRecordsTotal());
			res.setResponseMessage("success");
			res.setStatusCode("200");
		}
		return res;
	}

	@Override
	public Response<UserGroup> validateUserGroupIdOrGroupName(OrgLocations orgLocations, UserGroupDTO userGroupDTO)
	{
		Response<UserGroup> resObj = new Response<>();
		if(orgLocations != null){
			try {
				boolean flag = userGroupDao.validateUserGroupIdOrGroupName(orgLocations.getId(), userGroupDTO);
				if (flag) {
					resObj.setResponse("Record Exists");
					resObj.setResponseMessage("Record Exists");
				}else {
					resObj.setResponse(Result.SUCCESS.name());
					resObj.setResponseMessage(Result.SUCCESS.getValue());
				}
			}catch (Exception e) {
				resObj.setResponse(Result.AWKWARD.name());
				resObj.setResponseMessage(Result.AWKWARD.getValue());
			}
		}
		
		return resObj;
	}
}
