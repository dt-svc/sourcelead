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
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;


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
	private UserService userService;
	
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
		
		
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        
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
			Date startDate = format.parse(jsonObj.getString("startDate"));
			orgDocuments.setStartDate(startDate);
		}
		if(jsonObj.has("endDate") && StringUtils.isNotBlank(jsonObj.getString("endDate")) && StringUtils.isNotEmpty(jsonObj.getString("endDate"))){
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
		
		orgDocuments.setUpdatedBy(user.getUserCode());
		orgDocuments.setUpdatedDate(new Date());
		
		orgDocumentsDao.saveDocuments(orgDocuments);
	}

	@Override
	public List<OrgDocuments> getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(Long responderLocationId,Long loginUserId,
			Long orgLocationId, String sourceType) throws Exception {
		
		 List<Long> managerUserIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		
         List<OrgDocuments> documentsList = orgDocumentsDao.getOrgDocumentsListByResponderLocationIdAndOrgLocationIdAndSourceType(responderLocationId, loginUserId, managerUserIds, orgLocationId,sourceType);
		
		for(OrgDocuments documents :documentsList){
			if(documents.getStartDate() != null){
			    documents.setStartDateStr(Constants.DF_YMD.format(documents.getStartDate()));
			}
			if(documents.getEndDate() != null){
			   documents.setEndDateStr(Constants.DF_YMD.format(documents.getEndDate()));
			}
			if(documents.getSpocUser() != null){
				documents.setSpocName(documents.getSpocUser().getFirstName()+" "+documents.getSpocUser().getLastName());
			}
			if(documents.getOwnerUser() != null){
				documents.setOwnerName(documents.getOwnerUser().getFirstName()+" "+documents.getOwnerUser().getLastName());
			}
		}
		return documentsList;
	}


	@Override
	public List<OrgDocuments> getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(Long responderLocationId,
			Long orgLocationId, Long spocUserId) {
		 List<OrgDocuments> documentsList = orgDocumentsDao.getclientDocumentsListByResponderLocationIdAndSpocUserIdOrgLocationID(responderLocationId, orgLocationId,spocUserId);
		 for(OrgDocuments documents :documentsList){
				if(documents.getStartDate() != null){
				    documents.setStartDateStr(Constants.DF_YMD.format(documents.getStartDate()));
				}
				if(documents.getEndDate() != null){
				   documents.setEndDateStr(Constants.DF_YMD.format(documents.getEndDate()));
				}
				if(documents.getSpocUser() != null){
					documents.setSpocName(documents.getSpocUser().getFirstName()+" "+documents.getSpocUser().getLastName());
				}
				if(documents.getOwnerUser() != null){
					documents.setOwnerName(documents.getOwnerUser().getFirstName()+" "+documents.getOwnerUser().getLastName());
				}
			}
		 return documentsList;
	}
	
	@Override
    public OrgDocuments getOrgDocumentsById(Long Id) throws Exception {
        logger.debug("In OrgDocumentsServiceImpl class getOrgDocumentsById method...");
        OrgDocuments documents = orgDocumentsDao.getOrgDocumentsById(Id);
        if(documents.getStartDate() != null){
            documents.setStartDateStr(Constants.DF_YMD.format(documents.getStartDate()));
        }
        if(documents.getEndDate() != null){
           documents.setEndDateStr(Constants.DF_YMD.format(documents.getEndDate()));
        }        
        if(documents.getOwnerUser() != null){
        	documents.setOwnerId(documents.getOwnerUser().getId());
        }
        return documents;
    }

    @Override
    public OrgDocuments updateDocumnet(OrgDocuments orgDocument) throws Exception {
        logger.debug("In OrgDocumentsServiceImpl class updateDocumnet method...");
        return orgDocumentsDao.updateDocumnet(orgDocument);
    }

	@Override
	public OrgDocuments getOrgDocumentsByIdAndSpocId(Long docId, Long userId) {
		logger.debug("In OrgDocumentsServiceImpl class getOrgDocumentsById method...");
        OrgDocuments documents = orgDocumentsDao.getOrgDocumentsByIdAndSpocId(docId,userId);
        if(documents.getStartDate() != null){
            documents.setStartDateStr(Constants.DF_YMD.format(documents.getStartDate()));
        }
        if(documents.getEndDate() != null){
           documents.setEndDateStr(Constants.DF_YMD.format(documents.getEndDate()));
        }        
        if(documents.getOwnerUser() != null){
        	documents.setOwnerId(documents.getOwnerUser().getId());
        }
        return documents;
	}
	
}
