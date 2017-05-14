package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.InvoiceLineItemDao;
import com.bis.sourcelead.entity.InvoiceLineItems;
import com.bis.sourcelead.service.InvoiceLineItemService;

@Service
public class InvoiceLineItemServiceImpl implements InvoiceLineItemService{
	
	@Autowired
	InvoiceLineItemDao InvoiceLineItemDao;

	@Override
	public List<InvoiceLineItems> getInvoiceLineItemsByInvoiceId(Long invoiceId) throws Exception {
		return InvoiceLineItemDao.getInvoiceLineItemsByInvoiceId(invoiceId);
	}

	@Override
	public List<InvoiceLineItems> invoiceLineItemsByInvoiceIdAndOrglocationId(Long invoiceId, Long orgLocationId) throws Exception {
		return InvoiceLineItemDao.getInvoiceLineItemsByInvoiceIdAndOrglocationId(invoiceId,orgLocationId);
	}
	
	@Override
	public InvoiceLineItems getInvoiceLineItemsById(Long Id) throws Exception {
		return InvoiceLineItemDao.getInvoiceLineItemsById(Id);
	}

	@Override
	public InvoiceLineItems saveInvoiceLineItems(InvoiceLineItems invoiceLineItems) throws Exception {
		return InvoiceLineItemDao.saveInvoiceLineItems(invoiceLineItems);
	}

	@Override
	public void deleteLineItem(InvoiceLineItems invoiceLineItems) throws Exception {
		InvoiceLineItemDao.deleteLineItem(invoiceLineItems);
	}

	@Override
	public List<Long> getInvoiceLineItemsIdsByInvoiceId(Long invoiceId) throws Exception {
		return InvoiceLineItemDao.getInvoiceLineItemsIdsByInvoiceId(invoiceId);
	}

}
