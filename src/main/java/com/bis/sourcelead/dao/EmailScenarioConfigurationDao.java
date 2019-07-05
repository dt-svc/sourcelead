package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.EmailConfigurationFromMail;
import com.bis.sourcelead.entity.EmailScenarioConfiguration;

public interface EmailScenarioConfigurationDao {
	
	EmailScenarioConfiguration save(EmailScenarioConfiguration configuration) throws Exception;
	
	EmailScenarioConfiguration getEmailscenarioConByOrgLocId(String email,Long orgLocId) throws Exception;
	
	EmailScenarioConfiguration getEmailConfigOrgLocId(Long orgLocId ) throws Exception;
	
	EmailConfigurationFromMail saveFromMail(EmailConfigurationFromMail fromMail) throws Exception;
	
	EmailConfigurationFromMail getFromMailById(Long fromMailId) throws Exception;
	
	List<EmailConfigurationFromMail> getConfFromMailList(Long orgLocId)throws Exception;
	
	EmailConfigurationFromMail deleteFromMail(EmailConfigurationFromMail emailConfigurationFromMail) throws Exception;
	
	EmailConfigurationFromMail getFromMailByUUID(String UUID) throws Exception;
	
	public int changeDefaultEmailFromMail(long id, long orgId, boolean defaultAccount);
	
	EmailConfigurationFromMail getConfFromMailByStatus(Long orgLocId)throws Exception;
	
	EmailScenarioConfiguration getVerifiedConfMail(Long orgLocId ) throws Exception;

	List<EmailScenarioConfiguration> getConfParserMailList(Long orgLocId,String parsingType, boolean isActive);

	EmailScenarioConfiguration getParserEmailsDetailsByUUID(String UUID);

	String deleteParserEMail(Long emailId);
	
	EmailScenarioConfiguration validateParserByEmail(String parserEmailId, String emailUUID,Long orgLocId);

	Boolean validateParser(String parserEmailId,Long orgLocId);

	int changeDefaultParserEmailConfig(String uuid, Long orgLocationId, boolean activeFlag, Date updatedDate);

	int getResumeParserEmailCountForOrg(Long id);
}
