
#vendor accept invoice
SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best regards\n{{InvoiceAcceptMailtoVendor.LoginUser}},\n{{InvoiceAcceptMailtoVendor.orgName}}', `SUBJECT`='Alert Invoice -{{InvoiceAcceptMailtoVendor.invoiceID}} Accepted  by {{InvoiceAcceptMailtoVendor.orgName}}', `BODY`=' Dear {{InvoiceAcceptMailtoVendor.clientorVendorName}},\n\nWe accept the invoice {{InvoiceAcceptMailtoVendor.invoiceID}} attached for services provided.\nWe appreciate Your services.\nWe will process the payment upon due\n\nthe brief bill details below\nBill Date : {{InvoiceAcceptMailtoVendor.date}}\nAmount: {{InvoiceAcceptMailtoVendor.totalAmouprojectMembersListnt}}\nDue Date: {{InvoiceAcceptMailtoVendor.dueDate}}\n\nThank you\nPlease contact us if you have any queries or concerns\n\n{{InvoiceAcceptMailtoVendor.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceAcceptMailtoVendor' and `ENTITY_TYPE`='Email';

#vendor reject invoice
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best regards\n{{InvoiceRejectMailtoVendor.LoginUser}},\n{{InvoiceRejectMailtoVendor.orgName}}', `SUBJECT`='Invoice-{{InvoiceRejectMailtoVendor.invoiceID}} Rejected by {{InvoiceRejectMailtoVendor.LoginUser}}', `BODY`='Dear {{InvoiceRejectMailtoVendor.clientorVendorName}},\n\nWe have rejected the invoice {{InvoiceRejectMailtoVendor.invoiceID}} due to the following reasons.\nReason for Rejection : {{InvoiceRejectMailtoVendor.reason}}\n\nWe appreciate your services.please update the invoice and resend\n\nThank you\nPlease contact us if you have any queries or concerns.\n	\n{{InvoiceRejectMailtoVendor.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceRejectMailtoVendor' and `ENTITY_TYPE`='Email';

#client accept invoice
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best regards\n{{InvoiceAcceptMailtoVendor.LoginUser}},\n{{InvoiceAcceptMailtoVendor.orgName}}', `SUBJECT`='Alert Invoice -{{InvoiceAcceptMailtoVendor.invoiceID}} Accepted  by {{InvoiceAcceptMailtoVendor.orgName}}', `BODY`=' Dear {{InvoiceAcceptMailtoVendor.clientorVendorName}},\n\nWe accept the invoice {{InvoiceAcceptMailtoVendor.invoiceID}} attached for services provided.\nWe appreciate Your services.\nWe will process the payment upon due\n\nthe brief bill details below\nBill Date : {{InvoiceAcceptMailtoVendor.date}}\nAmount: {{InvoiceAcceptMailtoVendor.totalAmouprojectMembersListnt}}\nDue Date: {{InvoiceAcceptMailtoVendor.dueDate}}\n\nThank you\nPlease contact us if you have any queries or concerns\n\n{{InvoiceAcceptMailtoVendor.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceAcceptMailtoClient' and `ENTITY_TYPE`='Email';

#client reject invoice
UPDATE `EMAIL_SCENARIO` SET `SIGNATURE`='Best regards\n{{InvoiceRejectMailtoVendor.LoginUser}},\n{{InvoiceRejectMailtoVendor.orgName}}', `SUBJECT`='Invoice-{{InvoiceRejectMailtoVendor.invoiceID}} Rejected by {{InvoiceRejectMailtoVendor.LoginUser}}', `BODY`='Dear {{InvoiceRejectMailtoVendor.clientorVendorName}},\n\nWe have rejected the invoice {{InvoiceRejectMailtoVendor.invoiceID}} due to the following reasons.\nReason for Rejection : {{InvoiceRejectMailtoVendor.reason}}\n\nWe appreciate your services.please update the invoice and resend\n\nThank you\nPlease contact us if you have any queries or concerns.\n	\n{{InvoiceRejectMailtoVendor.loginLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceRejectMailtoClient' and `ENTITY_TYPE`='Email';


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'invoiceID','{{InvoiceClientPayableReminderEmail.invoiceID}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'LoginUser','{{InvoiceClientPayableReminderEmail.LoginUser}}');	 

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'orgName','{{InvoiceClientPayableReminderEmail.orgName}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'clientName','{{InvoiceClientPayableReminderEmail.clientName}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'grandTotal','{{InvoiceClientPayableReminderEmail.grandTotal}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'dueDate','{{InvoiceClientPayableReminderEmail.dueDate}}');	
																									
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'acceptLink','{{InvoiceClientPayableReminderEmail.acceptLink}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'rejectLink','{{InvoiceClientPayableReminderEmail.rejectLink}}');	

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'InvoiceClientPayableReminderEmail' and `ENTITY_TYPE` = 'Email'), 'attachement','{{InvoiceClientPayableReminderEmail.attachement}}');	
											
# email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
VALUES ('InvoiceClientPayableReminderEmail', 'Email', 'InvoiceClientPayableReminderEmail', 'Invoice Reminder Mail To Client', 'Remider Payment Due for Invoice-{{InvoiceClientPayableReminderEmail.invoiceID}}', 'sourcelead', 'Thanks & Regards \n {{InvoiceClientPayableReminderEmail.LoginUser}},\n {{InvoiceClientPayableReminderEmail.orgName}}', 'Dear {{InvoiceClientPayableReminderEmail.clientName}},\n This is a reminder that your invoice {{InvoiceClientPayableReminderEmail.invoiceID}} is past due.\nPlease process the payment as soon as possible.\n\nPlease find the details below\nGrand total:{{InvoiceClientPayableReminderEmail.grandTotal}} Due date-{{InvoiceClientPayableReminderEmail.dueDate}}\n  {{InvoiceClientPayableReminderEmail.acceptLink}} \n {{InvoiceClientPayableReminderEmail.rejectLink}} \n {{InvoiceClientPayableReminderEmail.attachement}}', 'N', 'SLADMIN', 'com', 'in.com');
																			
#email_scenario
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) 
VALUES ('InvoiceClientPayableReminderEmail', 'Email', 'InvoiceClientReceivableReminderEmail', 'Invoice Reminder Mail To Client', 'Remider Payment Due for Invoice-{{InvoiceClientPayableReminderEmail.invoiceID}}', 'sourcelead', 'Thanks & Regards \n {{InvoiceClientPayableReminderEmail.LoginUser}},\n {{InvoiceClientPayableReminderEmail.orgName}}', 'Dear {{InvoiceClientPayableReminderEmail.clientName}},\nThis is a reminder that your invoice {{InvoiceClientPayableReminderEmail.invoiceID}} is past due.\nPlease process the payment as soon as possible.\n\nPlease find the details below\nGrand total:{{InvoiceClientPayableReminderEmail.grandTotal}}\nDue date-{{InvoiceClientPayableReminderEmail.dueDate}}\n  {{InvoiceClientPayableReminderEmail.acceptLink}} \n {{InvoiceClientPayableReminderEmail.rejectLink}} \n {{InvoiceClientPayableReminderEmail.attachement}}\n\nThank you for your kind information.', 'N', 'SLADMIN', 'com', 'in.com');
																						
#entity_type_Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('InvoiceClientPayableReminderEmail', 'Notification');

#email_scenario_ notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceClientPayableReminderEmail', 'Notification', 'InvoiceClientPayableReminderEmail', 'Invoice Reminder Mail To Client','org','Invoice Past due {{InvoiceClientPayableReminderEmail.invoiceID}}','N', 'SLADMIN', 'com', 'in.com');

#email_scenario_ notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `TEMPLATE_TYPE`, `BODY`, `IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`, `LOCATION_CODE`) VALUES ('InvoiceClientPayableReminderEmail', 'Notification', 'InvoiceClientReceivableReminderEmail', 'Invoice Reminder Mail To Client','org','Invoice Past due {{InvoiceClientPayableReminderEmail.invoiceID}}','N', 'SLADMIN', 'com', 'in.com');

#Invoice generation forInvoiceClientAcceptEmail
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{InvoiceClientAcceptEmail.clientName}},\n\nPlease find the invoice {{InvoiceClientAcceptEmail.invoiceID}} attached for services provided from {{InvoiceClientAcceptEmail.invoiceDate}} to {{InvoiceClientAcceptEmail.dueDate}}\nGrand Total : {{InvoiceClientAcceptEmail.grandTotal}}\nWe appreciate your prompt payment. \n\n{{InvoiceClientAcceptEmail.acceptLink}} \n {{InvoiceClientAcceptEmail.rejectLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceClientAcceptEmail' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Alert Invoice- {{InvoiceClientAcceptEmail.invoiceID}} from {{InvoiceClientAcceptEmail.orgName}} '  WHERE `UNIQUE_SCENARIO_NAME`='InvoiceClientAcceptEmail' and `ENTITY_TYPE`='Email';
	
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'invoiceDate', '{{InvoiceClientAcceptEmail.invoiceDate}}');

#Invoice generation InvoiceClientPayableEmail
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear {{InvoiceClientAcceptEmail.clientName}},\n\nPlease find the invoice {{InvoiceClientAcceptEmail.invoiceID}} attached for services provided from {{InvoiceClientAcceptEmail.invoiceDate}} to {{InvoiceClientAcceptEmail.dueDate}}\nGrand Total : {{InvoiceClientAcceptEmail.grandTotal}}\nWe appreciate your prompt payment. \n\n{{InvoiceClientAcceptEmail.acceptLink}} \n {{InvoiceClientAcceptEmail.rejectLink}}' WHERE `UNIQUE_SCENARIO_NAME`='InvoiceClientPayableEmail' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Alert Invoice- {{InvoiceClientAcceptEmail.invoiceID}} from {{InvoiceClientAcceptEmail.orgName}} '  WHERE `UNIQUE_SCENARIO_NAME`='InvoiceClientPayableEmail' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES=1;
