package com.bis.sourcelead.elk.index.helper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.UserRole;

@Component
public class UserIndexHelper {
	
	
	
	public UserIndex getUserIndexFromUser(UserRole userRole, Employees employee, String loginUserCode) throws Exception{
		UserIndex userIndex = null;
		if(null != userRole && null != userRole.getId()){
			userIndex = new UserIndex();
			userIndex.setUserId(userRole.getUser().getId());
			userIndex.setFirstName(userRole.getUser().getFirstName());
			userIndex.setLastName(userRole.getUser().getLastName());
			userIndex.setOrgLocationId(userRole.getOrgLocations().getId());
			userIndex.setEmail(userRole.getUser().getEmail());
			userIndex.setMobilePhone(userRole.getUser().getMobilePhone());
			userIndex.setIsMailSent(userRole.getUser().getIsMailSent().toString());
			userIndex.setUserState(userRole.getUserState().toString());
			userIndex.setUserType(userRole.getUserType());
			userIndex.setStatus(userRole.getUser().getStatus());
			userIndex.setTerminateReason(userRole.getTerminateReason());
			userIndex.setEmployeeId(employee.getEmployeeId());
			userIndex.setRate(employee.getRate());
			userIndex.setRatePer(employee.getRatePer());
			
			if(userRole.getCreatedDate() != null){
				userIndex.setCreatedDate(new Date(userRole.getCreatedDate().getTime()));
			}
			userIndex.setCreatedBy(loginUserCode);
			if(userRole.getUpdatedDate() != null){
				userIndex.setUpdatedDate(new Date(userRole.getUpdatedDate().getTime()));
			}
			
			userIndex.setUpdatedBy(loginUserCode);
		}
		
		
		return userIndex;
		
	}
	
	
}
