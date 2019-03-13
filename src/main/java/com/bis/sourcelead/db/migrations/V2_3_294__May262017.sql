
set SQL_SAFE_UPDATES=0;
CALL AlterTableAddColumn ('INVOICE','REJECT_REASON','TEXT NULL AFTER `INVOICE_STATUS`');


#Bill Accept

#InvoiceAcceptMailtoVendor_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceAcceptMailtoVendor', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceID', '{{InvoiceAcceptMailtoVendor.invoiceID}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{InvoiceAcceptMailtoVendor.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientorVendorName', '{{InvoiceAcceptMailtoVendor.clientorVendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalAmount', '{{InvoiceAcceptMailtoVendor.totalAmount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'date', '{{InvoiceAcceptMailtoVendor.date}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{InvoiceAcceptMailtoVendor.orgName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'dueDate', '{{InvoiceAcceptMailtoVendor.dueDate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{InvoiceAcceptMailtoVendor.loginLink}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
											VALUES ('InvoiceAcceptMailtoVendor', 'Email', 'InvoiceAcceptMailtoClient', 'Invoice Accept Mail To Client', '  {{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished by {{InvoiceAcceptMailtoVendor.orgName}}', 'sourcelead', 'Thanks & Regards \n {{InvoiceAcceptMailtoVendor.LoginUser}},\n {{InvoiceAcceptMailtoVendor.orgName}}', 'Dear {{InvoiceAcceptMailtoVendor.clientorVendorName}},\nWe are happy to inform you that  {{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished successfully. the brief bill details\n Bill Date : {{InvoiceAcceptMailtoVendor.date}} \n Ammount: {{InvoiceAcceptMailtoVendor.totalAmount}} \n Due Date: {{InvoiceAcceptMailtoVendor.dueDate}} \n Please contact us if you have any queries or concerns \n{{InvoiceAcceptMailtoVendor.loginLink}}', 'N', 'SLADMIN', 'com', 'in.com');

        

#InvoiceAcceptMailtoVendor_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceAcceptMailtoVendor', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceAcceptMailtoVendor', 'Notification', 'InvoiceAcceptMailtoClient', 'Invoice Accept Mail To Client','org','{{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished by {{InvoiceAcceptMailtoVendor.orgName}}','N', 'SLADMIN', 'com', 'in.com');



#invoic accept

#InvoiceAcceptMailtoVendor_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceAcceptMailtoVendor', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceID', '{{InvoiceAcceptMailtoVendor.invoiceID}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{InvoiceAcceptMailtoVendor.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientorVendorName', '{{InvoiceAcceptMailtoVendor.clientorVendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalAmount', '{{InvoiceAcceptMailtoVendor.totalAmount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'date', '{{InvoiceAcceptMailtoVendor.date}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{InvoiceAcceptMailtoVendor.orgName}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'dueDate', '{{InvoiceAcceptMailtoVendor.dueDate}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{InvoiceAcceptMailtoVendor.loginLink}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
											VALUES ('InvoiceAcceptMailtoVendor', 'Email', 'InvoiceAcceptMailtoVendor', 'Invoice Accept Mail To Client', '  {{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished by {{InvoiceAcceptMailtoVendor.orgName}}', 'sourcelead', 'Thanks & Regards \n {{InvoiceAcceptMailtoVendor.LoginUser}},\n {{InvoiceAcceptMailtoVendor.orgName}}', 'Dear {{InvoiceAcceptMailtoVendor.clientorVendorName}},\nWe are happy to inform you that  {{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished successfully. the brief invoice details\n Invoice Date : {{InvoiceAcceptMailtoVendor.date}} \n Ammount: {{InvoiceAcceptMailtoVendor.totalAmount}} \n Due Date: {{InvoiceAcceptMailtoVendor.dueDate}} \n Please contact us if you have any queries or concerns \n{{InvoiceAcceptMailtoVendor.loginLink}}', 'N', 'SLADMIN', 'com', 'in.com');

        

#InvoiceAcceptMailtoVendor_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceAcceptMailtoVendor', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceAcceptMailtoVendor', 'Notification', 'InvoiceAcceptMailtoVendor', 'Invoice Accept Mail To Client','org','{{InvoiceAcceptMailtoVendor.invoiceID}} Accomplished by {{InvoiceAcceptMailtoVendor.orgName}}','N', 'SLADMIN', 'com', 'in.com');




#invoice reject


#InvoiceRejectMailtoVendor_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceRejectMailtoVendor', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceID', '{{InvoiceRejectMailtoVendor.invoiceID}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{InvoiceRejectMailtoVendor.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientorVendorName', '{{InvoiceRejectMailtoVendor.clientorVendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalAmount', '{{InvoiceRejectMailtoVendor.totalAmount}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{InvoiceRejectMailtoVendor.orgName}}');



INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{InvoiceRejectMailtoVendor.loginLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{InvoiceRejectMailtoVendor.reason}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
											VALUES ('InvoiceRejectMailtoVendor', 'Email', 'InvoiceRejectMailtoVendor', 'Invoice Accept Mail To Client', '  {{InvoiceRejectMailtoVendor.invoiceID}} got Rejected', 'sourcelead', 'Thanks & Regards \n {{InvoiceRejectMailtoVendor.LoginUser}},\n {{InvoiceRejectMailtoVendor.orgName}}', 'Dear {{InvoiceRejectMailtoVendor.clientorVendorName}},\nWe have received invoice statement  {{InvoiceRejectMailtoVendor.invoiceID}} and in the ammount of {{InvoiceRejectMailtoVendor.totalAmount}} we believe this invoice statement {{InvoiceRejectMailtoVendor.invoiceID}} to be incorrect because \n Reason:  {{InvoiceRejectMailtoVendor.reason}}  \nPlease contact us if you have any queries or concerns. \n{{InvoiceRejectMailtoVendor.loginLink}}', 'N', 'SLADMIN', 'com', 'in.com');

        

#InvoiceAcceptMailtoVendor_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceRejectMailtoVendor', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceRejectMailtoVendor', 'Notification', 'InvoiceRejectMailtoVendor', 'Invoice Accept Mail To Client','org','{{InvoiceRejectMailtoVendor.invoiceID}} got Rejected','N', 'SLADMIN', 'com', 'in.com');




#Bill reject

#InvoiceRejectMailtoVendor_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceRejectMailtoVendor', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceID', '{{InvoiceRejectMailtoVendor.invoiceID}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{InvoiceRejectMailtoVendor.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientorVendorName', '{{InvoiceRejectMailtoVendor.clientorVendorName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalAmount', '{{InvoiceRejectMailtoVendor.totalAmount}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{InvoiceRejectMailtoVendor.orgName}}');



INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginLink', '{{InvoiceRejectMailtoVendor.loginLink}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reason', '{{InvoiceRejectMailtoVendor.reason}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
											VALUES ('InvoiceRejectMailtoVendor', 'Email', 'InvoiceRejectMailtoClient', 'Invoice Accept Mail To Client', '  {{InvoiceRejectMailtoVendor.invoiceID}} got Rejected', 'sourcelead', 'Thanks & Regards \n {{InvoiceRejectMailtoVendor.LoginUser}},\n {{InvoiceRejectMailtoVendor.orgName}}', 'Dear {{InvoiceRejectMailtoVendor.clientorVendorName}},\nWe have received bill statement  {{InvoiceRejectMailtoVendor.invoiceID}} and in the ammount of {{InvoiceRejectMailtoVendor.totalAmount}} we believe this bill statement {{InvoiceRejectMailtoVendor.invoiceID}} to be incorrect because \n Reason:  {{InvoiceRejectMailtoVendor.reason}}  \nPlease contact us if you have any queries or concerns. \n{{InvoiceRejectMailtoVendor.loginLink}}', 'N', 'SLADMIN', 'com', 'in.com');

        

#InvoiceAcceptMailtoVendor_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceRejectMailtoVendor', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceRejectMailtoVendor', 'Notification', 'InvoiceRejectMailtoClient', 'Invoice Accept Mail To Client','org','{{InvoiceRejectMailtoVendor.invoiceID}} got Rejected','N', 'SLADMIN', 'com', 'in.com');

