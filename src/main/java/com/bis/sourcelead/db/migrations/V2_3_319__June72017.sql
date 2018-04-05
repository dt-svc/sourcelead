
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesApprovalManagerEmailToEmployee','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerUser', '{{ExpensesApprovalManagerEmailToEmployee.managerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ExpensesApprovalManagerEmailToEmployee.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'submitForApprovalDate', '{{ExpensesApprovalManagerEmailToEmployee.submitForApprovalDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'expenseId', '{{ExpensesApprovalManagerEmailToEmployee.expenseId}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ExpensesApprovalManagerEmailToEmployee.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ExpensesApprovalManagerEmailToEmployee.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'endDate', '{{ExpensesApprovalManagerEmailToEmployee.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'amount', '{{ExpensesApprovalManagerEmailToEmployee.amount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{ExpensesApprovalManagerEmailToEmployee.loginOrg}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesApprovalManagerEmailToEmployee','Email', 'ExpensesApprovalManagerEmailToEmployee', 'Used for Manager Approved  to Employee Expenses.', 'Expense has been approved.','org', 'Best Regards,\n{{ExpensesApprovalManagerEmailToEmployee.managerUser}}\n{{ExpensesApprovalManagerEmailToEmployee.loginOrg}}', 'Dear {{ExpensesApprovalManagerEmailToEmployee.employeeUser}}\nWe are pleased to inform you,\nyour proposal for expenses submitted on {{ExpensesApprovalManagerEmailToEmployee.submitForApprovalDate}} has been approved.,\n Expense id :  {{ExpensesApprovalManagerEmailToEmployee.expenseId}}\n Project :  {{ExpensesApprovalManagerEmailToEmployee.projectName}}\n Start Date :  {{ExpensesApprovalManagerEmailToEmployee.startDate}}\n End Date :  {{ExpensesApprovalManagerEmailToEmployee.endDate}}\n Amount :  {{ExpensesApprovalManagerEmailToEmployee.amount}} \n ALL THE BEST','N', 'SLADMIN', 'slead', 'sleadIN');

#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ExpensesApprovalManagerEmailToEmployee', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesApprovalManagerEmailToEmployee', 'Notification', 'ExpensesApprovalManagerEmailToEmployee', 'Used for Manager Approved  to Employee Expenses.', 'Expense has been approved for the {{ExpensesApprovalManagerEmailToEmployee.projectName}}.', 'sourcelead', '', 'Approval request on {{ExpensesApprovalManagerEmailToEmployee.projectName}} Expense.','N','SLADMIN', 'com', 'in.com');

#Expense has been rejected email


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesApprovalRejectedEmailToEmployee','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerUser', '{{ExpensesApprovalRejectedEmailToEmployee.managerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ExpensesApprovalRejectedEmailToEmployee.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'submitForApprovalDate', '{{ExpensesApprovalRejectedEmailToEmployee.submitForApprovalDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'expenseId', '{{ExpensesApprovalRejectedEmailToEmployee.expenseId}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ExpensesApprovalRejectedEmailToEmployee.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ExpensesApprovalRejectedEmailToEmployee.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'endDate', '{{ExpensesApprovalRejectedEmailToEmployee.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'amount', '{{ExpensesApprovalRejectedEmailToEmployee.amount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{ExpensesApprovalRejectedEmailToEmployee.loginOrg}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesApprovalRejectedEmailToEmployee','Email', 'ExpensesApprovalRejectedEmailToEmployee', 'Used for Manager Rejected  to Employee Expenses.', 'Expense has been rejected.','org', 'Best Regards,\n{{ExpensesApprovalManagerEmailToEmployee.managerUser}}\n{{ExpensesApprovalRejectedEmailToEmployee.loginOrg}}', 'Dear {{ExpensesApprovalRejectedEmailToEmployee.employeeUser}}\nWe are sorry to inform you ,your proposal for expenses submitted on {{ExpensesApprovalRejectedEmailToEmployee.submitForApprovalDate}}has been rejected.,\nExpense id : {{ExpensesApprovalRejectedEmailToEmployee.expenseId}}\nProject : {{ExpensesApprovalRejectedEmailToEmployee.projectName}}\nStart Date :{{ExpensesApprovalRejectedEmailToEmployee.startDate}}\nEnd Date :{{ExpensesApprovalRejectedEmailToEmployee.endDate}}\nAmount : {{ExpensesApprovalRejectedEmailToEmployee.amount}}\n ALL THE BEST','N', 'SLADMIN', 'slead', 'sleadIN');

#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ExpensesApprovalManagerEmailToEmployee', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesApprovalRejectedEmailToEmployee', 'Notification', 'ExpensesApprovalRejectedEmailToEmployee', 'Used for Manager Rejected  to Employee Expenses.', 'Expense has been rejected for the {{ExpensesApprovalRejectedEmailToEmployee.projectName}}.', 'sourcelead', '', 'Approval request on {{ExpensesApprovalRejectedEmailToEmployee.projectName}} Expense.','N','SLADMIN', 'com', 'in.com');

#4573

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesReopenEmailToSuperAdmin','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerUser', '{{ExpensesReopenEmailToSuperAdmin.managerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'superUser', '{{ExpensesReopenEmailToSuperAdmin.superUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ExpensesReopenEmailToSuperAdmin.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ExpensesReopenEmailToSuperAdmin.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ExpensesReopenEmailToSuperAdmin.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'endDate', '{{ExpensesReopenEmailToSuperAdmin.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'amount', '{{ExpensesReopenEmailToSuperAdmin.amount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reasonForReopen', '{{ExpensesReopenEmailToSuperAdmin.reasonForReopen}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'accept', '{{ExpensesReopenEmailToSuperAdmin.accept}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reject', '{{ExpensesReopenEmailToSuperAdmin.reject}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginOrg', '{{ExpensesReopenEmailToSuperAdmin.loginOrg}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesReopenEmailToSuperAdmin','Email', 'ExpensesReopenEmailToSuperAdmin', 'Used for Manager Reopen  to Employee Expenses.', 'Request from{{ExpensesReopenEmailToSuperAdmin.managerUser}}.','org', 'Best Regards,\n{{ExpensesReopenEmailToSuperAdmin.managerUser}}\n{{ExpensesReopenEmailToSuperAdmin.loginOrg}}', 'Dear {{ExpensesReopenEmailToSuperAdmin.superUser}}\nRequest for editing the Expenses of {{ExpensesReopenEmailToSuperAdmin.employeeUser}}.,\nProject : {{ExpensesReopenEmailToSuperAdmin.projectName}}\nStart Date :{{ExpensesReopenEmailToSuperAdmin.startDate}}\nEnd Date :{{ExpensesReopenEmailToSuperAdmin.endDate}}\nAmount : {{ExpensesReopenEmailToSuperAdmin.amount}}\nReason for Reopen : {{ExpensesReopenEmailToSuperAdmin.reasonForReopen}}\nAccept : {{ExpensesReopenEmailToSuperAdmin.accept}}\nReject : {{ExpensesReopenEmailToSuperAdmin.reject}}\n    ALL THE BEST','N', 'SLADMIN', 'slead', 'sleadIN');



#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ExpensesReopenEmailToSuperAdmin', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesReopenEmailToSuperAdmin', 'Notification', 'ExpensesReopenEmailToSuperAdmin', 'Used for Manager Reopen  to Employee Expenses.', 'Expense has been rejected for the {{ExpensesApprovalRejectedEmailToEmployee.projectName}}.', 'sourcelead', '', 'Approval request on {{ExpensesApprovalRejectedEmailToEmployee.projectName}} Expense.','N','SLADMIN', 'com', 'in.com');
