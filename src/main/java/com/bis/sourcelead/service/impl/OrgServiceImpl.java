package com.bis.sourcelead.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dao.OrgDao;
import com.bis.sourcelead.dao.OrgLocationsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.form.OrgSettings;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.util.SourceLeadSessionManager;

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
	public Org saveOrgSettings(OrgSettings orgSettings, MultipartHttpServletRequest request, Org org) throws Exception {
		
		Iterator<String> itrator = request.getFileNames();
		MultipartFile multiFile = request.getFile(itrator.next());
		System.out.println(multiFile.getContentType());
		org.setOrgName(orgSettings.getCompanyName());
		org.setOrgDescription(orgSettings.getAboutCompany());
		
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		orgLocations.setState(orgSettings.getState());
		orgLocations.setCity(orgSettings.getCity());
		orgLocations.setZipCode(orgSettings.getZipcode());
		orgLocations.setPhone(orgSettings.getPhone());
		orgLocations.setFax(orgSettings.getFax());
		
		
        if(null != multiFile.getContentType() && !multiFile.getContentType().equalsIgnoreCase("application/octet-stream")){
    	  try {
    	   		org.setFileContentType(multiFile.getContentType());
    	   		org.setDocFile(multiFile.getBytes());
    	   
	       }catch (Exception e) {
    	      	 e.printStackTrace();
			    }
        }
        orgDao.updateOrg(org);
        orgLocationsDao.updateOrgLocations(orgLocations);
        SourceLeadSessionManager.setSessionAttribute(request, "orgLocations", orgLocations);
        
        return org;
	}

	@Override
	public Org getOrgByOrgCode(String orgCode) throws Exception {
		return orgDao.getOrgByOrgCode(orgCode);
	}
	
	
}
