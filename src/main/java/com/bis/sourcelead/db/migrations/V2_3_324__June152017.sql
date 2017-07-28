#email to employee(expenses)

INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToEmployee','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeUser', '{{ReopenApprovedExpenseEmailToEmployee.employeeUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reopenManagerUser', '{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ReopenApprovedExpenseEmailToEmployee.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'startDate', '{{ReopenApprovedExpenseEmailToEmployee.startDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'endDate', '{{ReopenApprovedExpenseEmailToEmployee.endDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reopenReason', '{{ReopenApprovedExpenseEmailToEmployee.reopenReason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUrl', '{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ReopenApprovedExpenseEmailToEmployee','Email', 'ReopenApprovedExpenseEmailToEmployee', 'Used for Email To Employee while Re Open The Expense', '{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}  have re-opened your Expense {{ReopenApprovedExpenseEmailToEmployee.startDate}} to {{ExpensesApprovalManagerEmailToEmployee.endDate}}','org', 'Thanks & Regards\n{{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}', 'Dear {{ExpensesApprovalManagerEmailToEmployee.employeeUser}},\nYour Expenses has been re-opened by  {{ReopenApprovedExpenseEmailToEmployee.reopenManagerUser}}for the {{ReopenApprovedExpenseEmailToEmployee.projectName}}.,\nReason : {{ReopenApprovedExpenseEmailToEmployee.reopenReason}}\nPlease login to update.\n{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#email to approvers(expenses-accept)
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToApprovers','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverUser', '{{ReopenApprovedExpenseEmailToApprovers.approverUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reopenUser', '{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeUser', '{{ReopenApprovedExpenseEmailToApprovers.employeeUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ReopenApprovedExpenseEmailToApprovers.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientName', '{{ReopenApprovedExpenseEmailToApprovers.clientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'startDate', '{{ReopenApprovedExpenseEmailToApprovers.startDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'endDate', '{{ReopenApprovedExpenseEmailToApprovers.endDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'amount', '{{ReopenApprovedExpenseEmailToApprovers.amount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUrl', '{{ReopenApprovedExpenseEmailToApprovers.loginUrl}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ReopenApprovedExpenseEmailToApprovers','Email', 'ReopenApprovedExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense', '{{ReopenApprovedExpenseEmailToApprovers.reopenUser}}  have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses {{ReopenApprovedExpenseEmailToApprovers.startDate}} to {{ReopenApprovedExpenseEmailToApprovers.endDate}}','org', 'Thanks & Regards\n{{ReopenApprovedExpenseEmailToApprovers.approverUser}}', 'Dear {{ReopenApprovedExpenseEmailToApprovers.reopenUser}},\n{{ReopenApprovedExpenseEmailToApprovers.approverUser}} have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses for correction.\n Project Name : {{ReopenApprovedExpenseEmailToApprovers.projectName}}\n Client Name : {{ReopenApprovedExpenseEmailToApprovers.clientName}}\nStart Date : {{ReopenApprovedExpenseEmailToApprovers.startDate}}\nEnd Date : {{ReopenApprovedExpenseEmailToApprovers.endDate}}\nAmount : {{ReopenApprovedExpenseEmailToApprovers.amount}}\n{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToApprovers', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ReopenApprovedExpenseEmailToApprovers', 'Notification', 'ReopenApprovedExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense.','{{ReopenApprovedExpenseEmailToApprovers.reopenUser}} have reopened {{ReopenApprovedExpenseEmailToApprovers.employeeUser}} Expenses.', 'sourcelead', '', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

#email to approvers(expenses-reject)
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ReopenApprovedExpenseEmailToApprovers','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'approverUser', '{{ReopenRejectExpenseEmailToApprovers.approverUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reopenUser', '{{ReopenRejectExpenseEmailToApprovers.reopenUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeUser', '{{ReopenRejectExpenseEmailToApprovers.employeeUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ReopenRejectExpenseEmailToApprovers.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'clientName', '{{ReopenRejectExpenseEmailToApprovers.clientName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'startDate', '{{ReopenRejectExpenseEmailToApprovers.startDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'endDate', '{{ReopenRejectExpenseEmailToApprovers.endDate}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'amount', '{{ReopenRejectExpenseEmailToApprovers.amount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'reason', '{{ReopenRejectExpenseEmailToApprovers.reason}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUrl', '{{ReopenRejectExpenseEmailToApprovers.loginUrl}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ReopenRejectExpenseEmailToApprovers','Email', 'ReopenRejectExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense', '{{ReopenRejectExpenseEmailToApprovers.reopenUser}}  have rejected the reopen request of {{ReopenRejectExpenseEmailToApprovers.employeeUser}} Expenses {{ReopenRejectExpenseEmailToApprovers.startDate}} to {{ReopenRejectExpenseEmailToApprovers.endDate}}','org', 'Thanks & Regards\n{{ReopenRejectExpenseEmailToApprovers.reopenUser}}', 'Dear {{ReopenRejectExpenseEmailToApprovers.approverUser}},\n{{ReopenRejectExpenseEmailToApprovers.reopenUser}} have rejected the reopen request of {{ReopenRejectExpenseEmailToApprovers.employeeUser}} Expenses for correction.\n Project Name : {{ReopenRejectExpenseEmailToApprovers.projectName}}\n Client Name : {{ReopenRejectExpenseEmailToApprovers.clientName}}\nStart Date : {{ReopenRejectExpenseEmailToApprovers.startDate}}\nEnd Date : {{ReopenRejectExpenseEmailToApprovers.endDate}}\nAmount : {{ReopenRejectExpenseEmailToApprovers.amount}}\nReject Reason : {{ReopenRejectExpenseEmailToApprovers.reason}}\n{{ReopenApprovedExpenseEmailToEmployee.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');


#notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ReopenRejectExpenseEmailToApprovers', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ReopenRejectExpenseEmailToApprovers', 'Notification', 'ReopenRejectExpenseEmailToApprovers', 'Used for Email To Approver while Re Open The Expense.','{{ReopenRejectExpenseEmailToApprovers.reopenUser}}have rejected the reopen request of {{ReopenRejectExpenseEmailToApprovers.employeeUser}} Expenses.', 'sourcelead', '', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');


