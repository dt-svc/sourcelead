package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgDocuments;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public interface OrgDocumentsDao{

	/**
	 *  to save the documents of the client and vendors
	 * @author Prasanna Vadla
	 *
	 */
	void saveDocuments();

	/**
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param orgLocationId
	 * @return
	 */
	List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId);

}
