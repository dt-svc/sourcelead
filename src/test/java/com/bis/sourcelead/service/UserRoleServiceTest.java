package com.bis.sourcelead.service;

import java.util.Date;

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
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.impl.UserRoleServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class UserRoleServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
	
	@Configuration
	static class UserRoleServiceConfigTest {
		@Bean
		public UserRoleService userRoleService() {
			return new UserRoleServiceImpl();
		}
		
		@Autowired
		UserRoleService userRoleService;
		
		@Autowired
		RolesService rolesService;
		
		@Autowired
		UserService userService;
		
		/*@Autowired
		OrgLocationService locationService;*/
		
		@Test
		public void addUserRole() throws Exception{
			UserRole userRole = new UserRole();
			User user = new User();
			user.setId(1L);
			userService.addUser(user);
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
		}
		
		
		@Test
		public void getLoginUserPrimaryRole() throws Exception{
			
			UserRole userRole = userRoleService.getLoginUserPrimaryRole(1L);
			logger.debug("userRole", userRole.getId());
			
		}

	}

}
