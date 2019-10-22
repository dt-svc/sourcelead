#When manager 1 approves timesheet,mail to manager2.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1ApproveTimesheet', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToLevel2WhenLevel1ApproveTimesheet','Email', 'MailToLevel2WhenLevel1ApproveTimesheet', 'The mail need to be forward to manager2 when manager1 approves the timesheet', '[Login_User] has approved the timesheet of [Employee_Name]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Manager2],\nThe timesheet for [Project_Name] for the [Frequency_Type], [Frequency] has been approved by [Manager1]','N', 'SLADMIN', 'slead', 'sleadIN', 4,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[Login_Link]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'manager2', '[Manager2]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'manager1', '[Manager1]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'frequency', '[Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'frequencyType', '[Frequency_Type]');

#Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1ApproveTimesheet', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('MailToLevel2WhenLevel1ApproveTimesheet', 'Notification', 'MailToLevel2WhenLevel1ApproveTimesheet', 'The mail need to be forward to manager2 when manager1 approves the timesheet', '[Login_User] has approved the timesheet of [Employee_Name]', 'org', '', '[Login_User] has approved the timesheet of [Employee_Name]','N','SLADMIN', 'slead', 'sleadIN',1,'Y');
	
#NOTIFICATION