package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public interface OrgDocumentsDao{

	/**
	 *  to save the documents of the client and vendors
	 * @author Prasanna Vadla
	 * @param orgDocuments 
	 * @throws SourceLeadConstraintViolationException 
	 *
	 */
	void saveDocuments(OrgDocuments orgDocuments) throws Exception;

	/** to get OrgDocumnets list
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param orgLocationId
	 * @return
	 */
	List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId) throws Exception;
	
	/**to get org document by Id
	 * @author Prasad Salina
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	OrgDocuments getOrgDocumentsById(Long Id) throws Exception;
	
	 /**To update Org Document Using OrgDocuments pojo only
     * @author Prasad Salina
     * @param orgDocument
     * @throws Exception
     */
	OrgDocuments updateDocumnet(OrgDocuments orgDocument) throws Exception;

	List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long responderLocationId,
			Long orgLocationId, Long spocUserId);

}
