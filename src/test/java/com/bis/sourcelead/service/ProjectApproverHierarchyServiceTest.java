package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ProjectApproverHierarchyServiceTest {

	@Autowired
	ProjectApproverHierarchyService approverHierarchyService;

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationService.class);

	@Test
	public void saveProjectApproverHierarchy() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		pah.setId(1l);
		Projects projects = new Projects();
		projects.setId(1L);
		OrgRelations orgRelationId= new OrgRelations();
		orgRelationId.setId(1l);
		projects.setOrgRelationId(orgRelationId);
		pah.setProjectId(projects);
		User user = new User();
		user.setId(1L);
		pah.setApproverUserId(user);
		pah.setLevel(1);
		pah.setStatus("Active");
		pah.setCreatedDate(new Date());
		pah.setCreatedBy("SourceLead");
		pah.setUpdatedDate(new Date());
		pah.setUpdatedBy("Sourcelead");
		pah = approverHierarchyService.saveProjectApproverHierarchy(pah);
		logger.info("Saved Successfully..."+pah.getStatus());
	}   
	
	@Test
	public void getLevelOneProjectApproverByProjectId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		Projects projects = new Projects();
		projects.setId(1L);
		pah.setProjectId(projects);
		pah = approverHierarchyService.getLevelOneProjectApproverByProjectId(pah.getProjectId().getId());
		logger.info("Retrieved Successfully...");
	}
	
	@Test
	public void getApproverUsersByProjectId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		Projects projects = new Projects();
		projects.setId(1L);
		pah.setProjectId(projects);
		List<ProjectApproverHierarchy> pahlist = approverHierarchyService.getApproverUsersByProjectId(pah.getProjectId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	@Test
	public void getProjectHierarchyListByLoginUserId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		User user = new User();
		user.setId(1L);
		pah.setApproverUserId(user);
		List<ProjectApproverHierarchy> pahlist = approverHierarchyService.getProjectHierarchyListByLoginUserId(pah.getApproverUserId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	@Test
	public void getProjectIdsByLoginUserId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		User user = new User();
		user.setId(1L);
		pah.setApproverUserId(user);
		List<Long> pahlist = approverHierarchyService.getProjectIdsByLoginUserId(pah.getApproverUserId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	
	@Test
	public void updateProjectApproverHierarchy() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		pah.setId(1l);
		User user = new User();
		user.setId(1L);
		pah.setApproverUserId(user);
		Projects projectId= new Projects();
		projectId.setId(1l);
		pah.setProjectId(projectId);
		@SuppressWarnings("unchecked")
		List<Long> pahlist = (List<Long>) approverHierarchyService.updateProjectApproverHierarchy(pah);
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	@Test
	public void getAllProjectApproverHierarchyByProjectId() throws Exception {
		long projectId=1l;
		long userId=1l;
		List<ProjectApproverHierarchy> pahlist = approverHierarchyService.getAllProjectApproverHierarchyByProjectId(projectId, userId);
		for(ProjectApproverHierarchy PAHierarchy:pahlist){
			PAHierarchy.getId();
		}
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	
	/*@Test
	public void prepareProjectApproverHierarchyListByJson() throws Exception {
		JSONObject jsonObj= new JSONObject();
		jsonObj.put("level1Manager", "suhas");
		jsonObj.put("level2Manager", "suhas");
		HttpServletRequest request = mock(HttpServletRequest.class);
		Projects projects= new Projects();
		projects.setId(1l);
		Set<ProjectApproverHierarchy> pahlist = approverHierarchyService.prepareProjectApproverHierarchyListByJson(jsonObj, request, projects);
		for(ProjectApproverHierarchy PAHierarchy:pahlist){
			PAHierarchy.getId();
		}
		logger.info("Retrieved Successfully...");
	}*/
}
