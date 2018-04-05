
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesEmailToManager','Email');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'employeeUser', '{{ExpensesEmailToManager.employeeUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'managerUser', '{{ExpensesEmailToManager.managerUser}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'projectName', '{{ExpensesEmailToManager.projectName}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'purposeOfVisit', '{{ExpensesEmailToManager.purposeOfVisit}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'expenseTypes', '{{ExpensesEmailToManager.expenseTypes}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'totalAmount', '{{ExpensesEmailToManager.totalAmount}}');
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'loginUrl', '{{ExpensesEmailToManager.loginUrl}}');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesEmailToManager','Email', 'ExpensesEmailToManager', 'Used for Submitting Expenses to Manager.', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','org', 'Best Regards,\n{{ExpensesEmailToManager.employeeUser}}.', 'Dear {{ExpensesEmailToManager.managerUser}},\nI request you to approve my expenses which i have spend on the {{ExpensesEmailToManager.projectName}} due to {{ExpensesEmailToManager.purposeOfVisit}}.\nThe brief of details of the expenses are following \nNumber of Expenses : {{ExpensesEmailToManager.expenseTypes}}\nAmount : {{ExpensesEmailToManager.totalAmount}}\n{{ExpensesEmailToManager.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');



#notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ExpensesEmailToManager', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesEmailToManager', 'Notification', 'ExpensesEmailToManager', 'Used for Submitting Expenses to Manager.', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.', 'sourcelead', '', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','N','SLADMIN', 'com', 'in.com');

ALTER TABLE `EXPENSES_APPROVER_HIERARCHY` DROP COLUMN `ACTION_PERFORMEDBY`;

