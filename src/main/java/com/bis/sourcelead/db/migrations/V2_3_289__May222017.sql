#InvoiceClientAcceptEmail_Email
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceClientAcceptEmail', 'Email');

#CLASS_FIELD
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceID', '{{InvoiceClientAcceptEmail.invoiceID}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'LoginUser', '{{InvoiceClientAcceptEmail.LoginUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{InvoiceClientAcceptEmail.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'grandTotal', '{{InvoiceClientAcceptEmail.grandTotal}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'dueDate', '{{InvoiceClientAcceptEmail.dueDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'orgName', '{{InvoiceClientAcceptEmail.orgName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'address', '{{InvoiceClientAcceptEmail.address}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'acceptLink', '{{InvoiceClientAcceptEmail.acceptLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectLink', '{{InvoiceClientAcceptEmail.rejectLink}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'attachement', '{{InvoiceClientAcceptEmail.attachement}}');

#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
											VALUES ('InvoiceClientAcceptEmail', 'Email', 'InvoiceClientPayableEmail', 'Invoice Accept Mail To Client', 'New Bill  {{InvoiceClientAcceptEmail.invoiceID}} by {{InvoiceClientAcceptEmail.orgName}}', 'sourcelead', 'Thanks & Regards \n {{InvoiceClientAcceptEmail.LoginUser}},\n {{InvoiceClientAcceptEmail.orgName}},\n {{InvoiceClientAcceptEmail.address}}', 'Dear {{InvoiceClientAcceptEmail.clientName}},\nWe are contacting you in regard to a new Bill {{InvoiceClientAcceptEmail.invoiceID}} that has been created on your account. You may find the bill attached bill of {{InvoiceClientAcceptEmail.grandTotal}} \n{{InvoiceClientAcceptEmail.acceptLink}} \n{{InvoiceClientAcceptEmail.rejectLink}}', 'N', 'SLADMIN', 'com', 'in.com');



#InvoiceClientAcceptEmail_Notification
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceClientAcceptEmail', 'Notification');


#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceClientAcceptEmail', 'Notification', 'InvoiceClientPayableEmail', 'Invoice Accept Mail To Client','org','New Bill {{InvoiceClientAcceptEmail.invoiceID}} by {{InvoiceClientAcceptEmail.orgName}}','N', 'SLADMIN', 'com', 'in.com');
