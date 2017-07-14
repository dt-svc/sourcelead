package com.bis.sourcelead.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Contracts;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.InvoiceServiceImpl;

/**
 * This is a JUnit Test class for testing InvoceService methods
 * 
 * @author: Rajashekar Chelpuri
 * @date: 27-Apr-2017
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class InvoiceServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceTest.class);
	
	@Configuration
	static class InvoiceServiceTestContextConfiguration {
		@Bean
		public InvoiceService invoiceService() {
			return new InvoiceServiceImpl();
		}
	}
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	InvoiceDao invoiceDao;

	//@Test
	public void savInvoice() throws Exception{
		
		Invoice invoice = new Invoice();
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setId(5l);
//		invoice.setClientOrgRelationID(orgRelations);
		User user = new User();
		user.setId(12l);
//		invoice.setContactContactUser(user);
		OrgLocations orgLocations = new OrgLocations();
		orgLocations.setId(5l);
		invoice.setOrgLocation(orgLocations);
		invoice.setBillingAddress("Hyderabad");
		invoice.setCreatedBy("sai");
		invoice.setUpdatedBy("sai");
		invoice.setCreatedDate(new Date());
		invoice.setDiscountAmount("5000");
		invoice.setDueAmount("2000");
		invoice.setDueDate(new Date());
		invoice.setInvoiceComment("client invoice");
		invoice.setInvoiceId("12345");
		invoice.setStatus("0");
		invoice.setSubTotal("10000");
		invoice.setTotal("20000");
        invoice.setUpdatedDate(new Date());
        
        Set<InvoiceLineItems> invoiceLineItemsSet = new HashSet<>();
        
        InvoiceLineItems  invoiceLineItems = new InvoiceLineItems();
        
        Candidate candidate = new Candidate();
        candidate.setId(1l);
        invoiceLineItems.setCandidate(candidate);
        invoiceLineItems.setAmount("50000");
        Contracts contract1 = new Contracts();
        contract1.setId(46l);
        invoiceLineItems.setContract(contract1);
        invoiceLineItems.setCreatedBy("Rajashekar");
        invoiceLineItems.setCreatedDate(new Date());
        invoiceLineItems.setEndDate(new Date());
        invoiceLineItems.setInvoice(invoice);
        invoiceLineItems.setRate("50000");
        invoiceLineItems.setSeperateInvoice("Y");
        invoiceLineItems.setOrgLocation(orgLocations);
        invoiceLineItems.setStartDate(new Date());
        invoiceLineItems.setEndDate(new Date());
        
        invoiceLineItemsSet.add(invoiceLineItems);
        invoice.setInvoiceLineItems(invoiceLineItemsSet);
        logger.info("invoice saved successfully");
	}
	/**
	 * @author Rajashekar 
	 * changing invoice payment status when due date is over
	 * @throws Exception
	 */
	@Test
	public void checkDeuDate() throws Exception{
		List<Invoice> invoiceList = invoiceService.getDueDateCompletedInvoicesList();
		invoiceList.forEach(invoice -> {
			if (invoice.getDueDate() != null && !"".equals(invoice.getDueDate())) {
				if (invoice.getDueDate().after(new Date())) {
					invoice.setPaymentStatus(Constants.OVER_DUE);
					try {
						invoiceDao.saveInvoce(invoice);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		});
	}
	
}
