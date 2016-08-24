package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;

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
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class OrgRelationServiceTest {

	@Autowired
	OrgRelationsService orgRelationsService;

	private static final Logger logger = LoggerFactory.getLogger(OrgRelationServiceTest.class);

	@Test
	public void addOrgRelations() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();

		orglocations.setId(2L);

		orgRelations.setResponderLocationId(orglocations);
		orgRelations.setLocationId(orglocations);
		orgRelations.setRelationType("Client");
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedDate(new Date());
		orgRelations.setCreatedBy("source");
		orgRelations.setUpdatedDate(new Date());
		orgRelations.setUpdatedBy("Source");
		orgRelationsService.addOrgRelations(orgRelations);
		logger.info("Added Successfully..."+orgRelations.getRelationType());
	}
	
	@Test
	public void getOrgRelationIdByOrgLocationIdAndClientLocationId() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(1L);
		orgRelations.setResponderLocationId(orglocations);
		orgRelations.setLocationId(orglocations);
		orgRelations = orgRelationsService.getOrgRelationIdByOrgLocationIdAndClientLocationId(3l, orgRelations.getResponderLocationId().getId());
		if(orgRelations != null)
		{
		logger.info("getOrgRelationIdByOrgLocationIdAndClientLocationId "+orgRelations.getRelationType());
	}
	}
	
	@Test
	public void getAllClientsByOrgLocationId() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(3L);
		orgRelations.setLocationId(orglocations);
		Collection<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgRelations.getLocationId().getId());
		if(orgRelations != null)
		{
		logger.info("getAllClientsByOrgLocationId "+clientList.size());
		}
	}
	
	@Test
	public void getClientByOrgLocationId() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(3L);
		orgRelations.setLocationId(orglocations);
		orgRelations = orgRelationsService.getClientByOrgLocationId(orgRelations.getLocationId().getId());
		if(orgRelations != null)
		{
		logger.info("getClientByOrgLocationId "+ orgRelations.getStatus());
		//logger.info("getClientByOrgLocationId "+orgRelations.getClientLocationId().getOrg().getOrgName());
	}
	}
	
	@Test
	public void getNumberOfProjectsPerClient() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(3L);
		orgRelations.setLocationId(orglocations);
		Long count = orgRelationsService.getNumberOfProjectsPerClient(orgRelations.getLocationId().getId());
		if(count != null)
		{
		logger.info("getNumberOfProjectsPerClient "+count);
	}
	}
	
	@Test
	public void getProjectsCountByOrgRelationIdAndOrgLocationId() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(1L);
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(4L);
		orgRelations.setLocationId(orglocations);
		Long count = orgRelationsService.getProjectsCountByOrgRelationIdAndOrgLocationId(orgRelations.getId(), orgRelations.getLocationId().getId());
		if(count != null)
		{
		logger.info("getProjectsCountByOrgRelationIdAndOrgLocationId "+count);
	}
	}
}
