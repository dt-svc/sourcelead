package com.bis.sourcelead.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.exception.SourceLeadConstraintViolationException;
import com.bis.sourcelead.service.OrgDocumentsService;


/**
 *  to handle the documents of the client and vendors
 *  
 * @author Prasanna Vadla
 *
 */
@Service
public class OrgDocumentsServiceImpl implements OrgDocumentsService{

	@Autowired
	private OrgDocumentsDao orgDocumentsDao;
	
	@Override
	public void saveDocument(JSONObject jsonObj, MultipartFile multiFile, String responderLocationId,OrgLocations orgLocation,User user) throws JSONException, IOException, ParseException, SourceLeadConstraintViolationException {
		
		OrgDocuments orgDocuments = new OrgDocuments();
		
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
		
		if(jsonObj.has("docName")){
			orgDocuments.setDocName(jsonObj.getString("docName"));
		}
		
		if(jsonObj.has("contractType")){
			orgDocuments.setContractType(jsonObj.getString("contractType"));
		}
		
		if(multiFile != null){
			orgDocuments.setDocFile(multiFile.getBytes());
			orgDocuments.setFileName(multiFile.getOriginalFilename());
			orgDocuments.setFileType(multiFile.getContentType());
		 }
		
		if(jsonObj.has("owner")){
			User ownerUser = new User();
			ownerUser.setId(Long.parseLong(jsonObj.getString("owner")));
			orgDocuments.setOwnerUser(ownerUser);
		}
		if(jsonObj.has("spoc")){
			User spocUser = new User();
			spocUser.setId(Long.parseLong(jsonObj.getString("spoc")));
			orgDocuments.setSpocUser(spocUser);
		}
		if(jsonObj.has("startDate")){
			Date startDate = format.parse(jsonObj.getString("startDate"));
			orgDocuments.setStartDate(startDate);
		}
		if(jsonObj.has("endDate")){
			Date endDate = format.parse(jsonObj.getString("endDate"));
			orgDocuments.setEndDate(endDate);
		}
		
		if(jsonObj.has("description")){
			orgDocuments.setDescription(jsonObj.getString("description"));
		}
		
		if(responderLocationId!= null){
			OrgLocations responderLocation = new OrgLocations();
			responderLocation.setId(Long.parseLong(responderLocationId));
			orgDocuments.setResponderLocationId(responderLocation);
		}
		if(orgLocation!= null){
			orgDocuments.setLocationId(orgLocation);
		}
		orgDocuments.setStatus(Constants.MEMBER_ACTIVE);
		orgDocuments.setCreatedBy(user.getUserCode());
		orgDocuments.setCreatedDate(new Date());
		orgDocuments.setUpdatedBy(user.getUserCode());
		orgDocuments.setUpdatedDate(new Date());
		
		orgDocumentsDao.saveDocuments(orgDocuments);
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
