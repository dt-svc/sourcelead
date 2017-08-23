package com.bis.sourcelead.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgDocumentsDao;
import com.bis.sourcelead.entity.OrgDocuments;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgDocumentsService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.web.LoginSessionController;


/**
 *  to handle the documents of the client and vendors
 *  
 * @author Prasanna Vadla
 *
 */
@Service
public class OrgDocumentsServiceImpl implements OrgDocumentsService{
	
	private static final Logger logger = LoggerFactory.getLogger(OrgDocumentsServiceImpl.class);

	@Autowired
	private OrgDocumentsDao orgDocumentsDao;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Override
	public void saveDocument(JSONObject jsonObj, MultipartFile multiFile, String responderLocationId,String docId, OrgLocations orgLocation,User user, String sourceType) throws  Exception {
		
		OrgDocuments orgDocuments = null;
		
		if(!"null".equalsIgnoreCase(docId) && docId != null){
			orgDocuments = orgDocumentsDao.getOrgDocumentsById(Long.parseLong(docId));
			if(!jsonObj.getString("docFile").equalsIgnoreCase("")){
				if(multiFile != null){
					orgDocuments.setDocFile(multiFile.getBytes());
					orgDocuments.setFileName(multiFile.getOriginalFilename());
					orgDocuments.setFileType(multiFile.getContentType());
				 }
			}
		}else{
			orgDocuments = new OrgDocuments();
			orgDocuments.setStatus(Constants.MEMBER_ACTIVE);
			orgDocuments.setCreatedBy(user.getUserCode());
			orgDocuments.setCreatedDate(new Date());
			if(multiFile != null){
				orgDocuments.setDocFile(multiFile.getBytes());
				orgDocuments.setFileName(multiFile.getOriginalFilename());
				orgDocuments.setFileType(multiFile.getContentType());
			 }
		}
		
		
		//SimpleDateFormat formatter = orgLocation.getLocFullFormDateFormatter();
        
        orgDocuments.setSourceType(sourceType);
		
		if(jsonObj.has("docName")){
			orgDocuments.setDocName(jsonObj.getString("docName"));
		}
		
		if(jsonObj.has("contractType")){
			orgDocuments.setContractType(jsonObj.getString("contractType"));
		}
		
		if(jsonObj.has("owner") && StringUtils.isNotBlank(jsonObj.getString("owner")) && StringUtils.isNotEmpty(jsonObj.getString("owner"))){
			User ownerUser = new User();
			ownerUser.setId(Long.parseLong(jsonObj.getString("owner")));
			orgDocuments.setOwnerUser(ownerUser);
		}else{
			orgDocuments.setOwnerUser(null);
		}
		String userId="userId";
		if(jsonObj.has("userId") && jsonObj.getString(userId)!=null){
			if(jsonObj.has(userId) && StringUtils.isNotBlank(jsonObj.getString(userId)) && StringUtils.isNotEmpty(jsonObj.getString(userId))){
				User spocUser = new User();
				spocUser.setId(Long.parseLong(jsonObj.getString(userId)));
				orgDocuments.setSpocUser(spocUser);
			}else{
				orgDocuments.setSpocUser(null);
			}
		}
		else{
			if(jsonObj.has("spoc") && StringUtils.isNotBlank(jsonObj.getString("spoc")) && StringUtils.isNotEmpty(jsonObj.getString("spoc"))){
				User spocUser = new User();
				spocUser.setId(Long.parseLong(jsonObj.getString("spoc")));
				orgDocuments.setSpocUser(spocUser);
			}else{
				orgDocuments.setSpocUser(null);
			}
		}
		if(jsonObj.has("startDate") && StringUtils.isNotBlank(jsonObj.getString("startDate")) && StringUtils.isNotEmpty(jsonObj.getString("startDate"))){
			Date startDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("startDate"));
			orgDocuments.setStartDate(startDate);
		}
		if(jsonObj.has("endDate") && StringUtils.isNotBlank(jsonObj.getString("endDate")) && StringUtils.isNotEmpty(jsonObj.getString("endDate"))){
			Date endDate = commonUtil.orgLocationDateFormat(orgLocation.getDateFormat(),jsonObj.getString("endDate"));
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
		
		orgDocuments.setUpdatedBy(user.getUserCode());
		orgDocuments.setUpdatedDate(new Date());
		
		orgDocumentsDao.saveDocuments(orgDocuments);
	}

	@Override
	public List<OrgDocuments> getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long responderLocationId,Long loginUserId,
			Long orgLocationId, String sourceType) throws Exception {
		
		 List<Long> managerUserIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		
         List<OrgDocuments> documentsList = orgDocumentsDao.getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(responderLocationId, loginUserId, managerUserIds, orgLocationId,sourceType);
         OrgLocations orglo = orgLocationService.getOrgLocations(orgLocationId);
         //SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
		for(OrgDocuments documents :documentsList){
			if(documents.getStartDate() != null){
			    documents.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getStartDate()));
			}
			if(documents.getEndDate() != null){
			   documents.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getEndDate()));
			}
			if(documents.getSpocUser() != null){
				StringBuilder docSB=new StringBuilder();
				documents.setSpocName(docSB.append(documents.getSpocUser().getFirstName()).append(" ").append(documents.getSpocUser().getLastName()).toString());
			}
			if(documents.getOwnerUser() != null){
				StringBuilder ownerSB=new StringBuilder();
				documents.setOwnerName(ownerSB.append(documents.getOwnerUser().getFirstName()).append(" ").append(documents.getOwnerUser().getLastName()).toString());
			}
		}
		return documentsList;
	}


	@Override
	public List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long responderLocationId,
			Long orgLocationId, Long spocUserId) {
		 List<OrgDocuments> documentsList = orgDocumentsDao.getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(responderLocationId, orgLocationId,spocUserId);
		
		 OrgLocations orglo;
		try {
			orglo = orgLocationService.getOrgLocations(orgLocationId);
			//SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
			
		       
			 for(OrgDocuments documents :documentsList){
					if(documents.getStartDate() != null){
					    documents.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getStartDate()));
					}
					if(documents.getEndDate() != null){
					   documents.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getEndDate()));
					}
					if(documents.getSpocUser() != null){
						StringBuilder spocNameSB=new StringBuilder();
						documents.setSpocName(spocNameSB.append(documents.getSpocUser().getFirstName()).append(" ").append(documents.getSpocUser().getLastName()).toString());
					}
					if(documents.getOwnerUser() != null){
						StringBuilder ownerNameSB=new StringBuilder();
						documents.setOwnerName(ownerNameSB.append(documents.getOwnerUser().getFirstName()).append(" ").append(documents.getOwnerUser().getLastName()).toString());
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return documentsList;
	}
	
	@Override
    public OrgDocuments getOrgDocumentsById(Long Id) throws Exception {
        logger.debug("In OrgDocumentsServiceImpl class getOrgDocumentsById method...");
        OrgDocuments documents = orgDocumentsDao.getOrgDocumentsById(Id);
        OrgLocations orglo = orgLocationService.getOrgLocations(documents.getLocationId().getId());
        if(documents.getStartDate() != null){
            documents.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getStartDate()));
        }
        if(documents.getEndDate() != null){
           documents.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getEndDate()));
        }        
        if(documents.getOwnerUser() != null){
        	documents.setOwnerId(documents.getOwnerUser().getId());
        }
        return documents;
    }

    @Override
    public OrgDocuments updateDocumnet(OrgDocuments orgDocument) throws Exception {
        logger.debug("In OrgDocumentsServiceImpl class updateDocumnet method...");
        orgDocument.setUpdatedDate(commonUtil.currentDate());
        return orgDocumentsDao.updateDocumnet(orgDocument);
    }

	@Override
	public OrgDocuments getOrgDocumentsByIdAndSpocId(Long docId, Long userId,OrgLocations orgLocations) {
		logger.debug("In OrgDocumentsServiceImpl class getOrgDocumentsById method...");
        OrgDocuments documents = orgDocumentsDao.getOrgDocumentsByIdAndSpocId(docId,userId);
        
        //SimpleDateFormat formatter = orgLocations.getLocFullFormDateFormatter();
        if(documents.getStartDate() != null){
            documents.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),documents.getStartDate()));
        }
        if(documents.getEndDate() != null){
           documents.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),documents.getEndDate()));
        }        
        if(documents.getOwnerUser() != null){
        	documents.setOwnerId(documents.getOwnerUser().getId());
        }
        return documents;
	}

	@Override
	public Integer getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(Long userId, Long orgLocationId,
			String sourceType) throws Exception {
		return orgDocumentsDao.getDocumentOwnerCountByUserIdAndOrgLocationIdAndSourceType(userId, orgLocationId, sourceType);
	}

	@Override
	public List<OrgDocuments> getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(Long userId, Long orgLocationId,
			String sourceType) throws Exception {
		return orgDocumentsDao.getOrgDocumentsListByUserIdAndOrgLocationIdAndSourceType(userId, orgLocationId, sourceType);
	}

	@Override
	public OrgDocuments getOrgDocumentsByDocIdAndLocationId(Long Id, Long locationId) throws Exception
	{
		 logger.debug("In OrgDocumentsServiceImpl class getOrgDocumentsById method...");
	        OrgDocuments documents = orgDocumentsDao.getOrgDocumentsByDocIdAndLocationId(Id, locationId);
	        OrgLocations orglo = orgLocationService.getOrgLocations(locationId);
	        //SimpleDateFormat formatter = orglo.getLocFullFormDateFormatter();
	        if(documents.getStartDate() != null){
	            documents.setStartDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getStartDate()));
	        }
	        if(documents.getEndDate() != null){
	           documents.setEndDateStr(commonUtil.dateStrFromOrgDateFormatter(orglo.getDateFormat(),documents.getEndDate()));
	        }        
	        if(documents.getOwnerUser() != null){
	        	documents.setOwnerId(documents.getOwnerUser().getId());
	        }
	        return documents;
	}
	
}
