package com.bis.sourcelead.dao;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
@FunctionalInterface
public interface OrgDocumentsDao{

	/**
	 *  to save the documents of the client and vendors
	 * @author Prasanna Vadla
	 * @param orgDocuments 
	 * @throws SourceLeadConstraintViolationException 
	 *
	 */
	void saveDocuments(OrgDocuments orgDocuments) throws SourceLeadConstraintViolationException;


}
