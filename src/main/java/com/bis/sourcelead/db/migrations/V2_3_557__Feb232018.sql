DROP TABLE IF EXISTS `ORG_REQUEST`;
CREATE TABLE `ORG_REQUEST` (
   ID BIGINT NOT NULL AUTO_INCREMENT,
   FIRST_NAME VARCHAR(250) DEFAULT NULL,
   LAST_NAME VARCHAR(250) DEFAULT NULL,
   PHONE_NUMBER VARCHAR(20) DEFAULT NULL,
   EMAIL VARCHAR(100) DEFAULT NULL,
   COMPANY_NAME VARCHAR(500) DEFAULT NULL,
   COMPANY_WEBSITE VARCHAR(250) DEFAULT NULL,
   COUNTRY VARCHAR(50) DEFAULT NULL,
   COMPANY_ADDRESS TEXT DEFAULT NULL,
   MODULES VARCHAR(500) DEFAULT NULL,
   STATUS INT DEFAULT NULL,
   CREATED_DATE DATE,
   PRIMARY KEY (`ID`) 
) ;

INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'ATS',  'ATS', 'Request_Org', 'ATS'); 
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'VMS',  'VMS', 'Request_Org', 'VMS'); 
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'CRM',  'CRM', 'Request_Org', 'CRM'); 
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'HRMS', 'HRMS','Request_Org', 'HRMS'); 
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'PMS',  'PMS', 'Request_Org', 'PMS'); 
INSERT INTO `LOOKUP_DATA` ( `NAME`, `DESCRIPTION`, `CATEGORY`, `UNIQUE_KEY`) VALUES ( 'IMS',  'IMS', 'Request_Org', 'IMS'); 


#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyName', '{{orgRequest.companyName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyWebsite', '{{orgRequest.companyWebsite}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyLocation', '{{orgRequest.companyLocation}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'companyEmail', '{{orgRequest.companyEmail}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
			('orgRequestEmail','Email', 'orgRequestEmail', 'orgRequestEmail mail to Sourcelead', ' New org request from {{orgRequest.companyName}}','org', 'Best Regards,\n SourceLead Team.', 'Dear Sourcelead ,\n We have new request from {{orgRequest.companyName}} organization, kindly go through brief details \n Website : {{orgRequest.companyWebsite}} \n Location : {{orgRequest.companyLocation}} \n Email id : {{orgRequest.companyEmail}} \n Verify the Organization details and generate.','N', 'SLADMIN', 'slead', 'sleadIN');
			
			
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('orgRequestNotification', 'Notification', 'orgRequestNotification', ' orgRequestNotification Notification to Sourcelead', '','sourcelead', '', ' New org request from {{orgRequest.companyName}} organization','N','SLADMIN', 'com', 'in.com');
