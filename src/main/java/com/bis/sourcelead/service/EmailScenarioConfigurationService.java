package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;


public interface EmailScenarioConfigurationService {
	
	EmailScenarioConfiguration save(EmailScenarioConfiguration configuration) throws Exception;
	
	EmailScenarioConfiguration getEmailscenarioConByOrgLocId(String email,Long orgLocId) throws Exception;
	
	EmailScenarioConfiguration getEmailConfigOrgLocId(Long orgLocId ) throws Exception;
	
	EmailScenarioConfiguration getVerifiedConfMail(Long orgLocId ) throws Exception;
	
	EmailConfigurationFromMail saveFromMail(EmailConfigurationFromMail fromMail) throws Exception;
	
	EmailConfigurationFromMail deleteFromMail(Long emailId,Long orgLocId) throws Exception;
	
	List<EmailConfigurationFromMail> getConfFromMailList(Long orgLocId)throws Exception;
	
	EmailConfigurationFromMail getFromMailByUUID(String UUID) throws Exception;
	
	EmailConfigurationFromMail getFromMailById(Long id) throws Exception;
	
	public String verifyConfgFromMail(Long id,EmailScenarioConfiguration emailScenarioConfiguration) throws Exception;
	
	public String verifyConfgParserMail(Long id,EmailScenarioConfiguration emailScenarioConfiguration) throws Exception;
	
	public int changeDefaultEmailFromMail(long id, long orgId, boolean defaultAccount);
	
	EmailConfigurationFromMail getConfFromMailByStatus(Long orgLocId)throws Exception;
	
	List<EmailScenarioConfiguration> getConfParserMailList(Long orgLocId, String parsingType , boolean isActive)throws Exception;
	
	EmailScenarioConfiguration getParserEmailsDetailsByUUID(String UUID)throws Exception;
	
	String deleteParserEMail(Long emailId) throws Exception;
	
    EmailScenarioConfiguration validateParserByEmail(String parserEmailId,String emailUUID,Long orgLocId);
	
	Boolean validateParser(String parserEmailId,Long orgLocId);

	int changeDefaultParserEmailConfig(String uuid, Long orgLocationId, boolean activeFlag, Date updatedDate);
	
	int getResumeParserEmailCountForOrg(Long id);
}