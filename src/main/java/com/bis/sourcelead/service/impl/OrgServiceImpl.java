package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.dto.CompanyRegistrationDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.FileUploadUtil;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */

@Service
public class OrgServiceImpl implements OrgService {

	@Autowired
	OrgDao orgDao;
	
	@Autowired
	OrgLocationsDao orgLocationsDao;

	@Override
	public Org addOrganization(Org org) throws Exception {
		return orgDao.addOrganization(org);

	}

	@Override
	public Org updateOrg(Org org) throws Exception {
		return orgDao.updateOrg(org);
	}

	@Override
	public Org findOrgByID(Long id) throws Exception {
		return orgDao.findOrgByID(id);
	}

	@Override
	public Boolean deleteOrg(Long id) throws Exception {
		return orgDao.deleteOrg(id);
	}

	@Override
	public List<Org> getOrgs() throws Exception {
		return orgDao.getOrgs();
	}

	@Override
	public String websiteValidation(String website,String sourceleadBLockedwebsites) {
		return orgDao.websiteValidation(website,sourceleadBLockedwebsites);
	}

	@Override
	public Org saveOrgSettings(CompanyRegistrationDTO companyRegistrationDTO, Org org) throws Exception {
		
		
        if(companyRegistrationDTO.getFileInfo() != null){
        	FileInfo fileInfo = companyRegistrationDTO.getFileInfo().get(0);
    	  try {
    	   		org.setFileContentType(fileInfo.getContentType());
    	   		org.setFileName(fileInfo.getFileName());
    	   		org.setFileUrl(fileInfo.getFileURL());
    	   		FileUploadUtil.moveFile(companyRegistrationDTO.getStaticPath(), fileInfo, "profile");
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
        orgDao.updateOrg(org);
        
        return org;
	}

	@Override
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		return orgDao.getOrgByOrgCode(orgCode);
	}

	@Override
	public boolean isClientNameValid(String clientName ,Long orgLocationId,String responderType) throws Exception {
		return orgDao.isClientNameValid(clientName,orgLocationId,responderType);
	}

	@Override
	public List<Org> getEnrolledOrgs(Long orgId,String userState,String searchByName,Integer offSet) throws Exception {
		return orgDao.getEnrolledOrgs(orgId, userState, searchByName,offSet);
	}

	@Override
	public boolean isOrgNameExisted(String orgName) throws Exception {
		return orgDao.isOrgNameExisted(orgName);
	}

	@Override
	public List<Org> isSpocExistedTheOwner(String clientName, Long orgLocationId, String responderType) throws Exception {
		
		return orgDao.isSpocExistedTheOwner(clientName, orgLocationId, responderType);
	}

	@Override
	public Org getOrgByOrgName(String orgName) {
		return orgDao.getOrgByOrgName(orgName);
		
	}

	@Override
	public Boolean isLoginUserIsSuperAdmin(Long userId,Long orgId) throws Exception
	{
		return orgDao.isLoginUserIsSuperAdmin(userId,orgId);
	}
	
	@Override
	public List<UserDTO> getUserListForOrgChart(Long orgLocationId) throws Exception
	{
		return orgDao.getuserListForOrgChart(orgLocationId);
	}
	
}
