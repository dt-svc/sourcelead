package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
import com.bis.sourcelead.dao.CandidateDao;
import com.bis.sourcelead.dto.MarketplacePurchaseSalesDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateMarketCart;
import com.bis.sourcelead.entity.CandidateMarketPlace;
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
				OrgLocations orglocations = new OrgLocations();
				orglocations.setId(5l);
				MarketplacePurchaseSalesDTO salesDto = candidateService.getMarketplaceSalesOrderBySalesUuid(orglocations, salesOrder.getSalesUuid());
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
				OrgLocations orglocations = new OrgLocations();
				orglocations.setId(5l);
				MarketplacePurchaseSalesDTO purchaseDto = candidateService.getPurchaseDetailByPurchaseUuid(purchaseOrder.getPurchaseUuid(), orglocations);
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
				OrgLocations orglocations = new OrgLocations();
				orglocations.setId(5l);
				Double walletAmount = candidateService.getBalanceAmountFromWallet(orglocations.getId());
				assertNotNull(walletAmount);
				OrgLocations orglocations1 = new OrgLocations();
				orglocations.setId(5l);
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


}


