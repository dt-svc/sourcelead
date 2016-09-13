package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgService;


/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
@Service
public class OrgDocumentsServiceImpl implements OrgDocumentsService{

	@Autowired
	private OrgDocumentsDao orgDocumentsDao;
	
	@Override
	public void saveDocument() {
		orgDocumentsDao.saveDocuments();
	}

	@Override
	public List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId,
			Long orgLocationId) {
         List<OrgDocuments> documentsList = orgDocumentsDao.getClientDocumentsListByResponderLocationIdAndOrgLocationID(responderLocationId, orgLocationId);
		
		for(OrgDocuments documents :documentsList){
			if(documents.getStartDate() != null){
			documents.setStartDateStr(Constants.DF_DMY.format(documents.getStartDate()));
			}
			if(documents.getEndDate() != null){
			documents.setEndDateStr(Constants.DF_DMY.format(documents.getEndDate()));
			}
		}
		return documentsList;
	}
	
	


}
