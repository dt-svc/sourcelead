package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Org;

public interface OrgService {
	
	public Org addOrganization(Org org) throws Exception;
	
	public Org updateOrg(Org o) throws Exception;

	public Org findOrgByID(Long id) throws Exception;

	public Boolean deleteOrg(Long id) throws Exception;

	public List<Org> getOrgs() throws Exception;
	
	String websiteValidation(String website,String sourceleadBLockedwebsites);

}
