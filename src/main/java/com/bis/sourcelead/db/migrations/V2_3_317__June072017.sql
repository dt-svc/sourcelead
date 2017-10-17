
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`,`ENTITY_TYPE`) VALUES ('ExpensesEmailToManager','Email');

INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'employeeUser', '{{ExpensesEmailToManager.employeeUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'managerUser', '{{ExpensesEmailToManager.managerUser}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'projectName', '{{ExpensesEmailToManager.projectName}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'purposeOfVisit', '{{ExpensesEmailToManager.purposeOfVisit}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'expenseTypes', '{{ExpensesEmailToManager.expenseTypes}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'totalAmount', '{{ExpensesEmailToManager.totalAmount}}');
INSERT INTO `sourcelead`.`CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `sourcelead`.`ENTITY_TYPE`), 'loginUrl', '{{ExpensesEmailToManager.loginUrl}}');

INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES 
('ExpensesEmailToManager','Email', 'ExpensesEmailToManager', 'Used for Submitting Expenses to Manager.', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','org', 'Best Regards,\n{{ExpensesEmailToManager.employeeUser}}.', 'Dear {{ExpensesEmailToManager.managerUser}},\nI request you to approve my expenses which i have spent on the {{ExpensesEmailToManager.projectName}} due to {{ExpensesEmailToManager.purposeOfVisit}}.\nThe brief of details of the expenses are following \nNumber of Expenses : {{ExpensesEmailToManager.expenseTypes}}\nAmount : {{ExpensesEmailToManager.totalAmount}}\n{{ExpensesEmailToManager.loginUrl}}','N', 'SLADMIN', 'slead', 'sleadIN');



#notification
INSERT INTO `sourcelead`.`ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('ExpensesEmailToManager', 'Notification');
INSERT INTO `sourcelead`.`EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`) VALUES
('ExpensesEmailToManager', 'Notification', 'ExpensesEmailToManager', 'Used for Submitting Expenses to Manager.', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.', 'sourcelead', '', 'Approval request on {{ExpensesEmailToManager.projectName}} Expense.','N','SLADMIN', 'sourceLead.com', 'in.sourceLead.com');

ALTER TABLE `sourcelead`.`EXPENSES_APPROVER_HIERARCHY` DROP COLUMN `ACTION_PERFORMEDBY`;

