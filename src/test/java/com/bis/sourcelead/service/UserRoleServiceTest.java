package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.impl.UserRoleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class UserRoleServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceTest.class);
		@Configuration
	static class UserRoleServiceConfigTest {
		@Bean
		public UserRoleService userRoleService() {
			return new UserRoleServiceImpl();
		}
		}
		
		@Autowired
		UserRoleService userRoleService;
		
		@Autowired
		RolesService rolesService;
		
		@Autowired
		ProjectsService  projectsService;
		
		
		@Autowired
		UserService userService;
		
		/*@Autowired
		OrgLocationService locationService;*/
		
		//@Test
		public void addUserRole() throws Exception{
			UserRole userRole = new UserRole();
			User user = new User();
			user.setId(2L);
		//	userService.addUser(user);
			userRole.setUser(user);
			OrgLocations orgLocations = new OrgLocations();
			orgLocations.setId(1L);
			userRole.setOrgLocations(orgLocations);
			Roles roles = null;
			try {
				roles = rolesService.getRolesByRoleName("ROLE_ADMIN");
			} catch (Exception e) {
				e.printStackTrace();
			}
			userRole.setRoles(roles);
			userRole.setIsPrimary(1);
			userRole.setCreatedDate(new Date());
			userRole.setCreatedBy(user.getUserCode());
			userRoleService.addUserRole(userRole);
			
			userRoleService.addUserRole(userRole);
			logger.info("addUserRole successfully updated"+userRole.getRoles());
		}
		
		
		//@Test
		public void getLoginUserPrimaryRole() throws Exception{
			
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(1L);
			logger.info("userRole"+userRole.getUser());
			
		}
		
		//@Test
		
		public void getUsersListByLocationId() throws Exception{
			
			Long locationId = 1l;
			String userState = "INACTIVE";
			Long userId = 2l;
			
			List<UserRole> userslist = userRoleService.getUsersListByLocationId(locationId,userState,userId);
			logger.info("getUsersListByLocationId successfully updated"+userslist);
			
			
		}
		@Test
		public void getUsersListByRoleAndLocationId() throws Exception{
			
			List<UserRole> userRole = new ArrayList<>();
			userRole = userRoleService.getUsersListByRoleAndLocationId(1l,2l,"EMPLOYEE",2l);
			logger.info("getUsersListByRoleAndLocationId "+userRole.size());
		}

		
		//@Test
		public void getUsersListByUserNameAndLocationId() throws Exception{
			
			String userName = "ajith";
			Long locationId = 4l;
			String userState = "INACTIVE";
			Long userId = 1l;
			
			List<UserRole> userslist = userRoleService.getUsersListByUserNameAndLocationId(userName,locationId,userState,userId);
			logger.info("getUsersListByUserNameAndLocationId successfully updated"+userslist);
		}
		
		//@Test
		public void getUsersListByRoleAndNameAndLocationId() throws Exception{
			
			UserRole userRole = new UserRole();
			userRole.setId(1l);
			Long roleId = 2l;
			String userName = "shiva";
			Long locationId = 1l;
			String userState = "ACTIVE";
			Long userId = 3l;
			
			List<UserRole> userslist =userRoleService.getUsersListByRoleAndNameAndLocationId(roleId,userName,locationId,userState,userId);
			logger.info("getUsersListByRoleAndNameAndLocationId"+userName);
		}
		
		//@Test
		public void getUsersListByUserStateAndLocationId() throws Exception{
			
			String userState = "ACTIVE"; 
			Long locationId = 19l;
			Long userId = 5l;
			
			List<UserRole> userslist = userRoleService.getUsersListByUserStateAndLocationId(userState,locationId,userId);
			logger.info("getUsersListByUserStateAndLocationId"+userState);
			
		}
		
		//@Test
		public void getUsersBasedOnUserRoles() throws Exception{
			
			List<UserRole> userRole = userRoleService.getUsersBasedOnUserRoles( );
		
			logger.info("getUsersBasedOnUserRoles"+userRole.size());
			
		}
		
		//@Test
		public void getApproverAssignedUsers() throws Exception{
			
			String userState = "ACTIVE"; 
			String searchByName = "ROLE_APPROVER";
			List<Long> projectIds = projectsService.getApprovarAssignedProjects(3l, 3l);
			
			//List<UserRole> userrole  = userRoleService.getApproverAssignedUsers(projectIds, userState, searchByName);
			
			List<UserRole> userRole = userRoleService.getApproverAssignedUsers(projectIds,userState,searchByName );
			
			logger.info("getApproverAssignedUsers"+userRole.size());
			
			
		}

	}

