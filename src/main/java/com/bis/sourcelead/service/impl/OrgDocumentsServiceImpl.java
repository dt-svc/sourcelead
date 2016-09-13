package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgService;


/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public class OrgDocumentsServiceImpl implements OrgDocumentsService{

	@Autowired
	private OrgDocumentsDao orgDocumentsDao;
	
	@Override
	public void saveDocument() {
		orgDocumentsDao.saveDocuments();
	}
	
	


}
