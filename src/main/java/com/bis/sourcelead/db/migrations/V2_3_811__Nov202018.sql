#Change job status mail To Managers,Assigned Members,Primary Recruiter,Vendor. 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeJobStatusEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('ChangeJobStatusEmail','Email', 'ChangeJobStatusEmail', 'This notification and email is sent when a job status is updated', 'Status for [Job_Name] has been changed from [Previous_Status] to [Current_Status]','org',
'Best Regards,\n[Login_User]\n[Login_Org_Name]', 'Dear [User],\n The job status for [Job_Name] has been changed from [Previous_Status] to [Current_Status].\n To view this change please log into Sourcelead.\n [Login]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'jobName', '[Job_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PreviousStatus', '[Previous_Status]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'CurrentStatus', '[Current_Status]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrgName', '[Login_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'toUser', '[User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[Login]');

#EmailScenario Notification To Managers,Assigned Members,Primary Recruiter,Vendor from Login User
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeJobStatusNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('ChangeJobStatusNotification', 'Notification', 'ChangeJobStatusNotification', 'This notification and email is sent when a job status is updated', 'Status for [Job_Name] has been changed from [Previous_Status] to [Current_Status]', 'org', '', 'Dear [User],\n The job status for [Job_Name] has been changed from [Previous_Status] to [Current_Status].\n To view this change please log into Sourcelead.\n [Login]','N','SLADMIN', 'slead', 'sleadIN', 3,1,'Y');



#email sent to an Associate when his reporting manager has been changed. 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeReportingManagerEmail', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('ChangeReportingManagerEmail','Email', 'ChangeReportingManagerEmail', 'This email is sent to an Associate when his reporting manager has been changed.', 'Your reporting manager has been changed.','org',
'Best Regards,\n[Login_User]\n[Login_Org_Name]', 'Dear [Associate],\n Your reporting manager has been changed from [Previous_Reporting_Manager] to [New_Reporting_Manager].','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'PreviousReportingManager', '[Previous_Reporting_Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NewReportingManager', '[New_Reporting_Manager]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrgName', '[Login_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'toAssociate', '[Associate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');

#Notification sent to an Associate when his reporting manager has been changed.
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ChangeReportingManagerNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('ChangeReportingManagerNotification', 'Notification', 'ChangeReportingManagerNotification', 'This notification is sent to an Associate when his reporting manager has been changed.', 'Your reporting manager has been changed.', 'org', '', 'Your reporting manager has been changed.','N','SLADMIN', 'slead', 'sleadIN', 3,1,'Y');