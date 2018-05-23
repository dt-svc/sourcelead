package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;

public interface UserGroupService {

	Response<UserGroup> createUserGroup(OrgLocations orgLocations, UserGroupDTO userGroupDTO, User loginUser);

	ResponseList<UserGroupDTO> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation);

	Response<UserGroup> validateUserGroupId(OrgLocations orgLocations, UserGroupDTO userGroupDTO);
}
