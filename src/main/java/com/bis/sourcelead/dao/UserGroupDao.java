package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserGroupDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.entity.UserIdView;

public interface UserGroupDao {

	UserGroup saveUserGroup(UserGroup userGroup);

	List<UserIdView> getCommaSeperatedUserId(Long orgLocationId);

	ResponseList<UserGroup> findAllUserGroup(UserGroupDTO userGroupDTO, OrgLocations orgLocation);
}
