# Expenses Submission
SET SQL_SAFE_UPDATES=0;
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ExpensesEmailToManager.managerUser}},\nI request you to approve my expenses which I have spend on the {{ExpensesEmailToManager.projectName}} due to {{ExpensesEmailToManager.purposeOfVisit}}.\nThe brief of details of the expenses are following \nNumber of Expenses : {{ExpensesEmailToManager.expenseTypes}}\nAmount : {{ExpensesEmailToManager.totalAmount}}\n\n{{ExpensesEmailToManager.loginLink}}\n' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesEmailToManager' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Approval request for expense spend on {{ExpensesEmailToManager.projectName}} project. '  WHERE `UNIQUE_SCENARIO_NAME`='ExpensesEmailToManager' and `ENTITY_TYPE`='Email';


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ExpensesEmailToManager' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{ExpensesEmailToManager.loginLink}}');

# Expenses Approved
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ExpensesApprovalManagerEmailToEmployee' and `ENTITY_TYPE` = 'Email'),
'frequencyType','{{ExpensesApprovalManagerEmailToEmployee.frequencyType}}');

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Expense has been approved for {{ExpensesApprovalManagerEmailToEmployee.frequencyType}}, {{ExpensesApprovalManagerEmailToEmployee.startDate}} to {{ExpensesApprovalManagerEmailToEmployee.endDate}}   '  WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalManagerEmailToEmployee' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Expense has been approved for {{ExpensesApprovalManagerEmailToEmployee.frequencyType}}, {{ExpensesApprovalManagerEmailToEmployee.startDate}} to {{ExpensesApprovalManagerEmailToEmployee.endDate}}   '  WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalManagerEmailToEmployee' and `ENTITY_TYPE`='Notification';

# Expense Rejected
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`) VALUES ((SELECT MAX( ID ) FROM `ENTITY_TYPE`), 'rejectedReason', '{{ExpensesApprovalRejectedEmailToEmployee.rejectedReason}}');

INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE` = 'Email'),
'frequencyType','{{ExpensesApprovalRejectedEmailToEmployee.frequencyType}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{ExpensesApprovalRejectedEmailToEmployee.employeeUser}}\nWe regret to inform you, your proposal for expenses submitted on {{ExpensesApprovalRejectedEmailToEmployee.submitForApprovalDate}} has been rejected.\nExpense id : {{ExpensesApprovalRejectedEmailToEmployee.expenseId}}\nProject : {{ExpensesApprovalRejectedEmailToEmployee.projectName}}\nStart Date :{{ExpensesApprovalRejectedEmailToEmployee.startDate}}\nEnd Date :{{ExpensesApprovalRejectedEmailToEmployee.endDate}}\nAmount : {{ExpensesApprovalRejectedEmailToEmployee.amount}}\nReason : {{ExpensesApprovalRejectedEmailToEmployee.rejectedReason}} \n\nALL THE BEST' WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Expense has been rejected for {{ExpensesApprovalRejectedEmailToEmployee.frequencyType}}, {{ExpensesApprovalRejectedEmailToEmployee.startDate}} to {{ExpensesApprovalRejectedEmailToEmployee.endDate}}   '  WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `BODY`='Expense has been rejected for {{ExpensesApprovalRejectedEmailToEmployee.frequencyType}}, {{ExpensesApprovalRejectedEmailToEmployee.startDate}} to {{ExpensesApprovalRejectedEmailToEmployee.endDate}}   '  WHERE `UNIQUE_SCENARIO_NAME`='ExpensesApprovalRejectedEmailToEmployee' and `ENTITY_TYPE`='Notification';




# Reopen request
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'ReopenTimeSheet' and `ENTITY_TYPE` = 'Email'),
'loginLink','{{ReopenTimeSheet.loginLink}}');

UPDATE `EMAIL_SCENARIO` SET `BODY`='Re open Time cart for {{ReopenTimeSheet.employeeName}}, {{ReopenTimeSheet.projectName}}    '  WHERE `UNIQUE_SCENARIO_NAME`='ReopenTimeSheet' and `ENTITY_TYPE`='Notification';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Request to Reopen Time cart for {{ReopenTimeSheet.employeeName}}   '  WHERE `UNIQUE_SCENARIO_NAME`='ReopenTimeSheet' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `BODY`=' Dear {{ReopenTimeSheet.superUserName}},\n\n I would like to Request you to re open the time chart for {{ReopenTimeSheet.employeeName}}.\n Project Name : {{ReopenTimeSheet.projectName}}\n Frequency : {{ReopenTimeSheet.frequencyType}}, {{ReopenTimeSheet.frequencyPeriod}}\n Total Hours : {{ReopenTimeSheet.totalHours}}\n Reason for Reopen : {{ReopenTimeSheet.reason}}\n Click on the following link for accepting the request and allow  to edit the timesheet.\n {{ReopenTimeSheet.acceptURL}} {{ReopenTimeSheet.rejectURL}} {{ReopenTimeSheet.loginLink}}     ' WHERE `UNIQUE_SCENARIO_NAME`='ReopenTimeSheet' and `ENTITY_TYPE`='Email';


# ReOpen Request accepted
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{AcceptReopenTimeSheet.managerName}},\n\n{{ReopenTimeSheet.employeeName}} Time cart has been re opened for you to modify.\nPlease Login into the SourceLead to udate\n\n{{AcceptReopenTimeSheet.loginURL}}' WHERE `UNIQUE_SCENARIO_NAME`='AcceptReopenTimeSheet' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Request accepted for editing {{ReopenTimeSheet.employeeName}} Timesheet for {{AcceptReopenTimeSheet.ProjectName}}  '  WHERE `UNIQUE_SCENARIO_NAME`='AcceptReopenTimeSheet' and `ENTITY_TYPE`='Notification';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Time cart ReOpened for {{ReopenTimeSheet.employeeName}}    '  WHERE `UNIQUE_SCENARIO_NAME`='AcceptReopenTimeSheet' and `ENTITY_TYPE`='Email';


# ReOpen Request rejected
UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{RejectReopenTimeSheet.managerName}},\n\nYour request to Reopen the timesheet has been rejected.\nReject reason: {{RejectReopenTimeSheet.reason}}\nPlease Login into the SourceLead to find more details\n\n{{RejectReopenTimeSheet.loginURL}} ' WHERE `UNIQUE_SCENARIO_NAME`='RejectReopenTimeSheet' and `ENTITY_TYPE`='Email';

UPDATE `EMAIL_SCENARIO` SET `BODY`='Request rejected for editing {{RejectReopenTimeSheet.employeeName}} Timesheet for {{RejectReopenTimeSheet.projectName}}  '  WHERE `UNIQUE_SCENARIO_NAME`='RejectReopenTimeSheet' and `ENTITY_TYPE`='Notification';

UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Time cart ReOpen Request Denied for {{RejectReopenTimeSheet.employeeName}}    '  WHERE `UNIQUE_SCENARIO_NAME`='RejectReopenTimeSheet' and `ENTITY_TYPE`='Email';




# Timesheets Approved
INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'MailToTeamMembers' and `ENTITY_TYPE` = 'Email'),
'projectName','{{MailToTeamMembers.projectName}}');


INSERT INTO `CLASS_FIELD` (`ENTITY_TYPE_ID`, `FIELD_NAME`, `DELIM_STR`)
VALUES ((SELECT ID FROM `ENTITY_TYPE` where `ENTITY_NAME` = 'MailToTeamMembers' and `ENTITY_TYPE` = 'Email'),
'frequencyType','{{MailToTeamMembers.frequencyType}}');


UPDATE `EMAIL_SCENARIO` SET `BODY`='Dear {{MailToTeamMembers.EmployeeFirstName}} {{MailToTeamMembers.EmployeeLastName}},\n\nThe timesheet for {{MailToTeamMembers.projectName}} for the {{MailToTeamMembers.frequencyType}}, {{MailToManagerAtTimesheetSubmission.TimeFrequency}} has been approved by {{MailToTeamMembers.ManagerFirstName}} {{MailToTeamMembers.ManagerLastName}}    ' WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Approved By Approver' and `ENTITY_TYPE`='Email';


UPDATE `EMAIL_SCENARIO` SET `SUBJECT`='Timesheet-Approved  '  WHERE `UNIQUE_SCENARIO_NAME`='Timesheet Approved By Approver' and `ENTITY_TYPE`='Email';

SET SQL_SAFE_UPDATES=1;