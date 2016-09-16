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

	void saveDocument(JSONObject jsonObj, MultipartFile multiFile, String responderLocationId,String docId, OrgLocations orgLocation, User user, String sourceType) throws Exception;

	/**to get OrgDocumnets list
	 * @author Prasad Salina
	 * @param responderLocationId
	 * @param loginUserId
	 * @param orgLocationId
	 * @param sourceType
	 * @return
	 * @throws Exception
	 */
	List<OrgDocuments> getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long responderLocationId, Long loginUserId, Long orgLocationId, String sourceType) throws Exception;
	
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

	List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long valueOf, Long id,
			Long spocUserId);

	OrgDocuments getOrgDocumentsByIdAndSpocId(Long parseLong, Long userId);


}
