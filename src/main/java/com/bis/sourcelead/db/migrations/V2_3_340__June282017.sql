DROP TABLE IF EXISTS `INVITE_COMPANY`;
CREATE TABLE `INVITE_COMPANY` (
ID BIGINT NOT NULL AUTO_INCREMENT,
USER_ID BIGINT DEFAULT NULL,
LOCATION VARCHAR(200),
COUNTRY VARCHAR(100),
STATE VARCHAR(200),
CITY VARCHAR(200),
COMPANY_NAME VARCHAR(150),
EMAIL VARCHAR(100),
NAME VARCHAR(150),
PHONE_NUMBER VARCHAR(20),
STATUS VARCHAR(5),
CREATED_DATE DATE,
CREATED_BY VARCHAR(10),
UPDATED_DATE DATE,
UPDATED_BY VARCHAR(10),
PRIMARY KEY (`ID`),
FOREIGN KEY(USER_ID) REFERENCES `USER`(ID)
);


#Invite Company mail
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('inviteCompany', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser Email', '{{inviteCompany.LoginUserEmail}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'Name', '{{inviteCompany.Name}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{inviteCompany.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginOrg', '{{inviteCompany.LoginOrg}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'ServicesList', '{{inviteCompany.ServicesList}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('inviteCompany','Email', 'inviteCompany', 'Send invite company email', 'Invitation From {{inviteCompany.LoginUser}}','org', 'Best Regards,\n{{inviteCompany.LoginUser}}\n{{inviteCompany.LoginUserEmail}}\n{{inviteCompany.LoginOrg}}', 'Dear {{inviteCompany.Name}},\nI am delighted to Introduce Platform to Manage Day to day Operations,SourceLead is a Software as a Service talent acquisition platform that helps you attract, source, onboard and manage talent. Our platform provides the business insight needed to track the processes with in talent ecosystem.{{inviteCompany.ServicesList}}\nIf you have any questions please refer to our Blog and FAQ sections here
 ','N', 'SLADMIN', 'slead', 'sleadIN');

 
 CALL AlterTableAddColumn ('RECOMMENDED_CUSTOMIZE_SCORE','CUSTOMIZE_EDUCATION','TEXT NULL AFTER `CUSTOMIZE_KEYWORDS_SCORE`');
 CALL AlterTableAddColumn ('RECOMMENDED_CUSTOMIZE_SCORE','CUSTOMIZE_EDUCATION_SCORE','VARCHAR(10) NULL AFTER `CUSTOMIZE_EDUCATION`');
 
 CALL AlterTableAddColumn ('RECOMMENDED_CUSTOMIZE_SCORE','CUSTOMIZE_VISATYPE','TEXT NULL AFTER `CUSTOMIZE_EDUCATION_SCORE`');
 CALL AlterTableAddColumn ('RECOMMENDED_CUSTOMIZE_SCORE','CUSTOMIZE_VISATYPE_SCORE','VARCHAR(10) NULL AFTER `CUSTOMIZE_VISATYPE`');
 
 

