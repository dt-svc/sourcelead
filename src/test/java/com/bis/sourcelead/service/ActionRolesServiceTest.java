package com.bis.sourcelead.service;

import java.util.Date;
import static org.junit.Assert.*;
import java.util.List;

import javax.validation.constraints.AssertTrue;

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
import com.bis.sourcelead.entity.ActionRoles;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.TabActions;
import com.bis.sourcelead.enums.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ActionRolesServiceTest {
	
	@Autowired
	ActionRolesService actionRolesService;
	
	@Autowired
	ModulesService modulesService;
	private static final Logger logger = LoggerFactory.getLogger(ActionRolesServiceTest.class);
	
	@Test
	public void addActionRoles() throws Exception{
		ActionRoles actionRoles = new ActionRoles();
		actionRoles.setId(1L);
		TabActions tabActions = new TabActions();
		tabActions.setId(1L);
		actionRoles.setTabActions(tabActions);
		Roles roles = new Roles();
		roles.setId(2L);
		actionRoles.setRoles(roles);
		actionRoles.setIsNotify(Status.Y);
		Org org = new Org();
		org.setId(2L);
		actionRoles.setOrg(org);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(3L);
		actionRoles.setOrgLocations(orgLocations);
		actionRoles.setCreatedDate(new Date());
		actionRoles.setCreatedBy("Sourcelead");
		actionRoles.setUpdatedDate(new Date());
		actionRoles.setUpdatedBy("Sourcelead");
		actionRolesService.addActionRoles(actionRoles);
		assertSame(1L, actionRoles.getId());
	}
	
	@Test
	public void getSourceLeadActionRolesListByOrgId() throws Exception{
		Org org = new Org();
		org.setId(2L);
		List<Long> modulesid = modulesService.getModulesIdByModuleName("SOURCELEAD_DEFAULT_MODULE");
		List<ActionRoles> actionRolesList = actionRolesService.getSourceLeadActionRolesListByOrgId(org.getId(), modulesid,org);
		if(actionRolesList != null)
		{
			logger.info("getSourceLeadActionRolesListByOrgId "+actionRolesList.size());
	}
	}
	
	@Test
	public void getOrgActionRolesByRoleAndOrgLocationId() throws Exception{
		/*OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(1L);
		Roles roles = new Roles();
		roles.setId(2L);*/
		List<ActionRoles> actionRolesList = actionRolesService.getOrgActionRolesByRoleAndOrgLocationId(2l,3l);
		if(actionRolesList != null){
			logger.info("getOrgActionRolesByRoleAndOrgLocationId "+actionRolesList.size());
		}
	}
	
	@Test
	public void getActionRolesByActionIdAndRoleIdAndLocationId() throws Exception{
		ActionRoles actionRoles = actionRolesService.getActionRolesByActionIdAndRoleIdAndLocationId(1L, 1l, 2L);
		logger.info("Retrieved action roles are... "+actionRoles.getRoles().getDisplayName());
	}
}
