package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.entity.Roles;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class RoleServicesTest {

	private static final Logger logger = LoggerFactory.getLogger(RoleServicesTest.class);
	
	@Autowired
	RolesService roleservice;
	
	@Test
	public void getRolesByRoleName() throws Exception{
		Roles roles =roleservice.getRolesByRoleName("ROLE_USER");
		logger.info("getRolesByRoleName " +roles.getDisplayName());
	}
	
   @Test
	public void getRoles()throws Exception{
		List<Roles> roles = roleservice.getRoles(2L);
		logger.info("getRoles added successfully"+roles.size());
	}
	@Test
	public void getRolesByDisplayName()throws Exception{
		Roles RolesByDisplayName =  roleservice.getRolesByDisplayName("Admin");
		logger.info("getRolesByDisplayName added successfully"+ RolesByDisplayName.getDisplayName());
	}
	
	@Test
	public void getRolesById()throws Exception{
		Roles roles = roleservice.getRolesById(3l);
		logger.info("getRolesById "+roles.getRoleName());
	}
	
	@Test
	public void getMoudleRoles()throws Exception{
		List<Roles> roles = roleservice.getMoudleRoles(3l,1l);
		logger.info("getMoudleRoles"+roles.size());
	}
}