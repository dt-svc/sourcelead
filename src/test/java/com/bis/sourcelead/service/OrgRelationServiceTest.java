package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;

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
		//orgRelations.setRelationType("Client");
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedDate(new Date());
		orgRelations.setCreatedBy("source");
		orgRelations.setUpdatedDate(new Date());
		orgRelations.setUpdatedBy("Source");
		orgRelationsService.addOrgRelations(orgRelations);
		logger.info("Added Successfully..."+orgRelations.getStatus());
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
		logger.info("getOrgRelationIdByOrgLocationIdAndClientLocationId "+orgRelations.getStatus());
	}
	}
	
	@Test
	public void getAllClientsByOrgLocationId() throws Exception {
		OrgRelations orgRelations = new OrgRelations();
		OrgLocations orglocations = new OrgLocations();
		orglocations.setId(3L);
		orgRelations.setLocationId(orglocations);
		List<OrgRelations> clientList = orgRelationsService.getAllClientsByOrgLocationId(orgRelations.getLocationId().getId());
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
	
	@Test
	public void orgRelationsListByJson() throws Exception {
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("level", "1");
		
		OrgLocations clientOrgLocation = new OrgLocations();
		clientOrgLocation.setId(3L);
		
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(5l);
		
		String loginUserCode = "9rYSE8Pz";
		String responderType = "Client";
		
		Set<OrgRelations> orgRelations = orgRelationsService.orgRelationsListByJson(jsonObj, clientOrgLocation, orgLocations, loginUserCode, responderType);
		logger.info("orgRelationsListByJson "+orgRelations.size());
	}
	
	@Test
	public void getAllVendorsListByOrgLocationId() throws Exception {
		List<OrgRelations> list = orgRelationsService.getAllVendorsListByOrgLocationId(3L);
		logger.info("getAllVendorsListByOrgLocationId "+list.size());
	}
	
	@Test
	public void getClientAndVendorOrgDeatilsById() throws Exception {
		OrgRelations orgRelations = orgRelationsService.getClientAndVendorOrgDeatilsById(3l, 2l);
		logger.info("getClientAndVendorOrgDeatilsById "+orgRelations.getResponderType());
	}
	
	@Test
	public void getOrRelationsById() throws Exception {
		OrgRelations orgRelations = orgRelationsService.getOrRelationsById(2l);
		logger.info("getOrRelationsById "+orgRelations.getResponderType());
	}
	
	@Test
	public void getAllVendorsContactListByOrgLocationId() throws Exception {
		List<OrgSpoc> list = orgRelationsService.getAllVendorsContactListByOrgLocationId(3l, 3l, "Client");
		logger.info("getAllVendorsContactListByOrgLocationId "+list.size());
	}
	
	@Test
	public void getRespondingPartyDetailsById() throws Exception {
		OrgRelations orgRelations = orgRelationsService.getRespondingPartyDetailsById(2l);
		logger.info("getRespondingPartyDetailsById "+orgRelations.getResponderType());
	}
}
