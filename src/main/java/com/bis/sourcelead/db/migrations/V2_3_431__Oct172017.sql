
# email to vendor when manager reject the expenses
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesRejectEmailToVendor','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeename', '{{ExpensesRejectEmailToVendor.employeename}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgname', '{{ExpensesRejectEmailToVendor.orgname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'expensesduration', '{{ExpensesRejectEmailToVendor.expensesduration}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'amount', '{{ExpensesRejectEmailToVendor.amount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectname', '{{ExpensesRejectEmailToVendor.projectname}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginlink', '{{ExpensesRejectEmailToVendor.loginlink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{ExpensesRejectEmailToVendor.reason}}');

#Email
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesRejectEmailToVendor','Email', 'ExpensesRejectEmailToVendor', 'Mail should forward when the client(rejects) rejecs the expenses of vendor resource', ' Expenses rejected','org', 'Thanks & Regards\n Sourcelead team', 'Claimed expenses rejected for the{{ExpensesRejectEmailToVendor.employeename}}, by  {{ExpensesRejectEmailToVendor.orgname}}. The expenses brief details are following /n Project Name : {{ExpensesRejectEmailToVendor.projectname}} /n Expense Duration : {{ExpensesRejectEmailToVendor.expensesduration}} /n Amount : {{ExpensesRejectEmailToVendor.amount}} \n 
/n Reject reson : {{ExpensesRejectEmailToVendor.reason}} For details login into the system /n ','N', 'SLADMIN', 'slead', 'sleadIN');

	
#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesRejectEmailToVendor', 'Notification', 'ExpensesRejectEmailToVendor', ' Notification should forward when the client(reject) reject the expenses of vendor resource','{{ExpensesRejectEmailToVendor.employeename}} expenses has been rejected', 'sourcelead', '', '{{ExpensesRejectEmailToVendor.employeename}} expenses has been rejected','N','SLADMIN', 'com', 'in.com');




