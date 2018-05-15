#email to employee(expenses)

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToEmployee','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ReopenApprovedExpenseEmailToEmployee.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reopenManagerUser', '{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ReopenApprovedExpenseEmailToEmployee.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ReopenApprovedExpenseEmailToEmployee.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'endDate', '{{ReopenApprovedExpenseEmailToEmployee.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reopenReason', '{{ReopenApprovedExpenseEmailToEmployee.reopenReason}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ReopenApprovedExpenseEmailToEmployee','Email', 'ReopenApprovedExpenseEmailToEmployee', 'Used for Email To Employee while Re Open The Expense', '{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}  have re-opened your Expense {{ReopenApprovedExpenseEmailToEmployee.startDate}} to {{ExpensesApprovalManagerEmailToEmployee.endDate}}','org', 'Thanks & Regards\n{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}', 'Dear {{ExpensesApprovalManagerEmailToEmployee.employeeUser}},\nYour Expenses has been re-opened by  {{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}for the {{ReopenApprovedExpenseEmailToEmployee.projectName}}.,\nReason : {{ReopenApprovedExpenseEmailToEmployee.reopenReason}}\nPlease login to update.\n{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#email to approvers(expenses)
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToApprovers','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'approverUser', '{{ReopenApprovedExpenseEmailToApprovers.approverUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'reopenUser', '{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ReopenApprovedExpenseEmailToApprovers.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ReopenApprovedExpenseEmailToApprovers.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'clientName', '{{ReopenApprovedExpenseEmailToApprovers.clientName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'startDate', '{{ReopenApprovedExpenseEmailToApprovers.startDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'endDate', '{{ReopenApprovedExpenseEmailToApprovers.endDate}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'amount', '{{ReopenApprovedExpenseEmailToApprovers.amount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{ReopenApprovedExpenseEmailToApprovers.loginUrl}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ReopenApprovedExpenseEmailToApprovers','Email', 'ReopenApprovedExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense', '{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}  have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses {{ReopenApprovedExpenseEmailToApprovers.startDate}} to {{ReopenApprovedExpenseEmailToApprovers.endDate}}','org', 'Thanks & Regards\n{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}', 'Dear {{ReopenApprovedExpenseEmailToApprovers.approverUser}},\n{{ReopenApprovedExpenseEmailToApprovers.reopenUser}} have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses for correction.\n Project Name : {{ReopenApprovedExpenseEmailToApprovers.projectName}}\n Client Name : {{ReopenApprovedExpenseEmailToApprovers.clientName}}\nStart Date : {{ReopenApprovedExpenseEmailToApprovers.startDate}}\nEnd Date : {{ReopenApprovedExpenseEmailToApprovers.endDate}}\nAmount : {{ReopenApprovedExpenseEmailToApprovers.amount}}\n{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToApprovers', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ReopenApprovedExpenseEmailToApprovers', 'Notification', 'ReopenApprovedExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense.','{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses.', 'sourcelead', '', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','N','SLADMIN', 'com', 'in.com');


