
# email to vendor when manager approves the expenses
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesApproveEmailToVendor','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeename', '{{ExpensesApproveEmailToVendor.employeename}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'orgname', '{{ExpensesApproveEmailToVendor.orgname}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'expensesduration', '{{ExpensesApproveEmailToVendor.expensesduration}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'amount', '{{ExpensesApproveEmailToVendor.amount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectname', '{{ExpensesApproveEmailToVendor.projectname}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginlink', '{{ExpensesApproveEmailToVendor.loginlink}}');

#Email
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesApproveEmailToVendor','Email', 'ExpensesApproveEmailToVendor', 'Mail should forward when the client(approver) approves the expenses of vendor resource', ' Expenses approved','org', 'Thanks & Regards\n Sourcelead team', 'Claimed expenses approved for the{{ExpensesApproveEmailToVendor.employeename}}, by  {{ExpensesApproveEmailToVendor.orgname}}. The expenses brief details are following /n Project Name : {{ExpensesApproveEmailToVendor.projectname}} /n Expense Duration : {{ExpensesApproveEmailToVendor.expensesduration}} /n Amount : {{ExpensesApproveEmailToVendor.amount}} \n 
For details login into the system /n ','N', 'SLADMIN', 'slead', 'sleadIN');

	
#Notification
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesApproveEmailToVendor', 'Notification', 'ExpensesApproveEmailToVendor', ' Notification should forward when the client(approver) approves the expenses of vendor resource','{{ExpensesApproveEmailToVendor.employeename}} expenses has been approved', 'sourcelead', '', '{{ExpensesApproveEmailToVendor.employeename}} expenses has been approved','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');




