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
import com.bis.sourcelead.util.CommonUtil;

import org.mockito.Spy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
	
	
		@Autowired
		CandidateService candidateService ;
		private static final Logger logger = LoggerFactory.getLogger(CandidateServiceTest.class);
		
		@Autowired
		CommonUtil commonUtil;
		
		@Autowired
		OrgLocationService orgLocationService;
		
		@Spy
		CandidateService spyCandidateService;
		
		@Spy
		CandidateDao spyCandidateDao;
		
		@Spy
		CandidateEmployersDao spyCandidateEmployersDao;
		
		@Spy
	    CandidateEducationDao spyCandidateEducationDao;
		
		@Spy
	    CandidateCertificationsDao spyCandidateCertificationsDao;
		
		@Spy
		CandidateMarketPlace spyMarketPlace;
		
		@Spy
		CandidateMarketPlacePaymentTransactionHistory spyTransactionHistory;
		
		@Spy
		OrgLocations spyOrgLocations;
		
		@Spy
		MarketplacePurchaseSalesDTO spyMarketplacePurchaseSalesDTO;
		
		@Spy
		List<CandidateEmployers> spyCandidateEmployers;
		
		@Spy
		List<CandidateEducation> spyEducations;
		
		@Spy
		List<CandidateCertifications> spyCertifications;
		
		@Spy
		List<CandidateMarketPlace> spyGlobalCandidatesMarketplaceData;
		
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
		public void getMarketplaceTransactionDetails(){
			try{
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
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getCandidateDetailsByCandidateId(){
			try{
			CandidateMarketPlace cmp = new CandidateMarketPlace();
			CandidateMarketPlace cmpreturn = new CandidateMarketPlace();
			User user = new User();
			Candidate candidate = new Candidate();
			candidate.setId(4l);
			user.setId(11l);
			cmp.setCandidateId(candidate);
			cmp.setMarketPrice("72a");
			cmp.setStatus(Constants.CANDIDATE_ACTIVE);
			candidateService.copyCandidateToMarketplace(String.valueOf(candidate.getId()),user);
			cmpreturn = candidateService.getCandidateDetailsByCandidateId(candidate.getId());
			assertEquals(cmp.getStatus(),cmpreturn.getStatus());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getUserDetailsBySourceOrgLocationId(){
			try{
			OrgLocations sourceOrgLocationId = new OrgLocations();
			OrgSettings orgSettings = new OrgSettings();
			orgSettings.setId(12);
	        orgSettings.setWebsite("www.dum.com");
			orgSettings.setTimeZone("246");
			User user = new User();
			User user1 = new User();
			user.setId(15l);
			sourceOrgLocationId.setId(14l);
			sourceOrgLocationId.setUser(user);
			orgLocationService.saveOrgLocations(orgSettings,sourceOrgLocationId);
			user1 = candidateService.getUserDetailsBySourceOrgLocationId(sourceOrgLocationId.getId());
			assertEquals(user.getId(),user1.getId());
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		@Test
		public void updateMarketplaceCartCountAndPrice(){
			try{
				logger.info("testing updateMarketplaceCartCountAndPrice service");
				User user = new User();
				//OrgLocations orglocations = new OrgLocations();
				user.setId(4l);
				spyOrgLocations.setId(5l);
				Object obj[] = candidateService.updateMarketplaceCartCountAndPrice(spyOrgLocations.getId(), Constants.ROLE_ADMIN, user);
				assertNotNull(obj);
				User user1 = new User();
				OrgLocations orglocations1 = new OrgLocations();
				user1.setId(3l);
				orglocations1.setId(7l);
				Object obj1[] = candidateService.updateMarketplaceCartCountAndPrice(orglocations1.getId(), Constants.ROLE_ADMIN, user1);
				assertNotNull(obj1);
				assertEquals(obj[0].toString(), obj1[0].toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void updateMarketCartStatus(){
			try{
				logger.info("testing updateMarketCartStatus service");
				List<Long> marketCartIds = new ArrayList<Long>();
				marketCartIds.add(3l);
				marketCartIds.add(5l);
				//OrgLocations orglocations = new OrgLocations();
				spyOrgLocations.setId(5l);
				Boolean flag = candidateService.updateMarketCartStatus(marketCartIds, spyOrgLocations.getId());
				assertTrue(flag);
				marketCartIds.add(8l);
				marketCartIds.add(10l);
				OrgLocations orglocations1 = new OrgLocations();
				orglocations1.setId(6l);
				Boolean flag1 = candidateService.updateMarketCartStatus(marketCartIds, orglocations1.getId());
				assertTrue(flag1);
				assertEquals(flag, flag1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getCandidateMarketPlaceDetailsByUUId(){
			try{
				logger.info("testing getCandidateMarketPlaceDetailsByUUId service");
				CandidateMarketPlace market = new CandidateMarketPlace();
				market.setMarketplaceUUID("zRA7Z6Rw");
				CandidateMarketPlace market1 = candidateService.getCandidateMarketPlaceDetailsByUUId(market.getMarketplaceUUID());
				assertNotNull(market1);
				assertEquals(market.getMarketplaceUUID(), market1.getMarketplaceUUID());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getMarketplaceSalesOrderBySalesUuid(){
			try{
				logger.info("testing getMarketplaceSalesOrderBySalesUuid service");
				MarketplaceSalesOrder salesOrder = new MarketplaceSalesOrder();
				salesOrder.setSalesUuid("bwjswsPO");
				//OrgLocations orglocations = new OrgLocations();
				spyOrgLocations.setId(5l);
				MarketplacePurchaseSalesDTO salesDto = candidateService.getMarketplaceSalesOrderBySalesUuid(spyOrgLocations, salesOrder.getSalesUuid());
				assertNotNull(salesDto);
				assertEquals(salesOrder.getSalesUuid(), salesDto.getSalesUuid());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getPurchaseDetailByPurchaseUuid(){
			try{
				logger.info("testing getPurchaseDetailByPurchaseUuid service");
				MarketplacePurchaseOrder purchaseOrder = new MarketplacePurchaseOrder();
				purchaseOrder.setPurchaseUuid("PG1doDtM");
				//OrgLocations orglocations = new OrgLocations();
				spyOrgLocations.setId(5l);
				MarketplacePurchaseSalesDTO purchaseDto = candidateService.getPurchaseDetailByPurchaseUuid(purchaseOrder.getPurchaseUuid(), spyOrgLocations);
				assertNotNull(purchaseDto);
				assertEquals(purchaseOrder.getPurchaseUuid(), purchaseDto.getPurchaseUuid());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getBalanceAmountFromWallet(){
			try{
				logger.info("testing getBalanceAmountFromWallet service");
				//OrgLocations orglocations = new OrgLocations();
				spyOrgLocations.setId(5l);
				Double walletAmount = candidateService.getBalanceAmountFromWallet(spyOrgLocations.getId());
				assertNotNull(walletAmount);
				OrgLocations orglocations1 = new OrgLocations();
				orglocations1.setId(5l);
				Double walletAmount1 = candidateService.getBalanceAmountFromWallet(orglocations1.getId());
				assertNotNull(walletAmount1);
				assertEquals(walletAmount, walletAmount1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void deleteCandidateMarketCartByMarketUUID(){
			try{
				String candidatgeMarketUUID = "abc";
				doNothing().when(spyCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
				verify(spyCandidateDao).deleteCandidateMarketCartByMarketUUID(candidatgeMarketUUID);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		@Test
		public void saveToMarketplacePurchaseOrder(){
			try{
				MarketplacePurchaseOrder newMarketplacePurchaseorder = new MarketplacePurchaseOrder();
				doNothing().when(spyCandidateDao).saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
				verify(spyCandidateDao).saveToMarketplacePurchaseOrder(newMarketplacePurchaseorder);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void saveToMarketplaceSalesOrder(){
			try{
				
				 MarketplaceSalesOrder newMarketplaceSalesorder = new MarketplaceSalesOrder();
				 doNothing().when(spyCandidateDao).saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
				 verify(spyCandidateDao).saveToMarketplaceSalesOrder(newMarketplaceSalesorder);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void deleteAllCandidatesFromMarketcart(){
			try{
				Long orgLocationId = 123l;
				 doNothing().when(spyCandidateDao).deleteAllCandidatesFromMarketcart(orgLocationId);
				 verify(spyCandidateDao).deleteAllCandidatesFromMarketcart(orgLocationId);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getPurchaseCandidateListByOrglocation(){
			try{
				Long id = 1234l;
				MarketplacePurchaseOrder mpo = new MarketplacePurchaseOrder();
				List<MarketplacePurchaseOrder> list = new ArrayList<MarketplacePurchaseOrder>();
				List<MarketplacePurchaseOrder> list1 = new ArrayList<MarketplacePurchaseOrder>();
				list.add(mpo);
				when(spyCandidateDao.getPurchaseCandidateListByOrglocation(id)).thenReturn(list);
				list1 = spyCandidateDao.getPurchaseCandidateListByOrglocation(id);
				assertThat(list1.size(),is(1));
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void saveCandidateMarketPlaceWallet(){
			try{
				CandidateMarketWallet wallet = new CandidateMarketWallet();
				doNothing().when(spyCandidateDao).saveCandidateMarketPlaceWallet(wallet);
				verify(spyCandidateDao).saveCandidateMarketPlaceWallet(wallet);
				}catch(Exception e){
				e.printStackTrace();
			}
		}

		@Test
		public void withdrawCandidateFromMarketplace(){
			try{
				logger.info("testing withdrawCandidateFromMarketplace service");
				CandidateMarketPlace marketplace = new CandidateMarketPlace();
				Candidate candidate = new Candidate();
				candidate.setId(007l);
				marketplace.setCandidateId(candidate);
				candidate = doNothing().when(spyCandidateDao).getCandidateById(candidate.getId());
				assertNotNull(candidate);
				verify(spyCandidateDao).getCandidateById(candidate.getId());
				candidate.setIsSharedInMarketplace(Constants.NO);
				doNothing().when(spyCandidateService).saveOrUpdateCandidateElkDocument(candidate);
				verify(spyCandidateService).saveOrUpdateCandidateElkDocument(candidate);
				doNothing().when(spyCandidateDao).storeCandidate(candidate);
				verify(spyCandidateDao).storeCandidate(candidate);
				doNothing().when(spyCandidateDao).withdrawCandidateFromMarketplace(marketplace);
				verify(spyCandidateDao).withdrawCandidateFromMarketplace(marketplace);
				marketplace.setCandidateUUID("aXbYcZ");
				doNothing().when(spyCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
				verify(spyCandidateDao).deleteCandidateFromMarketcartByCandidateUuid(marketplace.getCandidateUUID());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void updateMarketWalletAmount() {
			try{
				Double balanceAmountValue = 123.4;
				Long id = 123l;
				doNothing().when(spyCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
				verify(spyCandidateDao).updateMarketWalletAmount(balanceAmountValue, id);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getPurchaseCandidateExcelFile(){
			try{
				MarketplacePurchaseOrder mpo1 = new MarketplacePurchaseOrder();
				List<MarketplacePurchaseOrder> purchaseCandidatesList = new ArrayList<MarketplacePurchaseOrder>();
				Long orgLocationId = 123l;
				purchaseCandidatesList.add(mpo1);
				File file = candidateService.getPurchaseCandidateExcelFile(purchaseCandidatesList, orgLocationId);
				System.out.println("marketplace file"+ file.getName());
				assertEquals(file.getName().contains("Purchased Candidates"),true);
				file.delete();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getPurchaseCandidatePdf(){
			try{
				MarketplacePurchaseOrder mpo1 = new MarketplacePurchaseOrder();
				List<MarketplacePurchaseOrder> purchaseCandidatesList = new ArrayList<MarketplacePurchaseOrder>();
				Long orgLocationId = 123l;
				purchaseCandidatesList.add(mpo1);
				File file = candidateService.getPurchaseCandidatePdf(purchaseCandidatesList, orgLocationId);
				System.out.println("marketplace file"+ file.getName());
				assertEquals(file.getName().contains("PurchaseCandidate"),true);
				file.delete();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getWalletList(){
			try{
				MarketplaceWalletDTO marketplaceWalletDTO = new MarketplaceWalletDTO();
				String searchValue = "abc";
				String transactionType = "dummy";
				Map<String, Date> dateValue = new HashMap<>();
				ResponseList<MarketplaceWalletDTO> responseList = new ResponseList<>();
				ResponseList<MarketplaceWalletDTO> responseList1 = null;
				when(spyCandidateDao.getWalletList(marketplaceWalletDTO, searchValue,transactionType, dateValue)).thenReturn(responseList);
				responseList1 = spyCandidateDao.getWalletList(marketplaceWalletDTO, searchValue,transactionType, dateValue);
				assertNotNull(responseList1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getMarketplaceSalesOrderList(){
			try{
				//OrgLocations orgLocations = new OrgLocations();
				String searchValue = "dummy";
				String salesOrderFromDate = ""; 
				String salesOrderToDate = "";
				Map<String, Date> dateValue = new HashMap<>();
				ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
				ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
				when(spyCandidateDao.getMarketplaceSalesOrderList(spyMarketplacePurchaseSalesDTO, spyOrgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue)).thenReturn(responseList);
				responseList1 = spyCandidateDao.getMarketplaceSalesOrderList(spyMarketplacePurchaseSalesDTO, spyOrgLocations, searchValue, salesOrderFromDate, salesOrderToDate, dateValue);
				assertNotNull(responseList1);
						
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getMarketplacePurchaseOrder(){
			try{
				OrgLocations orgLocations = new OrgLocations();
				orgLocations.setId(123l);
				ResponseList<MarketplacePurchaseSalesDTO> responseList = new ResponseList<>();
				ResponseList<MarketplacePurchaseSalesDTO> responseList1 = null;
				when(spyCandidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), spyMarketplacePurchaseSalesDTO)).thenReturn(responseList);
				responseList1 = spyCandidateDao.getMarketplacePurchaseOrder(orgLocations.getId(), spyMarketplacePurchaseSalesDTO);
				assertNotNull(responseList1);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getOrderNo(){
			try{
				Long id = 123l;
				CandidateMarketPlacePaymentTransactionHistory transactionHistory1 = null;
				when(spyCandidateDao.getOrderNo(id)).thenReturn(spyTransactionHistory);
				transactionHistory1 = spyCandidateDao.getOrderNo(id);
				assertNotNull(transactionHistory1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getCandidatesListByIds(){
			try{
				List<Long> Ids = new ArrayList<Long>();
				List<CandidateDTO> candidateDTOList = new ArrayList<CandidateDTO>();
				List<CandidateDTO> candidateDTOList1 = null;
				when(spyCandidateDao.getCandidatesListByIds(Ids)).thenReturn(candidateDTOList);
				candidateDTOList1 = spyCandidateDao.getCandidatesListByIds(Ids);
				assertNotNull(candidateDTOList1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getMarketCartIdsFromMarketcart(){
			try{
				Long orgLocationId = 123l;
				List<CandidateMarketCart> candidateMarketCartList = new ArrayList<>();
				List<CandidateMarketCart> candidateMarketCartList1 = null;
				when(spyCandidateDao.getMarketCartIdsFromMarketcart(orgLocationId)).thenReturn(candidateMarketCartList);
				candidateMarketCartList1 = spyCandidateDao.getMarketCartIdsFromMarketcart(orgLocationId);
				assertNotNull(candidateMarketCartList1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getCandidateMarketPlaceDTODetailsByUUId(){
			try{
				String marketplaceUUID = "123A";
				//OrgLocations orgLocations = new OrgLocations();
				CandidateMarketPlace marketPlace1 = null;
				List<CandidateEmployers> candidateEmployers1 = null;
				List<CandidateEducation> educations1 = null;
				List<CandidateCertifications> certifications1 = null;
				//List<CandidateMarketPlace> globalCandidatesMarketplaceData = new ArrayList<>();
				List<CandidateMarketPlace> globalCandidatesMarketplaceData1 = null;
				when(spyCandidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID)).thenReturn(spyMarketPlace);
				marketPlace1 = spyCandidateDao.getCandidateMarketPlaceDetailsByUUId(marketplaceUUID);
				assertNotNull(marketPlace1);
				when(spyCandidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId())).thenReturn(spyCandidateEmployers);
				candidateEmployers1 = spyCandidateEmployersDao.getCandidatesEmployersListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId());
				assertNotNull(candidateEmployers1);
				when(spyCandidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId())).thenReturn(spyEducations);
				educations1 = spyCandidateEducationDao.getCandidateEducationListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId());
				assertNotNull(educations1);
			    when(spyCandidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId())).thenReturn(spyCertifications);
			    certifications1 = spyCandidateCertificationsDao.getCandidateCertificationListByCandidateIdAndOrgLocationID(spyMarketPlace.getCandidateId().getId(), spyMarketPlace.getOrgLocationId().getId());
			    assertNotNull(certifications1);
			    when(spyCandidateDao.getGlobalMarketplaceDataByOrgId(spyOrgLocations.getId(), spyMarketPlace.getId())).thenReturn(spyGlobalCandidatesMarketplaceData);
			    globalCandidatesMarketplaceData1 = spyCandidateDao.getGlobalMarketplaceDataByOrgId(spyOrgLocations.getId(), spyMarketPlace.getId());
			    assertNotNull(globalCandidatesMarketplaceData1);
			    when(spyCandidateDao.getMarketCartStatusByMarketIdOrgId(spyMarketPlace.getId(), spyMarketPlace.getOrgLocationId().getId())).thenReturn(1);
			    verify(spyCandidateDao).getMarketCartStatusByMarketIdOrgId(spyMarketPlace.getId(), spyMarketPlace.getOrgLocationId().getId());
			    
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getGlobalDataRecommendations(){
			try{
				CandidateMarketPlaceDTO candidateMarketPlaceDTO = new CandidateMarketPlaceDTO();
				//OrgLocations orgLocations = new OrgLocations();
				String searchValue = "dummy";
				ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList = new ResponseList<>();
				ResponseList<CandidateMarketPlaceDTO> candidateMarketPlaceDTOList1 = null;
				when(spyCandidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, spyOrgLocations, searchValue)).thenReturn(candidateMarketPlaceDTOList);
				candidateMarketPlaceDTOList1 = spyCandidateDao.getGlobalDataRecommendations(candidateMarketPlaceDTO, spyOrgLocations, searchValue);
				assertNotNull(candidateMarketPlaceDTOList1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void updateMarketplaceCandidates(){
			try{
				//OrgLocations orgLocations = new OrgLocations();
				String companyNameFlag = "abc";
				int x = 0;
				when(spyCandidateDao.updateMarketplaceCandidates(spyOrgLocations, companyNameFlag)).thenReturn(1);
				x = spyCandidateDao.updateMarketplaceCandidates(spyOrgLocations, companyNameFlag);
				assertThat(x, is(1));
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		@Test
		public void getCandidateDetailsFromMarketcart(){
			try{
				User user = new User(); 
				String userRole = ""; 
				String creationStartDate = ""; 
				String creationEndDate = "";
				String searchValue = "";
				Map<String, Date> dateValue = new HashMap<>();
				List<CandidateMarketPlaceCartView> candidateMarketPlaceCartView = new ArrayList<>();
				List<CandidateMarketPlaceCartView> candidateMarketPlaceCartView1 = null;
				when(spyCandidateDao.getCandidateDetailsFromMarketcart(user, userRole, spyOrgLocations, creationStartDate, creationEndDate, dateValue, searchValue)).thenReturn(candidateMarketPlaceCartView);
				candidateMarketPlaceCartView1 = spyCandidateDao.getCandidateDetailsFromMarketcart(user, userRole, spyOrgLocations, creationStartDate, creationEndDate, dateValue, searchValue);
				assertNotNull(candidateMarketPlaceCartView1);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
}


