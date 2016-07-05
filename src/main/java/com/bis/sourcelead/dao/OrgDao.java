package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Org;

/**
 * 
 * @author Rajashekar
 * @date 29th Mar 2016
 *
 */
public interface OrgDao {

	public Org addOrganization(Org org) throws Exception;

	public Org updateOrg(Org o) throws Exception;

	public Org findOrgByID(Long id) throws Exception;

	public Boolean deleteOrg(Long id) throws Exception;

	public List<Org> getOrgs() throws Exception;
	
	String websiteValidation(String website);
	
}
