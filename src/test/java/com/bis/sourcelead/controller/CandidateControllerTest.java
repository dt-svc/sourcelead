package com.bis.sourcelead.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.CandidateController;

@RunWith(PowerMockRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateControllerTest {

@Autowired	
private CandidateController controller;

@Autowired
private CandidateService candidateService;

@Mock
private CandidateService mockCandidateService;

@Mock
private HttpServletRequest mockHttpServletRequest;

@Mock
SourceLeadSessionManager sourceLeadSessionManager;

@Before
public void setUp() throws Exception {
initMocks(this);
controller = new CandidateController();
ReflectionTestUtils.setField(controller, "candidateService", mockCandidateService);
}

@SuppressWarnings("deprecation")
@Test
public void getGlobalMarketplaceCandList() throws Exception {
	List<CandidateMarketPlaceDTO> candidateDTOList = new ArrayList<CandidateMarketPlaceDTO>();
	ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = new ResponseList<CandidateMarketPlaceDTO>();
	CandidateMarketPlaceDTO dto = new CandidateMarketPlaceDTO();
	CandidateMarketPlaceDTO dto1 = new CandidateMarketPlaceDTO();
	CandidateMarketPlaceDTO dto2 = new CandidateMarketPlaceDTO();
	dto1.setId(989l);
	dto2.setId(999l);
	candidateDTOList.add(dto1);
	candidateDTOList.add(dto2);
	candidateMarketPlaceDTOList.setData(candidateDTOList);
	candidateMarketPlaceDTOList.setRecordsTotal(2);
	System.out.println(" test "+candidateMarketPlaceDTOList.getRecordsTotal());
	when(mockCandidateService.getGlobalMarketCandidateList(dto,"")).thenReturn(candidateMarketPlaceDTOList);
	ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOResponseList = null;
	candidateMarketPlaceDTOResponseList = mockCandidateService.getGlobalMarketCandidateList(dto,"");
	System.out.println(candidateMarketPlaceDTOList.getRecordsTotal());
	assertEquals(mockCandidateService.getGlobalMarketCandidateList(dto,"").getRecordsTotal(),2);
}

@SuppressWarnings("deprecation")
@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void getGlobalMarketplaceCandListGridCard(){
String searchValue = null;
String[] searchCandidateIds = null;
String candidateLocation = null;
List<CandidateMarketPlaceDTO> candidateMarketPlaces = new ArrayList<CandidateMarketPlaceDTO>();
List<CandidateMarketPlaceDTO> candidateMarketPlace = new ArrayList<CandidateMarketPlaceDTO>();
OrgLocations orgLocation = new OrgLocations();
orgLocation.setId(1959l);
CandidateMarketPlaceDTO dto = new CandidateMarketPlaceDTO();
dto.setCandidateId("176294");
candidateMarketPlace.add(dto);
mockHttpServletRequest.setAttribute("searchValue","Aditi");
mockHttpServletRequest.setAttribute("searchCandidateId","176294");
mockHttpServletRequest.setAttribute("candidateLocation","Hyderabad");
mockHttpServletRequest.setAttribute("SourceLeadWeb_orgLocations",orgLocation);
PowerMockito.mockStatic(SourceLeadSessionManager.class);
PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "orgLocations"))
.thenReturn(orgLocation);
when(mockCandidateService.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocation, candidateLocation, searchValue)).thenReturn(candidateMarketPlace);
System.out.println(controller);
candidateMarketPlaces =  controller.getGlobalMarketplaceCandListGridCard(searchValue, candidateLocation, searchCandidateIds, mockHttpServletRequest);
assertEquals(candidateMarketPlaces.size(),1); 
}

}
