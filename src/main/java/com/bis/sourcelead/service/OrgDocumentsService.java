package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OrgDocuments;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public interface OrgDocumentsService {

	void saveDocument();

	List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId);


}
