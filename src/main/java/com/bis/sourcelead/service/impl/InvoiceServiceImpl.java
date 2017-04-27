package com.bis.sourcelead.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.InvoiceDao;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
	
	@Autowired
	private InvoiceDao invoiceDao;

	@Override
	public Invoice saveInvoice(Invoice invoice) throws Exception
	{
		return invoiceDao.saveInvoce(invoice);
	}
	
}
