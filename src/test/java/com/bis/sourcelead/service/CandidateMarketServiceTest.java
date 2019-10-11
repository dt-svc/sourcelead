package com.bis.sourcelead.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.CandidateCertificationsDao;
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dao.CandidateEducationDao;
import com.bis.sourcelead.dao.CandidateEmployersDao;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.CandidateMarketPlaceDTO;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.dto.MarketplaceWalletDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.elk.index.CandidateIndex;
import com.bis.sourcelead.elk.index.helper.CandidateIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.CandidateServiceImpl;
import com.bis.sourcelead.util.CommonUtil;

@RunWith(PowerMockRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateMarketServiceTest {
	
	private CandidateService candidateService ;
	private static final Logger logger = LoggerFactory.getLogger(CandidateMarketServiceTest.class);
	
	@Mock
	CandidateDao mockCandidateDao;
	
	@Mock
    CommonUtil mockCommonUtil;
	
	@Mock
	CandidateIndexHelper mockCandidateIndexHelper;
	
	@Mock
    SourceleadELKBaseIndexer<CandidateIndex> mockSourceleadELKBaseIndexer;
	
	@Mock
	CandidateEmployersDao mockCandidateEmployersDao;
	
	@Mock
    CandidateEducationDao mockCandidateEducationDao;
	
	@Mock
    CandidateCertificationsDao mockCandidateCertificationsDao;
	
	@Before
	public void setUp() throws Exception {
	initMocks(this);
	candidateService = new CandidateServiceImpl();
	ReflectionTestUtils.setField(candidateService, "candidateDao", mockCandidateDao);
	ReflectionTestUtils.setField(candidateService, "commonUtil" , mockCommonUtil);
	ReflectionTestUtils.setField(candidateService, "candidateIndexHelper" , mockCandidateIndexHelper);
	ReflectionTestUtils.setField(candidateService, "sourceleadELKBaseIndexer" , mockSourceleadELKBaseIndexer);
	ReflectionTestUtils.setField(candidateService, "candidateEmployersDao", mockCandidateEmployersDao);
	ReflectionTestUtils.setField(candidateService, "candidateEducationDao", mockCandidateEducationDao);
	ReflectionTestUtils.setField(candidateService, "candidateCertificationsDao", mockCandidateCertificationsDao);
	}
	
	@Test
	public void getMarketplaceTransactionDetails() throws Exception{
		
		CandidateMarketPlacePaymentTransactionHistory cmpth = new CandidateMarketPlacePaymentTransactionHistory();
		CandidateMarketPlacePaymentTransactionHistory cmpthreturn = null;
		Long orgLocationId = 234l;
		when(mockCandidateDao.getMarketplaceTransactionDetails(orgLocationId)).thenReturn(cmpth);
		cmpthreturn = candidateService.getMarketplaceTransactionDetails(orgLocationId);
		assertNotNull(cmpthreturn);
		
	}
	
	@Test
	public void getCandidateDetailsByCandidateId() throws Exception{
		
		Long candidateId = 123l;
		CandidateMarketPlace candidateDetails = new CandidateMarketPlace();
		CandidateMarketPlace candidateDetails1 = null;
		when(mockCandidateDao.getCandidateDetailsByCandidateId(candidateId)).thenReturn(candidateDetails);
		candidateDetails1 = candidateService.getCandidateDetailsByCandidateId(candidateId);
		assertNotNull(candidateDetails1);
		
	}
	
	@Test
	public void getUserDetailsBySourceOrgLocationId() throws Exception{
		
		Long sourceOrgLocationId = 123l;
		User user = new User();
		User user1 = null;
		when(mockCandidateDao.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId)).thenReturn(user);
		user1 = candidateService.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId);
		assertNotNull(user1);
	}

	@Test
	public void updateMarketplaceCartCountAndPrice() throws Exception{
		
		Long orgLocationId = 123l; 
		String userRole = "";
		User user = new User();
		Object[] obj = new Object[5];
		Object[] obj1 = null;
		when(mockCandidateDao.updateMarketplaceCartCountAndPrice(orgLocationId, userRole, user)).thenReturn(obj);
		obj1 = candidateService.updateMarketplaceCartCountAndPrice(orgLocationId, userRole, user);
		assertNotNull(obj1);
	
	}
	
	@Test
	public void updateMarketCartStatus() throws Exception{
		
		List<Long> marketCartIds = new ArrayList<>();
		Long orgLocationId = 234l;
		Boolean bool = false;
		when(mockCandidateDao.updateMarketCartStatus(marketCartIds, orgLocationId)).thenReturn(true);
		bool = candidateService.updateMarketCartStatus(marketCartIds, orgLocationId);
		assertTrue(bool);
		
	}
	
	@Test
	public void getCandidateMarketPlaceDetailsByUUId() throws Exception{
		
		String uuid = "";
		CandidateMarketPlace marketPlace = new CandidateMarketPlace();
		CandidateMarketPlace marketPlace1 = null;
		when(mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(uuid)).thenReturn(marketPlace);
		marketPlace1 = candidateService.getCandidateMarketPlaceDetailsByUUId(uuid);
		assertNotNull(marketPlace1);
	}
	
	@Test
	public void getMarketplaceSalesOrderBySalesUuid() throws Exception{
		
		OrgLocations orgLocations = new OrgLocations(); 
		String salesUuid = "";
		String formattedDate = "abc";
		String fullName = "";
		MarketplacePurchaseSalesDTO salesOrderDto = new MarketplacePurchaseSalesDTO();
		MarketplaceSalesOrder salesOrder = new MarketplaceSalesOrder();
		CandidateMarketPlace candidateMarketPlace = new CandidateMarketPlace();
		CandidateGlobal candidateId = new CandidateGlobal();
		candidateMarketPlace.setCandidateId(candidateId);
		candidateId.setFullName(fullName);
		salesOrder.setId(123l);
		salesOrder.setOrderNo("243");
		salesOrder.setCandidateMarketPlace(candidateMarketPlace);
		when(mockCandidateDao.getMarketplaceSalesOrderBySalesUuid(orgLocations.getId(), salesUuid)).thenReturn(salesOrder);
		when(mockCommonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), salesOrder.getOrderDate())).thenReturn(formattedDate);
		salesOrderDto = candidateService.getMarketplaceSalesOrderBySalesUuid(orgLocations, salesUuid);
		assertEquals(salesOrderDto.getOrderNo(),salesOrder.getOrderNo());
	}
	
	@Test
	public void getPurchaseDetailByPurchaseUuid() throws Exception{
		
		String purchaseUuid = ""; 
		String startTime = "";
		String fullName = "";
		OrgLocations orgLocations = new OrgLocations();
		MarketplacePurchaseSalesDTO purchaseOrderDto = new MarketplacePurchaseSalesDTO();
		MarketplacePurchaseOrder mps = new MarketplacePurchaseOrder();
		CandidateMarketPlace candidateMarketPlace = new CandidateMarketPlace();
		CandidateGlobal candidateId = new CandidateGlobal();
		candidateMarketPlace.setCandidateId(candidateId);
		candidateId.setFullName(fullName);
		mps.setOrderNo("abc");
		mps.setCandidateMarketplace(candidateMarketPlace);
		when(mockCandidateDao.getPurchaseDetailByPurchaseUuid(purchaseUuid,orgLocations.getId())).thenReturn(mps);
		when(mockCommonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(), mps.getOrderDate())).thenReturn(startTime);
		purchaseOrderDto = candidateService.getPurchaseDetailByPurchaseUuid(purchaseUuid,orgLocations);
		assertEquals(purchaseOrderDto.getOrderNo(),mps.getOrderNo());
	}
	
	@Test
	public void getBalanceAmountFromWallet() throws Exception{
		
		Long id = 123l;
		Double totalAmount = 20.0;
		Double totalAmount1 = 0.0;
		when(mockCandidateDao.getBalanceAmountFromWallet(id)).thenReturn(totalAmount);
		totalAmount1 = candidateService.getBalanceAmountFromWallet(id);
		assertThat(totalAmount1, is(20.0));
		
	}
	
	@Test
	public void getWalletList() throws Exception{
		
			MarketplaceWalletDTO marketplaceWalletDTO = new MarketplaceWalletDTO();
			OrgLocations orgLocations = new OrgLocations();
			String dateFormat = "";
			marketplaceWalletDTO.setOrgLocations(orgLocations);
			orgLocations.setDateFormat(dateFormat);
			String searchValue = "abc";
			String transactionType = "dummy";
			String selectedTime = "";
			String fromDate = "";
			String toDate = "";
			Date formattedDate = new Date();
			Date today = new Date();
			Map<String, Date> dateValue = new HashMap<>();
			ResponseList<MarketplaceWalletDTO> responseList = new ResponseList<>();
			ResponseList<MarketplaceWalletDTO> responseList1 = null;
			when(mockCandidateDao.getWalletList(marketplaceWalletDTO, searchValue,transactionType, dateValue)).thenReturn(responseList);
			when(mockCommonUtil.getOrgLocationCurrentDate(marketplaceWalletDTO.getOrgLocations().getDateFormat(), today)).thenReturn(formattedDate);
			responseList1 = candidateService.getWalletList(marketplaceWalletDTO, searchValue, selectedTime, transactionType, fromDate, toDate);
			assertNotNull(responseList1);
		
	}
	
	@Test
	public void deleteCandidateMarketCartByMarketUUID() throws Exception{
		
			String candidatgeMarketUUID = "abc";
		    doNothing().when(mockCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
		    candidateService.deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
			verify(mockCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
			
	}
	
	@Test
	public void saveToMarketplacePurchaseOrder() throws Exception{
		
			MarketplacePurchaseOrder newMarketplacePurchaseorder = new MarketplacePurchaseOrder();
			CandidateMarketCart candidateMarketCart = new CandidateMarketCart();
			List<CandidateMarketCart> marketCartList = new ArrayList<>();
			OrgLocations orgLocations = new OrgLocations();
			String orderNo = "";
			String purchasedOrgName = "";
			String paymentId = "";
			marketCartList.add(candidateMarketCart);
			doNothing().when(mockCandidateDao).saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
			candidateService.saveToMarketplacePurchaseOrder(marketCartList, orgLocations, orderNo, purchasedOrgName, paymentId);
			verify(mockCandidateDao).saveToMarketplacePurchaseOrder(any());
		
	}
	
	@Test
	public void saveToMarketplaceSalesOrder() throws Exception{
		
			 MarketplaceSalesOrder newMarketplaceSalesorder = new MarketplaceSalesOrder();
			 CandidateMarketCart candidateMarketCart = new CandidateMarketCart();
			 List<CandidateMarketCart> marketCartList = new ArrayList<>();
			 Org sourceOrgId = new Org();
			 String orgName = "";
			 sourceOrgId.setOrgName(orgName);
			 String sourceOrgName = "" ;
			 String orderNo = "";
			 String paymentId = "";
			 candidateMarketCart.setSourceOrgId(sourceOrgId);
			 marketCartList.add(candidateMarketCart);
			 //candidateMarketCart.getSourceOrgId().getOrgName();	
			 doNothing().when(mockCandidateDao).saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
			 candidateService.saveToMarketplaceSalesOrder(marketCartList, orderNo, paymentId);
			 verify(mockCandidateDao).saveToMarketplaceSalesOrder(any());
			
	}
	
	@Test
	public void deleteAllCandidatesFromMarketcart() throws Exception{
		
			Long orgLocationId = 123l;
			 doNothing().when(mockCandidateDao).deleteAllCandidatesFromMarketcart(orgLocationId);
			 candidateService.deleteAllCandidatesFromMarketcart(orgLocationId);
			 verify(mockCandidateDao).deleteAllCandidatesFromMarketcart(orgLocationId);
		
	}
	
	@Test
	public void getPurchaseCandidateListByOrglocation() throws Exception{
		
			Long id = 1234l;
			MarketplacePurchaseOrder mpo = new MarketplacePurchaseOrder();
			List<MarketplacePurchaseOrder> list = new ArrayList<MarketplacePurchaseOrder>();
			List<MarketplacePurchaseOrder> list1 = new ArrayList<MarketplacePurchaseOrder>();
			list.add(mpo);
			when(mockCandidateDao.getPurchaseCandidateListByOrglocation(id)).thenReturn(list);
			list1 = candidateService.getPurchaseCandidateListByOrglocation(id);
			assertThat(list1.size(),is(1));
		
	}
	
	@Test
	public void saveCandidateMarketPlaceWallet() throws Exception{
		
			CandidateMarketWallet wallet = new CandidateMarketWallet();
			doNothing().when(mockCandidateDao).saveCandidateMarketPlaceWallet(wallet);
			candidateService.saveCandidateMarketPlaceWallet(wallet);
			verify(mockCandidateDao).saveCandidateMarketPlaceWallet(wallet);
			
	}

	@Test
	public void withdrawCandidateFromMarketplace() throws Exception{
		
		Long id = 123l;
		String withdrawReason = "";
		Candidate candidate = new Candidate();
		candidate.setId(id);
		Candidate candidate1 = null;
		CandidateMarketPlace marketplace = new CandidateMarketPlace();
		CandidateGlobal candidateId = new CandidateGlobal();
		candidateId.setId(id);
		marketplace.setCandidateId(candidateId);
		when(mockCandidateDao.getCandidateById(id)).thenReturn(candidate);
		candidate1 = candidateService.getCandidateById(marketplace.getCandidateId().getId());
		assertNotNull(candidate1);
		CandidateIndex candidateIndex = new CandidateIndex();
		when(mockCandidateIndexHelper.getCandidateIndexFromCandidate(candidate)).thenReturn(candidateIndex);
		doNothing().when(mockSourceleadELKBaseIndexer).saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
		doNothing().when(mockCandidateDao).withdrawCandidateFromMarketplace(marketplace);
		candidateService.withdrawCandidateFromMarketplace(marketplace, withdrawReason);
		verify(mockCandidateDao).withdrawCandidateFromMarketplace(marketplace);
		doNothing().when(mockCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
		verify(mockCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
	}
	
	@Test
	public void updateMarketWalletAmount() throws Exception{
		
			Double balanceAmountValue = 123.4;
			Long id = 123l;
			doNothing().when(mockCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
			candidateService.updateMarketWalletAmount(balanceAmountValue, id);
			verify(mockCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
			
		
	}
	
	/*@Test
	public void getPurchaseCandidateExcelFile() throws Exception{
		
			MarketplacePurchaseOrder mpo1 = new MarketplacePurchaseOrder();
			List<MarketplacePurchaseOrder> purchaseCandidatesList = new ArrayList<MarketplacePurchaseOrder>();
			Long orgLocationId = 123l;
			purchaseCandidatesList.add(mpo1);
			File file = candidateService.getPurchaseCandidateExcelFile(purchaseCandidatesList, orgLocationId);
			System.out.println("marketplace file" file.getName());
			assertEquals(file.getName().contains("Purchased Candidates"),true);
			file.delete();
			
		
	}
	
	@Test
	public void getPurchaseCandidatePdf() throws Exception{
		
			MarketplacePurchaseOrder mpo1 = new MarketplacePurchaseOrder();
			List<MarketplacePurchaseOrder> purchaseCandidatesList = new ArrayList<MarketplacePurchaseOrder>();
			Long orgLocationId = 123l;
			purchaseCandidatesList.add(mpo1);
			File file = candidateService.getPurchaseCandidatePdf(purchaseCandidatesList, orgLocationId);
			System.out.println("marketplace file" file.getName());
			assertEquals(file.getName().contains("PurchaseCandidate"),true);
			file.delete();
			
		
	}*/
	
	
	@Test
	public void getMarketplaceSalesOrderList() throws Exception{
		
			OrgLocations orgLocations = new OrgLocations();
			MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO = new MarketplacePurchaseSalesDTO();
			String searchValue = "dummy";
			String salesOrderFromDate = ""; 
			String salesOrderToDate = "";
			String salesOrderFilterDate = "";
			Map<String, Date> dateValue = new HashMap<>();
			ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
			ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
			when(mockCandidateDao.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue)).thenReturn(responseList);
			responseList1 = candidateService.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, salesOrderFilterDate);
			assertNotNull(responseList1);
					
		
	}
	
	@Test
	public void getMarketplacePurchaseOrder() throws Exception{
		
			OrgLocations orgLocations = new OrgLocations();
			MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO = new MarketplacePurchaseSalesDTO();
			orgLocations.setId(123l);
			ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
			String searchValue="";
			String purchaseOrderFromDate = "";
			String purchaseOrderToDate = ""; 
			String purchaseOrderFilterDate = "";
			Map<String, Date> dateValue = new HashMap<>();
			ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
			when(mockCandidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), marketplacePurchaseSalesDTO,orgLocations, searchValue,purchaseOrderFromDate, purchaseOrderToDate, dateValue)).thenReturn(responseList);
			responseList1 = candidateService.getMarketplacePurchaseOrder(orgLocations, marketplacePurchaseSalesDTO,searchValue,  purchaseOrderFromDate, purchaseOrderToDate, purchaseOrderFilterDate);
			assertNotNull(responseList1);
		
	}
	
	@Test
	public void getOrderNo() throws Exception{
		
			Long id = 123l;
			CandidateMarketPlacePaymentTransactionHistory transactionHistory = new CandidateMarketPlacePaymentTransactionHistory();
			CandidateMarketPlacePaymentTransactionHistory transactionHistory1 = null;
			when(mockCandidateDao.getOrderNo(id)).thenReturn(transactionHistory);
			transactionHistory1 = candidateService.getOrderNo(id);
			assertNotNull(transactionHistory1);
		
	}
	
	@Test
	public void getCandidatesListByIds() throws Exception{
		
			List<Long> Ids = new ArrayList<Long>();
			List<CandidateDTO> candidateDTOList = new ArrayList<CandidateDTO>();
			List<CandidateDTO> candidateDTOList1 = null;
			when(mockCandidateDao.getCandidatesListByIds(Ids)).thenReturn(candidateDTOList);
			candidateDTOList1 = candidateService.getCandidatesListByIds(Ids);
			assertNotNull(candidateDTOList1);
			
		
	}
	
	@Test
	public void getMarketCartIdsFromMarketcart() throws Exception{
		
			Long orgLocationId = 123l;
			List<CandidateMarketCart> candidateMarketCartList = new ArrayList<>();
			List<CandidateMarketCart> candidateMarketCartList1 = null;
			when(mockCandidateDao.getMarketCartIdsFromMarketcart(orgLocationId)).thenReturn(candidateMarketCartList);
			candidateMarketCartList1 = candidateService.getMarketCartIdsFromMarketcart(orgLocationId);
			assertNotNull(candidateMarketCartList1);
			
		
	}
	
	@Test
	public void getCandidateMarketPlaceDTODetailsByUUId() throws Exception{
		
		    CandidateMarketPlaceDTO marketPlaceDTO = new CandidateMarketPlaceDTO();	
		    String marketplaceUUID = "123A";
			OrgLocations orgLocations = new OrgLocations();
			CandidateMarketPlace marketPlace = new CandidateMarketPlace();
			CandidateGlobal candidate = new CandidateGlobal();
			candidate.setSkillsName("j");
			marketPlace.setCandidateId(candidate);
			marketPlace.setOrgLocationId(orgLocations);
			CandidateMarketPlace marketPlace1 = null;
			Long candidateId = 123l;
			Long orgLocationId = 1234l;
			List<CandidateEmployers> candidateEmployers = new ArrayList<>();
			List<CandidateEmployers> spyList = Mockito.spy(candidateEmployers);
			CandidateEmployers dummyEmployer= new CandidateEmployers();
			dummyEmployer.setId(12345L);
			spyList.add(dummyEmployer);
			List<CandidateEducation> educations = new ArrayList<>();
			List<CandidateCertifications> certifications = new ArrayList<>();
			List<CandidateMarketPlace> globalCandidatesMarketplaceData = new ArrayList<>();
			when(mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID)).thenReturn(marketPlace);
			marketPlace1 = candidateService.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID);
			assertNotNull(marketPlace1);
			when(mockCandidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId)).thenReturn(spyList);
			when(mockCandidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId)).thenReturn(educations);
			when(mockCandidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId)).thenReturn(certifications);
			when(mockCandidateDao.getGlobalMarketplaceDataByOrgId(orgLocations.getId(), marketPlace.getId())).thenReturn(globalCandidatesMarketplaceData);
			when(mockCandidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocationId)).thenReturn(1);
			marketPlaceDTO = candidateService.getCandidateMarketPlaceDTODetailsByUUId(marketplaceUUID, orgLocations);
			verify(spyList).add(dummyEmployer);
			verify(mockCandidateEmployersDao).getCandidatesEmployersListByCandidateIdAndOrgLocationID(any(), any());
			verify(mockCandidateEducationDao).getCandidateEducationListByCandidateIdAndOrgLocationID(any(),any());
			verify(mockCandidateCertificationsDao).getCandidateCertificationListByCandidateIdAndOrgLocationID(any(), any());
			verify(mockCandidateDao).getGlobalMarketplaceDataByOrgId(any(), any());
			verify(mockCandidateDao).getMarketCartStatusByMarketIdOrgId(any(), any());
	}
	
	@Test
	public void getGlobalDataRecommendations() throws Exception{
		
			CandidateMarketPlaceDTO candidateMarketPlaceDTO = new CandidateMarketPlaceDTO();
			OrgLocations orgLocations = new OrgLocations();
			String searchValue = "dummy";
			ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = new ResponseList<>();
			ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList1 = null;
			when(mockCandidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue)).thenReturn(candidateMarketPlaceDTOList);
			candidateMarketPlaceDTOList1 = candidateService.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue);
			assertNotNull(candidateMarketPlaceDTOList1);
			
		
	}
	
	@Test
	public void updateMarketplaceCandidates() throws Exception{
		
			OrgLocations orgLocations = new OrgLocations();
			String companyNameFlag = "abc";
			int x = 0;
			when(mockCandidateDao.updateMarketplaceCandidates(orgLocations, companyNameFlag)).thenReturn(1);
			x = candidateService.updateMarketplaceCandidates(orgLocations, companyNameFlag);
			assertThat(x, is(1));
			
		
	}
	
	@Test
	public void getCandidateDetailsFromMarketcart() throws Exception{
		
			User user = new User(); 
			OrgLocations orgLocations = new OrgLocations();
			String userRole = ""; 
			String creationStartDate = ""; 
			String creationEndDate = "";
			String checkedDate = "";
			String searchValue = "";
			Map<String, Date> dateValue = new HashMap<>();
			Date[] checkedDateArray = new Date[0];
			List<CandidateMarketPlaceCartView> candidateMarketPlaceCartView = new ArrayList<>();
			List<CandidateMarketPlaceCartView> candidateMarketPlaceCartView1 = null;
			when(mockCandidateDao.getCandidateDetailsFromMarketcart(user, userRole, orgLocations, creationStartDate, creationEndDate, checkedDateArray, searchValue)).thenReturn(candidateMarketPlaceCartView);
			candidateMarketPlaceCartView1 = mockCandidateDao.getCandidateDetailsFromMarketcart(user, userRole, orgLocations, creationStartDate, creationEndDate, checkedDateArray, searchValue);
			assertNotNull(candidateMarketPlaceCartView1);
			
	}
	
	@Test
	public void getCandidateProfilePercentageByCandidateId() throws Exception{
		
			Long id = 123l;
			Integer profilePercentage = 10;
			Integer profilePercentage1 = 0;
			when(mockCandidateDao.getCandidateProfilePercentageByCandidateId(id)).thenReturn(profilePercentage);
			profilePercentage1 = candidateService.getCandidateProfilePercentageByCandidateId(id);
			assertThat(profilePercentage1,is(10));
		
	}
	
	@Test
	public void getGlobalMarketplaceCandListGridCard() throws Exception{
		    OrgLocations orgLocations = new OrgLocations();
		    CandidateMarketPlace marketPlace = new CandidateMarketPlace();
		    CandidateGlobal candidate = new CandidateGlobal();
		    candidate.setFullName("");
		    candidate.setFileUrl("");
		    candidate.setDesignation("");
		    candidate.setCurrentRateCurrency("");
		    candidate.setCurrentRate("");
		    candidate.setCurrentRateType("");
		    candidate.setExpectedRateCurrencey("");
		    candidate.setExpectedRate("");
		    candidate.setExperienceYears("");
		    candidate.setExperienceMonths("");
		    candidate.setLocation("");
		    marketPlace.setCandidateId(candidate);
		    List<CandidateMarketPlaceDTO> dtoList = new ArrayList<>();
			String[] searchCandidateIds =  {""};
			String candidateLocation = ""; 
			String searchValue = "";
			int count = 0;
			List<CandidateMarketPlace> candidateMarketPlaces = new ArrayList<>();
			candidateMarketPlaces.add(marketPlace);
//			when(mockCandidateDao.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations.getId(), candidateLocation, searchValue)).thenReturn(candidateMarketPlaces);
		    when(mockCandidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocations.getId())).thenReturn(count);
//			dtoList = candidateService.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations, candidateLocation, searchValue);
			verify(mockCandidateDao).getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocations.getId());
		    assertEquals(dtoList.size(),1);
	}
	
	@Test
	public void addCandidateToMarketCart() throws Exception{
		
			CandidateMarketCart newMarketCart = new CandidateMarketCart();
			User user = new User();
			String email = "";
			String marketPrice = "123";
			Org org = new Org();
			CandidateMarketPlace marketplace = new  CandidateMarketPlace();
			OrgLocations orgLocations = new OrgLocations();
			CandidateGlobal candidateId = new CandidateGlobal();
			candidateId.setEmail(email);
			orgLocations.setOrg(org);
			marketplace.setCandidateId(candidateId);
			marketplace.setOrgLocationId(orgLocations);
			marketplace.setMarketPrice(marketPrice);
			doNothing().when(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
			candidateService.addCandidateToMarketCart(user, marketplace, orgLocations);
			verify(mockCandidateDao).addCandidateToMarketCart(any());
			
		
	}
	
	@Test
	public void getCandidateListMarketPlaceDetailsByIdAndOrgLocationId() throws Exception{
		
			List<Long> id = new ArrayList<>();
			Long orgLocationId = 123l;
			List<CandidateMarketPlace> marketPlaceCandidateList = new ArrayList<>();
			List<CandidateMarketPlace> marketPlaceCandidateList1 = null;
			when(mockCandidateDao.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId)).thenReturn(marketPlaceCandidateList);
			marketPlaceCandidateList1 = candidateService.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
			assertNotNull(marketPlaceCandidateList1);
		
	}
	
	@Test
	public void getCandidateMarketPlaceDetailsByIdAndOrgLocationId() throws Exception{
		
			Long id = 123l; 
			Long orgLocationId = 1234l;
			CandidateMarketPlace marketPlace = new CandidateMarketPlace();
			CandidateMarketPlace marketPlaceCandidate = null;
			when(mockCandidateDao.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId)).thenReturn(marketPlace);
			marketPlaceCandidate = candidateService.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
			assertNotNull(marketPlaceCandidate);
		
	}
	
	@Test
	public void updateMarketPlacePrice() throws Exception{
		
			Long id = 123l;
			 String priceVal = "123";
			 String priceValType = "";
			CandidateMarketPlace marketPlace = new CandidateMarketPlace();
			CandidateMarketCart newMarketCart = new CandidateMarketCart();
			when(mockCandidateDao.getCandidateMarketPlaceById(id)).thenReturn(marketPlace);
			when(mockCandidateDao.getCandidateFromMarketCartByMarketplaceId(id)).thenReturn(newMarketCart);
			doNothing().when(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
			doNothing().when(mockCandidateDao).updateMarketPlacePrice(marketPlace);
			candidateService.updateMarketPlacePrice(id, priceVal, priceValType);
			verify(mockCandidateDao).updateMarketPlacePrice(marketPlace);
			verify(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
			verify(mockCandidateDao).getCandidateMarketPlaceById(id);
			verify(mockCandidateDao).getCandidateFromMarketCartByMarketplaceId(id);
			
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void getAddedMarketplaceCandidates() throws Exception{
		
			List<Long> candidateIdsVal = new ArrayList<>();
			Long l = 123l;
			List<Long> marketplaceCandidates = new ArrayList<>();
			marketplaceCandidates.add(l);
			Map<String, Object[]> candidatesIds = new HashMap<>();
			when(mockCandidateDao.getAddedMarketplaceCandidates(candidateIdsVal)).thenReturn(marketplaceCandidates);
			candidatesIds = candidateService.getAddedMarketplaceCandidates(candidateIdsVal);
			assertEquals(candidatesIds.get("candidatesInMarketplace"), marketplaceCandidates.toArray());
			
		
	}
	
	@Test
	public void saveCandidateMarketPlaceList() throws NumberFormatException, Exception{
		
			Long dtoCandidateId = 123l;
			String candidateId = "12";
			String orgName = "";
			CandidateMarketPlace marketPlaceCandidate = new CandidateMarketPlace();
			OrgLocations orgLocationId = new OrgLocations();
			Org org = new Org();
			org.setOrgName(orgName);
			orgLocationId.setOrg(org);
			orgLocationId.setShowCompanyNameInMarkeplace(orgName);
			Candidate candidate = new Candidate();
			candidate.setId(dtoCandidateId);
			candidate.setOrgLocationId(orgLocationId);
			List<CandidateMarketPlaceDTO> marketPlaceList = new ArrayList<>(); 
			CandidateMarketPlaceDTO dto = new CandidateMarketPlaceDTO();
			dto.setCandidateId(candidateId);
			marketPlaceList.add(dto);
			User user = new User();
			when(mockCandidateDao.getCandidateMarketPlaceByCandidateId(dtoCandidateId)).thenReturn(marketPlaceCandidate);
			doNothing().when(mockCandidateDao).saveMarketPlace(marketPlaceCandidate);
			when(mockCandidateDao.storeCandidate(candidate)).thenReturn(candidate);
			when(mockCandidateDao.getCandidateById(any())).thenReturn(candidate);
			CandidateIndex candidateIndex = new CandidateIndex();
			when(mockCandidateIndexHelper.getCandidateIndexFromCandidate(candidate)).thenReturn(candidateIndex);
			doNothing().when(mockSourceleadELKBaseIndexer).saveOrUpdateElkDocument(candidateIndex, Constants.ELK_CANDIDATE_INDEX, Constants.ELK_CANDIDATE_TYPE,candidate.getId().toString());
			candidateService.saveCandidateMarketPlaceList(marketPlaceList,user);
			verify(mockCandidateDao).getCandidateMarketPlaceByCandidateId(any());
			verify(mockCandidateDao).saveMarketPlace(any());
			verify(mockCandidateDao).storeCandidate(candidate);
			verify(mockCandidateDao).getCandidateById(any());
		
	}
	
	@Test
	public void getCandidateEmailsByOrgLocationId() throws Exception{
		Long orgLocationId = 123l;
		List<String> emailsList = new ArrayList<>();
		List<String> emailsList1 = null;
		when(mockCandidateDao.getCandidateEmailsByOrgLocationId(orgLocationId)).thenReturn(emailsList);
		emailsList1 = candidateService.getCandidateEmailsByOrgLocationId(orgLocationId);
		assertNotNull(emailsList1);
		
	}
	
	@Test
	public void getCandidateIdByEmailAndOrgLocationId() throws Exception{
		String email = "";
		Long orgLocationId = 123l;
		Long candidateId = 1234l;
		Long candidateId1 = 12l;
		when(mockCandidateDao.getCandidateIdByEmailAndOrgLocationId(email, orgLocationId)).thenReturn(candidateId);
		candidateId1 = candidateService.getCandidateIdByEmailAndOrgLocationId(email, orgLocationId);
		assertEquals(candidateId, candidateId1);
		
	}
	
	@Test
	public void saveBulkCandidates() throws Exception{
		List<Candidate> candidatesList = new ArrayList<>();
		Long orgLocationId = 123l;
		doNothing().when(mockCandidateDao).saveBulkCandidates(candidatesList, orgLocationId);
		candidateService.saveBulkCandidates(candidatesList, orgLocationId);
		verify(mockCandidateDao).saveBulkCandidates(candidatesList, orgLocationId);
	}
	
	@Test
	public void getGlobalMarketCandidateList() throws Exception{
		CandidateMarketPlaceDTO candidateMarketPlaceDTO = new  CandidateMarketPlaceDTO();
		String searchValue = "";
		ResponseList<CandidateMarketPlaceDTO> responseList = new ResponseList<>();
		ResponseList<CandidateMarketPlaceDTO> responseList1 = null;
		when(mockCandidateDao.getGlobalMarketCandidateList(candidateMarketPlaceDTO, searchValue)).thenReturn(responseList);
		responseList1 = candidateService.getGlobalMarketCandidateList(candidateMarketPlaceDTO, searchValue);
		assertNotNull(responseList1);
		
	}
}