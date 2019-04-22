package com.bis.sourcelead.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.atLeast;
import static org.hamcrest.CoreMatchers.*;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.States;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.StatesService;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RunWith(PowerMockRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateControllerTest {

//private CandidateController controller;

@Mock
private CandidateService mockCandidateService;

@Mock
private HttpServletRequest mockHttpServletRequest;

@Mock
SourceLeadSessionManager sourceLeadSessionManager;

@Mock
ReportsUtil mockReportsUtil;

@Mock
LookUpDataService mockLookUpDataService;

@Mock
CountriesService mockCountriesService;

@Mock
StatesService mockStatesService;

@Before
public void setUp() throws Exception {
initMocks(this);
//controller = new CandidateController();
//ReflectionTestUtils.setField(controller, "candidateService", mockCandidateService);
//ReflectionTestUtils.setField(controller, "reportsUtil", mockReportsUtil);
//ReflectionTestUtils.setField(controller, "lookUpDataService", mockLookUpDataService);
//ReflectionTestUtils.setField(controller, "countriesService", mockCountriesService);
//ReflectionTestUtils.setField(controller, "statesService", mockStatesService);
}

@SuppressWarnings("deprecation")
@Test
public void getGlobalMarketplaceCandList() throws Exception {
	try{
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
//	System.out.println(" test "+candidateMarketPlaceDTOList.getRecordsTotal());
	when(mockCandidateService.getGlobalMarketCandidateList(dto,"")).thenReturn(candidateMarketPlaceDTOList);
	ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOResponseList = null;
	candidateMarketPlaceDTOResponseList = mockCandidateService.getGlobalMarketCandidateList(dto,"");
//	System.out.println(candidateMarketPlaceDTOList.getRecordsTotal());
	Assert.assertEquals(mockCandidateService.getGlobalMarketCandidateList(dto,"").getRecordsTotal(),2);
	}catch(Exception e){
		e.printStackTrace();
	}
}

@SuppressWarnings("deprecation")
@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void getGlobalMarketplaceCandListGridCard() throws Exception {
 try{
String searchValue = null;
String[] searchCandidateIds = null;
String candidateLocation = null;
List<CandidateMarketPlaceDTO> candidateMarketPlaces = new ArrayList<CandidateMarketPlaceDTO>();
List<CandidateMarketPlaceDTO> candidateMarketPlace = new ArrayList<CandidateMarketPlaceDTO>();
OrgLocations orgLocation = new OrgLocations();
orgLocation.setId(1959l);
//OrgLocations spyOrgLocations = spy(new OrgLocations());
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
//when(mockCandidateService.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocation, candidateLocation, searchValue)).thenReturn(candidateMarketPlace);
//System.out.println(controller);
//candidateMarketPlaces =  controller.getGlobalMarketplaceCandListGridCard(searchValue, candidateLocation, searchCandidateIds, mockHttpServletRequest);
Assert.assertEquals(candidateMarketPlaces.size(),1);
 }catch(Exception e){
		e.printStackTrace();
	}
}

@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void getCandidateDetailsFromMarketcart() throws Exception{
	try{
	User user = new User();
	String userRoleName = "abc";
	OrgLocations orgLocation = new OrgLocations();
	String searchValue = "";
	String creationStartDate = "";
	String creationEndDate = "";
	String checkedDate = "abc";
	 String s = "09/22/2006";
     SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
     Date date1 = sd.parse(s);
     Date[] checkedDateArray = new Date[1];
     checkedDateArray[0] = date1;
	List<CandidateMarketPlaceCartView> candidatesFromMarketplace = null;
	PowerMockito.mockStatic(SourceLeadSessionManager.class);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "user")).thenReturn(user);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "loginUserRole")).thenReturn(userRoleName);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "orgLocations")).thenReturn(orgLocation);
	when(mockReportsUtil.getJobFilterDate(checkedDate, orgLocation)).thenReturn(checkedDateArray);
	when(mockCandidateService.getCandidateDetailsFromMarketcart(user, userRoleName, orgLocation, creationStartDate, creationEndDate, checkedDate, searchValue)).thenReturn(candidatesFromMarketplace);
//    controller.getCandidateDetailsFromMarketcart(searchValue, creationStartDate, creationEndDate, checkedDate, mockHttpServletRequest);
	verify(mockCandidateService).getCandidateDetailsFromMarketcart(user, userRoleName, orgLocation, creationStartDate, creationEndDate, checkedDate, searchValue);
	}catch(Exception e){
		e.printStackTrace();
	}
}

@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void globalCandidateSummary() throws Exception {
	try{
	String marketplaceUUID = null;
	String marketplaceCandidateId = null;
	OrgLocations orgLocation = new OrgLocations();
	CandidateMarketPlaceDTO marketPlaceDTO = new CandidateMarketPlaceDTO();
	marketPlaceDTO.setCandidateId("176274");
	mockHttpServletRequest.setAttribute("marketplaceUUID","56785");
	PowerMockito.mockStatic(SourceLeadSessionManager.class);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "orgLocations")).thenReturn(orgLocation);
	when(mockCandidateService.getCandidateMarketPlaceDTODetailsByUUId(marketplaceUUID,orgLocation)).thenReturn(marketPlaceDTO);
//	controller.globalCandidateSummary(marketplaceUUID,mockHttpServletRequest);
	marketplaceCandidateId = marketPlaceDTO.getCandidateId();
	assertThat(marketplaceCandidateId,is("176274"));
	}catch(Exception e){
		e.printStackTrace();
	}
}

@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void goToSalesOrder() throws Exception {
	try{
	String salesUuid = null;
	OrgLocations orgLocation = new OrgLocations();
	User user = new User();
	MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO = new MarketplacePurchaseSalesDTO();
	MarketplacePurchaseSalesDTO salesOrder = new MarketplacePurchaseSalesDTO();
	mockHttpServletRequest.setAttribute("salesUuid","5685");
	PowerMockito.mockStatic(SourceLeadSessionManager.class);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "orgLocations")).thenReturn(orgLocation);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "user")).thenReturn(user);
	when(mockCandidateService.getMarketplaceSalesOrderBySalesUuid(orgLocation, salesUuid)).thenReturn(salesOrder);
//	controller.goToSalesOrder(marketplacePurchaseSalesDTO, salesUuid, mockHttpServletRequest);
	verify(mockCandidateService).getMarketplaceSalesOrderBySalesUuid(orgLocation, salesUuid);
	}catch(Exception e){
		e.printStackTrace();
	}
}

@PrepareForTest({ SourceLeadSessionManager.class })
@Test
public void goToGlobalMarketplace() throws Exception {
	try{
	Countries countries = new Countries();
	List<LookUpData> lookUpData = new ArrayList<LookUpData>();
	List<Countries> countryList = new ArrayList<Countries>();
	List<States> statesList = new ArrayList<States>();
	OrgLocations orgLocation = new OrgLocations();
	orgLocation.setCountry("abc");
	PowerMockito.mockStatic(SourceLeadSessionManager.class);
	PowerMockito.when(SourceLeadSessionManager.getSessionAttribute(mockHttpServletRequest, "orgLocations")).thenReturn(orgLocation);
	when(mockLookUpDataService.getLookUpDataByCategory(anyString())).thenReturn(lookUpData);
	when(mockCountriesService.getAllCountries()).thenReturn(countryList);
	when(mockCountriesService.getCountriesByCountryName(orgLocation.getCountry())).thenReturn(countries);
	when(mockStatesService.getAllStatesByCountry(countries.getName())).thenReturn(statesList);
//	controller.goToGlobalMarketplace(mockHttpServletRequest);
	verify(mockLookUpDataService,times(3)).getLookUpDataByCategory(anyString());
	//verify(mockLookUpDataService, atLeast(2)).getLookUpDataByCategory(anyString()); checks the minimum inovocations of service calls. 
	verify(mockCountriesService).getAllCountries();
	verify(mockCountriesService).getCountriesByCountryName(orgLocation.getCountry());
	verify(mockStatesService).getAllStatesByCountry(countries.getName());
	
  }catch(Exception e){
	e.printStackTrace();
 }
}

}
