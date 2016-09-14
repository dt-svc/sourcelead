package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public interface OrgDocumentsService {

	void saveDocument(JSONObject jsonObj, MultipartFile multiFile, String responderLocationId,String docId, OrgLocations orgLocation, User user) throws Exception;

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

	List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long valueOf, Long id,
			Long spocUserId);


}
