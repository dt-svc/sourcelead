package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.InvoiceTaxDao;
import com.bis.sourcelead.entity.InvoiceTax;
import com.bis.sourcelead.service.InvoiceTaxService;

@Service
public class InvoiceTaxServiceImpl implements InvoiceTaxService{
	
	@Autowired
	InvoiceTaxDao invoiceTaxDao;

	@Override
	public List<InvoiceTax> getInvoiceTaxListByInvoiceId(Long invoiceId) throws Exception {
		return invoiceTaxDao.getInvoiceTaxListByInvoiceId(invoiceId);
	}

}
