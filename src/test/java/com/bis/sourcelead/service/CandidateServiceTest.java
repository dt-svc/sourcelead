package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

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
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateCertifications;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.CandidateEmployers;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
import com.bis.sourcelead.entity.CandidateMarketPlaceCartView;
import com.bis.sourcelead.entity.CandidateMarketPlacePaymentTransactionHistory;
import com.bis.sourcelead.entity.CandidateMarketWallet;
import com.bis.sourcelead.entity.MarketplacePurchaseOrder;
import com.bis.sourcelead.entity.MarketplaceSalesOrder;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.impl.CandidateServiceImpl;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.CandidateController;

import org.mockito.Spy;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Ajith Matta,Rakshith
 * @date 31/08/2016
 * @date 06/12/2016
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class CandidateServiceTest {
	
	private CandidateServiceImpl service;
	
		@Autowired
		CandidateService candidateService ;
		private static final Logger logger = LoggerFactory.getLogger(CandidateServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Autowired
		OrgLocationService orgLocationService;
		
		@Mock
		CandidateService mockCandidateService;
		
		@Mock
		CandidateDao mockCandidateDao;
		
		@Mock
		CandidateEmployersDao mockCandidateEmployersDao;
		
		@Mock
	    CandidateEducationDao mockCandidateEducationDao;
		
		@Mock
	    CandidateCertificationsDao mockCandidateCertificationsDao;
		
		@Before
		public void setUp() throws Exception {
		initMocks(this);
		service = new CandidateServiceImpl();
		ReflectionTestUtils.setField(service, "candidateDao", mockCandidateDao);
		ReflectionTestUtils.setField(service, "candidateEmployersDao", mockCandidateEmployersDao);
		ReflectionTestUtils.setField(service, "candidateEducationDao", mockCandidateEducationDao);
		ReflectionTestUtils.setField(service, "candidateCertificationsDao", mockCandidateCertificationsDao);
		}
		
		@Test
		public void storeCandidate() throws Exception{
			
			Candidate candidate = new Candidate();
			User user = new User();
			OrgLocations orgLocationId = new OrgLocations();
			orgLocationId.setId(1l);
			user.setId(1l);
			candidate.setId(1l);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("firstName", "sudeep");
			jsonObj.put("lastName", "reddy");
			jsonObj.put("country", "india");
			jsonObj.put("state", "telnagana");
			jsonObj.put("city", "nezamabad");
			jsonObj.put("phone", "24578965");
			jsonObj.put("email", "sudeep@gmail.com");
			jsonObj.put("currentRate", "12");
			jsonObj.put("currentRateCurrency", "INR");
			jsonObj.put("designation", "develpoer");
			jsonObj.put("expectedRate", "123");
			jsonObj.put("expectedRateCurrencey", "INR");
			jsonObj.put("summary", "hello");
			jsonObj.put("designationType", "exp");
			jsonObj.put("experienceYears", "3");
			jsonObj.put("experienceMonths", "4");
			jsonObj.put("address", "wgl");
			jsonObj.put("status","1");
			jsonObj.put("createdBy", "srinivas");
			jsonObj.put("updatedBy", "srinivas");
			jsonObj.put("createdDate", "commonUtil.currentDate()");
						
			MultipartFile multipartFile = getMultiPartFile();
						
//			candidate = candidateService.storeCandidate(jsonObj,orgLocationId,user,multipartFile);
//			logger.info("success fully store " +candidate.getCity());
			
		}
		private MultipartFile getMultiPartFile() throws IOException {
			File file = new File("src/test/resources/input.txt");
		    FileInputStream input = new FileInputStream(file);
		    MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			return multipartFile;
		}
		
		@Test
		public void addCandidate() throws Exception{
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(2l);
			candidate.setId(1l);
			candidate.setFirstName("david");
			candidate.setLastName("raju");
			candidate.setEmail("raju@gmail.com");
			candidate.setFileContentType("input.txt");
			candidate.setOrgLocationId(orglocations);
			candidate.setMobilePhone("547896654");
			candidate.setCountry("usa");
			candidate.setState("telangana");
			candidate.setCity("waragal");
			candidate = candidateService.addCandidate(candidate);
			logger.info("addCandidate successfully " +candidate.getLastName());
		}
		
		@Test
		public void getCandidateByCandidateId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateId(1l);
			logger.info("getCandidateByCandidateId " + candidate.getFirstName());
		}
		
		@Test
		public void getCandidatesListByOrgLocationID() throws Exception{
			
			List<Candidate> candidateList = new ArrayList<>();
			candidateList = candidateService.getCandidatesListByOrgLocationID(1l,1l,Constants.CANDIDATE_ACTIVE,"","All","Y","","","",null,null);
			logger.info("getCandidatesListByOrgLocationID " + candidateList.size());

			
		}
		
		@Test
		public void getCandidateByCandidateIdAndOrgLocationId() throws Exception{
			Candidate candidate = new Candidate();
			candidate = candidateService.getCandidateByCandidateIdAndOrgLocationId(1l,1l);
			logger.info("getCandidateByCandidateIdAndOrgLocationId " + candidate.getEmail());
			
		}
		@Test
		public void findByCandidateEmail() throws Exception{
		Candidate candidate = new Candidate();
		candidate = candidateService.findByCandidateEmail("ajith@gmail.com",1l);
		logger.info("findByCandidateEmail "+ candidate.getId());
				}
		@Test
		public void saveOrUpdateCandidate() throws Exception{
			
			Candidate candidate = new Candidate();
			OrgLocations orglocations = new OrgLocations();
			orglocations.setId(1l);
			candidate.setId(1l);
			candidate.setFirstName("swathi");
			candidate.setLastName("reddy");
			candidate.setEmail("swathi@gmail.com");
			candidate.setFileContentType("input.txt");
			candidate.setOrgLocationId(orglocations);
			candidate.setMobilePhone("547896654");
			candidate.setCountry("usa");
			candidate.setState("telangana");
			candidate.setCity("waragal");
			candidate.setStatus(Constants.CANDIDATE_ACTIVE);
			candidate.setCreatedDate(commonUtil.currentDate());
			candidate.setUpdatedDate(commonUtil.currentDate());
			candidate = candidateService.addCandidate(candidate);
			logger.info("saveOrUpdateCandidate successfully " +candidate.getLastName());
			
		}
		
		@Test
		public void getCandidatesByOrgLocationID() throws Exception {
			
			List<Candidate> candidatesList = candidateService.getCandidatesByOrgLocationID(1l,1l,"Candidate");
			for(Candidate candidate : candidatesList){
				candidate.getId();
				System.out.println("rakshith"+candidate.getId());
			}
			logger.info("saveOrUpdateCandidate successfully " +candidatesList.size());

		}
		
		@Test
		public void getAllCandidatesByLocation() throws Exception{
			
			Candidate can = new Candidate();
			can.setId(1l);
			Long orgLocationId=1l;
			List<Candidate> candidatesList = candidateService.getAllCandidatesByLocation(orgLocationId);
			for(Candidate candidate : candidatesList){
				candidate.getId();
				System.out.println("rakshith"+candidate.getId());
			}
			
		}
		
		@Test
		public void getCandidateExcelFile() throws Exception{
			
			List<Candidate> candidatesList = new ArrayList<>();
			OrgLocations orgLocationId=new OrgLocations();
			Candidate can= new Candidate();
			can.setId(1l);
			can.setFirstName("rak");
			can.setFileName("rak");
			
			orgLocationId.setId(1l);
			can.setOrgLocationId(orgLocationId);
			
			Candidate can1= new Candidate();
			can1.setId(1l);
			can1.setFirstName("rakshith");
			can1.setFileName("rakshith");
			
			candidatesList.add(can);
			candidatesList.add(can1);
			
			String orgName="bis";
			File file = candidateService.getCandidateExcelFile(candidatesList ,orgName,"Y");
			System.out.println("rakshith"+file.getName());
		}
		
		@Test
		public void getCandidatePdf() throws Exception{
			List<Candidate> candidatesList = new ArrayList<>();
			OrgLocations orgLocationId=new OrgLocations();
			orgLocationId.setId(1l);
			Candidate can= new Candidate();
			can.setId(1l);
			can.setFileName("pdf");
			can.setOrgLocationId(orgLocationId);
			
			Candidate can1= new Candidate();
			can1.setId(1l);
			can1.setFileName("pdf2");
			
			candidatesList.add(can);
			candidatesList.add(can1);
			
			String orgName="bis";
			File file = candidateService.getCandidatePdf(candidatesList, orgName,"Y");
			System.out.println("rakshith"+file.getName());
			

		}
		
		@Test
		public void getMarketplaceTransactionDetails() throws Exception{
			
			CandidateMarketPlacePaymentTransactionHistory cmpth = new CandidateMarketPlacePaymentTransactionHistory();
			CandidateMarketPlacePaymentTransactionHistory cmpthreturn = new CandidateMarketPlacePaymentTransactionHistory();
			OrgLocations orgLocationId=new OrgLocations();
			orgLocationId.setId(12l);
			long d = System.currentTimeMillis();
			Date date = new Date(d);
			cmpth.setOrgLocationId(orgLocationId);
			cmpth.setTransactionId("23A13");
			cmpth.setTransactionDate(date);
			candidateService.saveCandidateMarketplacePaymentTranscationHistory(cmpth);
			cmpthreturn = candidateService.getMarketplaceTransactionDetails(orgLocationId.getId());
			assertEquals(cmpth.getOrgLocationId().getId(),cmpthreturn.getOrgLocationId().getId());
			
		}
		
		@Test
		public void getCandidateDetailsByCandidateId() throws Exception{
			
			Long candidateId = 123l;
			CandidateMarketPlace candidateDetails = new CandidateMarketPlace();
			CandidateMarketPlace candidateDetails1 = null;
			when(mockCandidateDao.getCandidateDetailsByCandidateId(candidateId)).thenReturn(candidateDetails);
			candidateDetails1 = mockCandidateDao.getCandidateDetailsByCandidateId(candidateId);
			assertNotNull(candidateDetails1);
			
		}
		
		@Test
		public void getUserDetailsBySourceOrgLocationId() throws Exception{
			
			Long sourceOrgLocationId = 123l;
			User user = new User();
			User user1 = null;
			when(mockCandidateDao.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId)).thenReturn(user);
			user1 = mockCandidateDao.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId);
			assertNotNull(user1);
		}

		@Test
		public void updateMarketplaceCartCountAndPrice() throws Exception{
			
				logger.info("testing updateMarketplaceCartCountAndPrice service");
				User user = new User();
				OrgLocations orglocations = new OrgLocations();
				user.setId(4l);
				orglocations.setId(5l);
				Object obj[] = candidateService.updateMarketplaceCartCountAndPrice(orglocations.getId(), Constants.ROLE_ADMIN, user);
				assertNotNull(obj);
				User user1 = new User();
				OrgLocations orglocations1 = new OrgLocations();
				user1.setId(3l);
				orglocations1.setId(7l);
				Object obj1[] = candidateService.updateMarketplaceCartCountAndPrice(orglocations1.getId(), Constants.ROLE_ADMIN, user1);
				assertNotNull(obj1);
				assertEquals(obj[0].toString(), obj1[0].toString());
			
		}
		
		@Test
		public void updateMarketCartStatus() throws Exception{
			
				logger.info("testing updateMarketCartStatus service");
				List<Long> marketCartIds = new ArrayList<Long>();
				marketCartIds.add(3l);
				marketCartIds.add(5l);
				OrgLocations orglocations = new OrgLocations();
				orglocations.setId(5l);
				Boolean flag = candidateService.updateMarketCartStatus(marketCartIds, orglocations.getId());
				assertTrue(flag);
				marketCartIds.add(8l);
				marketCartIds.add(10l);
				OrgLocations orglocations1 = new OrgLocations();
				orglocations1.setId(6l);
				Boolean flag1 = candidateService.updateMarketCartStatus(marketCartIds, orglocations1.getId());
				assertTrue(flag1);
				assertEquals(flag, flag1);
			
		}
		
		@Test
		public void getCandidateMarketPlaceDetailsByUUId() throws Exception{
			
			String uuid = "";
			CandidateMarketPlace marketPlace = new CandidateMarketPlace();
			CandidateMarketPlace marketPlace1 = null;
			when(mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(uuid)).thenReturn(marketPlace);
			marketPlace1 = mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(uuid);
			assertNotNull(marketPlace1);
		}
		
		@Test
		public void getMarketplaceSalesOrderBySalesUuid() throws Exception{
		
			OrgLocations orgLocations = new OrgLocations(); 
			String salesUuid = "";
			MarketplaceSalesOrder salesOrder = new MarketplaceSalesOrder();
			MarketplaceSalesOrder salesOrder1 = null;
			when(mockCandidateDao.getMarketplaceSalesOrderBySalesUuid(orgLocations.getId(), salesUuid)).thenReturn(salesOrder);
			salesOrder1 = mockCandidateDao.getMarketplaceSalesOrderBySalesUuid(orgLocations.getId(), salesUuid);
			assertNotNull(salesOrder1);
		}
		
		@Test
		public void getPurchaseDetailByPurchaseUuid() throws Exception{
			
			String purchaseUuid = ""; 
			OrgLocations orgLocations = new OrgLocations();
			MarketplacePurchaseOrder mps = new MarketplacePurchaseOrder();
			MarketplacePurchaseOrder mps1 = null;
			when(mockCandidateDao.getPurchaseDetailByPurchaseUuid(purchaseUuid,orgLocations.getId())).thenReturn(mps);
			mps1 = mockCandidateDao.getPurchaseDetailByPurchaseUuid(purchaseUuid,orgLocations.getId());
			assertNotNull(mps1);
		}
		
		@Test
		public void getBalanceAmountFromWallet() throws Exception{
			
			Long id = 123l;
			Double totalAmount = 20.0;
			Double totalAmount1 = 0.0;
			when(mockCandidateDao.getBalanceAmountFromWallet(id)).thenReturn(totalAmount);
			totalAmount1 = mockCandidateDao.getBalanceAmountFromWallet(id);
			assertThat(totalAmount1, is(20.0));
			
		}
		
		@Test
		public void getWalletList() throws Exception{
			
				MarketplaceWalletDTO marketplaceWalletDTO = new MarketplaceWalletDTO();
				String searchValue = "abc";
				String transactionType = "dummy";
				Map<String, Date> dateValue = new HashMap<>();
				ResponseList<MarketplaceWalletDTO> responseList = new ResponseList<>();
				ResponseList<MarketplaceWalletDTO> responseList1 = null;
				when(mockCandidateDao.getWalletList(marketplaceWalletDTO, searchValue,transactionType, dateValue)).thenReturn(responseList);
				responseList1 = mockCandidateDao.getWalletList(marketplaceWalletDTO, searchValue,transactionType, dateValue);
				assertNotNull(responseList1);
			
		}
		
		@Test
		public void deleteCandidateMarketCartByMarketUUID() throws Exception{
			
				String candidatgeMarketUUID = "abc";
			    doNothing().when(mockCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
			    mockCandidateDao.deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
				verify(mockCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
				
		}
		
		@Test
		public void saveToMarketplacePurchaseOrder() throws Exception{
			
				MarketplacePurchaseOrder newMarketplacePurchaseorder = new MarketplacePurchaseOrder();
				doNothing().when(mockCandidateDao).saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
				mockCandidateDao.saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
				verify(mockCandidateDao).saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
			
		}
		
		@Test
		public void saveToMarketplaceSalesOrder() throws Exception{
			
				
				 MarketplaceSalesOrder newMarketplaceSalesorder = new MarketplaceSalesOrder();
				 doNothing().when(mockCandidateDao).saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
				 mockCandidateDao.saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
				 verify(mockCandidateDao).saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
				
		}
		
		@Test
		public void deleteAllCandidatesFromMarketcart() throws Exception{
			
				Long orgLocationId = 123l;
				 doNothing().when(mockCandidateDao).deleteAllCandidatesFromMarketcart(orgLocationId);
				 mockCandidateDao.deleteAllCandidatesFromMarketcart(orgLocationId);
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
				list1 = mockCandidateDao.getPurchaseCandidateListByOrglocation(id);
				assertThat(list1.size(),is(1));
			
		}
		
		@Test
		public void saveCandidateMarketPlaceWallet() throws Exception{
			
				CandidateMarketWallet wallet = new CandidateMarketWallet();
				doNothing().when(mockCandidateDao).saveCandidateMarketPlaceWallet(wallet);
				mockCandidateDao.saveCandidateMarketPlaceWallet(wallet);
				verify(mockCandidateDao).saveCandidateMarketPlaceWallet(wallet);
				
		}

		@Test
		public void withdrawCandidateFromMarketplace() throws Exception{
			
			Long candidateId = 123l;
			Candidate candidate = new Candidate();
			Candidate candidate1 = null;
			CandidateMarketPlace marketplace = new CandidateMarketPlace();
			when(mockCandidateDao.getCandidateById(candidateId)).thenReturn(candidate);
			candidate1 = mockCandidateDao.getCandidateById(candidateId);
			assertNotNull(candidate1);
			doNothing().when(mockCandidateDao).withdrawCandidateFromMarketplace(marketplace);
			mockCandidateDao.withdrawCandidateFromMarketplace(marketplace);
			verify(mockCandidateDao).withdrawCandidateFromMarketplace(marketplace);
			doNothing().when(mockCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
			mockCandidateDao.deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
			verify(mockCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
		}
		
		@Test
		public void updateMarketWalletAmount() throws Exception{
			
				Double balanceAmountValue = 123.4;
				Long id = 123l;
				doNothing().when(mockCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
				mockCandidateDao.updateMarketWalletAmount(balanceAmountValue, id);
				verify(mockCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
				
			
		}
		
		@Test
		public void getPurchaseCandidateExcelFile() throws Exception{
			
				MarketplacePurchaseOrder mpo1 = new MarketplacePurchaseOrder();
				List<MarketplacePurchaseOrder> purchaseCandidatesList = new ArrayList<MarketplacePurchaseOrder>();
				Long orgLocationId = 123l;
				purchaseCandidatesList.add(mpo1);
				File file = candidateService.getPurchaseCandidateExcelFile(purchaseCandidatesList, orgLocationId);
				System.out.println("marketplace file"+ file.getName());
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
				System.out.println("marketplace file"+ file.getName());
				assertEquals(file.getName().contains("PurchaseCandidate"),true);
				file.delete();
				
			
		}
		
		
		@Test
		public void getMarketplaceSalesOrderList() throws Exception{
			
				OrgLocations orgLocations = new OrgLocations();
				MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO = new MarketplacePurchaseSalesDTO();
				String searchValue = "dummy";
				String salesOrderFromDate = ""; 
				String salesOrderToDate = "";
				Map<String, Date> dateValue = new HashMap<>();
				ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
				ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
				when(mockCandidateDao.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue)).thenReturn(responseList);
				responseList1 = mockCandidateDao.getMarketplaceSalesOrderList(marketplacePurchaseSalesDTO, orgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue);
				assertNotNull(responseList1);
						
			
		}
		
	@Test
	public void getMarketplacePurchaseOrder() throws Exception {

		OrgLocations orgLocations = new OrgLocations();
		MarketplacePurchaseSalesDTO marketplacePurchaseSalesDTO = new MarketplacePurchaseSalesDTO();
		orgLocations.setId(123l);
		ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
		String purchaseOrderFromDate = "";
		String purchaseOrderToDate = "";
		Map<String, Date> dateValue = new HashMap<>();
		ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
		/*when(mockCandidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), marketplacePurchaseSalesDTO,
				purchaseOrderFromDate, purchaseOrderToDate, dateValue)).thenReturn(responseList);
		responseList1 = mockCandidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), marketplacePurchaseSalesDTO,
				purchaseOrderFromDate, purchaseOrderToDate, dateValue);*/
		assertNotNull(responseList1);

	}
		
		@Test
		public void getOrderNo() throws Exception{
			
				Long id = 123l;
				CandidateMarketPlacePaymentTransactionHistory transactionHistory = new CandidateMarketPlacePaymentTransactionHistory();
				CandidateMarketPlacePaymentTransactionHistory transactionHistory1 = null;
				when(mockCandidateDao.getOrderNo(id)).thenReturn(transactionHistory);
				transactionHistory1 = mockCandidateDao.getOrderNo(id);
				assertNotNull(transactionHistory1);
			
		}
		
		@Test
		public void getCandidatesListByIds() throws Exception{
			
				List<Long> Ids = new ArrayList<Long>();
				List<CandidateDTO> candidateDTOList = new ArrayList<CandidateDTO>();
				List<CandidateDTO> candidateDTOList1 = null;
				when(mockCandidateDao.getCandidatesListByIds(Ids)).thenReturn(candidateDTOList);
				candidateDTOList1 = mockCandidateDao.getCandidatesListByIds(Ids);
				assertNotNull(candidateDTOList1);
				
			
		}
		
		@Test
		public void getMarketCartIdsFromMarketcart() throws Exception{
			
				Long orgLocationId = 123l;
				List<CandidateMarketCart> candidateMarketCartList = new ArrayList<>();
				List<CandidateMarketCart> candidateMarketCartList1 = null;
				when(mockCandidateDao.getMarketCartIdsFromMarketcart(orgLocationId)).thenReturn(candidateMarketCartList);
				candidateMarketCartList1 = mockCandidateDao.getMarketCartIdsFromMarketcart(orgLocationId);
				assertNotNull(candidateMarketCartList1);
				
			
		}
		
		@Test
		public void getCandidateMarketPlaceDTODetailsByUUId() throws Exception{
			
				String marketplaceUUID = "123A";
				OrgLocations orgLocations = new OrgLocations();
				CandidateMarketPlace marketPlace = new CandidateMarketPlace();
				CandidateMarketPlace marketPlace1 = null;
				Long candidateId = 123l;
				Long orgLocationId = 1234l;
				List<CandidateEmployers> candidateEmployers = new ArrayList<>();
				List<CandidateEmployers> spyList = Mockito.spy(candidateEmployers);
				CandidateEmployers dummyEmployer= new CandidateEmployers();
				dummyEmployer.setId(12345L);
				spyList.add(dummyEmployer);
				List<CandidateEmployers> candidateEmployers1 = null;
				List<CandidateEducation> educations = new ArrayList<>();
				List<CandidateEducation> educations1 = null;
				List<CandidateCertifications> certifications = new ArrayList<>();
				List<CandidateCertifications> certifications1 = null;
				List<CandidateMarketPlace> globalCandidatesMarketplaceData = new ArrayList<>();
				List<CandidateMarketPlace> globalCandidatesMarketplaceData1 = null;
				when(mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID)).thenReturn(marketPlace);
				marketPlace1 = mockCandidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID);
				assertNotNull(marketPlace1);
				when(mockCandidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId)).thenReturn(spyList);
				candidateEmployers1 = mockCandidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
				assertThat(candidateEmployers1.get(0).getId(), is(12345L));
				verify(spyList).add(dummyEmployer);
				when(mockCandidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId,orgLocationId)).thenReturn(educations);
				educations1 = mockCandidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
				assertNotNull(educations1);
			    when(mockCandidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId)).thenReturn(certifications);
			    certifications1 = mockCandidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(candidateId, orgLocationId);
			    assertNotNull(certifications1);
			    when(mockCandidateDao.getGlobalMarketplaceDataByOrgId(orgLocations.getId(), marketPlace.getId())).thenReturn(globalCandidatesMarketplaceData);
			    globalCandidatesMarketplaceData1 = mockCandidateDao.getGlobalMarketplaceDataByOrgId(orgLocations.getId(), marketPlace.getId());
			    assertNotNull(globalCandidatesMarketplaceData1);
			    when(mockCandidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocationId)).thenReturn(1);
			    mockCandidateDao.getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocationId);
			    verify(mockCandidateDao).getMarketCartStatusByMarketIdOrgId(marketPlace.getId(), orgLocationId);
			    
			
		}
		
		@Test
		public void getGlobalDataRecommendations() throws Exception{
			
				CandidateMarketPlaceDTO candidateMarketPlaceDTO = new CandidateMarketPlaceDTO();
				OrgLocations orgLocations = new OrgLocations();
				String searchValue = "dummy";
				ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = new ResponseList<>();
				ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList1 = null;
				when(mockCandidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue)).thenReturn(candidateMarketPlaceDTOList);
				candidateMarketPlaceDTOList1 = mockCandidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, orgLocations, searchValue);
				assertNotNull(candidateMarketPlaceDTOList1);
				
			
		}
		
		@Test
		public void updateMarketplaceCandidates() throws Exception{
			
				OrgLocations orgLocations = new OrgLocations();
				String companyNameFlag = "abc";
				int x = 0;
				when(mockCandidateDao.updateMarketplaceCandidates(orgLocations, companyNameFlag)).thenReturn(1);
				x = mockCandidateDao.updateMarketplaceCandidates(orgLocations, companyNameFlag);
				assertThat(x, is(1));
				
			
		}
		
		@Test
		public void getCandidateDetailsFromMarketcart() throws Exception{
			
				User user = new User(); 
				OrgLocations orgLocations = new OrgLocations();
				String userRole = ""; 
				String creationStartDate = ""; 
				String creationEndDate = "";
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
				profilePercentage1 = mockCandidateDao.getCandidateProfilePercentageByCandidateId(id);
				assertThat(profilePercentage1,is(10));
			
		}
		
		@Test
		public void getGlobalMarketplaceCandListGridCard() throws Exception{
			    OrgLocations orgLocations = new OrgLocations();
			    CandidateMarketPlace marketPlace = new CandidateMarketPlace();
				String[] searchCandidateIds =  {""};
				String candidateLocation = ""; 
				String searchValue = "";
				
				List<CandidateMarketPlace> candidateMarketPlaces = new ArrayList<>();
				List<CandidateMarketPlace> candidateMarketPlaces1 = null;
//				when(mockCandidateDao.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations.getId(), candidateLocation, searchValue)).thenReturn(candidateMarketPlaces);
//				candidateMarketPlaces1 = mockCandidateDao.getGlobalMarketplaceCandListGridCard(searchCandidateIds, orgLocations.getId(), candidateLocation, searchValue);
				assertNotNull(candidateMarketPlaces1);
				//candidateDao.getMarketCartStatusByMarketIdOrgId(spyMarketPlace.getId(), spyMarketPlace.getOrgLocationId().getId());
			
		}
		
		@Test
		public void addCandidateToMarketCart() throws Exception{
			
				CandidateMarketCart newMarketCart = new CandidateMarketCart();
				doNothing().when(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
				mockCandidateDao.addCandidateToMarketCart(newMarketCart);
				verify(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
				
			
		}
		
		@Test
		public void getCandidateListMarketPlaceDetailsByIdAndOrgLocationId() throws Exception{
			
				List<Long> id = new ArrayList<>();
				Long orgLocationId = 123l;
				List<CandidateMarketPlace> marketPlaceCandidateList = new ArrayList<>();
				List<CandidateMarketPlace> marketPlaceCandidateList1 = null;
				when(mockCandidateDao.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId)).thenReturn(marketPlaceCandidateList);
				marketPlaceCandidateList1 = mockCandidateDao.getCandidateListMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
				assertNotNull(marketPlaceCandidateList1);
			
		}
		
		@Test
		public void getCandidateMarketPlaceDetailsByIdAndOrgLocationId() throws Exception{
			
				Long id = 123l; 
				Long orgLocationId = 1234l;
				CandidateMarketPlace marketPlace = new CandidateMarketPlace();
				CandidateMarketPlace marketPlaceCandidate = null;
				when(mockCandidateDao.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId)).thenReturn(marketPlace);
				marketPlaceCandidate = mockCandidateDao.getCandidateMarketPlaceDetailsByIdAndOrgLocationId(id, orgLocationId);
				assertNotNull(marketPlaceCandidate);
			
		}
		
		@Test
		public void updateMarketPlacePrice() throws Exception{
			
				Long id = 123l;
				CandidateMarketPlace marketPlace = new CandidateMarketPlace();
				CandidateMarketPlace marketPlaceCandidate = null;
				CandidateMarketCart newMarketCart = new CandidateMarketCart();
				CandidateMarketCart newMarketCart1 = null;
				when(mockCandidateDao.getCandidateMarketPlaceById(id)).thenReturn(marketPlace);
				marketPlaceCandidate = mockCandidateDao.getCandidateMarketPlaceById(id);
				assertNotNull(marketPlaceCandidate);
				when(mockCandidateDao.getCandidateFromMarketCartByMarketplaceId(id)).thenReturn(newMarketCart);
				newMarketCart1 = mockCandidateDao.getCandidateFromMarketCartByMarketplaceId(id);
				assertNotNull(newMarketCart1);
				doNothing().when(mockCandidateDao).updateMarketPlacePrice(marketPlace);
			    mockCandidateDao.updateMarketPlacePrice(marketPlace);
				verify(mockCandidateDao).updateMarketPlacePrice(marketPlace);
				doNothing().when(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
				mockCandidateDao.addCandidateToMarketCart(newMarketCart);
				verify(mockCandidateDao).addCandidateToMarketCart(newMarketCart);
				
			
		}
		
		@Test
		public void getAddedMarketplaceCandidates() throws Exception{
			
				List<Long> candidateIdsVal = new ArrayList<>();
				List<Long> marketplaceCandidates = new ArrayList<>();
				List<Long> marketplaceCandidates1 = null;
				when(mockCandidateDao.getAddedMarketplaceCandidates(candidateIdsVal)).thenReturn(marketplaceCandidates);
				marketplaceCandidates1 = mockCandidateDao.getAddedMarketplaceCandidates(candidateIdsVal);
				assertNotNull(marketplaceCandidates1);
				
			
		}
		
		@Test
		public void saveCandidateMarketPlaceList() throws NumberFormatException, Exception{
			
				Long dtoCandidateId = 123l;
				CandidateMarketPlace marketPlaceCandidate = new CandidateMarketPlace();
				CandidateMarketPlace marketPlace = null;
				when(mockCandidateDao.getCandidateMarketPlaceByCandidateId(dtoCandidateId)).thenReturn(marketPlaceCandidate);
				marketPlace = mockCandidateDao.getCandidateMarketPlaceByCandidateId(dtoCandidateId);
				assertNotNull(marketPlace);
				doNothing().when(mockCandidateDao).saveMarketPlace(marketPlace);
				mockCandidateDao.saveMarketPlace(marketPlace);
				verify(mockCandidateDao).saveMarketPlace(marketPlace);
				//candidateDao.getCandidateById(Long.parseLong(dto.getCandidateId()))
				//candidateDao.storeCandidate(candidate);
				
			
		}
		
		@Test
		public void getCandidateEmailsByOrgLocationId() throws Exception{
			Long orgLocationId = 123l;
			List<String> emailsList = new ArrayList<>();
			List<String> emailsList1 = null;
			when(mockCandidateDao.getCandidateEmailsByOrgLocationId(orgLocationId)).thenReturn(emailsList);
			emailsList1 = mockCandidateDao.getCandidateEmailsByOrgLocationId(orgLocationId);
			assertNotNull(emailsList1);
			
		}
		
		@Test
		public void getCandidateIdByEmailAndOrgLocationId() throws Exception{
			String email = "";
			Long orgLocationId = 123l;
			Long candidateId = 1234l;
			Long candidateId1 = 12l;
			when(mockCandidateDao.getCandidateIdByEmailAndOrgLocationId(email, orgLocationId)).thenReturn(candidateId);
			candidateId1 = mockCandidateDao.getCandidateIdByEmailAndOrgLocationId(email, orgLocationId);
			assertEquals(candidateId, candidateId1);
			
		}
		
		@Test
		public void saveBulkCandidates() throws Exception{
			List<Candidate> candidatesList = new ArrayList<>();
			Long orgLocationId = 123l;
			doNothing().when(mockCandidateDao).saveBulkCandidates(candidatesList, orgLocationId);
			mockCandidateDao.saveBulkCandidates(candidatesList, orgLocationId);
			verify(mockCandidateDao).saveBulkCandidates(candidatesList, orgLocationId);
		}
		
		@Test
		public void getGlobalMarketCandidateList() throws Exception{
			CandidateMarketPlaceDTO candidateMarketPlaceDTO = new  CandidateMarketPlaceDTO();
			String searchValue = "";
			ResponseList<CandidateMarketPlaceDTO> responseList = new ResponseList<>();
			ResponseList<CandidateMarketPlaceDTO> responseList1 = null;
			when(mockCandidateDao.getGlobalMarketCandidateList(candidateMarketPlaceDTO, searchValue)).thenReturn(responseList);
			responseList1 = mockCandidateDao.getGlobalMarketCandidateList(candidateMarketPlaceDTO, searchValue);
			assertNotNull(responseList1);
			
		}
		
}


