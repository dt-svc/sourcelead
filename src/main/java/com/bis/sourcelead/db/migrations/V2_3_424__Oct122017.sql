# Budget Request Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('BudgetRequest','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{BudgetRequest.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{BudgetRequest.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{BudgetRequest.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'descrption', '{{BudgetRequest.descrption}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedHours', '{{BudgetRequest.estimatedHours}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'estimatedBudget', '{{BudgetRequest.estimatedBudget}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{BudgetRequest.loginLink}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Budget Request','Email', 'Budget Request', 'Budget Request for {{BudgetRequest.projectName}} Project', 'Budget Request for {{BudgetRequest.projectName}} Project','org', 'Best Regards,\n{{BudgetRequest.loginUserName}}', ' Dear {{BudgetRequest.approverName}},\n Requesting you for budget approval to the Project {{BudgetRequest.projectName}}. The brief project budget details are below.\n\n Project Name  : {{BudgetRequest.projectName}}\n Description : {{BudgetRequest.descrption}}\n Estimated Hours : {{BudgetRequest.estimatedHours}}\n Estimated Budget : {{BudgetRequest.estimatedBudget}}\n\n {{BudgetRequest.loginLink}}','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('BudgetRequestNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('BudgetRequestNotification', 'Notification', 'BudgetRequestNotification', 'Send mail to BudgetRequestNotification','org','Budget Request Request for {{BudgetRequest.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');




# Budget Request Accepted Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('BudgetRequestAccepted','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{BudgetRequestAccepted.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{BudgetRequestAccepted.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{BudgetRequestAccepted.loginUserName}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Budget Request Accepted','Email', 'Budget Request Accepted', 'Budget Request Accepted for {{BudgetRequestAccepted.projectName}} Project', 'Budget Request for {{BudgetRequestAccepted.projectName}} Project','org', 'Best Regards,\n{{BudgetRequestAccepted.loginUserName}}', ' Dear {{BudgetRequestAccepted.approverName}},\n Budget request for {{BudgetRequestAccepted.projectName}} project got approved. Go ahead with the further process.','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('BudgetRequestAcceptedNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('BudgetRequestAcceptedNotification', 'Notification', 'BudgetRequestAcceptedNotification', 'Send mail to BudgetRequestAcceptedNotification','org','Budget approved for {{BudgetRequestAccepted.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');


# Budget Request Rejected Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('BudgetRequestRejected','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{BudgetRequestRejected.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{BudgetRequestRejected.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{BudgetRequestRejected.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectedReason', '{{BudgetRequestRejected.reason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Budget Request Rejected','Email', 'Budget Request Rejected', 'Budget Request Rejected for {{BudgetRequestRejected.projectName}} Project', 'Budget Request for {{BudgetRequestRejected.projectName}} Project','org', 'Best Regards,\n{{BudgetRequestRejected.loginUserName}}', ' Dear {{BudgetRequestRejected.approverName}},\n Budget requested approval for project {{BudgetRequestRejected.projectName}} has been rejected due to following reason \n Rejected Reason  : {{BudgetRequestRejected.reason}} ','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('BudgetRequestRejectedNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('BudgetRequestRejectedNotification', 'Notification', 'BudgetRequestRejectedNotification', 'Send mail to BudgetRequestRejectedNotification','org','Budget rejected for {{BudgetRequestRejected.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');



# Budget Request OnHold Email 

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('BudgetRequestOnHold','Email');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{BudgetRequestOnHold.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverName', '{{BudgetRequestOnHold.approverName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUserName', '{{BudgetRequestOnHold.loginUserName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectedReason', '{{BudgetRequestOnHold.reason}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES 
			('Budget Request OnHold','Email', 'Budget Request OnHold', 'Budget Request OnHold for {{BudgetRequestOnHold.projectName}} Project', 'Budget Request for {{BudgetRequestOnHold.projectName}} Project','org', 'Best Regards,\n{{BudgetRequestOnHold.loginUserName}}', ' Dear {{BudgetRequestOnHold.approverName}},\n Budget requested approval for project {{BudgetRequestOnHold.projectName}} has been on Hold due to following reason \n On Hold Reason  : {{BudgetRequestOnHold.reason}} ','N', 'SLADMIN', 'sourcelead', 'sourceleadIN');

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('BudgetRequestOnHoldNotification', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('BudgetRequestOnHoldNotification', 'Notification', 'BudgetRequestOnHoldNotification', 'Send mail to BudgetRequestOnHoldNotification','org','Budget on Hold for {{BudgetRequestRejected.projectName}} Project','N', 'SLADMIN', 'com', 'in.com');






