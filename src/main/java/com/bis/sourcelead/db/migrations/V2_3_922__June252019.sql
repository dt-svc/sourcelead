#When manager 1 submit expenses behalf of employee,mail to manager2and reporting manager.

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1SubmitsExpense', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToLevel2WhenLevel1SubmitsExpense','Email', 'MailToLevel2WhenLevel1SubmitsExpense', 'The mail need to be forward to manager2 and reporting manager when manager1 submits the expense on behalf of employee', '[Login_User] has submitted expenses on behalf of [Employee_Name]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Approver],\n[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].\nPurpose : [Purpose]\n Amount : [Submitted_Amount]\n\n For brief details click on following link[Expenses_Submitted]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'amount', '[Submitted_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginURL', '[Expenses_Submitted]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'purpose', '[Purpose]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approver', '[Approver]');

#Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1SubmitsExpense', 'Notification');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NotificationToLevel2WhenLevel1SubmitsExpense', 'Notification', 'MailToLevel2WhenLevel1SubmitsExpense', 'The notification need to be forward to manager2 and reporting manager when manager1 submits the expense on behalf of employee', '[Login_User] has submitted expenses on behalf of [Employee_Name]', 'org', '', '[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].','N','SLADMIN', 'slead', 'sleadIN',1,'Y');
	
#NOTIFICATION

SET SQL_SAFE_UPDATES = 0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Manager_Name],\n[Login_User] Rejected the Expenses of [Employee_Name] for the Project [Project_Name]' WHERE `UNIQUE_SCENARIO_NAME`='MailToManager1WhenManager2orAdminRejectTheExpense' and `ENTITY_TYPE`='Notification';
SET SQL_SAFE_UPDATES = 1;
                          

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES   ((SELECT `ID` FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND `ENTITY_TYPE` = 'Email'), 'loginURL', '[Login_Link]');

