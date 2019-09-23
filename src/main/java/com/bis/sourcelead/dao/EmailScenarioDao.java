package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.OrgLocations;

public interface EmailScenarioDao {
	
	EmailScenario getEmailScenarioByUniqueNameAndEntityTypeAndOrgCodeAndLocationsCode(String uniqueScenarioName, String entityType, String orgCode, String locationsCode) throws Exception;
 
	EmailScenario save(EmailScenario scenario) throws Exception;

	EmailScenario getEmailScenarioByUniqueName(String uniqueScenarioName, String entityType) throws Exception;
	
	EmailScenario getEmailTemplateById(Long id);
	
	EmailScenario getEmailDefTemplateNo(String uniqueScenarioName, String defaultTemp) throws Exception ;
	
	List<EmailScenario> getEmailScenarioList(String templateType,List<Long> tabIds, List<String> specification) throws Exception;
	
	EmailScenario getEmailScenarioByUniqueNameAndOrgLocationId(String uniqueScenarioName, String entityType,Long orgLocationId) throws Exception;
	
	List<EmailScenario> getEmailScenarioListByOrgLocId(Long orgLocId) throws Exception;

	EmailScenario getEmailTemplateByUUID(String templateUUID);

}
