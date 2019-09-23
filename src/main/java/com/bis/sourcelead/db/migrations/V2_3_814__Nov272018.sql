
CALL AlterTableAddColumn ('CANDIDATE','PAN_NUMBER','VARCHAR(20)  DEFAULT NULL AFTER `DATE_OF_BIRTH`');

#Email scenario for Payroll submit for approver so, the selected member will be get the mail about that.
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('sendMailToIndPayrollApprover', 'Email');

 

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('sendMailToIndPayrollApprover','Email', 'sendMailToIndPayrollApprover', 'The mail should forward when the user runned the payroll & submit for approval.', 'Request for approving payroll for [Frequency] duration.','org',
'Best Regards,\n[Login_User]\n[Login_Org_Name]', 'Dear [Approver_Name],\n Request you to approve the payroll for [Frequency] duration. For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

 

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Frequency', '[Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'ApproverName', '[Approver_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheBatchName', '[Get_The_Batch_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrgName', '[Login_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheCountNoOfAssociates', '[Get_The_Count_No_Of_Associates]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[Login]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheTotalAmount', '[Get_The_Total_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NotificationLink', '[Notification_link]');

 

#Notification Scenario for payroll submit for approver so, the selected member will be notify about that.
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('sendNotificationToIndPayrollApprover', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('sendNotificationToIndPayrollApprover', 'Notification', 'sendNotificationToIndPayrollApprover', ' Request for approving payroll for [frequency] duration.', '', 'org', '', ' click on the notification Link [Notification_link]','N','SLADMIN', 'slead', 'sleadIN', 3,1,'Y');



#Email scenario for Payroll submit for user so, the particular user will be notify about that approver it is approved or not.
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('sendApprovedMailToRequestedUserInd', 'Email');


INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('sendApprovedMailToRequestedUserInd','Email', 'sendApprovedMailToRequestedUserInd', 'Mail should be forward to the user who have requested for the Payroll for approval.', 'Payroll got approved for the [frequency] Duration.','org',
'Best Regards,\n[Login_User]\n[Login_Org_Name]', 'Dear [Request_User_name],\n Payroll got approved for the [frequency] duration. For more information go with the following details.\n Batch : [Get_The_Batch_Name]\n No of Associates : [Get_The_Count_No_Of_Associates]\n Amount :[Get_The_Total_Amount]\n [Login]','N', 'SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Frequency', '[Frequency]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'RequestUsername', '[Request_User_name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheBatchName', '[Get_The_Batch_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'LoginOrgName', '[Login_Org_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheCountNoOfAssociates', '[Get_The_Count_No_Of_Associates]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginLink', '[Login]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'GetTheTotalAmount', '[Get_The_Total_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'NotificationLink', '[Notification_link]');

#Notification scenario for Payroll submit for user so, the particular user will be notify about that approver it is approved or not.
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('sendApprovedNotificationToRequestedUserInd', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('sendApprovedNotificationToRequestedUserInd', 'Notification', 'sendApprovedNotificationToRequestedUserInd', ' Payroll got approved for the [frequency] Duration.', '', 'org', '', ' click on the notification Link [Notification_link]','N','SLADMIN', 'slead', 'sleadIN', 3,1,'Y');

