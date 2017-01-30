package com.bis.sourcelead.elk.index.helper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dao.UserDao;
import com.bis.sourcelead.elk.index.UserIndex;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;

@Component
public class UserIndexHelper {
	
	@Autowired
	private UserDao userDao;
	
	
	
	public UserIndex getUserIndexFromUser(UserRole userRole, Employees employee, String loginUserCode) throws Exception{
		UserIndex userIndex = null;
		User user = userDao.getUserByIdAndOrgLocationsId(userRole.getUser().getId(), userRole.getOrgLocations().getId());
		
		if(null != userRole && null != userRole.getId()){
			userIndex = new UserIndex();
			userIndex.setUserId(userRole.getUser().getId());
			userIndex.setFirstName(user.getFirstName());
			userIndex.setLastName(user.getLastName());
			userIndex.setOrgLocationId(userRole.getOrgLocations().getId());
			userIndex.setEmail(user.getEmail());
			userIndex.setMobilePhone(user.getMobilePhone());
			userIndex.setIsMailSent(user.getIsMailSent().toString());
			userIndex.setUserStatus(userRole.getUserState().toString());
			userIndex.setUserType(userRole.getUserType());
			userIndex.setStatus(userRole.getUser().getStatus());
			userIndex.setTerminateReason(userRole.getTerminateReason());
			userIndex.setEmployeeId(employee.getEmployeeId());
			userIndex.setRate(employee.getRate());
			userIndex.setRatePer(employee.getRatePer());
			userIndex.setHireDate(employee.getHireDate());
			
			if(null != userRole.getCreatedDate()){
				userIndex.setCreatedDate(new Date(userRole.getCreatedDate().getTime()));
			}
			/*if(null == userRole.getCreatedBy() || "".equalsIgnoreCase(userRole.getCreatedBy())){
				userIndex.setCreatedBy(loginUserCode);
			}*/
			userIndex.setCreatedBy(userRole.getCreatedBy());
			if(null != userRole.getUpdatedDate()){
				userRole.setUpdatedDate(new Date(userRole.getUpdatedDate().getTime()));
			}
			
			userIndex.setUpdatedBy(loginUserCode);
		}
		
		
		return userIndex;
		
	}
	
	
}
