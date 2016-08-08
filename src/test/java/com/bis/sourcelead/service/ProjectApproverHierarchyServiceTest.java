package com.bis.sourcelead.service;

import java.util.Date;
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
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ProjectApproverHierarchyServiceTest {

	@Autowired
	ProjectApproverHierarchyService approverHierarchyService;

	private static final Logger logger = LoggerFactory.getLogger(OrgLocationService.class);

	//@Test
	public void saveProjectApproverHierarchy() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		Projects projects = new Projects();
		projects.setId(3L);
		pah.setProjectId(projects);
		User user = new User();
		user.setId(4L);
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
	
	//@Test
	public void getLevelOneProjectApproverByProjectId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		Projects projects = new Projects();
		projects.setId(3L);
		pah.setProjectId(projects);
		pah = approverHierarchyService.getLevelOneProjectApproverByProjectId(pah.getProjectId().getId());
		logger.info("Retrieved Successfully..."+pah.getApproverUserId().getFirstName());
	}
	
	//@Test
	public void getApproverUsersByProjectId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		Projects projects = new Projects();
		projects.setId(3L);
		pah.setProjectId(projects);
		List<ProjectApproverHierarchy> pahlist = approverHierarchyService.getApproverUsersByProjectId(pah.getProjectId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	//@Test
	public void getProjectHierarchyListByLoginUserId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		User user = new User();
		user.setId(3L);
		pah.setApproverUserId(user);
		List<ProjectApproverHierarchy> pahlist = approverHierarchyService.getProjectHierarchyListByLoginUserId(pah.getApproverUserId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
	
	@Test
	public void getProjectIdsByLoginUserId() throws Exception {
		ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
		User user = new User();
		user.setId(3L);
		pah.setApproverUserId(user);
		List<Long> pahlist = approverHierarchyService.getProjectIdsByLoginUserId(pah.getApproverUserId().getId());
		logger.info("Retrieved Successfully..."+pahlist.size());
	}
}
