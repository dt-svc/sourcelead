package com.bis.sourcelead.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.form.OrgSettings;

public interface OrgService {
	
	public Org addOrganization(Org org) throws Exception;
	
	public Org updateOrg(Org o) throws Exception;

	public Org findOrgByID(Long id) throws Exception;

	public Boolean deleteOrg(Long id) throws Exception;

	public List<Org> getOrgs() throws Exception;
	
	public String websiteValidation(String website,String sourceleadBLockedwebsites);
	
	public Org saveOrgSettings(OrgSettings orgSettings,MultipartHttpServletRequest request,Org org) throws Exception;
	
	public Org getOrgByOrgCode(String orgCode) throws Exception;
	
	/**
	 * Handles whether the given client is duplicate or not
	 * @param clienName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isClientNameValid(String clientName) throws Exception;
	
	
	public List<Org> getEnrolledOrgs(Long orgId) throws Exception;

}
