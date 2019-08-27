
#When manager 1 submit expenses behalf of employee,mail to manager2and reporting manager.

SET SQL_SAFE_UPDATES = 0;

INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1SubmitsExpense', 'Email');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`, `CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`TAB_ID`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('MailToLevel2WhenLevel1SubmitsExpense','Email', 'MailToLevel2WhenLevel1SubmitsExpense', 'The mail need to be forward to manager2 and reporting manager when manager1 submits the expense on behalf of employee', '[Login_User] has submitted expenses on behalf of [Employee_Name]','org', 'Best Regards,\n[Login_User]\n[Login_Org]', 'Dear [Approver],\n[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].\nPurpose : [Purpose]\nExpenseId :[Expense_Id]\n Amount : [Submitted_Amount]\n\n For brief details click on following link[Expenses_Submitted]','N', 'SLADMIN', 'slead', 'sleadIN', 23,1,'Y');

INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'projectName', '[Project_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'amount', '[Submitted_Amount]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginOrg', '[Login_Org]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'loginURL', '[Expenses_Submitted]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'purpose', '[Purpose]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'approver', '[Approver]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO`), 'Expense_Id', '[Expense_Id]');
#Notification
INSERT INTO `ENTITY_TYPE` (`ENTITY_NAME`, `ENTITY_TYPE`) VALUES ('MailToLevel2WhenLevel1SubmitsExpense', 'Notification');
INSERT INTO `EMAIL_SCENARIO` (`SCENARIO_NAME`, `ENTITY_TYPE`, `UNIQUE_SCENARIO_NAME`, `DESCRIPTION`, `SUBJECT`, `TEMPLATE_TYPE`, `SIGNATURE`, `BODY`,`IS_TEMPLATE_MODIFIED`,`CREATED_BY`, `ORG_CODE`,`LOCATION_CODE`,`STATUS`,`DEFAULT_TEMPLATE`) VALUES
('NotificationToLevel2WhenLevel1SubmitsExpense', 'Notification', 'MailToLevel2WhenLevel1SubmitsExpense', 'The notification need to be forward to manager2 and reporting manager when manager1 submits the expense on behalf of employee', '[Login_User] has submitted expenses on behalf of [Employee_Name]', 'org', '', '[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].','N','SLADMIN', 'slead', 'sleadIN',1,'Y');

#NOTIFICATION

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear [Manager_Name],\n[Login_User] Rejected the Expenses of [Employee_Name] for the Project [Project_Name]' WHERE `UNIQUE_SCENARIO_NAME`='MailToManager1WhenManager2orAdminRejectTheExpense' and `ENTITY_TYPE`='Notification';
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT MAX( ID ) FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND `ENTITY_TYPE` = 'Email'), 'loginURL', '[Login_Link]');

#=========================================================================================================================

# inserting expenseId


#When emp submit expense mail to managers--ok11
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Manager_User],\nI request you to approve my expenses which I have spent on the project, [Project_Name] due to [Purpose_Of_Visit].
\nPlease find the expense details below:\nNumber of Expenses : [Expense_Types]\nExpenseId :[Expense_Id]\nAmount : [Total_Amount]\n[Login_Link]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ExpensesEmailToManager';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Approval request for Expense spent on project [Project_Name] ' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesEmailToManager' and `ENTITY_TYPE`='Notification';


#when manager submits expense behalf of emp mail to empl--dup6--ok22
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Employee_Name],\nYour Expenses got submitted by [Login_User] for [Project_Name].\nPurpose : [Purpose]\nExpenseId :[Expense_Id]\nTotal Amount : [Submitted_Amount]
\nFor brief details click on following link[Expenses_Submitted]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'MailToEmployeeWhenManagerSubmitExpense';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Your expenses got submitted by [Login_User]' WHERE `UNIQUE_SCENARIO_NAME`='MailToEmployeeWhenManagerSubmitExpense' and `ENTITY_TYPE`='Notification';

#when manager1 submits emp expense mail to level 2
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Approver],\n[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].\nPurpose : [Purpose]\nExpenseId :[Expense_Id]\n Amount : [Submitted_Amount]\n\n For brief details click on following link[Expenses_Submitted]' WHERE ENTITY_TYPE = 'Email' and UNIQUE_SCENARIO_NAME = 'MailToLevel2WhenLevel1SubmitsExpense';
UPDATE `EMAIL_SCENARIO` SET `BODY`='[Employee_Name] expenses got submitted by [Login_User]' WHERE `UNIQUE_SCENARIO_NAME`='MailToLevel2WhenLevel1SubmitsExpense' and `ENTITY_TYPE`='Notification';

#========================


#when manager1 accept expense mail to manager2--- dup6---ok43
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Approver],\n[Login_User] has submitted Expenses on behalf of [Employee_Name] for the [Project_Name].\nPurpose : [Purpose]\nExpenseId :[Expense_Id]\n Amount : [Submitted_Amount]\n\n For brief details click on following link[Expenses_Submitted]' WHERE ENTITY_TYPE = 'Email' and UNIQUE_SCENARIO_NAME = 'MailToApprover2WhenApprover1ApprovesTheExpense';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Approve Expenses of [Employee_Name] for [Expense_StartDate]to[Expense_EndDate]' WHERE `UNIQUE_SCENARIO_NAME`='MailToApprover2WhenApprover1ApprovesTheExpense' and `ENTITY_TYPE`='Notification';

#when manager2 accept expense mail to manager1---ok54
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Manager_Name],\n[Login_User] approved the Expenses of [Employee_Name].\nProject Name : [Project_Name]\nPurpose : [Purpose]\nFrequency : [Expense_StartDate]to[Expense_EndDate]
\nExpenseId :[Expense_Id]\nAmount : [Approved_Amount]\nFor brief details click on following link[Approved_ExpensesLink]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminApprovesTheExpense';
UPDATE `EMAIL_SCENARIO` SET `BODY`='[Employee_Name] expenses got approved by [Login_User]' WHERE `UNIQUE_SCENARIO_NAME`='MailToManager1WhenManager2orAdminApprovesTheExpense' and `ENTITY_TYPE`='Notification';

#When manager2 approve expense mail to emplyee
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Employee_User],
We are pleased to inform you that your proposal for expenses submitted on [Submit_For_Approval_Date] has been approved.
Expense id : [Expense_Id]
Project : [Project_Name]
Start Date : [Start_Date]
End Date : [End_Date]
Subitted Amount : [Amount]
Approved Amount: [Approved_Amount]\nFor brief details click on following link[Login_Link]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalManagerEmailToEmployee';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Your expenses got approved ' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalManagerEmailToEmployee' and `ENTITY_TYPE`='Notification';

#=================================================

#when manager2 rejects expense mail to manager1---ok65
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Manager_Name],\n[Login_User] Rejected the Expenses of [Employee_Name].\nProject Name : [Project_Name]\nPurpose : [Purpose]\nFrequency : [Expense_StartDate]to[Expense_EndDate]
ExpenseId :[Expense_Id]\nAmount : [Approved_Amount]\nReject Reason :[Reject_Reason]\nFor brief details click on following link[Rejected_ExpensesLink]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminRejectTheExpense';
UPDATE `EMAIL_SCENARIO` SET `BODY`='[Employee_Name] expenses got rejected by [Login_User]' WHERE `UNIQUE_SCENARIO_NAME`='MailToManager1WhenManager2orAdminRejectTheExpense' and `ENTITY_TYPE`='Notification';

#when manager2 rejects expense mail to emp--ok7 6
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Employee_User],
We are sorry to inform you that your proposal for expenses submitted on [Submit_For_Approval_Date] has been rejected.
Expense id : [Expense_Id]
Project : [Project_Name]
Start Date :[Start_Date]
End Date :[End_Date]
Rejectted Reason : [Rejectted_Reason]
Amount : [Amount]\nFor brief details click on following link[Login_Link]' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Email';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Expense has been rejected for [Frequency_Type], [Start_Date] to [End_Date] for an employee' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Notification';

#when manager1 rejects expense mail to emp---ok87
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Employee_User],\nWe are sorry to inform you that your proposal for expenses submitted on [Submit_For_Approval_Date] has been rejected.
\nExpense id : [Expense_Id]\nProject : [Project_Name]\nStart Date :[Start_Date]\nEnd Date :[End_Date]\nAmount : [Amount]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Expense has been rejected for [Frequency_Type], [Start_Date] to [End_Date] for an employee' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Notification';


#===========================================================================

#when manager1 reopen request mail to manager2---0k98
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Approver_User],\n[Reopen_User] has reopened [Employee_User] Expenses for correction.\nProject Name : [Project_Name]
\nStart Date : [Start_Date]\nEnd Date : [End_Date]\nExpenseID : [Expense_Id] \nAmount : [Amount]\nPlease login and update:\n[Login_Url]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ExpensesReopenEmailToSuperAdmin';
#===========================================================================
#when manager2 accept mail to manager1--ok109
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Approver_User],\n[Reopen_User] has reopened [Employee_User] Expenses for correction.\nProject Name : [Project_Name]
Start Date : [Start_Date]\nEnd Date : [End_Date]\nAmount : [Amount]\nExpenseId:[Expense_Id] \nPlease login and update:\n[Login_Url]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ReopenApprovedExpenseEmailToApprovers';
UPDATE `EMAIL_SCENARIO` SET `BODY`='Your re-open request is accepted for the project [Project_Name].' WHERE `UNIQUE_SCENARIO_NAME`='ReopenApprovedExpenseEmailToApprovers' and `ENTITY_TYPE`='Notification';

#when manager2 reject mail to manager1--ok109
UPDATE `EMAIL_SCENARIO` SET `BODY`= 'Dear [Approver_User],
[Reopen_User] has rejected the reopen request of [Employee_User] expenses for correction.
Project Name : [Project_Name]
Start Date : [Start_Date]
End Date : [End_Date]
Amount : [Amount]
ExpenseId :[Expense_Id]
Reject Reason : [Reason]
For more details, login to the system:
[Login_Url]' WHERE `ENTITY_TYPE` = 'Email' and `UNIQUE_SCENARIO_NAME` = 'ReopenRejectExpenseEmailToApprovers' ;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Your re-open request is rejected for the project [Project_Name].' WHERE `UNIQUE_SCENARIO_NAME`='ReopenRejectExpenseEmailToApprovers' and `ENTITY_TYPE`='Notification';



INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesEmailToManager' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToEmployeeWhenManagerSubmitExpense' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToApprover2WhenApprover1ApprovesTheExpense' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToApprover2WhenApprover1ApprovesTheExpense' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'LoginURL', '[Login_Link]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalManagerEmailToEmployee' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'LoginURL', '[Login_Link]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'LoginURL', '[Login_Link]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'RejecttedReason', '[Rejectted_Reason]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminApprovesTheExpense' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminRejectTheExpense' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ExpensesReopenEmailToSuperAdmin' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ReopenApprovedExpenseEmailToApprovers' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'ReopenRejectExpenseEmailToApprovers' AND ENTITY_TYPE = 'Email' AND `ORG_LOCATION_ID` IS NULL), 'Expense_Id', '[Expense_Id]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminApprovesTheExpense' AND `ENTITY_TYPE`='Notification' AND `ORG_LOCATION_ID` IS NULL), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminApprovesTheExpense' AND `ENTITY_TYPE`='Notification' AND `ORG_LOCATION_ID` IS NULL), 'loginUser', '[Login_User]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminRejectTheExpense' AND `ENTITY_TYPE`='Notification' AND `ORG_LOCATION_ID` IS NULL), 'employeeName', '[Employee_Name]');
INSERT INTO `CLASS_FIELD` (`EMAIL_SCENARIO_ID`, `FIELD_NAME`, `DELIMITER_STR`) VALUES ((SELECT ID FROM `EMAIL_SCENARIO` WHERE `UNIQUE_SCENARIO_NAME` = 'MailToManager1WhenManager2orAdminRejectTheExpense' AND `ENTITY_TYPE`='Notification' AND `ORG_LOCATION_ID` IS NULL), 'loginUser', '[Login_User]');

SET SQL_SAFE_UPDATES=1;