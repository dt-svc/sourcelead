#As a employee i can receive email when the manager/admin submitted my expenses behalf of me.Acceptance Criteria
#entity_type
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToEmployeeWhenManagerSubmitExpense', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToEmployeeWhenManagerSubmitExpense','Email', 'MailToEmployeeWhenManagerSubmitExpense', 'The mail need to be forward to employee when the expenses of employees are submitted by manager/admin', 'Your expenses got submitted by [Login_User]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Employee_Name],\nYour Expenses got submitted by [Login_User]  for [Project_Name].\nPurpose : [Purpose]\nTotal Amount : [Submitted_Amount]\n\n For brief details click on following link[Expenses_Submitted]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'totalAmount', '[Submitted_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expensesSubmitted', '[Expenses_Submitted]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'purpose', '[Purpose]');


#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NotificationToEmployeeWhenManagerSubmitExpense', 'Notification', 'MailToEmployeeWhenManagerSubmitExpense', 'The notification need to be forward to employee when the expenses of employees are submitted by manager/admin', ' Your expenses got submitted by [Login_User]', 'org', '', '','N','SLADMIN', 'com', 'in.com', 23,1,'Y');



#Mail when Approver 1 approves the Expenses & forward to the Approver 2

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToApprover2WhenApprover1ApprovesTheExpense', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToApprover2WhenApprover1ApprovesTheExpense','Email', 'MailToApprover2WhenApprover1ApprovesTheExpense', 'Mail when Approver 1 approves the Expenses ,forward to the Approver 2', ' Approve Expenses of [Employee_Name] for [Project_Name]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Manager_Two],\n This is to notify that expenses of Employee [Employee_Name] need to get final approval.

 \nProject : [Project_Name]\nFrequency : [Expense_StartDate]to[Expense_EndDate]\nApprover 1 :[Login_User]\nClaimed Amount :[Approved_Amount]\n[Approve_Expenses]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approved_Amount', '[Approved_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approve_Expenses', '[Approve_Expenses]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_StartDate', '[Expense_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_EndDate', '[Expense_EndDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'manager_Two', '[Manager_Two]');

#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NotificationToApprover2WhenApprover1ApprovesTheExpense', 'Notification', 'MailToApprover2WhenApprover1ApprovesTheExpense', 'Notification when Approver 1 approves the Expenses,  forward to the Approver 2', ' Approve Expenses of [Employee_Name] for [Expense_StartDate]to[Expense_EndDate]', 'org', '', '','N','SLADMIN', 'com', 'in.com', 23,1,'Y');


#Reject mail for managers in expenses


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToManager1WhenManager2orAdminRejectTheExpense', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToManager1WhenManager2orAdminRejectTheExpense','Email', 'MailToManager1WhenManager2orAdminRejectTheExpense', 'When manager 2 or admin reject expense mail to remaining managers', '[Employee_Name] expenses got rejected by [Login_User].','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Manager_Name],\n[Login_User] Rejected the Expenses of [Employee_Name].\nProject Name : [Project_Name]\nPurpose : [Purpose]\nFrequency : [Expense_StartDate]to[Expense_EndDate]\nAmount : [Approved_Amount]\nReject Reason :[Reject_Reason]\n\n For brief details click on following link[Rejected_ExpensesLink]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginURL', '[Rejected_ExpensesLink]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'purpose', '[Purpose]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_StartDate', '[Expense_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_EndDate', '[Expense_EndDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approved_Amount', '[Approved_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'manager_Name', '[Manager_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'reject_Reason', '[Reject_Reason]');


#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('MailToManager1WhenManager2orAdminRejectTheExpense', 'Notification', 'MailToManager1WhenManager2orAdminRejectTheExpense', 'The notification need to be forward When manager 2 or admin reject expense notification to remaining managers', ' [Employee_Name] expenses got rejected by [Login_User]', 'org', '', '','N','SLADMIN', 'com', 'in.com', 23,1,'Y');


# manager mail when admin/manager2 approves the expenses


INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToManager1WhenManager2orAdminApprovesTheExpense', 'Email');

INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES 
('MailToManager1WhenManager2orAdminApprovesTheExpense','Email', 'MailToManager1WhenManager2orAdminApprovesTheExpense', 'When manager 2 or admin approves expense notification to remaining managers', '[Employee_Name] expenses got approved by [Login_User]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Manager_Name],\n[Login_User] approved the Expenses of [Employee_Name].\nProject Name : [Project_Name]\nPurpose : [Purpose]\nFrequency : [Expense_StartDate]to[Expense_EndDate]\nAmount : [Approved_Amount]\n\n For brief details click on following link[Approved_ExpensesLink]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginURL', '[Approved_ExpensesLink]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'purpose', '[Purpose]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_StartDate', '[Expense_StartDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'expense_EndDate', '[Expense_EndDate]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approved_Amount', '[Approved_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'manager_Name', '[Manager_Name]');


#Notification
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('MailToManager1WhenManager2OrAdminApprovesTheExpense', 'Notification', 'MailToManager1WhenManager2OrAdminApprovesTheExpense', 'The notification need to be forward When manager 2 or admin approves expense mail to remaining managers', ' [Employee_Name] expenses got approved by [Login_User]', 'org', '', '','N','SLADMIN', 'com', 'in.com', 23,1,'Y');


