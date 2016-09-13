package com.bis.sourcelead.service;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import java.util.List;
import com.bis.sourcelead.entity.OrgDocuments;

/**
 *  to handle the documents of the client and vendors
 * @author Prasanna Vadla
 *
 */
public interface OrgDocumentsService {

	void saveDocument(JSONObject jsonObj, MultipartFile multiFile, String responderLocationId, OrgLocations orgLocation, User user) throws JSONException, IOException, ParseException, SourceLeadConstraintViolationException;

	List<OrgDocuments> getClientDocumentsListByResponderLocationIdAndOrgLocationID(Long responderLocationId, Long orgLocationId);


}
