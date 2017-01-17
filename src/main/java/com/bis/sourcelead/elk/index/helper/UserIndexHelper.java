package com.bis.sourcelead.elk.index.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

@Component
public class UserIndexHelper {
	
	
	
	public UserIndex getUserIndexFromUser(User user, OrgLocations orgLocations) throws Exception{
		UserIndex userIndex = null;
		if(null != user && null != user.getId()){
			userIndex = new UserIndex();
			userIndex.setUserId(user.getId());
			userIndex.setFirstName(user.getFirstName());
			userIndex.setLastName(user.getLastName());
			userIndex.setOrgLocationId(orgLocations.getId());
			userIndex.setEmail(user.getEmail());
			userIndex.setMobilePhone(user.getMobilePhone());
		
			userIndex.setStatus(user.getStatus());
			userIndex.setTerminateReason(user.getTerminateReason());
			if(user.getCreatedDate() != null){
				userIndex.setCreatedDate(new Date(user.getCreatedDate().getTime()));
			}
			userIndex.setCreatedBy(user.getCreatedBy());
			if(user.getUpdatedDate() != null){
				userIndex.setUpdatedDate(new Date(user.getUpdatedDate().getTime()));
			}
			
			userIndex.setUpdatedBy(user.getUpdatedBy());
		}
		
		
		return userIndex;
		
	}
	
	
}
