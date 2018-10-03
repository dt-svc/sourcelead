#indian payroll mail scnarios
#Request For Approving IndianPayroll mail To Approver
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('RequestForApprovingIndianPayroll', 'Email');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('RequestForApprovingIndianPayroll','Email', 'RequestForApprovingIndianPayroll', 'Indian Payroll Submit For Approval', 'Request for approving payroll for [Frequency] duration','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Approver_Name],\nRequest you to approve the payroll for [Frequency] duration. For complete details go with the following details.\n\nBatch : [Batch_Name]\nNo of Associates : [No_Of_Associates]\nAmount : [Amount]\n [Payroll_Link]','N', 'SLADMIN', 'slead', 'sleadIN', 32,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approverName', '[Approver_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'batchName', '[Batch_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfAssociates', '[No_Of_Associates]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'frequency', '[Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'amount', '[Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'login', '[Login]');



#EmailScenario Notification To Approver
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('RequestForApprovingIndianPayroll', 'Notification', 'RequestForApprovingIndianPayroll', 'Indian Payroll Submit For Approval', 'Request for approving payroll for [Frequency] duration', 'org', '', '','N','SLADMIN', 'com', 'in.com', 32,1,'Y');


#Approver mail To Request User mail 
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('IndianPayrollApproverMailToRequestUser', 'Email');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('IndianPayrollApproveMailToRequestUser','Email', 'IndianPayrollApproveMailToRequestUser', 'Approver mail to payroll requester', 'Payroll got approved for the frequency [Frequency]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Request_User_Name],\n Payroll got approved for the frequency [Frequency]. For complete details go with the following details.\n\nBatch : [Batch_Name]\nNo of Associates : [No_Of_Associates]\nAmount : [Amount]\n [Payroll_Link]','N', 'SLADMIN', 'slead', 'sleadIN', 32,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'requestUserName', '[Request_User_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'group', '[Group]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'noOfAssociates', '[No_Of_Associates]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'frequency', '[Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'amount', '[Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approveLoginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'login', '[Login]');

#EmailScenario Notification To Request User from approver
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('IndianPayrollApproveMailToRequestUser', 'Notification', 'IndianPayrollApproveMailToRequestUser', 'Approver notification to payroll requester', 'Payroll got approved for the frequency [Frequency]', 'org', '', '','N','SLADMIN', 'com', 'in.com', 32,1,'Y');
